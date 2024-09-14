package com.ambitious.parampara.Activity.others;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Adapter.cartItem_Adapter;
import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetSubCategory_Response;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCart_items extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackImg;
    private TextView mHeadernameTxt;
    private RelativeLayout mHeaderLay;
    private RecyclerView mViewRecycle;
    private Button mOrderPlace;
    private RelativeLayout mBotomRec;
    private TextView mNoData;
    ArrayList<GetSubCategory_Result> product = new ArrayList<>();
    ArrayList<GetSubCategory_Result> productOrg = new ArrayList<>();
    private TextView mPriceTotal;

    private cartItem_Adapter adapter;
    int productItemQuantity = 0;
    int productItemPrice = 0;
    private ImageView back;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_items);
        initView();
        getitems();



        getProductData();
    }

    private void getProductData() {
        progressDialog = new ProgressDialog(MyCart_items.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().get_product("");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("subcate" + object + " " + responedata);
                        if (error.equals("1")) {
                            try {
                                Gson gson = new Gson();
                                GetSubCategory_Response successResponse = gson.fromJson(responedata, GetSubCategory_Response.class);


                                productOrg.addAll(successResponse.getResult());

                                for (int i = 0; i < successResponse.getResult().size(); i++) {
                                    for (int j = 0; j < product.size(); j++) {
                                        if (successResponse.getResult().get(i).getProduct_id().equalsIgnoreCase(product.get(j).getProduct_id())) {
                                            if (Integer.parseInt(successResponse.getResult().get(i).getQty()) < Integer.parseInt(product.get(j).getQty())) {

                                                product.get(j).setEntrydt("OOS");
                                            }
                                        }
                                    }
                                }


                                adapter = new cartItem_Adapter(MyCart_items.this,product,productOrg);
                                mViewRecycle.setAdapter(adapter);
                                int totalprice = 0;
                                int quanity = 0;
                                int TotalQunty, i1, gst, del, ab, ac, tot, ad;
                                int delivery, a1, a2, a3, a4 = 0;
                                for (int i = 0; i < product.size(); i++) {
                                    gst = Integer.parseInt(product.get(i).getgst_no());
                                    i1=Integer.parseInt(product.get(i).getPrice());
                                    del = Integer.parseInt(product.get(i).getdelivery_charge());
                                    TotalQunty = Integer.parseInt(product.get(i).getQty());
                                    ab = i1*gst/100;
                                    ac = i1 + del;
                                    tot = ac + ab;

                                    ad = TotalQunty * tot;
                                    Log.e("productprice"," "+product.get(i).getPrice());

                                    totalprice = totalprice + ad;
                                    quanity = quanity + Integer.parseInt(product.get(i).getQty());
                                }
                                productItemPrice = totalprice;
                                productItemQuantity = quanity;
                                mPriceTotal.setText("Rs " + totalprice);
                                mOrderPlace.setEnabled(true);
                                progressDialog.dismiss();


                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();

                            }
                        } else {
                            progressDialog.dismiss();


                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();

                    }
                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(MyCart_items.this,"Please check your internet connection!","e");
            }
        });


    }

    private void getitems() {

        if (MySharedPref.getProductCart(MyCart_items.this) != null) {
            product.clear();
            product.addAll(MySharedPref.getProductCart(MyCart_items.this));
        }
        setData();

    }

    private void setData() {
        if (product.size() <= 0) {
            mNoData.setVisibility(View.VISIBLE);
            mOrderPlace.setEnabled(false);
            mPriceTotal.setText("Rs " + 0);
        } else {
            int totalprice = 0;
            int quanity = 0;
            int TotalQunty, i1, gst, del, ab, ac, tot, ad;
            int delivery = 0, a1 = 0, a2 = 0, a3 = 0, a4 = 0;
            for (int i = 0; i < product.size(); i++) {
                gst = Integer.parseInt(product.get(i).getgst_no());
                i1=Integer.parseInt(product.get(i).getPrice());
                del = Integer.parseInt(product.get(i).getdelivery_charge());
                TotalQunty = Integer.parseInt(product.get(i).getQty());
                ab = i1*gst/100;
                ac = i1 + del;
                tot = ac + ab;

                ad = TotalQunty * tot;
                Log.e("productprice"," "+product.get(i).getPrice());

                totalprice = totalprice + ad;
                quanity = quanity + Integer.parseInt(product.get(i).getQty());
            }
            //Toast.makeText(MyCart_items.this, "hmm"+totalprice+" "+quanity+" "+gst+" "+delivery+" "+a1+" "+a2+" "+a3+" "+a4, Toast.LENGTH_SHORT).show();
            productItemPrice = totalprice;
            productItemQuantity = quanity;
            mPriceTotal.setText("Rs " + totalprice);
            mOrderPlace.setEnabled(true);

        }
    }

    public void removeItem(int pos) {

        product.remove(pos);
        MySharedPref.addProductToCart(MyCart_items.this, product);
        if (adapter != null) {
            adapter.notifyItemRemoved(pos);
        }
        getitems();
        MySharedPref.saveData(MyCart_items.this, "cartSize", "" + product.size());


    }

    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(this);
        mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
        mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
        mViewRecycle = (RecyclerView) findViewById(R.id.recycle_view);
        mOrderPlace = (Button) findViewById(R.id.place_order);
        mOrderPlace.setOnClickListener(this);
        mBotomRec = (RelativeLayout) findViewById(R.id.rec_botom);
        mNoData = (TextView) findViewById(R.id.noData);
        mPriceTotal = (TextView) findViewById(R.id.Total_price);
        mPriceTotal.setText("Rs.");
        mOrderPlace.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.place_order) {
            Intent intent = new Intent(MyCart_items.this, EStoreBookingDetails_Activity.class);
            intent.putExtra("allData", (Serializable) product);
            intent.putExtra("price", "" + productItemPrice);
            intent.putExtra("qnt", "" + productItemQuantity);
            startActivity(intent);
            Animatoo.animateSlideUp(MyCart_items.this);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
        Animatoo.animateSlideRight(MyCart_items.this);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getitems();
    }
}
