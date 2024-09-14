package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.Adapter.Virtualservice_Adapter;
import com.ambitious.parampara.Model.GetVertualResult;
import com.ambitious.parampara.Model.GetVertualServiceModal;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VirtualService_Activity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView rec_virtualserc;
    private ImageView img_back;
    private TextView txt_headername;
    RecyclerView.Adapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<GetVertualResult> dataItem = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_service);

        findID();
        rec_virtualserc.setItemAnimator(new DefaultItemAnimator());
    }


    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Virtual Service");
        rec_virtualserc = findViewById(R.id.rec_virtualserc);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                VertualServiceURL();
            }
        });


        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_virtualserc.setLayoutManager(gridLayoutManager);

        img_back.setOnClickListener(this);
    }


    // getVertualService
    private void VertualServiceURL() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(VirtualService_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().getVertualService();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                dataItem.clear();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            GetVertualServiceModal serviceModal = gson.fromJson(responedata, GetVertualServiceModal.class);
                            dataItem.addAll(serviceModal.getResult());

                            adapter = new Virtualservice_Adapter(VirtualService_Activity.this, dataItem);
                            rec_virtualserc.setAdapter(adapter);
                        } else {
                            utlity.toast(VirtualService_Activity.this, "Data Not Available", "e");
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(VirtualService_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                utlity.toast(VirtualService_Activity.this, "Please Check Internet Connection", "e");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        VertualServiceURL();
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }
}
