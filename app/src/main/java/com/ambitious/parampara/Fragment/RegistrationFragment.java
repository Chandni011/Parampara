package com.ambitious.parampara.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Activity.others.PrivacyPolici;
import com.ambitious.parampara.Activity.others.TermsAndConditionActivity;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Fragment.Registration_mainLayout.addFragment;
import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    View view;
    Button btn_signup;
    private EditText ed_username, ed_email, ed_phone, ed_password, ed_cpassword;
    private String str_username, str_email, str_phone, str_password, regID = "", strcpassword;
    CheckBox checkBox;
    TextView termconditiontxt, privacytxt;
    private ProgressDialog progressDialog;

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);

        findID();
        regID = getData(getContext(), "regId", null);

        ed_cpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                str_password = ed_password.getText().toString();
                strcpassword = ed_cpassword.getText().toString();
                if (editable.length() > 0 && str_password.length() > 0) {
                    if (!strcpassword.equals(str_password)) {
                        // give an error that password and confirm password not match
                        ed_cpassword.setError("Please Enter Confirm Password");

                    }

                }
            }
        });
        return view;
    }

    private void findID() {
        btn_signup = view.findViewById(R.id.btn_signup);

        ed_username = view.findViewById(R.id.ed_username);
        ed_email = view.findViewById(R.id.ed_email);
        ed_phone = view.findViewById(R.id.ed_phone);
        ed_password = view.findViewById(R.id.ed_password);
        checkBox = view.findViewById(R.id.check_showpassword);
        ed_cpassword = view.findViewById(R.id.ed_cpassword);
        termconditiontxt = view.findViewById(R.id.term_conditiontxt);
        privacytxt = view.findViewById(R.id.term_privacytxt);

        btn_signup.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    ed_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ed_cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    ed_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ed_cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        termconditiontxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TermsAndConditionActivity.class);
                startActivity(i);
            }
        });
        privacytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PrivacyPolici.class);
                startActivity(i);
            }
        });
        // txt_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_signup) {
            validation();

        }/*else if (v==txt_password){
            addFragment(new ForgotPasswordFragment(), false);
        }*/
    }

    private void validation() {
        str_username = ed_username.getText().toString();
        str_phone = ed_phone.getText().toString();
        str_password = ed_password.getText().toString();
        str_email = ed_email.getText().toString();
        if (str_username.equalsIgnoreCase("") || str_username == null) {
            ed_username.setError("Please Enter Username");
        } else if (!isValidEmailId(str_email)) {
            ed_email.setError("Please Enter Valid Email Address");
        } else if (str_phone.equalsIgnoreCase("") || str_phone == null || str_phone.length() < 10) {
            ed_phone.setError("Please Enter Valid Number");
        } else if (!isValidPassword(str_password)) {
            Toast.makeText(getActivity(), "Password must contain mix of upper and lower case letters as well as digits and one special charecter", Toast.LENGTH_SHORT).show();
        } else if (str_password.length() < 6) {
            Toast.makeText(getActivity(), "Password Should Be 6 - 8 Character", Toast.LENGTH_SHORT).show();
        } else if (strcpassword.equalsIgnoreCase("")) {
            ed_cpassword.setError("Please Enter Confirm Password");
        } else {
            SignUpCall(str_username, str_email, str_phone, str_password, regID, "USER");
        }
    }


    private void SignUpCall(final String username, String email, String phone, String password, String register_id, String type) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().signup(username, email, phone, password, register_id, type,"0.0","0.0");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("signup" + object);
                        String message = object.getString("result");
                        if (object.getString("status").equals("1")) {
                            String str_result = object.getString("result");
                            JSONObject jsonObject = new JSONObject(str_result);
                            String str_uid = jsonObject.getString("user_id");
                            String str_mobile = jsonObject.getString("mobile");
                            //  String str_code = jsonObject.getString("code");
                            saveData(getContext(), "userID", str_uid);
                            saveData(getActivity(), "mob", str_mobile);
                            // saveData(getContext(),"code",str_code);
                            addFragment(new OTPFragment(), false);
                            utlity.toast(getActivity(), "Registration Successfully!", "s");
                        } else {
                            utlity.toast(getActivity(), message, "e");
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
                utlity.toast(getActivity(), "Please Check Connection", "e");
            }
        });
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

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
