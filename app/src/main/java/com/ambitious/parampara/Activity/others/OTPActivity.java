package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {

    EditText o1, o2, o3, o4;
    String verify_code="";
    String otp1, otp2, otp3, otp4, str_code, str_uid, str_otp, str_mob;
    Button btnsubmit;
    private TextView txt_resendOTP;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        findID();

        str_uid = getData(getApplicationContext(), "userID", null);
        str_mob = getData(getApplicationContext(), "mob", null);
        //str_otp=getData(getContext(),"OTP",null);


        o1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (o1.getText().toString().length() == 1)
                    o2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otp1 = o1.getText().toString();
                System.out.println("first=====otp==================" + otp1);

            }
        });
        o2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (o2.getText().toString().length() == 1)
                    o3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otp2 = o2.getText().toString();
                System.out.println("second=====otp==================" + otp2);

            }
        });
        o3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (o3.getText().toString().length() == 1)
                    o4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otp3 = o3.getText().toString();
                System.out.println("third=====otp==================" + otp3);

            }
        });
        o4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (o4.getText().toString().length() == 1)
                    o4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                otp4 = o4.getText().toString();
                System.out.println("four=====otp==================" + otp4);

            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                verify_code = otp1 + otp2 + otp3 + otp4;
                System.out.println("sb wali=================" + verify_code);
                if (verify_code.equalsIgnoreCase("") || verify_code == null) {
                    Toast.makeText(OTPActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    OTPVERIFY(str_uid, verify_code);


                }

            }
        });


    }
    private void findID() {
        btnsubmit = findViewById(R.id.btnsubmit);
        o1 = findViewById(R.id.et1);
        o2 = findViewById(R.id.et2);
        o3 = findViewById(R.id.et3);
        o4 = findViewById(R.id.et4);
        txt_resendOTP = findViewById(R.id.txt_resendOTP);
        txt_resendOTP.setOnClickListener(this);


    }

    private void OTPVERIFY(final String user_id, String code) {
        progressDialog = new ProgressDialog(OTPActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().OTPverify(user_id, code);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("OITverification" + object);
                        if (object.getString("status").equals("1")) {
                            String str_result = object.getString("result");
                            JSONObject jsonObject = new JSONObject(str_result);
                            String verifyOTP = jsonObject.getString("otp_verified");
                            if (verifyOTP.equals("1")) {
                                saveData(OTPActivity.this, "ldata", str_result + "");
                                saveData(OTPActivity.this,"type","USER");
                                saveData(OTPActivity.this,"userID",str_uid);
                                startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                                Animatoo.animateSlideLeft(OTPActivity.this);
                                finish();
                                utlity.toast(OTPActivity.this, "OTP Is Correct and Login Successfully","s");

                            } else {
                                final Dialog dialog = new Dialog(OTPActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                dialog.setContentView(R.layout.success_dialog);
                                dialog.setCancelable(true);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                TextView content = dialog.findViewById(R.id.text_msg);
                                content.setText("Your account has been Inactivated...Try Again!");
                                TextView title = dialog.findViewById(R.id.title);
                                title.setVisibility(View.GONE);

                                ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(OTPActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                dialog.getWindow().setAttributes(lp);

                            }
                        }
                    } else ;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                utlity.toast(OTPActivity.this, "Please Check Connection","e");
            }
        });
    }

    private void resendOTP(String mobile) {
        progressDialog = new ProgressDialog(OTPActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().resendOTP(mobile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("OITverification" + object);
                        if (object.getString("status").equals("1")) {
                            utlity.toast(OTPActivity.this, "Code sent succefully","s");
                        }
                    } else ;
                    // AppConfig.showToast("server error");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                utlity.toast(OTPActivity.this, "Please Check Connection","e");

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == txt_resendOTP) {
            resendOTP(str_mob);
        }
    }
}
