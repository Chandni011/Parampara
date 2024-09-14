package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

public class DailyPandit_Activity extends AppCompatActivity implements View.OnClickListener {


    private ImageView img_back, forOfficeImg, forHomeImg;
    private TextView txt_headername;
    CardView forOfficeCardView, forHomeCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pandit);
        findID();

    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Daily Pandit");

        forOfficeCardView = findViewById(R.id.for_office_card_view);
        forHomeCardView = findViewById(R.id.for_home_card_view);
        forOfficeImg = findViewById(R.id.for_office_img);
        forHomeImg = findViewById(R.id.for_home_img);


        Picasso.get()
                .load("https://occupa.co.uk/wp-content/uploads/2020/01/Affordable-Office-to-rent-in-London.jpg")
                .error(R.drawable.sathiya)
                .placeholder(R.drawable.ic_processing)
                .into(forOfficeImg);

        Picasso.get()
                .load("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRwjcwe7J1cLritxZvAZti1Z-xqFfCh3clfmg&usqp=CAU")
                .error(R.drawable.sathiya)
                .placeholder(R.drawable.ic_processing)
                .into(forHomeImg);

        forOfficeCardView.setOnClickListener(this);
        forHomeCardView.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        } else if (v == forHomeCardView) {
            Intent i = new Intent(getApplicationContext(), DailyPanditListAct.class);
            i.putExtra("type", "home");
            startActivity(i);
        } else if (v == forOfficeCardView) {
            Intent i = new Intent(getApplicationContext(), DailyPanditListAct.class);
            i.putExtra("type", "office");
            startActivity(i);
        }
    }
}
