package com.ambitious.parampara.Activity.others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class BookingDetails_Activity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinerstatus;
    String[] Launguage = {"Please Select Launguage", "English", "Hindi"};
    String spin_val;
    private ImageView img_back;
    private TextView txt_headername, txt_bookingDate, poojaname, txt_time, txt_amount, with_samagri, with_out_samagri;
    String country, landmark, str_poojaname, poojawithsamagri, poojawithoutsamagriprice, str_poojaID, str_uid, str_samagri;
    EditText edit_addres, edit_lanmark, edit_street, edit_pincode;
    RadioButton radio_yes, radio_no;
    RadioGroup radiogrp;
    Button btn_booking;
    Context mContext;
    private EditText edit_location;
    private double latitude;
    private double longitude;
    int checkgo = 0;
    private String poojaDec, poojaImage;
    private String id, city, state, pincode, addressss, lat, lng, userName;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        findID();

        userName = getData(getApplicationContext(), "userName", null);
        city = getData(getApplicationContext(), "city", null);
        pincode = getData(getApplicationContext(), "pincode", null);
        addressss = getData(getApplicationContext(), "address", null);
        lat = getData(getApplicationContext(), "lattitude", null);
        lng = getData(getApplicationContext(), "logitude", null);
        country = getData(getApplicationContext(), "country", null);
        landmark = getData(getApplicationContext(), "landmark", null);

        if (getIntent().getExtras() != null) {
            str_poojaname = getIntent().getExtras().getString("Poojaname");
            poojawithsamagri = getIntent().getExtras().getString("Poojasamagriprice");
            poojawithoutsamagriprice = getIntent().getExtras().getString("Poojawithoutsamagriprice");
            str_poojaID = getIntent().getExtras().getString("poojaID");
            poojaDec = getIntent().getExtras().getString("poojaDec");
            poojaImage = getIntent().getExtras().getString("poojaImage");
            poojaname.setText(str_poojaname.toString().trim());
            with_out_samagri.setText("Without Samagri : " + "Rs " + poojawithoutsamagriprice + " /-");
            with_samagri.setText("With Samagri : " + "Rs " + poojawithsamagri + " /-");
        }
        str_uid = getData(getApplicationContext(), "userID", null);
        spinerstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                spin_val = Launguage[position];//saving the value selected

                try {
                    ((TextView) arg0.getChildAt(0)).setTextColor(Color.BLACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("what is select vehicle__________________________" + spin_val);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        if (addressss != null) {
            edit_location.setText(addressss);
        }

        if (city != null) {
            edit_addres.setText(city);
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
            edit_street.setText(country);
        }

        if (landmark != null) {
            edit_lanmark.setText(landmark);
        }

    }

    private void findID() {


        spinerstatus = findViewById(R.id.spinerstatus);
        edit_location = findViewById(R.id.edit_location);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        with_samagri = findViewById(R.id.with_samagri);
        with_out_samagri = findViewById(R.id.with_out_samagri);
        txt_bookingDate = findViewById(R.id.txt_bookingDate);
        poojaname = findViewById(R.id.poojaname);
        txt_time = findViewById(R.id.txt_time);
        edit_addres = findViewById(R.id.edit_addres);
        edit_lanmark = findViewById(R.id.edit_lanmark);
        edit_street = findViewById(R.id.edit_street);
        edit_pincode = findViewById(R.id.edit_pincode);
        radio_yes = findViewById(R.id.radio_yes);
        radio_no = findViewById(R.id.radio_no);
        radiogrp = findViewById(R.id.radiogrp);
        txt_amount = findViewById(R.id.txt_amount);
        btn_booking = findViewById(R.id.btn_booking);
        txt_headername.setText("Booking Details");
        img_back.setOnClickListener(this);
        txt_time.setOnClickListener(this);
        txt_bookingDate.setOnClickListener(this);
        btn_booking.setOnClickListener(this);
        edit_addres.setOnClickListener(this);


        if (addressss != null) {
            edit_addres.setText(addressss);
        }

        if (city != null) {
            edit_street.setText(city);
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

        edit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(BookingDetails_Activity.this, MapsActivity.class), 103);
            }
        });

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radiogrp.findViewById(checkedId);
                int index = radiogrp.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        str_samagri = "1";
                        txt_amount.setText("Rs " + poojawithsamagri + " /-");
                        break;
                    case 1: // secondbutton
                        str_samagri = "0";
                        txt_amount.setText("Rs " + poojawithoutsamagriprice + " /-");

                        break;
                }
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
        } else if (v == edit_addres) {
            findPlace();
        } else if (v == edit_location) {
            //  edit_location.setEnabled(true);
            if (edit_location.getText().toString().trim().length() == 0) {
                startActivityForResult(new Intent(BookingDetails_Activity.this, MapsActivity.class), 103);

            }
        }
    }

    private void findPlace() {
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
        if (edit_addres.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_addres.setError("Please Enter Address");
        }
        if (edit_lanmark.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_lanmark.setError("Please Enter Landmark");
        }
        if (edit_street.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_street.setError("Please Enter Street");
        }
        if (edit_pincode.getText().toString().equalsIgnoreCase("")) {
            res = false;

            edit_pincode.setError("Please Enter Pincode");
        }
        if (txt_amount.getText().toString().equalsIgnoreCase("")) {
            res = false;

            Toast.makeText(this, "Please Enter Samagri or Not", Toast.LENGTH_SHORT).show();
        }
        if (edit_pincode.getText().toString().length() < 5 && edit_pincode.getText().toString().length() > 6) {
            res = false;

            edit_pincode.setError("Please Enter Valid Pincode");
        }
        if (edit_location.getText().toString().length() < 5) {
            res = false;
            edit_location.setError("Please choose Location");
        }
        if (str_samagri == null) {
            res = false;
            utlity.toast(BookingDetails_Activity.this, "Fill the information!", "e");
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
        datePickerDialog = new DatePickerDialog(BookingDetails_Activity.this, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
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
        mTimePicker = new TimePickerDialog(BookingDetails_Activity.this, R.style.datepicker, new TimePickerDialog.OnTimeSetListener() {
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
        progressDialog = new ProgressDialog(BookingDetails_Activity.this);
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
                            utlity.toast(BookingDetails_Activity.this, object.getString("message"), "s");
                        } else {
                            checkgo = 0;
                            utlity.toast(BookingDetails_Activity.this, "Booking will not proceed with in 24 hr.", "e");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(BookingDetails_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(BookingDetails_Activity.this, "Please Check Internet Connection", "e");
            }
        });
    }


    private void checkPandit() {
        progressDialog = new ProgressDialog(BookingDetails_Activity.this);
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
                            Intent intent = new Intent(BookingDetails_Activity.this, BookingReview_Activity.class);
                            intent.putExtra("Poojaname", str_poojaname);
                            intent.putExtra("poojaID", str_poojaID);
                            intent.putExtra("date", txt_bookingDate.getText().toString());
                            intent.putExtra("time", txt_time.getText().toString());
                            intent.putExtra("address", edit_location.getText().toString() + " " + edit_addres.getText().toString());
                            intent.putExtra("landmark", edit_lanmark.getText().toString());
                            intent.putExtra("street", edit_street.getText().toString());
                            intent.putExtra("pincode", edit_pincode.getText().toString());
                            intent.putExtra("amount", txt_amount.getText().toString());
                            intent.putExtra("samagri", str_samagri);
                            intent.putExtra("latitude", String.valueOf(latitude));
                            intent.putExtra("longitude", String.valueOf(longitude));
                            intent.putExtra("image", poojaImage);
                            startActivity(intent);
                        } else {
                            utlity.toast(BookingDetails_Activity.this, object.getString("message"), "e");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(BookingDetails_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(BookingDetails_Activity.this, "Please Check Internet Connection", "e");
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


                EStoreBookingDetails_Activity eStoreBookingDetails_activity = new EStoreBookingDetails_Activity();
                List<Address> locations = eStoreBookingDetails_activity.getAddress(BookingDetails_Activity.this, latitude, longitude);


                edit_pincode.setText("" + locations.get(0).getPostalCode());
                edit_street.setText("" + locations.get(0).getCountryName());
                edit_location.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality());
                edit_location.setFocusable(true);
                edit_addres.setText(locations.get(0).getSubAdminArea() + ", " + locations.get(0).getAdminArea());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}