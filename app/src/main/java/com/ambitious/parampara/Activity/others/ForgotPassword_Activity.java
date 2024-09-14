package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword_Activity extends AppCompatActivity implements View.OnClickListener {
    EditText ed_emailID;
    Button btn_submit;
    private ImageView img_back;
    private TextView txt_headername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        ed_emailID = findViewById(R.id.et_forgotphone);

        btn_submit = findViewById(R.id.btn_forgotpasxsword);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Forgot Password");
        img_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == img_back) {
            onBackPressed();
        } else if (view == btn_submit) {
            String strchangepassword = ed_emailID.getText().toString();
            if (strchangepassword.equalsIgnoreCase("") || strchangepassword == null) {
                ed_emailID.setError("Please Enter Number");
            } else {
                changepasswordcall(strchangepassword);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(this);

    }

    private void changepasswordcall(final String mobile) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ForgotPassword_Activity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().forgetpassword(mobile);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("ForgetPassword" + object);
                        String message = object.getString("result");
                        if (object.getString("status").equals("1")) {
                            final Dialog dialog = new Dialog(ForgotPassword_Activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                            dialog.setContentView(R.layout.success_dialog);
                            dialog.setCancelable(true);

                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                            TextView content = dialog.findViewById(R.id.text_msg);
                            content.setText("Your password changed successfully!");
                            TextView title = dialog.findViewById(R.id.title);
                            title.setVisibility(View.GONE);

                            dialog.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ForgotPassword_Activity.this, LoginMode_Activity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                            dialog.getWindow().setAttributes(lp);
                            utlity.toast(ForgotPassword_Activity.this, "Your password is reset please enter new password ","s");

                        } else {
                            utlity.toast(ForgotPassword_Activity.this, message,"e");

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
                utlity.toast(ForgotPassword_Activity.this, "Please Check Connection","e");

            }
        });
    }
}
