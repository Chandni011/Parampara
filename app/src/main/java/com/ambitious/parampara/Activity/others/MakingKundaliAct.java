package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Random;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class MakingKundaliAct extends AppCompatActivity implements View.OnClickListener {

    ImageView backImg;
    TextView tooltxt, pricetxt, pretimetxt, calloptiontxt, dateofbrithtxt, timeofbirthtxt;
    EditText nametxt, placeofbirthtxt, questiontxt, descriptiontxt, contacttxt, emailtxt, addresstxt;
    Button submitbtn;
    Spinner timingspinner, callOptionSpinner, languageoptionSpinner;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();
    private int mYear, mMonth, mDay, mHour, mMinute;
    RadioGroup radiogroup;
    RadioButton radiobutton;
    private ProgressDialog progressDialog;

    String vID, vName, vPrice, userID, currentDate, prefferdTime, callOption, userName, desc, prefferedlanguage;
    String add, em, cont, tob, gender, TraID;
    int check = 0;
    String timeArr[] = {"10:00 am", "11:00 am", "12:00 pm", "01:00 pm", "02:00 pm", "03:00 pm", "04:00 pm", "05:00 pm ", "06:00 pm"};
    String callOptionsArr[] = {"Audio", "Video"};
    String languageArr[] = {"Hindi"};

    RelativeLayout rlquestionlayout, rl_addresslayout;

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_making_kundali);

        userID = getData(getApplicationContext(), "userID", null);
        userName = getData(getApplicationContext(), "userName", null);


        Intent extras = getIntent();
        if (extras != null) {
            vID = extras.getStringExtra("vertualID");
            vName = extras.getStringExtra("vertualName");
            vPrice = extras.getStringExtra("vertuaPrice");
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentDate = df.format(c);

        System.out.println(currentDate + ".........currentDate");

        backImg = findViewById(R.id.img_back);
        tooltxt = findViewById(R.id.txt_headername);
        timingspinner = findViewById(R.id.timing_sppiner);
        nametxt = findViewById(R.id.edit_username);
        placeofbirthtxt = findViewById(R.id.place_of_birth_txt);
        dateofbrithtxt = findViewById(R.id.date_of_birth_txt);
        questiontxt = findViewById(R.id.question_txt);
        descriptiontxt = findViewById(R.id.desription_txt);
        desc = descriptiontxt.getText().toString().trim();
        pricetxt = findViewById(R.id.price_txt);
        submitbtn = findViewById(R.id.btn_profile_submit);
        pretimetxt = findViewById(R.id.prefferd_timeing_txt);
        callOptionSpinner = findViewById(R.id.call_option_spinner);
        languageoptionSpinner = findViewById(R.id.language_sppiner);
        radiogroup = findViewById(R.id.radiogroup);
        timeofbirthtxt = findViewById(R.id.time_of_birth_txt);
        tob = timeofbirthtxt.getText().toString().trim();
        contacttxt = findViewById(R.id.contact);
        cont = contacttxt.getText().toString().trim();
        emailtxt = findViewById(R.id.email);
        em = emailtxt.getText().toString().trim();
        addresstxt = findViewById(R.id.addrss_txt);
        add = addresstxt.getText().toString().trim();
        calloptiontxt = findViewById(R.id.call_optiontxt);
        rlquestionlayout = findViewById(R.id.rl_questionlayout);
        rl_addresslayout = findViewById(R.id.rl_addresslayout);


        if (vPrice != null) {
            pricetxt.setText(symbol + " " + vPrice);
        }

        tooltxt.setText(vName);
        nametxt.setText(userName);

        if (vName.equals("Kundali") || vName.equals("kundali")) {
            rlquestionlayout.setVisibility(View.GONE);
            pretimetxt.setVisibility(View.GONE);
            timingspinner.setVisibility(View.GONE);
            callOptionSpinner.setVisibility(View.GONE);
            calloptiontxt.setVisibility(View.GONE);
            check = 2;
        }

        if(vName.equals("Paramars") || vName.equals("paramars")){
            rl_addresslayout.setVisibility(View.GONE);
        }

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radiobutton = group.findViewById(checkedId);
                gender = radiobutton.getText().toString().trim();
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeArr);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timingspinner.setAdapter(dataAdapter);
        timingspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                prefferdTime = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languageArr);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageoptionSpinner.setAdapter(languageAdapter);
        languageoptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                prefferedlanguage = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> callOptionAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, callOptionsArr);
        callOptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        callOptionSpinner.setAdapter(callOptionAdapter);
        callOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                callOption = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        backImg.setOnClickListener(this);
        submitbtn.setOnClickListener(this);
        dateofbrithtxt.setOnClickListener(this);
        timeofbirthtxt.setOnClickListener(this);

        final Random random = new Random();
        sb = new StringBuilder(8);
        for (int i = 0; i < 10; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        TraID = String.valueOf(sb);
    }


    // implement kundali api
    private void makeKundaliApi(String name, String placeOfBirth, String dateOfBrith) {
        progressDialog = new ProgressDialog(MakingKundaliAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().makekundali(userID, name, currentDate, dateOfBrith,
                placeOfBirth, vPrice, descriptiontxt.getText().toString(), contacttxt.getText().toString(), emailtxt.getText().toString(), gender, timeofbirthtxt.getText().toString(), addresstxt.getText().toString(), prefferedlanguage, TraID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");


                        if (error.equals("1")) {
                            utlity.toast(MakingKundaliAct.this, object.getString("message"), "s");
                            Intent i = new Intent(MakingKundaliAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "kundali");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(MakingKundaliAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(MakingKundaliAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(MakingKundaliAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    // implement paramars api
    private void paramarsApi(String name, String placeOfBirth, String dateOfBrith, String question) {
        progressDialog = new ProgressDialog(MakingKundaliAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().makeParamars(userID, name, currentDate, dateOfBrith, placeOfBirth,
                question, prefferdTime, callOption, vPrice, descriptiontxt.getText().toString(), timeofbirthtxt.getText().toString(), emailtxt.getText().toString(), gender, contacttxt.getText().toString(), prefferedlanguage, TraID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        System.out.println(responedata + "....sss");
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            utlity.toast(MakingKundaliAct.this, object.getString("message"), "s");
                            Intent i = new Intent(MakingKundaliAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "pramars");
                            i.putExtra("tranid", TraID);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(MakingKundaliAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(MakingKundaliAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(MakingKundaliAct.this, "Please Check Internet Connection", "e");
            }
        });
    }


    private void paramarsApigetBooking() {
        progressDialog = new ProgressDialog(MakingKundaliAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().get_paramars_booking(userID);
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
                            int size = jsonArray.length() - 1;
                            String createdDate = jsonArray.getJSONObject(size).getString("date_time");


//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
//
//                            Date date1 = simpleDateFormat.parse(createdDate);
//                            Date date2 = simpleDateFormat.parse(currentDate);
//
//                            long difference = date2.getTime() - date1.getTime();
//                            int days = (int) (difference / (1000 * 60 * 60 * 24));
//                            int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
//                            int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
//                            hours = (hours < 0 ? -hours : hours);
//
//                            System.out.println(hours +"....hhh");

                        } else {
                            utlity.toast(MakingKundaliAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(MakingKundaliAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(MakingKundaliAct.this, "Please Check Internet Connection", "e");
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
            String placeOfBirth = placeofbirthtxt.getText().toString().trim();
            String dateOfBrith = dateofbrithtxt.getText().toString().trim();
            String description = descriptiontxt.getText().toString().trim();
            String timeofbirth = timeofbirthtxt.getText().toString().trim();
            String emial = emailtxt.getText().toString().trim();
            String contac = contacttxt.getText().toString().trim();
            String addre = addresstxt.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                nametxt.setError("Please enter name");
                nametxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(placeOfBirth)) {
                placeofbirthtxt.setError("Please enter Place of birth");
                placeofbirthtxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(dateOfBrith)) {
                dateofbrithtxt.setError("Please enter Date of birth");
                dateofbrithtxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(contac)) {
                contacttxt.setError("Please enter Contact No.");
                contacttxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(emial)) {
                emailtxt.setError("Please enter E-mail");
                emailtxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(timeofbirth)) {
                timeofbirthtxt.setError("Please enter T-O-B");
                timeofbirthtxt.requestFocus();
                return;
            }

            if (check == 2) {
                openDialogbox(name, placeOfBirth, dateOfBrith, "", "k");
            } else {
                String question = questiontxt.getText().toString().trim();

                if (TextUtils.isEmpty(question)) {
                    questiontxt.setError("Please enter your question");
                    questiontxt.requestFocus();
                    return;
                }

                openDialogbox(name, placeOfBirth, dateOfBrith, question, "p");
            }
        } else if (id == R.id.date_of_birth_txt) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            dateofbrithtxt.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (id == R.id.time_of_birth_txt) {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String timeSet = "";
                            if (hourOfDay > 12) {
                                hourOfDay -= 12;
                                timeSet = "PM";
                            } else if (hourOfDay == 0) {
                                hourOfDay += 12;
                                timeSet = "AM";
                            } else if (hourOfDay == 12) {
                                timeSet = "PM";
                            } else {
                                timeSet = "AM";
                            }
                            timeofbirthtxt.setText(hourOfDay + ":" + minute + " " + timeSet);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


    // open dialog box
    private void openDialogbox(final String name, final String placeOfBirth, final String dateOfBrith, final String question, final String k) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure ? your all details are correct.")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (k.equals("p")) {
                            paramarsApi(name, placeOfBirth, dateOfBrith, question);
                        } else {
                            makeKundaliApi(name, placeOfBirth, dateOfBrith);
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
