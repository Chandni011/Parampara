package com.ambitious.parampara.Activity.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ambitious.parampara.Activity.poojabooking.MyPooja_Booking;
import com.ambitious.parampara.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class OrderSuccessPayment extends AppCompatActivity {
    TextView receiptnumber, transaction_id;
    Button track;
    String s;
    RequestQueue requestQueue;
    FrameLayout linearLayout;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);
        if (getIntent().getExtras() != null) {

            s = getIntent().getExtras().getString("tranid");

        }

        Toast.makeText(getApplicationContext(),"Booking successfully done",Toast.LENGTH_SHORT).show();

        receiptnumber = findViewById(R.id.id);
        transaction_id = findViewById(R.id.thank);
        receiptnumber.setText(s);

        requestQueue = Volley.newRequestQueue(OrderSuccessPayment.this);
        linearLayout = findViewById(R.id.frame_payment_done);

        track = findViewById(R.id.next);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderSuccessPayment.this, MyOrders_items.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OrderSuccessPayment.this, MyPooja_Booking.class);
        i.putExtra("type", "Pooja");
        startActivity(i);
        finish();
        saveData(getApplicationContext(), "BookType", "Pooja");

    }
}