package com.ambitious.parampara.Activity.others;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ambitious.parampara.Model.orderModel;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Currency;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class Order_details_activity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBackImg;
    private TextView mHeadernameTxt;
    private RelativeLayout mHeaderLay;
    private TextView mIdTxtOrder;
    private TextView mDateTxtOrder;
    private TextView mNameTxtShop;
    private TextView mNameTxtProduct;
    private TextView mQuantityTxtProduct;
    private TextView mPriceTxtProduct;
    private TextView mModeTxtPayment;
    private TextView mAddressTxtDelivery;
    private TextView mTxtStatus;
    private RelativeLayout mLyStatus;
    private Button mCancelOrder;
    private String OrderID;
    private String Status;
    private orderModel.order order;
    private ImageView mImgProduct;
    private ProgressDialog progressDialog;

    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();

    int amt, admin_amt, final_amt;
    String amount;

    private TextView mTitile;
    private ImageView m1Rate;
    private ImageView m2Rate;
    private ImageView m3Rate;
    private ImageView m4Rate;
    private ImageView m5Rate;
    private Button mSubmitBtn;
    private int rate = 0;
    private AlertDialog deleteDialog;
    private EditText feedback_edt;
    ImageView rate1, rate2, rate3, rate4, rate5;
    LinearLayout vender_rating_ly;
    String product_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details_activity);
        initView();


        if (getIntent().getSerializableExtra("orderModel") != null) {
            order = (orderModel.order) getIntent().getSerializableExtra("orderModel");
            System.out.println("Afsdasf:::   "+order.toString());
            setData();
            OrderID = order.getId();
            product_id = order.getProduct_id();
            Status = order.getStatus();
            if(Integer.parseInt(Status)==4){
                mCancelOrder.setVisibility(View.GONE);
            }
            if(Integer.parseInt(Status)>4){
                mCancelOrder.setEnabled(false);
            }
        }

        System.out.println("afdsaf::::  "+order.getUser_rating()+"   "+order.getStatus());
        if(Integer.parseInt(order.getUser_rating())<=0 && order.getStatus().equalsIgnoreCase("4")){
            showRateDialog();
        }


    }

    private void setData() {
        try {
            Picasso.get()
                    .load("http://vedicparampara.com/panel" + order.getImage())
                    .placeholder(R.drawable.ic_processing)
                    .error(R.drawable.ic_product)
                    .into(mImgProduct);

        } catch (Exception e) {
            e.printStackTrace();
        }
        mIdTxtOrder.setText(order.getId());
        mDateTxtOrder.setText(order.getEntry_date());
        mNameTxtShop.setText(order.getShopName());
        mNameTxtProduct.setText(order.getProduct_name());

        mQuantityTxtProduct.setText("Qty : "+order.getQuantity());

        amt = Integer.parseInt(order.getAmount());
        admin_amt = Integer.parseInt(order.getAdmin_amount());
        final_amt = amt + admin_amt;

        amount = Integer.toString(final_amt);

        mPriceTxtProduct.setText(symbol+" "+amount);
        mModeTxtPayment.setText(order.getPayment_mode());
        mAddressTxtDelivery.setText(order.getUser_name() + "\n" + order.getAddress() + "\n" + order.getState() + "\n" + order.getCity());
        switch (Integer.parseInt(order.getStatus())) {
            case 0:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_yellow));
                mTxtStatus.setText("Placed");
                break;
            case 1:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_green));
                mTxtStatus.setText("Accepted");
                break;
            case 2:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_cyan));
                mTxtStatus.setText("Packed");
                break;
            case 3:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_blue));
                mTxtStatus.setText("Deliver");
                break;

            case 4:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_purple));
                mTxtStatus.setText("Delivered");

                System.out.println("ASfasf::::  "+order.getVender_rating());
                if(Integer.parseInt(order.getVender_rating())>0){
                    vender_rating_ly.setVisibility(View.VISIBLE);
                    setVenderRating(Integer.parseInt(order.getVender_rating()));

                }
                break;

            case 5:
            case 6:
            case 7:
            case 8:
                mLyStatus.setBackground(getResources().getDrawable(R.drawable.right_round_red));
                mTxtStatus.setText("Canceled");
                break;
        }





    }

    private void setVenderRating(int i) {

        switch (i){
            case 1:
                rate1.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 2:
                rate1.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 3:
                rate1.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 4:
                rate1.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 5:
                rate1.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(Order_details_activity.this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
        }
    }

    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(this);
        mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
        mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
        vender_rating_ly =  findViewById(R.id.vender_rating_ly_main);
        vender_rating_ly.setVisibility(View.GONE);

        rate1 = findViewById(R.id.rate_1);
        rate2 = findViewById(R.id.rate_2);
        rate3 = findViewById(R.id.rate_3);
        rate4 = findViewById(R.id.rate_4);
        rate5 = findViewById(R.id.rate_5);


        mIdTxtOrder = (TextView) findViewById(R.id.order_id_txt);
        mDateTxtOrder = (TextView) findViewById(R.id.order_date_txt);
        mNameTxtShop = (TextView) findViewById(R.id.shop_name_txt);
        mNameTxtProduct = (TextView) findViewById(R.id.product_name_txt);

        mQuantityTxtProduct = (TextView) findViewById(R.id.product_quantity_txt);
        mPriceTxtProduct = (TextView) findViewById(R.id.product_price_txt);
        mModeTxtPayment = (TextView) findViewById(R.id.payment_mode_txt);
        mAddressTxtDelivery = (TextView) findViewById(R.id.delivery_address_txt);
        mTxtStatus = (TextView) findViewById(R.id.status_txt);
        mLyStatus = (RelativeLayout) findViewById(R.id.status_ly);
        mCancelOrder = (Button) findViewById(R.id.cancelOrder);
        mCancelOrder.setOnClickListener(this);
        mImgProduct = (ImageView) findViewById(R.id.product_img);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.cancelOrder) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Do you want to cancel this item ?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            cancelOrder(OrderID, product_id);
                        }
                    })
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        } else if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.rate_1) {
            rate = 1;
            setRatingColor(R.color.light_green, R.color.colorGray, R.color.colorGray, R.color.colorGray, R.color.colorGray);
        } else if (id == R.id.rate_2) {
            rate = 2;
            setRatingColor(R.color.light_green, R.color.light_green, R.color.colorGray, R.color.colorGray, R.color.colorGray);
        } else if (id == R.id.rate_3) {
            rate = 3;
            setRatingColor(R.color.light_green, R.color.light_green, R.color.light_green, R.color.colorGray, R.color.colorGray);
        } else if (id == R.id.rate_4) {
            rate = 4;
            setRatingColor(R.color.light_green, R.color.light_green, R.color.light_green, R.color.light_green, R.color.colorGray);
        } else if (id == R.id.rate_5) {
            rate = 5;
            setRatingColor(R.color.light_green, R.color.light_green, R.color.light_green, R.color.light_green, R.color.light_green);
        } else if (id == R.id.btn_submit) {
            if (rate > 0) {
                addRate(rate);
            } else if (deleteDialog != null) {
                deleteDialog.dismiss();
                saveData(Order_details_activity.this, "isRating", "0");
            }
        }
    }

    // Helper method to set rating color
    private void setRatingColor(int color1, int color2, int color3, int color4, int color5) {
        m1Rate.setColorFilter(ContextCompat.getColor(Order_details_activity.this, color1), android.graphics.PorterDuff.Mode.SRC_IN);
        m2Rate.setColorFilter(ContextCompat.getColor(Order_details_activity.this, color2), android.graphics.PorterDuff.Mode.SRC_IN);
        m3Rate.setColorFilter(ContextCompat.getColor(Order_details_activity.this, color3), android.graphics.PorterDuff.Mode.SRC_IN);
        m4Rate.setColorFilter(ContextCompat.getColor(Order_details_activity.this, color4), android.graphics.PorterDuff.Mode.SRC_IN);
        m5Rate.setColorFilter(ContextCompat.getColor(Order_details_activity.this, color5), android.graphics.PorterDuff.Mode.SRC_IN);
    }


    private void cancelOrder(String orderID, String product_id) {

        progressDialog = new ProgressDialog(Order_details_activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();


        // 5= cancel by user.
        Call<ResponseBody> call = AppConfig.loadInterface().change_status("5",orderID,product_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("DetailsFragment" + object);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(Order_details_activity.this, "Something Went Wrong !!","e");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                progressDialog.dismiss();

                t.printStackTrace();
                utlity.toast(Order_details_activity.this, "Please Check Internet Connection","e");

            }
        });

    }



    private void addRate(int rate) {
        progressDialog = new ProgressDialog(Order_details_activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().add_OrderRating("" + OrderID, "" + rate, ""+order.getProduct_id(),"" + feedback_edt.getText().toString(), "user");

        System.out.println("sadfasdf::::  "+call.request().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("dashboard" + object);
                        if (error.equals("200")) {
                            saveData(Order_details_activity.this, "isRating", "0");
                            deleteDialog.dismiss();
                            progressDialog.dismiss();

                        }

                    } catch (IOException e) {
                        deleteDialog.dismiss();
                        progressDialog.dismiss();
                        e.printStackTrace();
                    } catch (JSONException e) {
                        deleteDialog.dismiss();
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                deleteDialog.dismiss();
                progressDialog.dismiss();
                utlity.toast(Order_details_activity.this, "Please Check Internet Connection","e");
            }
        });


    }

    private void showRateDialog() {

        try {
            LayoutInflater factory = LayoutInflater.from(Order_details_activity.this);
            final View itemView = factory.inflate(R.layout.dialog_rating, null);
            deleteDialog = new AlertDialog.Builder(Order_details_activity.this).create();
            deleteDialog.setView(itemView);

            mTitile = (TextView) itemView.findViewById(R.id.titile);
            feedback_edt = itemView.findViewById(R.id.feedback_edt);
            m1Rate = (ImageView) itemView.findViewById(R.id.rate_1);
            m1Rate.setOnClickListener(this);
            m2Rate = (ImageView) itemView.findViewById(R.id.rate_2);
            m2Rate.setOnClickListener(this);
            m3Rate = (ImageView) itemView.findViewById(R.id.rate_3);
            m3Rate.setOnClickListener(this);
            m4Rate = (ImageView) itemView.findViewById(R.id.rate_4);
            m4Rate.setOnClickListener(this);
            m5Rate = (ImageView) itemView.findViewById(R.id.rate_5);
            m5Rate.setOnClickListener(this);
            mSubmitBtn = (Button) itemView.findViewById(R.id.btn_submit);
            mSubmitBtn.setOnClickListener(this);

            deleteDialog.show();
            deleteDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    mCancelOrder.setText("Delivered");
                    mCancelOrder.setEnabled(false);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
