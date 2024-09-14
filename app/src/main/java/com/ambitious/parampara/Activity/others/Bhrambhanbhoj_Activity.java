package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.R;

public class Bhrambhanbhoj_Activity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_headername;
    private Button notif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        setContentView(R.layout.activity_bhrambhanbhoj);
        findID();
    }
    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Brahman Bhoj");
        img_back.setOnClickListener(this);
        final Dialog dialog = new Dialog(Bhrambhanbhoj_Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.coming_soon_dialog);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        TextView content = dialog.findViewById(R.id.text_msg);
        content.setText("We are going to start this service soon!");
        TextView title = dialog.findViewById(R.id.title);
        title.setVisibility(View.GONE);

        ((TextView) dialog.findViewById(R.id.btn_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bhrambhanbhoj_Activity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }
}
