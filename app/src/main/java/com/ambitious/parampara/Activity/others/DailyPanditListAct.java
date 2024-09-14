package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.Adapter.DailyPanditListAdapter;
import com.ambitious.parampara.Model.DailyPanditModal;
import com.ambitious.parampara.Model.DailyPanditResult;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class DailyPanditListAct extends AppCompatActivity implements View.OnClickListener {

    String type, userID,currentDate;
    private RecyclerView rec_virtualserc;
    private ImageView img_back;
    private TextView txt_headername;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.Adapter adapter = null;
    ArrayList<DailyPanditResult> dataItem = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pandit_list);
        userID = getData(getApplicationContext(), "userID", null);

        Intent extras = getIntent();
        if (extras != null) {
            type = extras.getStringExtra("type");
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat tim = new SimpleDateFormat("HH:mm aa");
        currentDate = df.format(c);

        findID();
        rec_virtualserc.setItemAnimator(new DefaultItemAnimator());
    }


    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Daily Pandit");
        rec_virtualserc = findViewById(R.id.rec_daily_pandit);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPanditList();
            }
        });


        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rec_virtualserc.setLayoutManager(gridLayoutManager);

        img_back.setOnClickListener(this);
    }

    // getVertualService
    private void getPanditList() {
        swipeRefreshLayout.setRefreshing(true);
        progressDialog = new ProgressDialog(DailyPanditListAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = null;
        if (type.equals("home")) {
            call = AppConfig.loadInterface().get_home_dailypandit();
        } else {
            call = AppConfig.loadInterface().get_office_dailypandit();
        }
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
                            DailyPanditModal serviceModal = gson.fromJson(responedata, DailyPanditModal.class);
                            dataItem.addAll(serviceModal.getResult());

                            adapter = new DailyPanditListAdapter(DailyPanditListAct.this, dataItem, new DailyPanditListAdapter.DailyPanditItem() {
                                @Override
                                public void panditItemDetails(String id, String name, String price) {
                                    startActivity(new Intent(getApplicationContext(), DailyPanditSubsAddressAct.class)
                                            .putExtra("pID", id)
                                            .putExtra("pName", name)
                                            .putExtra("pPrice", price)
                                            .putExtra("type", "dailyPan")
                                            .putExtra("entrydate", currentDate)
                                            .putExtra("userID", userID));
                                }
                            });
                            rec_virtualserc.setAdapter(adapter);
                        } else {
                            utlity.toast(DailyPanditListAct.this, "Data Not Available", "e");
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(DailyPanditListAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
                t.printStackTrace();
                utlity.toast(DailyPanditListAct.this, "Please Check Internet Connection", "e");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        getPanditList();
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }

}
