package com.ambitious.parampara.Activity.others;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ambitious.parampara.Activity.poojabooking.MyPooja_Booking;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class PoojaDetails_Activity extends AppCompatActivity {
    CircleImageView image;
    TextView username, panditname, date, time, poojadate, usernamtxt, contacttxt, poojatime, address, experiancetxt, convi_freetxt, totalpricetxt, samagri, pooja_name, pooja_cost;
    String user, pdate, ptime, puja, psamagri, paddress, pcost, avg, experince, conveprice,
            order, bookingDate, bookingTime;
    Button myOrder, cancel_button;

    private ImageView mBackImg;
    private TextView mHeadernameTxt;
    private RelativeLayout mHeaderLay;
    private String status;
    private String id, city, mobilenum, pincode, addressss, lat, lng, userName;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pooja_details_);
        userName = getData(getApplicationContext(), "userName", null);
        mobilenum = getData(getApplicationContext(), "mob", null);


        findid();
        if (getIntent().getExtras() != null) {
            user = getIntent().getExtras().getString("user");
            puja = getIntent().getExtras().getString("puja");
            pdate = getIntent().getExtras().getString("date");
            ptime = getIntent().getExtras().getString("time");
            paddress = getIntent().getExtras().getString("address");
            psamagri = getIntent().getExtras().getString("samagri");
            pcost = getIntent().getExtras().getString("cost");
            avg = getIntent().getExtras().getString("avg");
            order = getIntent().getExtras().getString("orderid");
            status = getIntent().getExtras().getString("status");
            id = getIntent().getExtras().getString("orderid");
            bookingDate = getIntent().getExtras().getString("bookDate");
            bookingTime = getIntent().getExtras().getString("bookTime");
            experince = getIntent().getExtras().getString("experice");
            conveprice = getIntent().getExtras().getString("convePrice");


            if (status.equalsIgnoreCase("accepted")) {
                cancel_button.setVisibility(View.VISIBLE);
            }

            setText();
        }
    }

    private void setText() {
        panditname.setText("Pandit name : " + user);
        date.setText(bookingDate.substring(0, 10));
        time.setText("11:00");
        poojadate.setText(pdate);
        poojatime.setText(ptime);
        address.setText(paddress);

        if (psamagri.equals("Without Samagri")) {
            samagri.setText("NO");
        } else {
            samagri.setText("Yes");
        }
        pooja_name.setText(puja);
        pooja_cost.setText(pcost);
        totalpricetxt.setText(pcost);
        experiancetxt.setText(experince);
        usernamtxt.setText(userName);
        contacttxt.setText(mobilenum);
        convi_freetxt.setText(conveprice);
    }

    private void findid() {
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
        mHeadernameTxt.setText("Booking Details");
        mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
        image = findViewById(R.id.cv_userImage);
        username = findViewById(R.id.user_name);
        panditname = findViewById(R.id.panditname);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time_txt);
        poojadate = findViewById(R.id.pooja_date);
        poojatime = findViewById(R.id.poojatime);
        address = findViewById(R.id.address);
        convi_freetxt = findViewById(R.id.convi_free);
        usernamtxt = findViewById(R.id.user_nametxt);
        totalpricetxt = findViewById(R.id.total_pricetxt);
        samagri = findViewById(R.id.sma_price);
        contacttxt = findViewById(R.id.contact_num);
        myOrder = findViewById(R.id.my_order);
        experiancetxt = findViewById(R.id.expericnetxt);
        pooja_cost = findViewById(R.id.pooja_cost);
        pooja_name = findViewById(R.id.pooja_name);
        cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setVisibility(View.GONE);


        myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PoojaDetails_Activity.this, MyPooja_Booking.class).putExtra("type", "Pooja"));
                Animatoo.animateSlideLeft(PoojaDetails_Activity.this);
                saveData(getApplicationContext(), "BookType", "Pooja");
                finish();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(PoojaDetails_Activity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to Cancel")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                acceptcall(id, "Canceled By User");

                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });


    }

    private void acceptcall(String pooja_booking_id, String status) {

        progressDialog = new ProgressDialog(PoojaDetails_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().change_pooja_booking_status(pooja_booking_id, status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("accept" + object);
                        if (error.equals("1")) {
                            Toast.makeText(PoojaDetails_Activity.this, "Successfully Canceled", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PoojaDetails_Activity.this, "" + object.getString("result"), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PoojaDetails_Activity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
