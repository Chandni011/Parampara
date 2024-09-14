package com.ambitious.parampara.Activity.others;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsAndConditionActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView mBackImg;
    TextView termstxt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);
        initView();

        termMethod();
    }


    private void termMethod() {
        progressDialog = new ProgressDialog(TermsAndConditionActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().get_terms();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            JSONArray jsonArray = object.getJSONArray("result");
                            String terms = jsonArray.getJSONObject(0).getString("terms");
                            termstxt.setText(terms);

                        } else {
                            utlity.toast(TermsAndConditionActivity.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(TermsAndConditionActivity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(TermsAndConditionActivity.this, "Please Check Internet Connection", "e");
            }
        });
    }

    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.img_back);
        termstxt = (TextView) findViewById(R.id.term_txt);
        mBackImg.setOnClickListener(this);

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
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {

    }
}

