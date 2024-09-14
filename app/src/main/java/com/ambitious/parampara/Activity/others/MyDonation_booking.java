package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Adapter.Donation_Adapter;
import com.ambitious.parampara.Adapter.Kundali_Adapter;
import com.ambitious.parampara.Model.Donation_list;
import com.ambitious.parampara.Model.Kundali_list;
import com.ambitious.parampara.Model.MyDonation_Response;
import com.ambitious.parampara.Model.MyKundali_Response;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class MyDonation_booking extends AppCompatActivity {
    SwipeRefreshLayout swipe;
    RecyclerView rec_requestlist;
    ArrayList<Donation_list> data = new ArrayList<>();
    String userID;
    TextView txt_headername;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donation_booking);
        swipe = findViewById(R.id.swipehome);
        rec_requestlist = findViewById(R.id.rec_requestlist);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Donation");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userID = getData(getApplicationContext(), "userID", null);
        getDonation();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDonation();
            }
        });
    }

    private void getDonation() {
        swipe.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().donation_submittion(userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipe.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        //Toast.makeText(MyDonation_booking.this, ""+object, Toast.LENGTH_SHORT).show();
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            MyDonation_Response resquestList_response = gson.fromJson(responedata, MyDonation_Response.class);
                            rec_requestlist.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(MyDonation_booking.this);
                            rec_requestlist.setLayoutManager(mLayoutManager1);
                            rec_requestlist.setItemAnimator(new DefaultItemAnimator());
                            Donation_Adapter adapter = new Donation_Adapter(MyDonation_booking.this, resquestList_response.getResult());
                            rec_requestlist.setAdapter(adapter);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
            }
        });
    }
}