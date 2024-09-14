package com.ambitious.parampara.Activity.others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ambitious.parampara.Adapter.BhajanMandal_Adapter;
import com.ambitious.parampara.Model.BhajanMandalModal;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class BhavyaAjojanFormAct extends AppCompatActivity implements View.OnClickListener {

    ImageView backImg;
    TextView tooltxt, pricetxt, entrydatetxt, entrytimetxt, addresstxt;
    EditText ayojantxt, nametxt, pincodetxt, citytxt;
    Button submitbtn;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();
    private int mYear, mMonth, mDay, mHour, mMinute;
    double latitude = 0.0, longitude = 0.0;
    int checkgo = 0;
    private ProgressDialog progressDialog;


    String pID, pName, pPrice, userID, currentDate, entrydate, entrytime, type, userName,
            city, pincode, address, lat, lng, mandalCheck, ayojanCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhavya_ajojan_form);

        userID = getData(getApplicationContext(), "userID", null);
        userName = getData(getApplicationContext(), "userName", null);
        city = getData(getApplicationContext(), "city", null);
        pincode = getData(getApplicationContext(), "pincode", null);
        address = getData(getApplicationContext(), "address", null);
        lat = getData(getApplicationContext(), "lattitude", null);
        lng = getData(getApplicationContext(), "logitude", null);
        mandalCheck = getData(getApplicationContext(), "mandalCheck", null);
        ayojanCheck = getData(getApplicationContext(), "ayojanCheck", null);


        Intent extras = getIntent();
        if (extras != null) {
            pID = extras.getStringExtra("pID");
            pName = extras.getStringExtra("pname");
            pPrice = extras.getStringExtra("pprice");
            type = extras.getStringExtra("typeName");
        }


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // SimpleDateFormat tim = new SimpleDateFormat("HH:mm aa");
        currentDate = df.format(c);
        // currentTime = tim.format(c);


        backImg = findViewById(R.id.img_back);
        tooltxt = findViewById(R.id.txt_headername);

        nametxt = findViewById(R.id.edit_username);
        ayojantxt = findViewById(R.id.ayojan_txt);
        addresstxt = findViewById(R.id.address_txt);
        entrydatetxt = findViewById(R.id.entry_datetxt);
        pincodetxt = findViewById(R.id.pin_code_Txt);
        citytxt = findViewById(R.id.city_txt);
        pricetxt = findViewById(R.id.price_txt);
        entrytimetxt = findViewById(R.id.time_txt);

        submitbtn = findViewById(R.id.btn_profile_submit);


        if (pPrice != null) {
            pricetxt.setText(symbol + " " + pPrice);
            ayojantxt.setText(pName);
            ayojantxt.setHint(type);
        }

        tooltxt.setText(type);

        if (userName != null) {
            nametxt.setText(userName);
        }

        if (address != null) {
            addresstxt.setText(address);
        }

        if (city != null) {
            citytxt.setText(city);
        }
        if (pincode != null) {
            pincodetxt.setText(pincode);
        }

        if (lat != null) {
            latitude = Double.parseDouble(lat);
        }

        if (lng != null) {
            longitude = Double.parseDouble(lng);
        }

        backImg.setOnClickListener(this);
        submitbtn.setOnClickListener(this);
        entrydatetxt.setOnClickListener(this);
        entrytimetxt.setOnClickListener(this);
        addresstxt.setOnClickListener(this);
    }


    /// check availability

    private void checkTime(String entrydate, String entrytime) {
        progressDialog = new ProgressDialog(BhavyaAjojanFormAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().check_available_time(entrydate, entrytime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "......response");
                        JSONObject object = new JSONObject(responedata);
                        int status = object.getInt("status");


                        if (status == 1) {
                            checkgo = 1;
                            utlity.toast(BhavyaAjojanFormAct.this, object.getString("message"), "s");
                        } else {
                            checkgo = 0;
                            utlity.toast(BhavyaAjojanFormAct.this, "Booking will not proceed with in 24 hr.", "e");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(BhavyaAjojanFormAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(BhavyaAjojanFormAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_back) {
            finish();
        } else if (id == R.id.btn_profile_submit) {
            String name = nametxt.getText().toString().trim();
            String address = addresstxt.getText().toString().trim();
            String pincode = pincodetxt.getText().toString().trim();
            String city = citytxt.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                nametxt.setError("Please enter name");
                nametxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(address)) {
                addresstxt.setError("Please enter address");
                addresstxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(pincode)) {
                pincodetxt.setError("Please enter pincode");
                pincodetxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(city)) {
                citytxt.setError("Please enter city");
                citytxt.requestFocus();
                return;
            }

            openDialogbox(name, address, pincode, city);
        } else if (id == R.id.entry_datetxt) {
            selectdate(); // Ensure this method is correctly implemented

        } else if (id == R.id.address_txt) {
            startActivityForResult(new Intent(BhavyaAjojanFormAct.this, MapsActivity.class), 103);

        } else if (id == R.id.time_txt) {
            fromTime(); // Ensure this method is correctly implemented

        } else {
            // Optional: Handle unexpected cases
        }
    }




    private void selectdate() {
        final Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 1;
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(BhavyaAjojanFormAct.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mnth = month + 1;
//                if (dayOfMonth == mcurrentTime.get(Calendar.DAY_OF_MONTH)) {
//                    utlity.toast(BookingDetails_Activity.this, "We can't surve today!", "e");
//                } else {
                entrydatetxt.setText(year + "-" + mnth + "-" + dayOfMonth);
                //  }


            }
        }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void fromTime() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(BhavyaAjojanFormAct.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                entrytimetxt.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                checkTime(entrydatetxt.getText().toString(), entrytimetxt.getText().toString());
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 103) {
            try {
                assert data != null;
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                List<Address> locations = getAddress(BhavyaAjojanFormAct.this, latitude, longitude);

                    pincodetxt.setText("" + locations.get(0).getPostalCode());
                    citytxt.setText(locations.get(0).getLocality());
                    // edit_location.setFocusable(true);
                    if (locations.get(0).getAddressLine(0) != null) {
                        addresstxt.setText(locations.get(0).getAddressLine(0));
                    } else {
                        addresstxt.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality() +
                                ", " + locations.get(0).getAdminArea());
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private List<Address> getAddress(BhavyaAjojanFormAct context, double latitude, double longitude) {
        List<Address> addresses = null;
        String address1 = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address1 = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    // open dialog box
    private void openDialogbox(final String name, final String address, final String pincode, final String city) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure ? your all details are correct.")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        if (checkgo == 1) {
                            Intent intent = new Intent(getApplicationContext(), NewPaymentAct.class);
                            intent.putExtra("pID", pID);
                            intent.putExtra("pName", pName);
                            intent.putExtra("userID", userID);
                            intent.putExtra("name", name);
                            intent.putExtra("entrydate", entrydatetxt.getText().toString().trim());
                            intent.putExtra("entrytime", entrytimetxt.getText().toString().trim());
                            intent.putExtra("pPrice", pPrice);
                            intent.putExtra("address", address);
                            intent.putExtra("pincode", pincode);
                            intent.putExtra("city", city);
                            intent.putExtra("currentDate", currentDate);
                            intent.putExtra("type", type);
                            intent.putExtra("lat", String.valueOf(latitude));
                            intent.putExtra("lng", String.valueOf(longitude));
                            startActivity(intent);
                            finish();
                        }
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
}
