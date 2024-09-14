package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Donation_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_headername, descption_txt;
    //EditText Note;
    Spinner selelctDonation;
    ArrayList<String> listArr = new ArrayList<>();
    ArrayList<String> listID = new ArrayList<>();
    ArrayList<String> discriptionArr = new ArrayList<>();
    String seletItem, selectedID, selectedDes;
    Button donationPaybtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation);

        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        donationPaybtn = findViewById(R.id.btn_payDonation);
        //amt = findViewById(R.id.edit_Txt);
        txt_headername.setText("Donation");

        selelctDonation = findViewById(R.id.donation_sppiner);
        //selelctDonation.getText().toString

        descption_txt = findViewById(R.id.descption_txt);
        //Note = findViewById(R.id.note);

        getDonation();
        img_back.setOnClickListener(this);
        donationPaybtn.setOnClickListener(this);
    }


    private void getDonation() {
        progressDialog = new ProgressDialog(Donation_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().get_donation();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        System.out.println(responedata + "......ss");
                        if (error.equals("1")) {
                            listID.clear();
                            listArr.clear();
                            discriptionArr.clear();
                            JSONArray jsonArr = object.getJSONArray("result");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                listArr.add(jsonObject.getString("donation_cause"));
                                listID.add(jsonObject.getString("donation_id"));
                                discriptionArr.add(jsonObject.getString("donation_discription"));
                                //descption_txt.setText(discriptionArr);
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listArr);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            selelctDonation.setAdapter(dataAdapter);
                            selelctDonation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String item = parent.getItemAtPosition(position).toString();
                                    seletItem = item;
                                    selectedID = listID.get(position);
                                    selectedDes = discriptionArr.get(position);
                                    descption_txt.setText(selectedDes);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //descption_txt.setText(discriptionArr.get(position));

                        } else {
                            utlity.toast(Donation_Activity.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(Donation_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(Donation_Activity.this, "Please Check Internet Connection", "e");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        } else if (v == donationPaybtn) {
                Intent i = new Intent(getApplicationContext(), DonationFromAct.class);
                i.putExtra("selectItem", seletItem);
                i.putExtra("selectedID", selectedID);
                i.putExtra("des", selectedDes);
                //i.putExtra("Note", Note.getText().toString().trim());
                startActivity(i);
        }
    }
}
