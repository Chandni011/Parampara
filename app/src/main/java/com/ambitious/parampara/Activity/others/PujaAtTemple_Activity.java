package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.R;

public class PujaAtTemple_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_headername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puja_at_temple);
        findID();
    }
    private void findID() {

        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Puja at Temple");
        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }
}
