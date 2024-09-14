package com.ambitious.parampara.Activity.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ambitious.parampara.Model.nativePlace;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.GPSTracker;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TempleLocator_Activity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView txt_headername;
    ImageView img_back;

    private RecyclerView mCateRec;
    private SwipeRefreshLayout mEStaoreSwipe;
    private TextView mapView;
    ArrayList<nativePlace.Location> task = new ArrayList<>();
    public double latitiude;
    public double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_locator_);
        findId();
        onRefresh();
    }



    private void findId() {
        txt_headername = findViewById(R.id.txt_headername);
        mapView = findViewById(R.id.map_view);
        mapView.setVisibility(View.GONE);
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TempleLocator_Activity.this,MapsActivity.class).putExtra("mapLoc",task));
                Animatoo.animateSlideLeft(TempleLocator_Activity.this);

            }
        });
        txt_headername.setText("Temple Locater");
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCateRec = (RecyclerView) findViewById(R.id.rec_cate);
        mEStaoreSwipe = (SwipeRefreshLayout) findViewById(R.id.swipe_e_staore);
        mEStaoreSwipe.setOnRefreshListener(this);



    }

    @Override
    public void onRefresh() {
        if(!mEStaoreSwipe.isRefreshing()){
            mEStaoreSwipe.setRefreshing(true);
        }


        GPSTracker gpsTracker = new GPSTracker(TempleLocator_Activity.this);

        if (gpsTracker.canGetLocation()) {
           latitiude= gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
            gpsTracker.getLocation();
        }

        getNearByPlace( gpsTracker.getLatitude(),gpsTracker.getLongitude(),"Tample");

    }


    private void getNearByPlace(final Double latitude,Double longitude, String type) {

        HashMap<String, String> params = new HashMap<>();
        params.put("location",latitude+","+longitude);
        params.put("radius","1500");
        params.put("keyword",type);
        params.put("key","AIzaSyDILYQC5PYXYdvTCjttiKZj4bBF7LT7vtQ");


        System.out.println("fsafa params :::  "+params.toString());
//        ""+String.valueOf(latitude)+","+String.valueOf(longitude),""+type
        Call<nativePlace> call = AppConfig.loadInterface().getNearbyPlace(""+String.valueOf(latitude)+","+String.valueOf(longitude),""+type);
        call.enqueue(new Callback<nativePlace>() {
            @Override
            public void onResponse(Call<nativePlace> call, Response<nativePlace> response) {
                mEStaoreSwipe.setRefreshing(false);

                if (response.isSuccessful()) {

                    System.out.println("safasdf::::  "+response.body().getResults().toString());

                    for(int i=0;i<response.body().getResults().size();i++){
                        task.add(response.body().getResults().get(i).getGeometry().getLocation());

                    }
                    mapView.callOnClick();
                    finish();
//                    map_place_adapter map_place_adapter= new map_place_adapter(TempleLocator_Activity.this, (ArrayList<nativePlace.Results>) response.body().getResults());
//
//                    mCateRec.setAdapter(map_place_adapter);
//                    mapView.setVisibility(View.VISIBLE);

                } else {

                    Toast.makeText(TempleLocator_Activity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<nativePlace> call, Throwable t) {
                mEStaoreSwipe.setRefreshing(false);

                Toast.makeText(TempleLocator_Activity.this, "Please check connection", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }




}
