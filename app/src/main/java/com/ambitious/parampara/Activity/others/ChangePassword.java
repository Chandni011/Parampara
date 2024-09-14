package com.ambitious.parampara.Activity.others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    EditText ed_password,ed_old_password,ed_confpassword;
    Button btn_submit;
    CheckBox check_showpassword;
    String ldata, str_uid, strpassword, strConf_EMAIL;
    public static Activity changepass;
    private ImageView img_back;
    private TextView txt_headername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        findID();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // ******for Key board hiding
        changepass = ChangePassword.this;
        str_uid=getData(getApplicationContext(),"userID",null);



        ed_confpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                strpassword = ed_password.getText().toString();
                strConf_EMAIL = ed_confpassword.getText().toString();
                if (editable.length() > 0 && strpassword.length() > 0) {
                    if (!strConf_EMAIL.equals(strpassword)) {
                        // give an error that password and confirm password not match
                        ed_confpassword.setError("Please Enter Confirm Password");

                    }

                }
            }
        });
    }
    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Change Password");
        img_back.setOnClickListener(this);
        ed_password = findViewById(R.id.ed_password);
        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        ed_old_password = findViewById(R.id.ed_old_password);
        ed_confpassword = findViewById(R.id.ed_confpassword);
        check_showpassword = findViewById(R.id.check_showpassword);

        check_showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    ed_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ed_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ed_confpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    ed_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ed_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ed_confpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == img_back) {
            onBackPressed();
        } else if (view == btn_submit) {

            String strpass = ed_password.getText().toString();
            String oldpassword=ed_old_password.getText().toString();
            if (oldpassword.equalsIgnoreCase("")||oldpassword==null){
                ed_old_password.setError("Pleaser Enter Password");
            } else if (strpass.equalsIgnoreCase("") || strpass == null) {
                ed_password.setError("Pleaser Enter Password");
            } else {
                changepasswordcall(str_uid,oldpassword, strpass);
            }
        }
    }


    private void changepasswordcall(final String user_id,String old_password, String password) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().changepasswordapi(user_id, old_password,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("ChangePassword" + object);
                        String message = object.getString("result");
                        if (object.getString("status").equals("1")) {
                            /*final Dialog dialog = new Dialog(ChangePassword.this);
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

                            ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ChangePassword.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                    dialog.dismiss();
                                }
                            });

                            dialog.show();
                            dialog.getWindow().setAttributes(lp);*/
                            Intent intent = new Intent(ChangePassword.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                            utlity.toast(ChangePassword.this, "Password Changed Succesfully","s");

                        } else {
                            utlity.toast(ChangePassword.this, message,"e");
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
                utlity.toast(ChangePassword.this, "Please Check Connection","e");
            }
        });
    }
}
