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

public class Ayojan_desc extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back, image_pooja;
    private TextView txt_headername, txt_gstprice, txt_convenience, txt_price, txt_poojadesc;
    Button btn_proceedtobook;
    String str_poojaname, str_poojaID, type, str_poojaImage, str_poojades, poojawithsamagri, poojawithoutsamagriprice, convenienceprice, gstprice;
    int A, B, C, D, E, F, G;
    String pri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayojan_desc);
        findId();
        if (getIntent().getExtras() != null) {
            str_poojaID = getIntent().getExtras().getString("poojaID");
            str_poojaImage = getIntent().getExtras().getString("poojaImage");
            str_poojaname = getIntent().getExtras().getString("Poojaname");
            str_poojades = getIntent().getExtras().getString("poojades");
            poojawithsamagri = getIntent().getExtras().getString("Poojasamagriprice");
            //poojawithoutsamagriprice = getIntent().getExtras().getString("Poojawithoutsamagriprice");
            convenienceprice = getIntent().getExtras().getString("poojaconvenience");
            gstprice = getIntent().getExtras().getString("poojagst");
            type = getIntent().getExtras().getString("type");

            A = Integer.parseInt(poojawithsamagri);
            B = Integer.parseInt(gstprice);
            C = Integer.parseInt(convenienceprice);

            D = A * B / 100;
            E = D + C;
            F = E + A;

            pri = Integer.toString(F);

            txt_convenience.setText("₹ " + convenienceprice + " /-");
            txt_price.setText("₹ " + poojawithsamagri + " /-");
            txt_gstprice.setText(gstprice + " %");
            txt_headername.setText(str_poojaname);
            txt_poojadesc.setText(str_poojades);
            try {
                String names = str_poojaImage;
                String[] namesList = names.split(",");
                String name1 = namesList[0];

                Picasso.get()
                        .load("http://vedicparampara.com/panel/" + name1)
                        .error(R.drawable.satyanarayanpuja)
                        .placeholder(R.drawable.ic_processing)
                        .into(image_pooja);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        btn_proceedtobook = findViewById(R.id.btn_proceedtobook);
        txt_convenience = findViewById(R.id.txt_convenience);
        txt_gstprice = findViewById(R.id.txt_gst);
        txt_price = findViewById(R.id.txt_price);
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
            startActivity(new Intent(Ayojan_desc.this, BhavyaAjojanFormAct.class)
                    .putExtra("pID", str_poojaID)
                    .putExtra("pname", str_poojaname)
                    .putExtra("pprice", pri)
                    .putExtra("typeName", "Bhavaya Ayojan")

            );

            Animatoo.animateSlideLeft(Ayojan_desc.this);
        }
    }
}


