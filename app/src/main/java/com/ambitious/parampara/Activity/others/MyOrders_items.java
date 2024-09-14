package com.ambitious.parampara.Activity.others;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ambitious.parampara.Adapter.myOrderAdapter;
import com.ambitious.parampara.Model.orderModel;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class MyOrders_items extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView mBackImg;
    private TextView mHeadernameTxt;
    private RecyclerView mCateRec;
    private SwipeRefreshLayout mEStaoreSwipe;
    private RelativeLayout mHeaderLay;
    private TextView mNoData;
    ImageView empty;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        userID = getData(getApplicationContext(), "userID", null);

        initView();

        onRefresh();
    }

    private void initView() {
        empty =(ImageView)findViewById(R.id.empty);
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(this);
        mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
        mCateRec = (RecyclerView) findViewById(R.id.rec_cate);
        mEStaoreSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_e_staore);
        mEStaoreSwipe.setOnRefreshListener(this);
        mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
        mNoData = (TextView) findViewById(R.id.noData);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_back) {
            onBackPressed();
        }
        // You can add more conditions here if needed
    }


    @Override
    public void onRefresh() {
        if (!mEStaoreSwipe.isRefreshing()) {
            mEStaoreSwipe.setRefreshing(true);
        }
        getOrders();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void getOrders() {
        System.out.println(userID);
        Call<ResponseBody> call = AppConfig.loadInterface().get_order(userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mEStaoreSwipe.setRefreshing(false);
                if (response.isSuccessful()) {
                    mNoData.setVisibility(View.GONE);
                    try {
                        String responedata = response.body().string();
                        //Toast.makeText(MyOrders_items.this, ""+responedata, Toast.LENGTH_SHORT).show();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("PreviousFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            orderModel orderModel = gson.fromJson(responedata, orderModel.class);
                            myOrderAdapter myOrderAdapter = new myOrderAdapter(MyOrders_items.this, orderModel.getResult());
                            mCateRec.setAdapter(myOrderAdapter);
                        } else {
                            empty.setVisibility(View.VISIBLE);
                            final Dialog dialog = new Dialog(MyOrders_items.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                            dialog.setContentView(R.layout.sorry_dialog);
                            dialog.setCancelable(true);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            TextView content = dialog.findViewById(R.id.text_msg);
                            TextView btnback = dialog.findViewById(R.id.btn_back);
                            content.setText("No data found!");
                            TextView title = dialog.findViewById(R.id.content);

                            btnback.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MyOrders_items.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                            });


                            dialog.show();
                            dialog.getWindow().setAttributes(lp);


                            mNoData.setVisibility(View.VISIBLE);


                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    mNoData.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mEStaoreSwipe.setRefreshing(false);
                mNoData.setVisibility(View.VISIBLE);
                final Dialog dialog = new Dialog(MyOrders_items.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                dialog.setContentView(R.layout.sorry_dialog);
                dialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                TextView content = dialog.findViewById(R.id.text_msg);
                content.setText("No data found!");
                TextView title = dialog.findViewById(R.id.content);

                 dialog.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyOrders_items.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                dialog.getWindow().setAttributes(lp);


                utlity.toast(MyOrders_items.this, "Please Check Internet Connection","e");

                t.printStackTrace(); }
        });

    }

}
