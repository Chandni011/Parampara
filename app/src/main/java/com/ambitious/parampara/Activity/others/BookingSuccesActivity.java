package com.ambitious.parampara.Activity.others;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ambitious.parampara.Activity.poojabooking.MyPooja_Booking;
import com.ambitious.parampara.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class BookingSuccesActivity extends AppCompatActivity {
    TextView receiptnumber, transaction_id;
    Button track;
    SharedPreferences sharedpref;
    String s, chcek, setcc;
    String plan_id, payment;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    JSONObject parameters;
    String status, uid, order_id, tracking_id, order_status, bank_ref_no, failure_message, payment_mode, status_message;
    String picode, quantiy, b_price, address_id, coupon_amt, coupon_id, payment_method;
    FrameLayout linearLayout;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_success);
        if (getIntent().getExtras() != null) {
            s = getIntent().getExtras().getString("tranid");
            chcek = getIntent().getExtras().getString("check", null);
        }
        Toast.makeText(getApplicationContext(),"Booking successfully done",Toast.LENGTH_SHORT).show();

        receiptnumber = findViewById(R.id.id);
        transaction_id = findViewById(R.id.thank);
        if (s != null) {
            receiptnumber.setText(s);
        }

        requestQueue = Volley.newRequestQueue(BookingSuccesActivity.this);
        linearLayout = findViewById(R.id.frame_payment_done);

        track = findViewById(R.id.next);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chcek != null) {
                    if (chcek.equals("pramars")) {
                        setcc = "Pramars";
                    } else if (chcek.equals("kundali")) {
                        setcc = "Kundali";
                    } else if (chcek.equals("bhajan")) {
                        setcc = "Bhajan";
                    } else if (chcek.equals("ayojan")) {
                        setcc = "Bhavya";
                    } else if (chcek.equals("daily")) {
                        setcc = "daily";
                    } else {
                        setcc = "Pooja";
                    }
                } else {
                    setcc = "Pooja";
                }

                saveData(getApplicationContext(), "BookType", setcc);

                if (chcek.equals("donation") || chcek.equals("daily")) {
                    Intent i = new Intent(BookingSuccesActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else if(chcek.equals("ayojan")){
                    Intent i = new Intent(BookingSuccesActivity.this, MyAyojan_booking.class);
                    startActivity(i);
                    finish();
                }else if(chcek.equals("bhajan")){
                    Intent i = new Intent(BookingSuccesActivity.this, MyMandal_bokking.class);
                    startActivity(i);
                    finish();
                }else if(chcek.equals("kundali")){
                    Intent i = new Intent(BookingSuccesActivity.this, MyKundali_booking.class);
                    startActivity(i);
                    finish();
                }else if(chcek.equals("pramars")){
                    Intent i = new Intent(BookingSuccesActivity.this, MyParamarsh_booking.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(BookingSuccesActivity.this, MyPooja_Booking.class);
                    i.putExtra("type", setcc);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(BookingSuccesActivity.this, MyPooja_Booking.class);
        startActivity(i);
        finish();
    }
}
