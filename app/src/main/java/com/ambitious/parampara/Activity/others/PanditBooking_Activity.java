package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;

import com.ambitious.parampara.Adapter.PanditList_Adapter;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.Pooja_Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PanditBooking_Activity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rec_panditbooking;
    private ImageView img_back;
    private TextView txt_headername;
    RecyclerView.Adapter adapter = null;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandit_booking);
        findID();
        getPoojacall();
    }

    private void findID() {
        rec_panditbooking = findViewById(R.id.rec_panditbooking);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Pandit Booking");
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }

    private void getPoojacall() {
        progressDialog = new ProgressDialog(PanditBooking_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().get_pooja();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("Ratecard__" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            Pooja_Response successResponse = gson.fromJson(responedata, Pooja_Response.class);
                            rec_panditbooking.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(PanditBooking_Activity.this);
                            rec_panditbooking.setLayoutManager(mLayoutManager1);
                            rec_panditbooking.setItemAnimator(new DefaultItemAnimator());
                            PanditList_Adapter adapter = new PanditList_Adapter(PanditBooking_Activity.this, successResponse.getResult());
                            rec_panditbooking.setAdapter(adapter);
                        } else {

                            //    Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Snackbar.make(parentView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(PanditBooking_Activity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
