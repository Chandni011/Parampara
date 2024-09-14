package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class BookingReview_Activity extends AppCompatActivity {

    String str_Poojaname, str_poojaID, str_date, str_time, str_address, str_landmark, str_street, str_pincode, str_amount, str_samagri, str_username, str_number;
    TextView p_name, txt_time, p_date, p_time, p_samagri, flat, landmark, colony, pincode, display_payment, name, number;
    Button btn_book;
    private String latitude;
    private String longitude;
    private String ImagePooja;
    private ImageView img_pooja;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_review);
        findID();

        if (getIntent().getExtras() != null) {
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
            str_username = getIntent().getExtras().getString("username");
            str_number = getIntent().getExtras().getString("number");
            latitude = getIntent().getExtras().getString("latitude");
            longitude = getIntent().getExtras().getString("longitude");
            ImagePooja = getIntent().getExtras().getString("image");
            Log.d("here am i", str_date);
            settext();
        }
    }

    private void intent() {

        Intent intent = new Intent(BookingReview_Activity.this, PaymentActivity.class);
        intent.putExtra("Poojaname", str_Poojaname);
        intent.putExtra("poojaID", str_poojaID);
        intent.putExtra("date", str_date);
        intent.putExtra("time", str_time);
        intent.putExtra("address", str_address);
        intent.putExtra("landmark", str_landmark);
        intent.putExtra("street", str_street);
        intent.putExtra("pincode", str_pincode);
        intent.putExtra("amount", str_amount);
        intent.putExtra("samagri", str_samagri);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
        finish();
    }

    private void settext() {

        p_name.setText(str_Poojaname.toString().trim());
        p_date.setText("Date : " + str_date);
        txt_time.setText("Time : " + str_time);
        if (str_samagri != null) {
            if (str_samagri.equals("1")) {
                p_samagri.setText("With Samagri");
            } else {
                p_samagri.setText("Without Samagri");
            }
        }
        flat.setText(str_address + "," + str_street + "," + str_pincode);
        display_payment.setText(str_amount);

        String data = getData(BookingReview_Activity.this, "ldata", null);
        try {
            JSONObject jsonObject = new JSONObject(data);
            name.setText(jsonObject.getString("username"));
            number.setText(jsonObject.getString("mobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            String names = ImagePooja;
            String[] namesList = names.split(",");
            String name1 = namesList[0];


            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + name1)
                    .error(R.drawable.satyanarayanpuja)
                    .placeholder(R.drawable.ic_processing)
                    .into(img_pooja);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void findID() {

        p_name = findViewById(R.id.pname);
        txt_time = findViewById(R.id.txt_time);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        p_date = findViewById(R.id.pdate);
        p_samagri = findViewById(R.id.psamagri);
        flat = findViewById(R.id.flat);
        img_pooja = findViewById(R.id.img_pooja);
        btn_book = findViewById(R.id.book);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        display_payment = findViewById(R.id.payment_display);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent();
            }
        });
    }


}
