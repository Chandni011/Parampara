package com.ambitious.parampara.Activity.others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class DailyPanditSubsAddressAct extends AppCompatActivity implements View.OnClickListener {

    ImageView img_back;
    TextView txt_headername, txt_bookingDate, txt_time, txt_amount, subtxt;
    String country, landmark, userID, currentDate;
    EditText addresstxt, statext, countrytxt, edit_lanmark, edit_pincode, edit_contactpers, edit_mobile, edit_email;
    Button btn_booking;
    private double latitude;
    private double longitude;
    int checkgo = 0;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();
    String city, pincode, addressss, lat, lng, userName, pID, pName, pPrice, type, entryDate;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_pandit_subs_address);


        userName = getData(getApplicationContext(), "userName", null);
        city = getData(getApplicationContext(), "city", null);
        pincode = getData(getApplicationContext(), "pincode", null);
        addressss = getData(getApplicationContext(), "address", null);
        lat = getData(getApplicationContext(), "lattitude", null);
        lng = getData(getApplicationContext(), "logitude", null);
        country = getData(getApplicationContext(), "country", null);
        landmark = getData(getApplicationContext(), "landmark", null);
        userID = getData(getApplicationContext(), "userID", null);


        Intent extras = getIntent();
        if (extras != null) {
            pName = extras.getStringExtra("pName");
            pID = extras.getStringExtra("pID");
            pPrice = extras.getStringExtra("pPrice");
            type = extras.getStringExtra("type");
            entryDate = extras.getStringExtra("entrydate");
            userID = extras.getStringExtra("userID");
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentDate = df.format(c);


        findID();


        if (addressss != null) {
            addresstxt.setText(addressss);
        }

        if (city != null) {
            statext.setText(city);
        }
        if (pincode != null) {
            edit_pincode.setText(pincode);
        }

        if (lat != null) {
            latitude = Double.parseDouble(lat);
        }

        if (lng != null) {
            longitude = Double.parseDouble(lng);
        }

        if (country != null) {
            countrytxt.setText(country);
        }

        if (landmark != null) {
            edit_lanmark.setText(landmark);
        }
    }


    private void findID() {
        addresstxt = findViewById(R.id.edit_location);
        statext = findViewById(R.id.statetxt);
        countrytxt = findViewById(R.id.country_txt);

        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_bookingDate = findViewById(R.id.txt_bookingDate);
        subtxt = findViewById(R.id.subscrption_txt);
        txt_time = findViewById(R.id.txt_time);
        edit_lanmark = findViewById(R.id.edit_lanmark);
        edit_pincode = findViewById(R.id.edit_pincode);
        edit_contactpers = findViewById(R.id.edit_contactpers);
        edit_mobile = findViewById(R.id.edit_mobile);
        edit_email = findViewById(R.id.edit_email);
        txt_amount = findViewById(R.id.txt_amount);
        btn_booking = findViewById(R.id.btn_booking);
        txt_headername.setText("Daily Pandit");
        img_back.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        txt_bookingDate.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        addresstxt.setOnClickListener(this);


        if (addressss != null) {
            addresstxt.setText(addressss);
        }

        if (city != null) {
            statext.setText(city);
        }
        if (pincode != null) {
            edit_pincode.setText(pincode);
        }

        if (lat != null) {
            latitude = Double.parseDouble(lat);
        }

        if (lng != null) {
            longitude = Double.parseDouble(lng);
        }

        if (pName != null) {
            subtxt.setText(pName.toUpperCase());
        }

        if (pPrice != null) {
            txt_amount.setText(symbol + " " + pPrice);
        }

        addresstxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DailyPanditSubsAddressAct.this, MapsActivity.class), 103);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        } else if (v == txt_bookingDate) {
            selectdate();
        } else if (v == txt_time) {
            fromTime();
        } else if (v == btn_booking) {
            validation();
        }
    }


    private void validation() {
        boolean res = true;
        if (txt_bookingDate.getText().toString().equalsIgnoreCase("Date")) {
            res = false;
            txt_bookingDate.setError("Please Select Date");
        }

        if (txt_time.getText().toString().equalsIgnoreCase("Time")) {
            res = false;
            txt_time.setError("Please Select Time");
        }

        if(edit_contactpers.getText().toString().equalsIgnoreCase("")){
            res = false;
            edit_contactpers.setText("Please Enter Name");
        }

        if(edit_email.getText().toString().equalsIgnoreCase("") && isValidEmailId(edit_email.getText().toString())){
            res = false;
            edit_email.setText("Please Enter E-mail");
        }

        if(edit_mobile.getText().toString().length() < 10 && edit_mobile.getText().toString().equalsIgnoreCase("")){
            res = false;
            edit_mobile.setText("Please Enter Contact No.");
        }

        if (countrytxt.getText().toString().equalsIgnoreCase("")) {
            res = false;

            countrytxt.setError("Please Enter Country");
        }

        if (edit_lanmark.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_lanmark.setError("Please Enter Landmark");
        }


        if (statext.getText().toString().equalsIgnoreCase("")) {
            res = false;

            statext.setError("Please Enter Street");
        }


        if (edit_pincode.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_pincode.setError("Please Enter Pincode");
        }


        if (edit_pincode.getText().toString().length() < 5 && edit_pincode.getText().toString().length() > 6) {
            res = false;

            edit_pincode.setError("Please Enter Valid Pincode");
        }


        if (addresstxt.getText().toString().length() < 5) {
            res = false;
            addresstxt.setError("Please choose Location");
        }

        if (res) {
            if (checkgo == 1) {
                checkPandit();
            }

        }
    }


    private void selectdate() {
        final Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        int month = mcurrentTime.get(Calendar.MONTH);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 1;
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(DailyPanditSubsAddressAct.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int mnth = month + 1;
//                if (dayOfMonth == mcurrentTime.get(Calendar.DAY_OF_MONTH)) {
//                    utlity.toast(BookingDetails_Activity.this, "We can't surve today!", "e");
//                } else {
                txt_bookingDate.setText(year + "-" + mnth + "-" + dayOfMonth);
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
        mTimePicker = new TimePickerDialog(DailyPanditSubsAddressAct.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                txt_time.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                checkTime(txt_bookingDate.getText().toString(), txt_time.getText().toString());
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }

    /// check availability

    private void checkTime(String entrydate, String entrytime) {
        progressDialog = new ProgressDialog(DailyPanditSubsAddressAct.this);
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
                            utlity.toast(DailyPanditSubsAddressAct.this, object.getString("message"), "s");
                        } else {
                            checkgo = 0;
                            utlity.toast(DailyPanditSubsAddressAct.this, "Booking will not proceed in 24 hr.", "e");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(DailyPanditSubsAddressAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(DailyPanditSubsAddressAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    private void checkPandit() {
        progressDialog = new ProgressDialog(DailyPanditSubsAddressAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().confrm_range_pandit(longitude, latitude);
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

                            startActivity(new Intent(getApplicationContext(), NewPaymentAct.class)
                                    .putExtra("pID", pID)
                                    .putExtra("pName", pName)
                                    .putExtra("pPrice", pPrice)
                                    .putExtra("type", "dailyPan")
                                    .putExtra("name", edit_contactpers.getText().toString().trim())
                                    .putExtra("mobile", edit_mobile.getText().toString().trim())
                                    .putExtra("email", edit_email.getText().toString().trim())
                                    .putExtra("entrydate", txt_bookingDate.getText().toString().trim())
                                    .putExtra("entrytime", txt_time.getText().toString().trim())
                                    .putExtra("address", addresstxt.getText().toString().trim() +" "+ statext.getText().toString().trim())
                                    .putExtra("city", statext.getText().toString().trim())
                                    .putExtra("pincode", edit_pincode.getText().toString().trim())
                                    .putExtra("lat", lat)
                                    .putExtra("lng", lng)
                                    .putExtra("userID", userID)
                                    .putExtra("currentDate", currentDate));
                        } else {
                            utlity.toast(DailyPanditSubsAddressAct.this, object.getString("message"), "e");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(DailyPanditSubsAddressAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(DailyPanditSubsAddressAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 103) {
            try {
                assert data != null;
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);
                lat = String.valueOf(latitude);
                lng = String.valueOf(longitude);
                System.out.println(latitude);
                System.out.println(longitude);

                EStoreBookingDetails_Activity eStoreBookingDetails_activity = new EStoreBookingDetails_Activity();
                List<Address> locations = eStoreBookingDetails_activity.getAddress(DailyPanditSubsAddressAct.this, latitude, longitude);


                edit_pincode.setText("" + locations.get(0).getPostalCode());
                countrytxt.setText("" + locations.get(0).getCountryName());
                addresstxt.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality());
                addresstxt.setFocusable(true);
                statext.setText(locations.get(0).getSubAdminArea() + ", " + locations.get(0).getAdminArea());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

}