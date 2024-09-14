package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

public class PanditBooking_Desc extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, image_pooja;
    private TextView txt_headername, txt_pricewithout, txt_withsamgari, txt_poojadesc;
    Button btn_proceedtobook;
    String str_poojaname, str_poojaID, type, str_poojaImage, str_poojades, poojawithsamagri, poojawithoutsamagriprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandit_booking__desc);
        findID();
        if (getIntent().getExtras() != null) {
            str_poojaID = getIntent().getExtras().getString("poojaID");
            str_poojaImage = getIntent().getExtras().getString("poojaImage");
            str_poojaname = getIntent().getExtras().getString("Poojaname");
            str_poojades = getIntent().getExtras().getString("poojades");
            poojawithsamagri = getIntent().getExtras().getString("Poojasamagriprice");
            poojawithoutsamagriprice = getIntent().getExtras().getString("Poojawithoutsamagriprice");
            type = getIntent().getExtras().getString("type");


            txt_pricewithout.setText("₹ " + poojawithoutsamagriprice + " /-");
            txt_withsamgari.setText("₹ " + poojawithsamagri + " /-");
            txt_headername.setText(str_poojaname);
            txt_poojadesc.setText(str_poojades);
            try {
                String names = str_poojaImage;
                String[] namesList = names.split(",");
                String name1 = namesList[0];

                Picasso.get()
                        .load("http://vedicparampara.com/panel" + name1)
                        .error(R.drawable.satyanarayanpuja)
                        .placeholder(R.drawable.ic_processing)
                        .into(image_pooja);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        btn_proceedtobook = findViewById(R.id.btn_proceedtobook);
        txt_pricewithout = findViewById(R.id.txt_pricewithout);
        txt_withsamgari = findViewById(R.id.txt_withsamgari);
        txt_poojadesc = findViewById(R.id.txt_poojadesc);
        image_pooja = findViewById(R.id.image_pooja);
        img_back.setOnClickListener(this);
        btn_proceedtobook.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        } else if (v == btn_proceedtobook) {

            if (type != null) {
                if (type.equals("new")) {
                    startActivity(new Intent(PanditBooking_Desc.this, BhavyaAjojanFormAct.class)
                            .putExtra("pID", str_poojaID)
                            .putExtra("pname", str_poojaname)
                            .putExtra("pprice", poojawithsamagri)
                            .putExtra("typeName", "Bhajan Mandal")
                    );
                } else {
                    startActivity(new Intent(PanditBooking_Desc.this, BhavyaAjojanFormAct.class)
                            .putExtra("pID", str_poojaID)
                            .putExtra("pname", str_poojaname)
                            .putExtra("pprice", poojawithsamagri)
                            .putExtra("typeName", "Bhavaya Ayojan")

                    );
                }
            } else {
                startActivity(new Intent(PanditBooking_Desc.this, BookingDetails_Activity.class)
                        .putExtra("Poojaname", str_poojaname)
                        .putExtra("Poojasamagriprice", poojawithsamagri)
                        .putExtra("poojaID", str_poojaID)
                        .putExtra("poojaDec", str_poojades)
                        .putExtra("poojaImage", str_poojaImage)
                        .putExtra("Poojawithoutsamagriprice", poojawithoutsamagriprice)
                );
            }

            Animatoo.animateSlideLeft(PanditBooking_Desc.this);
        }
    }
}
