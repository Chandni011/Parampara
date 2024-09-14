package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
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
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Currency;
import java.util.Random;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class NewPaymentAct extends AppCompatActivity implements View.OnClickListener {
    ImageView img_back, img_cc, img_cash;
    CardView cv_cc, cc_cash;
    Button btn_pay;
    String paymentmode = "", lat, lng;
    TextView txt_tot_amount;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();
    private ProgressDialog progressDialog;

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    String pID, pName, TraID, userID, name, des, userName, entrydate, entrytime, pPrice, address, pincode, city, currentDate, type, mobile, email;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);
        userName = getData(getApplicationContext(), "userName", null);


        Intent extras = getIntent();
        if (extras != null) {
            pID = extras.getStringExtra("pID");
            pName = extras.getStringExtra("pName");
            userID = extras.getStringExtra("userID");
            name = extras.getStringExtra("name");
            mobile = extras.getStringExtra("mobile");
            email = extras.getStringExtra("email");
            entrydate = extras.getStringExtra("entrydate");
            entrytime = extras.getStringExtra("entrytime");
            pPrice = extras.getStringExtra("pPrice");
            address = extras.getStringExtra("address");
            pincode = extras.getStringExtra("pincode");
            city = extras.getStringExtra("city");
            currentDate = extras.getStringExtra("currentDate");
            type = extras.getStringExtra("type");
            des = extras.getStringExtra("des");
            lat = extras.getStringExtra("lat");
            lng = extras.getStringExtra("lng");
        }

        System.out.println(lat +".....lat");
        System.out.println(lng +".....lng");

        final Random random = new Random();
        sb = new StringBuilder(8);
        for (int i = 0; i < 10; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        TraID = String.valueOf(sb);

        findID();
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);

        img_cash = findViewById(R.id.img_cash);
        img_cc = findViewById(R.id.img_cc);

        cv_cc = findViewById(R.id.cv_cc);
        cc_cash = findViewById(R.id.cc_cash);
        btn_pay = findViewById(R.id.btn_pay);
        txt_tot_amount = findViewById(R.id.txt_tot_amount);


        txt_tot_amount.setText(symbol + " " + pPrice);


        img_back.setOnClickListener(this);

        cv_cc.setOnClickListener(this);
        cc_cash.setOnClickListener(this);
        btn_pay.setOnClickListener(this);
    }


    private void bhavyaAyojanApi() {
        progressDialog = new ProgressDialog(NewPaymentAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().submit_bhavya_ayojan(pID, pName, userID, name, entrydate,
                entrytime, pPrice, address, pincode, city, currentDate, paymentmode, TraID, lat, lng, "pending"
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "s");
                            Intent i = new Intent(NewPaymentAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "ayojan");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(NewPaymentAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(NewPaymentAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    /*private void donationApi() {
        System.out.println(userID + "....ss" + userName + ".......ll" + pName + "......pp" + des + "llll" + pPrice + "pp" +
                entrydate + "oooo" + paymentmode);
        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(NewPaymentAct.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().donation_pay(userID, userName, pName, des, pPrice, entrydate, paymentmode, TraID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "s");
                            Intent i = new Intent(NewPaymentAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "donation");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(NewPaymentAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(NewPaymentAct.this, "Please Check Internet Connection", "e");
            }
        });
    }*/


    private void bhajanMandal() {
        progressDialog = new ProgressDialog(NewPaymentAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().mandal_booked(pID, pName, userID, name, entrydate,
                entrytime, pPrice, address, pincode, city, currentDate, TraID, paymentmode, lat, lng, "pending");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "s");
                            Intent i = new Intent(NewPaymentAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "bhajan");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(NewPaymentAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(NewPaymentAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    private void DailyPandit() {
        progressDialog = new ProgressDialog(NewPaymentAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        System.out.println(pID);
        System.out.println(userID);
        System.out.println(paymentmode);
        System.out.println(pPrice);
        System.out.println(currentDate);
        System.out.println(TraID);
        Call<ResponseBody> call = AppConfig.loadInterface().pandit_booking(pID, userID, name, email, mobile, paymentmode,
                pPrice, currentDate, "0", TraID, address, lng, lat, pincode, entrydate, entrytime);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "s");
                            Intent i = new Intent(NewPaymentAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "daily");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(NewPaymentAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(NewPaymentAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(NewPaymentAct.this, "Please Check Internet Connection", "e");
            }
        });
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
                    if (userID != null) {
                        if (type.equals("Bhajan Mandal")) {
                            bhajanMandal();
                        } else if (type.equals("donation")) {
                            //donationApi();
                        } else if (type.equals("dailyPan")) {
                            DailyPandit();
                        } else {
                            bhavyaAyojanApi();
                        }
                    } else {
                        Intent i = new Intent(NewPaymentAct.this, BookingSuccesActivity.class);
                        i.putExtra("check", "pooja");
                        startActivity(i);
                        finish();
                    }

                    //placeOrderCall();
                    //Toast.makeText(this, "---->"+paymentmode, Toast.LENGTH_SHORT).show();
                } else {
                    // generateCheckSum();
                    Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
