package com.ambitious.parampara.Activity.others;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class Product_details_Page extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackImg;
    private TextView mHeadernameTxt;
    private RelativeLayout mHeaderLay;
    private ImageView mImage;
    private TextView mSubcatnameTxt;
    private TextView mPriceTxt;
    private TextView mStockTxt;
    private LinearLayout m2Ly;
    private ImageView mQntRemove;
    private EditText mQuantityTxtProduct;
    private ImageView mQntAdd;
    private LinearLayout mAddhelperRel;
    private TextView mDescripText;
    private TextView mDescriptionText, gst_no, delivery_charge;
    private ImageView m1Rate;
    private ImageView m2Rate;
    private ImageView m3Rate;
    private ImageView m4Rate;
    private ImageView m5Rate;
    private LinearLayout mRatingLyPandit;
    private RelativeLayout m3Ly;
    private TextView mPriceTotal;
    private Button mCartAdd;
    private Button mOrderPlace;
    private RelativeLayout mBotomRec;
    private GetSubCategory_Result data;
    private GetSubCategory_Result data2;
    String pritxt;
    String pritxt2;

    int Total_Price;
    int Total_Qnt, gst, del, pri, gstca, tot, tota;
    int Total_Qnt2, gst2, del2, pri2, gstca2, tot2, tota2;
    ArrayList<GetSubCategory_Result> product2 = new ArrayList<>();
    private int position;
    int Count = 1;
    private ImageView mCartImg;
    private TextView mCartItem;
    private RelativeLayout mCartNumberRel;

    int gstno, Totl_price, dlvry_chrg, a1, a2, a3, a4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details__page);
        initView();
        if (getIntent().getSerializableExtra("allData") != null) {
            data = (GetSubCategory_Result) getIntent().getSerializableExtra("allData");
            Total_Qnt = Integer.parseInt(getIntent().getStringExtra("qnt"));
            Totl_price = Integer.parseInt(getIntent().getStringExtra("pri"));
            gstno = Integer.parseInt(getIntent().getStringExtra("gstn"));
            dlvry_chrg = Integer.parseInt(getIntent().getStringExtra("deli"));

            /*gst = Integer.parseInt(data2.getgst_no());
        pri = Integer.parseInt(data2.getPrice());
        gstca = pri*gst/100;
        del = Integer.parseInt(data2.getdelivery_charge());
        tot = gstca + del;
        tota = tot + pri;

        pritxt = Integer.toString(tota);*/

            a1 = Totl_price*gstno/100;
            a2 = a1 + dlvry_chrg;
            a3 = a2 + Totl_price;

            pritxt = Integer.toString(a3);


            setData();
        }
    }

    private void setData() {

        boolean res = false;
        if (MySharedPref.getProductCart(Product_details_Page.this) != null) {
            product2.addAll(MySharedPref.getProductCart(Product_details_Page.this));
        }
        for (int i = 0; i < product2.size(); i++) {
            if (product2.get(i).getProduct_id().equalsIgnoreCase(data.getProduct_id())) {
                res = true;
                data2 = product2.get(i);
            }

        }
        mPriceTxt.setText("Rs.  " + data.getPrice());
        gst_no.setText(data.getgst_no()+"%");
        delivery_charge .setText("Rs. "+data.getdelivery_charge());
        //mPriceTotal.setText("Rs."+data.getPrice());

        /*gst = Integer.parseInt(data2.getgst_no());
        pri = Integer.parseInt(data2.getPrice());
        gstca = pri*gst/100;
        del = Integer.parseInt(data2.getdelivery_charge());
        tot = gstca + del;
        tota = tot + pri;

        pritxt = Integer.toString(tota);*/

        if (res) {
            System.out.println("jhggghjghjghj :::    " + data2.toString());
            mPriceTxt.setText("Rs. " + Integer.parseInt(data2.getPrice()));
            mQuantityTxtProduct.setText(data2.getQty());
            a4 = Integer.parseInt(data2.getQty());
            //Toast.makeText(this, ""+data2.getQty(), Toast.LENGTH_SHORT).show();
            //mPriceTotal.setText("Rs " + data2.getPrice());
            gst2 = Integer.parseInt(data2.getgst_no());
            pri2 = Integer.parseInt(data2.getPrice());
            gstca2 = pri2*gst2/100;
            del2 = Integer.parseInt(data2.getdelivery_charge());
            tot2 = gstca2 + del2;
            tota2 = tot2 + pri2;
            Total_Qnt2 = a4 * tota2;
            pritxt2 = Integer.toString(Total_Qnt2);
            //Toast.makeText(this, "hhh"+gst2+" "+pri2+" "+gstca2+" "+del2+" "+tot2+" "+tota2+" "+pritxt2, Toast.LENGTH_SHORT).show();
            mPriceTotal. setText("Rs. "+pritxt2);
            Count = Integer.parseInt(data2.getQty());

        } else {
            System.out.println("jghj :::    " + data.toString());

//            mPriceTxt.setText("Rs " + data.getPrice());
            mQuantityTxtProduct.setText("1");
            //mPriceTotal.setText("Rs " + data.getPrice());
            gst = Integer.parseInt(data.getgst_no());
            pri = Integer.parseInt(data.getPrice());
            gstca = pri*gst/100;
            del = Integer.parseInt(data.getdelivery_charge());
            tot = gstca + del;
            tota = tot + pri;
            //Toast.makeText(this, "hiii"+gst+" "+pri+" "+gstca+" "+del+" "+tot+" "+tota+" "+pritxt, Toast.LENGTH_SHORT).show();
            pritxt = Integer.toString(tota);
            mPriceTotal.setText("Rs. "+pritxt);
            Count = Integer.parseInt("1");

        }


        mHeadernameTxt.setText("Product Details");
        try {
            Picasso.get()
                    .load("http://vedicparampara.com/" + data.getImage())
                    .error(R.drawable.product_placeholder)
                    .placeholder(R.drawable.product_placeholder)
                    .into(mImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mSubcatnameTxt.setText(data.getName());


        mStockTxt.setText("Stock : " +  Total_Qnt+ " " + data.getQty_type());

        mDescriptionText.setText(data.getDescription());

        if (data.getEntrydt().equalsIgnoreCase("OOS")) {
            mOrderPlace.setBackgroundColor(Color.RED);

            mOrderPlace.setText("Out Of Stock");
            mOrderPlace.setEnabled(false);
        }
        if (!data.getAvg_rating().isEmpty()) {

            double a = Double.parseDouble(data.getAvg_rating());

            setRating((int) Math.ceil(a));
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setCartnuber();
    }

    private void setCartnuber() {

        if (MySharedPref.getData(Product_details_Page.this, "cartSize", null) != null) {
            int Size = Integer.parseInt(MySharedPref.getData(Product_details_Page.this, "cartSize", null));
            MySharedPref.setAnim(mCartNumberRel, Product_details_Page.this);
            mCartItem.setText("" + Size);

        }
    }


    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(this);
        mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
        mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
        mImage = (ImageView) findViewById(R.id.image);
        mSubcatnameTxt = (TextView) findViewById(R.id.txt_subcatname);
        mPriceTxt = (TextView) findViewById(R.id.txt_price);
        gst_no = (TextView) findViewById(R.id.gst_no);
        delivery_charge = (TextView) findViewById(R.id.delivery_charge);
        mStockTxt = (TextView) findViewById(R.id.txt_stock);
        m2Ly = (LinearLayout) findViewById(R.id.ly_2);
        mQntRemove = (ImageView) findViewById(R.id.remove_qnt);
        mQntRemove.setOnClickListener(this);
        mQuantityTxtProduct = (EditText) findViewById(R.id.product_quantity_txt);
        mQntAdd = (ImageView) findViewById(R.id.add_qnt);
        mQntAdd.setOnClickListener(this);
        mAddhelperRel = (LinearLayout) findViewById(R.id.rel_addhelper);
        mDescripText = (TextView) findViewById(R.id.text_descrip);
        mDescriptionText = (TextView) findViewById(R.id.text_description);
        m1Rate = (ImageView) findViewById(R.id.rate_1);
        m2Rate = (ImageView) findViewById(R.id.rate_2);
        m3Rate = (ImageView) findViewById(R.id.rate_3);
        m4Rate = (ImageView) findViewById(R.id.rate_4);
        m5Rate = (ImageView) findViewById(R.id.rate_5);
        mRatingLyPandit = (LinearLayout) findViewById(R.id.pandit_rating_ly);
        m3Ly = (RelativeLayout) findViewById(R.id.ly_3);
        mPriceTotal = (TextView) findViewById(R.id.Total_price);
        mCartAdd = (Button) findViewById(R.id.add_cart);
        mCartAdd.setOnClickListener(this);
        mOrderPlace = (Button) findViewById(R.id.place_order);
        mOrderPlace.setOnClickListener(this);
        mBotomRec = (RelativeLayout) findViewById(R.id.rec_botom);
        mCartImg = (ImageView) findViewById(R.id.img_cart);
        mCartImg.setOnClickListener(this);
        mCartItem = (TextView) findViewById(R.id.item_cart);
        mCartNumberRel = (RelativeLayout) findViewById(R.id.rel_cart_number);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.remove_qnt) {
            if (Count > 1) {
                Count--;
                changeQnt(Count);
            } else {
                Toast.makeText(Product_details_Page.this, "Can't Add 0 Quantity Stock", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.add_qnt) {
            System.out.println("fsafsadF::::   " + Count + "   " + data.getQty());
            if (Count < Total_Qnt) {
                Count++;
                changeQnt(Count);
            } else {
                Toast.makeText(Product_details_Page.this, "Can't Add More Than Stock", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.add_cart) {
            AddToCart();
        } else if (id == R.id.place_order) {
            BuyNow();
        } else if (id == R.id.img_cart) {
            startActivity(new Intent(Product_details_Page.this, MyCart_items.class));
            Animatoo.animateSlideLeft(Product_details_Page.this);
        } else if (id == R.id.txt_subcatname) {
            // TODO 20/04/30
        }
    }


    private void BuyNow() {
        ArrayList<GetSubCategory_Result> product = new ArrayList<>();

        if (data2 != null) {

            product.add(data2);
        } else{
            product.add(data);
        }

        startActivity(new Intent(Product_details_Page.this, EStoreBookingDetails_Activity.class)
                .putExtra("allData", (Serializable)product)
                .putExtra("price",""+mPriceTotal.getText().toString().replace("Rs. ", "").trim())
                .putExtra("qnt",""+mQuantityTxtProduct.getText().toString().trim()));
        Animatoo.animateSlideUp(Product_details_Page.this);
    }

    private void changeQnt(int i) {
        mQuantityTxtProduct.setText("" + i);
        mPriceTotal.setText("Rs. " + i * Integer.parseInt(pritxt)/*Integer.parseInt(mPriceTxt.getText().toString().replace("Rs ", ""))*/);
    }

    private void AddToCart() {

        int Size = 0;
        boolean res=false;

        ArrayList<GetSubCategory_Result> product3 = new ArrayList<>();

        if (MySharedPref.getProductCart(Product_details_Page.this) != null) {
            product3.addAll(MySharedPref.getProductCart(Product_details_Page.this));
        }
        for (int i = 0; i < product3.size(); i++) {
            if (product3.get(i).getProduct_id().equalsIgnoreCase(data.getProduct_id())) {
                position = i;
                res=true;
            }

        }
        if (res) {
            product3.get(position).setQty(mQuantityTxtProduct.getText().toString().trim());
            //product3.get(position).setPrice(mPriceTotal.getText().toString().replace("Rs ", "").trim());
            product3.get(position).setFinal_amount(mPriceTotal.getText().toString().replace("Rs ", "").trim());
            product3.get(position).setPrice(data.getPrice());
            MySharedPref.addProductToCart(Product_details_Page.this, product3);
            Size=0;
        } else {
            ArrayList<GetSubCategory_Result> product = new ArrayList<>();
            product.addAll(MySharedPref.getProductCart(Product_details_Page.this));
            product.add(data);
            //product.get(product.size() - 1).setPrice(mPriceTotal.getText().toString().replace("Rs ", "").trim());
            product.get(product.size() - 1).setFinal_amount(mPriceTotal.getText().toString().replace("Rs ", "").trim());
            product.get(product.size() - 1).setPrice(data.getPrice());
            product.get(product.size() - 1).setQty(mQuantityTxtProduct.getText().toString().trim());
            MySharedPref.addProductToCart(Product_details_Page.this, product);
            Size++;
        }

        if (MySharedPref.getData(Product_details_Page.this, "cartSize", null) != null) {
            Size =Size+Integer.parseInt(MySharedPref.getData(Product_details_Page.this, "cartSize", null));
        }
        MySharedPref.saveData(Product_details_Page.this, "cartSize", "" + Size);
        MySharedPref.setAnim(mCartNumberRel, Product_details_Page.this);
        mCartItem.setText("" + Size);
        Toast.makeText(Product_details_Page.this, "Added To Cart", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(Product_details_Page.this);
    }

    private void setRating(int i) {
        switch (i) {
            case 1:
                m1Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m2Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m3Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m4Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m5Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);

                break;
            case 2:
                m1Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m2Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m3Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m4Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m5Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);

                break;
            case 3:
                m1Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m2Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m3Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m4Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);
                m5Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);

                break;
            case 4:
                m1Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m2Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m3Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m4Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m5Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.colorGray), PorterDuff.Mode.SRC_IN);

                break;
            case 5:
                m1Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m2Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m3Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m4Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);
                m5Rate.setColorFilter(ContextCompat.getColor(Product_details_Page.this, R.color.light_green), PorterDuff.Mode.SRC_IN);

                break;
        }
    }

}
