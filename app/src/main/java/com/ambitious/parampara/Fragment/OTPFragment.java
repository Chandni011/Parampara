package com.ambitious.parampara.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ambitious.parampara.Activity.others.HomeActivity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class OTPFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText o1, o2, o3, o4;
    String verify_code = "";
    String otp1, otp2, otp3, otp4, str_code, str_uid, str_otp, str_mob;
    Button btnsubmit;
    private TextView txt_resendOTP;
    private ProgressDialog progressDialog;

    public OTPFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_o_t_p, container, false);
        findID();

        str_uid = getData(getContext(), "userID", null);
        str_mob = getData(getContext(), "mob", null);
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
                    utlity.toast(getActivity(), "Please Enter OTP!", "e");
                } else {
                    OTPVERIFY(str_uid, verify_code);
                }

            }
        });


        return view;
    }

    private void findID() {
        btnsubmit = view.findViewById(R.id.btnsubmit);
        o1 = view.findViewById(R.id.et1);
        o2 = view.findViewById(R.id.et2);
        o3 = view.findViewById(R.id.et3);
        o4 = view.findViewById(R.id.et4);
        txt_resendOTP = view.findViewById(R.id.txt_resendOTP);
        txt_resendOTP.setOnClickListener(this);
    }

    private void OTPVERIFY(final String user_id, String code) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
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
                            final String str_result = object.getString("result");
                            JSONObject jsonObject = new JSONObject(str_result);
                            String verifyOTP = jsonObject.getString("otp_verified");
                            if (verifyOTP.equals("1")) {
                                final Dialog dialog = new Dialog(getActivity());
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                                dialog.setContentView(R.layout.dialog_info);
                                dialog.setCancelable(true);

                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                                TextView content = dialog.findViewById(R.id.content);
                                content.setText("Now login with password.");

                                ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        saveData(getContext(), "ldata", str_result + "");
                                        saveData(getContext(), "type", "USER");
                                        saveData(getContext(), "userID", str_uid);
                                        startActivity(new Intent(getActivity(), HomeActivity.class));
                                        Animatoo.animateSlideLeft(getActivity());
                                        getActivity().finish();
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();
                                dialog.getWindow().setAttributes(lp);

                            } else {
                                utlity.toast(getActivity(), "Otp is correct but your account has been inactivated.Try Again! ", "e");
                            }
                        }
                    } else ;
                    // AppConfig.showToast("server error");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    utlity.toast(getActivity(), "Slow internet connection!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                utlity.toast(getActivity(), "Please Check Connection!", "e");
            }
        });
    }

    private void resendOTP(String mobile) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
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
                            utlity.toast(getActivity(), "Code sent succefully!", "s");
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
                utlity.toast(getActivity(), "Please Check Connection!", "e");

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
