package com.ambitious.parampara.Activity.others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ambitious.parampara.Adapter.notificationAdapter;
import com.ambitious.parampara.Model.notificationModel;
import com.ambitious.parampara.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.ambitious.parampara.Other.MySharedPref.addnotificationModel;
import static com.ambitious.parampara.Other.MySharedPref.getnotificationModel;

public class notification extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private int flag = 0;
    private View view;
    private RecyclerView mViewRecycle;
    private SwipeRefreshLayout mSwipehome;
    private ArrayList<notificationModel> dataitem = new ArrayList<>();

    TextView txt_headername, map_view;
    ImageView img_back, noData;

    private notificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_locator_);

        initView();
        onRefresh();
    }

    private void initView() {
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("My Notification");
        map_view = findViewById(R.id.map_view);
        noData = findViewById(R.id.noData);
        map_view.setVisibility(View.VISIBLE);
        map_view.setText("Clear All");
        map_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataitem.clear();
                addnotificationModel(notification.this, dataitem);
                if (notificationAdapter != null) {
                    notificationAdapter.notifyDataSetChanged();
                }


            }
        });
        mViewRecycle = (RecyclerView) findViewById(R.id.rec_cate);
        mSwipehome = (SwipeRefreshLayout) findViewById(R.id.swipe_e_staore);
        mSwipehome.setOnRefreshListener(this);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onRefresh() {
        if (!mSwipehome.isRefreshing()) {
            mSwipehome.setRefreshing(true);
        }
        getNotification();
    }

    private void getNotification() {
        dataitem.clear();
        if (getnotificationModel(notification.this) != null) {
            dataitem = getnotificationModel(notification.this);
            if (dataitem.size() <= 0) {
                noData.setVisibility(View.VISIBLE);
            } else {
                noData.setVisibility(View.GONE);
                notificationAdapter = new notificationAdapter(notification.this, dataitem);
                mViewRecycle.setAdapter(notificationAdapter);
            }
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("No notification find?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(notification.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    })
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
        mSwipehome.setRefreshing(false);

    }

    public void removeNotification(int pos) {
        try {
            dataitem.remove(pos);
            addnotificationModel(notification.this, dataitem);
            notificationAdapter.notifyItemRemoved(pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
