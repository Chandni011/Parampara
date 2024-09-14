package com.ambitious.parampara.Activity.others;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private ImageView mBackImg;
    FloatingActionButton email,call;
    private TextView txt_headername, name, address, city, country, text_email, text_mobile;
    String str_email, str_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact_us);
        initView();
        getContactUs();
    }

    private void getContactUs() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_admin_profile();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        JSONObject res = object.getJSONObject("result");
                        String Name = res.getString("admin_name");
                        String Address = res.getString("address");
                        String City = res.getString("city");
                        String State = res.getString("state");
                        String Country = res.getString("country");
                        String Pincode = res.getString("pincode");
                        String Email = res.getString("email");
                        String Mobile = res.getString("mobile");
                        //Toast.makeText(getApplicationContext(), ""+object, Toast.LENGTH_SHORT).show();
                        name.setText(Name);
                        address.setText(Address);
                        city.setText(City+", "+State);
                        country.setText(Country+", "+Pincode);
                        text_email.setText(Email);
                        text_mobile.setText(Mobile);
                        str_email = Email;
                        str_mobile = Mobile;

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

    private void initView() {

        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        city = (TextView) findViewById(R.id.city);
        country = (TextView) findViewById(R.id.country);
        text_email = (TextView) findViewById(R.id.text_email);
        text_mobile = (TextView) findViewById(R.id.text_mobile);
        mBackImg = (ImageView) findViewById(R.id.img_back);
        mBackImg.setOnClickListener(this);
        email =(FloatingActionButton)findViewById(R.id.email);
        email.setOnClickListener(this);
        call =(FloatingActionButton)findViewById(R.id.call);
        call.setOnClickListener(this);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Contact Us");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.email) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String[] recipients = { str_email };
            emailIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
            emailIntent.putExtra(Intent.EXTRA_CC, "mailcc@gmail.com");
            emailIntent.setType("text/html");
            emailIntent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(emailIntent, "Send mail"));
        } else if (id == R.id.call) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + str_mobile));
            startActivity(callIntent);
        } else {
            // Handle any default case if needed
        }
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

