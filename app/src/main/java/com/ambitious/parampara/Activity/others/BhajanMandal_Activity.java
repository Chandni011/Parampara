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

import com.ambitious.parampara.Adapter.BhajanMandal_Adapter;
import com.ambitious.parampara.Model.BhajanMandalModal;
import com.ambitious.parampara.Model.BhajanMandalResult;
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

public class BhajanMandal_Activity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rec_bhajanmandal;
    private ImageView img_back;
    private TextView txt_headername;
    RecyclerView.Adapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<BhajanMandalResult> dataItem = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhajan_mandal);
        findID();

        rec_bhajanmandal.setItemAnimator(new DefaultItemAnimator());
    }

    private void findID() {
        rec_bhajanmandal = findViewById(R.id.rec_bhajanmandal);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Bhajan Mandal");
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bhajanMandal();
            }
        });


        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_bhajanmandal.setLayoutManager(gridLayoutManager);


        img_back.setOnClickListener(this);
    }


    // getVertualService
    private void bhajanMandal() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(BhajanMandal_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().get_mandal();
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
                            BhajanMandalModal serviceModal = gson.fromJson(responedata, BhajanMandalModal.class);
                            dataItem.addAll(serviceModal.getResult());

                            adapter = new BhajanMandal_Adapter(BhajanMandal_Activity.this, dataItem);
                            rec_bhajanmandal.setAdapter(adapter);
                        } else {
                            utlity.toast(BhajanMandal_Activity.this, "Data Not Available", "e");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(BhajanMandal_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                utlity.toast(BhajanMandal_Activity.this, "Please Check Internet Connection", "e");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        bhajanMandal();
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }
}
