package com.ambitious.parampara.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ambitious.parampara.Activity.others.ForgotPassword_Activity;
import com.ambitious.parampara.Activity.others.HomeActivity;
import com.ambitious.parampara.Activity.others.OTPActivity;
import com.ambitious.parampara.Activity.others.PrivacyPolici;
import com.ambitious.parampara.Activity.others.TermsAndConditionActivity;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Activity.others.MapsActivity.PERMISSION_CALLBACK_CONSTANT;
import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    View view;
    private Button btn_Login;
    private EditText et_email, et_password;
    private String stremail, strpassword, regID;
    private TextView txt_forgot, termconditiontxt, policytxt;
    static String[] permissionsRequired = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
    AlertDialog alert;
    private LoginButton loginButton;
    private static final int PREFERENCE_PRIVATE_MODE = 0;
    SharedPreferences sh_preff;
    SharedPreferences.Editor toEdit, toEdittt;

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    CheckBox checkBox;

    public LoginFragment() {
        // Required empty public constructor
    }

    GoogleSignInClient googleSignInClient;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permissionsRequired[1])) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Access Location permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    alert = alertBuilder.create();

                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                }
                return false;
            } else {

                return true;
            }
        } else {

            return true;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        facebookSDKInitialize();
        sh_preff = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        toEdittt = sh_preff.edit();
        findID();
        regID = getData(getContext(), "regId", null);

        callbackManager = CallbackManager.Factory.create();
        view.findViewById(R.id.btn_fbb).setOnClickListener(this);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        view.findViewById(R.id.google_button).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                //  .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        checkBox = view.findViewById(R.id.check_showpassword);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // hide password
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

        if (checkPermission(getActivity())) {
            if (alert != null) {
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        }


        return view;
    }

    private void findID() {
        btn_Login = view.findViewById(R.id.btn_Login);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        txt_forgot = view.findViewById(R.id.txt_forgot);
        termconditiontxt = view.findViewById(R.id.term_conditiontxt);
        policytxt = view.findViewById(R.id.term_privacytxt);

        btn_Login.setOnClickListener(this);
        txt_forgot.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        termconditiontxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), TermsAndConditionActivity.class);
                startActivity(i);
            }
        });

        policytxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PrivacyPolici.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == btn_Login) {
            validation();

        } else if (v == txt_forgot) {
            startActivity(new Intent(getActivity(), ForgotPassword_Activity.class));
            Animatoo.animateSlideLeft(getActivity());
        }
        int id = v.getId();

        if (id == R.id.btn_fbb) {
            loginButton.performClick();
        } else if (id == R.id.google_button) {
            googleSignIn();
        }
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            String personName = acct.getDisplayName();
            String personPhotoUrl;
            try {
                personPhotoUrl = acct.getPhotoUrl().toString();
            } catch (Exception e) {
                e.printStackTrace();
                personPhotoUrl = "";
            }
            String email = acct.getEmail();
            String socialId = acct.getId();

            SocialLogin(socialId, personName, email, "", regID, "");

        } else {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //**********************Facebook Integration******************************************
    protected void facebookSDKInitialize() {
        FacebookSdk.sdkInitialize(getContext());
        callbackManager = CallbackManager.Factory.create();
    }

    protected void getUserDetails(LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject response,
                            GraphResponse response1) {
                        try {
                            final String socialId = (response.getString("id"));
                            String email = null;
                            final String username = (response.get("name").toString());
                            JSONObject profile_pic_data = new JSONObject(response.get("picture").toString());
                            JSONObject profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
                            //  final String imageUrl = profile_pic_url.getString("url");
                            final String imageUrl = "https://graph.facebook.com/" + socialId + "/picture";
                            System.out.println("User_Image----------" + imageUrl);

                            final String fb_profile_url = "https://graph.facebook.com/" + socialId + "/picture?type=large&redirect=true";

                            try {
                                email = (response.get("email").toString());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("***************" + email);
                            SocialLogin(socialId, username, email, "", regID, "");
                        } catch (Exception e) {

                            e.printStackTrace();

                        }
                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email, picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Logs a custom event when the app is paused
        AppEventsLogger logger = AppEventsLogger.newLogger(getActivity());
        logger.logEvent("App Paused");
    }


    private void SocialLogin(String socialIdString, String usernameString, String emailString, String mobileString, String register_id, String imageUrlString) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ResponseBody> resultCall = AppConfig.loadInterface().social_login(socialIdString, usernameString, emailString, mobileString, "USER", register_id, imageUrlString);
        resultCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        System.out.println("sociallogin" + object);
                        String error = object.getString("status");
                        if (error.equals("1")) {
                            String str_result = object.getString("result");
                            JSONObject jsonObject = new JSONObject(str_result);
                            saveData(getContext(), "ldata", str_result + "");
                            String str_uid = jsonObject.getString("user_id");
                            saveData(getContext(), "type", "USER");
                            saveData(getContext(), "userID", str_uid);
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            Animatoo.animateSlideLeft(getActivity());
                            getActivity().finish();
                            utlity.toast(getActivity(), "Login Successfully!", "s");

                        } else {
                            utlity.toast(getActivity(), "Something went wrong", "e");
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    utlity.toast(getActivity(), "Slow internet connection!", "e");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //   bar.setVisibility(View.GONE);
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(getActivity(), "Please Check Connection!", "e");

                // showErrorDialog();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        utlity.toast(getActivity(), "Connection failed!", "e");
    }

    private void validation() {
        stremail = et_email.getText().toString();
        strpassword = et_password.getText().toString();
        if (stremail.equalsIgnoreCase("") || stremail == null) {
            et_email.setError("Please Enter Email Or Number");
        } else if (strpassword.equalsIgnoreCase("") || strpassword == null) {
            et_password.setError("Please Enter Password");
        } else {
            LoginCall(stremail, strpassword, regID, "USER");
        }

    }

    /*{"result":{"user_id":"59","username":"priya","mobile":"7905531241",
    "email":"priyasparsh95@gmail.com","address":"hjjj",
    "logitude":"","lattitude":"","password":"Priya@05",
    "alternate_no":"9889146893","gender":"","city":"Kanpur Nagar",
    "state":"Uttar Pradesh","social_type":"","social_id":"",
    "image":"http:\/\/officialastitva.com\/uploads\/user\/1.png","aadhar_no":"",
    "aadhar_image":"http:\/\/officialastitva.com\/","dob":"","country":"",
    "pincode":"","skills":"","account_type":"","pancard_no":"","degree":"","bank_ac_no":"
    ","experience":"","ifsc_code":"","acc_holder_name":"","bank_name":"","bio":"",
    "type":"USER","status":"1",
    "register_id":"dzPSzAneQC2eCDFgbdK-Hz:APA91bFgF5QiHUmi8ol8s9R57wdr5hjam_6X0mv6
    hoX9yaQUkzM3dJF-1mcDwwBclgde1_s7qmLZxyz5JMWedhNjYYBgaDf7S4Vk4qQN8jqCHvanUt9q7qgO_b7qoCT
    6q7hsr89Kuj_z","pooja":"","otp":"4954","otp_verified":"1",
    "approved":"1","entrydt":"2020-06-25 11:34:23","kundali_status":"1","paramars_status":"0"},
    "message":"success","status":1}
     */

    private void LoginCall(final String email, String password, String register_id, String type) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().Login(email, password, register_id, type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        String message = object.getString("result");
                        System.out.println("Login" + object);
                        if (object.getString("status").equals("1")) {
                            String str_result = object.getString("result");
                            JSONObject jsonObject = new JSONObject(str_result);
                            String str_uid = jsonObject.getString("user_id");
                            String verifyOTP = jsonObject.getString("otp_verified");
                            String str_mobile = jsonObject.getString("mobile");
                            String userName = jsonObject.getString("username");
                            String city = jsonObject.getString("city");
                            String pincode = jsonObject.getString("pincode");
                            String address = jsonObject.getString("address");
                            String lattitude = jsonObject.getString("lattitude");
                            String logitude = jsonObject.getString("logitude");
                            if (verifyOTP.equals("1")) {
                                saveData(getContext(), "ldata", str_result + "");
                                saveData(getContext(), "type", "USER");
                                saveData(getContext(), "userID", str_uid);
                                saveData(getContext(), "userName", userName);
                                saveData(getContext(), "city", city);
                                saveData(getContext(), "pincode", pincode);
                                saveData(getContext(), "address", address);
                                saveData(getContext(), "lattitude", lattitude);
                                saveData(getContext(), "logitude", logitude);
                                saveData(getContext(), "mob", str_mobile);

                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                Animatoo.animateSlideLeft(getActivity());
                                getActivity().finish();
                                utlity.toast(getActivity(), "Login Successfully!", "s");

                            } else {
                                saveData(getActivity(), "userID", str_uid);
                                saveData(getActivity(), "mob", str_mobile);
                                startActivity(new Intent(getActivity(), OTPActivity.class));
                                Animatoo.animateSlideLeft(getActivity());
                                getActivity().finish();
                                utlity.toast(getActivity(), "Your account is Inactive...please active your account!", "e");

                            }

                        } else {
                            utlity.toast(getActivity(), message, "e");
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
                utlity.toast(getActivity(), "Please Check Internet Connection!", "e");

            }
        });
    }


}
