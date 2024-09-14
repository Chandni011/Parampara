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

import com.ambitious.parampara.Adapter.BhavyaAjojanAdapter;
import com.ambitious.parampara.Model.BhavyaAjojanModal;
import com.ambitious.parampara.Model.BhavyaAjojanResultModal;
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

public class bhavvya_ajojana extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rec_virtualserc;
    private ImageView img_back;
    private TextView txt_headername;
    RecyclerView.Adapter adapter = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<BhavyaAjojanResultModal> dataItem = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhavvya_ajojana);
        findID();

        rec_virtualserc.setItemAnimator(new DefaultItemAnimator());

    }

    private void findID() {

        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Bhavya Ayojana");
        rec_virtualserc = findViewById(R.id.rec_bhavyaayojan_recy);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bhavyaAjojan();
            }
        });


        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_virtualserc.setLayoutManager(gridLayoutManager);


        img_back.setOnClickListener(this);
    }

    // getVertualService
    private void bhavyaAjojan() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(bhavvya_ajojana.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().get_ayojan();
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
                            BhavyaAjojanModal serviceModal = gson.fromJson(responedata, BhavyaAjojanModal.class);
                            dataItem.addAll(serviceModal.getResult());

                            adapter = new BhavyaAjojanAdapter(bhavvya_ajojana.this, dataItem);
                            rec_virtualserc.setAdapter(adapter);
                        } else {
                            utlity.toast(bhavvya_ajojana.this, "Data Not Available", "e");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(bhavvya_ajojana.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                utlity.toast(bhavvya_ajojana.this, "Please Check Internet Connection", "e");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        bhavyaAjojan();
    }


    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
