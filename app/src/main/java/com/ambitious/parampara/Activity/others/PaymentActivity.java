package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetProfile_Response;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView img_back, img_cc, img_cash;
    CardView cv_cc, cc_cash;
    Button btn_pay;
    String str_at_location, str_txt_selectDate, str_txt_fromtime, str_txt_untilltime, str_grand_total, str_edit_city, str_edit_pincode, uid, str_cart_ID;
    GetProfile_Response successResponse;
    String paymentmode = "";
    String checkSum = "";
    String orderID, customerID, PayAmount = "1";
    Context mContext;
    TextView txt_tot_amount;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    String str_uid, str_Poojaname, trasID, str_poojaID, str_date, str_time, str_address, str_landmark, str_street, str_pincode, str_amount, str_samagri, str_username;
    private String longitude;
    private String latitude;
    StringBuilder sb;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        findID();

        if (getIntent().getExtras() != null) {

            /* intent.putExtra("Poojaname",str_poojaname);
            intent.putExtra("poojaID",str_poojaID);
            intent.putExtra("date",txt_bookingDate.getText().toString());
            intent.putExtra("time",txt_time.getText().toString());
            intent.putExtra("address",edit_addres.getText().toString());
            intent.putExtra("landmark",edit_lanmark.getText().toString());
            intent.putExtra("street",edit_street.getText().toString());
            intent.putExtra("pincode",edit_pincode.getText().toString());
            intent.putExtra("amount",txt_amount.getText().toString());
            intent.putExtra("samagri",str_samagri);*/

            str_Poojaname = getIntent().getExtras().getString("Poojaname");
            str_poojaID = getIntent().getExtras().getString("poojaID");
            str_date = getIntent().getExtras().getString("date");
            str_time = getIntent().getExtras().getString("time");
            str_address = getIntent().getExtras().getString("address");
            str_landmark = getIntent().getExtras().getString("landmark");
            str_street = getIntent().getExtras().getString("street");
            str_pincode = getIntent().getExtras().getString("pincode");
            str_amount = getIntent().getExtras().getString("amount");
            str_samagri = getIntent().getExtras().getString("samagri");
            latitude = getIntent().getExtras().getString("latitude");
            longitude = getIntent().getExtras().getString("longitude");
            longitude = getIntent().getExtras().getString("longitude");
            // Log.d("here is username",str_username);

        }


        str_uid = getData(getApplicationContext(), "userID", null);
        GetProfile(str_uid);
        txt_tot_amount.setText(str_amount);


        final Random random = new Random();
        sb = new StringBuilder(8);
        for (int i = 0; i < 10; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        trasID = String.valueOf(sb);

    }

    private void GetProfile(String user_id) {
        Call<ResponseBody> call = AppConfig.loadInterface().GetProfile(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("GetProfile" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            GetProfile_Response successResponse = gson.fromJson(responedata, GetProfile_Response.class);
                            str_username = successResponse.getResult().getUsername();


                            try {
                                //Picasso.with(Nav_profileActivity.this).load(successResponse.getResult().getImage()).error(R.drawable.ut).into(set_profile_image);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

                Toast.makeText(PaymentActivity.this, "Please check connection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    private void findID() {
        img_back = findViewById(R.id.img_back);

        img_cash = findViewById(R.id.img_cash);
        img_cc = findViewById(R.id.img_cc);

        cv_cc = findViewById(R.id.cv_cc);
        cc_cash = findViewById(R.id.cc_cash);
        btn_pay = findViewById(R.id.btn_pay);
        txt_tot_amount = findViewById(R.id.txt_tot_amount);

        img_back.setOnClickListener(this);

        cv_cc.setOnClickListener(this);
        cc_cash.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        } else if (v == cv_cc) {

            img_cash.setVisibility(View.GONE);
            img_cc.setVisibility(View.VISIBLE);
            paymentmode = "paytm";
        } else if (v == cc_cash) {

            img_cash.setVisibility(View.VISIBLE);
            img_cc.setVisibility(View.GONE);
            paymentmode = "cash";
        } else if (v == btn_pay) {
            if (paymentmode.equalsIgnoreCase("")) {
                Toast.makeText(this, "Please Select Payment", Toast.LENGTH_SHORT).show();
            } else {
                if (paymentmode.equals("cash")) {
                    bookEvent(str_date, str_time, str_address,
                            str_landmark, str_street, str_pincode, str_amount, str_samagri, "cash", latitude, longitude);

                } else {
                    // generateCheckSum();
                    Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void bookEvent(String date, String time, String flat, String landmark, String colony, String pin, String price, String samagri, String payment_type, String latitude, String longitude) {
        progressDialog = new ProgressDialog(PaymentActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();


        Call<ResponseBody> call;
        call = AppConfig.loadInterface().sendrequest(str_poojaID, str_Poojaname,
                str_uid, str_username, date, time, flat, landmark, colony, pin,
                price, samagri, "pending", "" +
                        latitude, "" + longitude, "" +
                        payment_type, trasID);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("bookEvent>>" + object);
                        if (error.equals("1")) {
                            intent();
                            Animatoo.animateSlideLeft(PaymentActivity.this);
                            Toast.makeText(PaymentActivity.this, "Request Sent by:" + str_username, Toast.LENGTH_SHORT).show();
                            finish();
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
                Toast.makeText(PaymentActivity.this, "Please check connection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void intent() {
        Intent intent = new Intent(PaymentActivity.this, BookingSuccesActivity.class);
        intent.putExtra("tranid", trasID);
        intent.putExtra("check", "pooja");
        startActivity(intent);
        finish();
    }
}