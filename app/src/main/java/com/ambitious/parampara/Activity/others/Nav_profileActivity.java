package com.ambitious.parampara.Activity.others;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.GPSTracker;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetProfile_Response;
import com.ambitious.parampara.Service.utlity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
//import com.vansuita.pickimage.bean.PickResult;
//import com.vansuita.pickimage.bundle.PickSetup;
//import com.vansuita.pickimage.dialog.PickImageDialog;
//import com.vansuita.pickimage.listeners.IPickCancel;
//import com.vansuita.pickimage.listeners.IPickResult;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class Nav_profileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_back;
    private TextView txt_headername;
    private Button btn_profile_submit;
    private CircleImageView set_profile_image;
    private String str_imagepath, strUser_ID;
    private EditText edit_username, edit_email, edit_phonenumber, edit_alternatephonenumber, edit_address, edit_city, edit_state;
    private MultipartBody.Part multi_driver_image;
    private String ldata;
    CheckBox EditServiceLocationCB;
    private EditText edit_location;
    private EditText edit_country;
    private EditText edit_pincode;
    int flag = 0;
    private double latitude;
    private double longitude;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_profile);
        getTrack();
        findID();
        strUser_ID = getData(getApplicationContext(), "userID", null);
        GetProfile(strUser_ID);
    }

    private void getTrack() {
        GPSTracker gpsTracker = new GPSTracker(Nav_profileActivity.this);
        if (gpsTracker.canGetLocation()) {
            gpsTracker.getLatitude();
            gpsTracker.getLongitude();
            gpsTracker.getLocation();
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();
        }
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        set_profile_image = findViewById(R.id.setprofileimage);
        btn_profile_submit = findViewById(R.id.btn_profile_submit);
        edit_username = findViewById(R.id.edit_username);
        edit_email = findViewById(R.id.edit_email);
        edit_phonenumber = findViewById(R.id.edit_phonenumber);
        edit_alternatephonenumber = findViewById(R.id.edit_alternatephonenumber);
        edit_state = findViewById(R.id.edit_state);
        edit_address = findViewById(R.id.edit_address);
        edit_city = findViewById(R.id.edit_city);

        edit_location = findViewById(R.id.edit_location);
        edit_country = findViewById(R.id.edit_country);
        edit_pincode = findViewById(R.id.edit_pincode);


        edit_location.setEnabled(false);
        edit_country.setEnabled(false);
        edit_pincode.setEnabled(false);
        edit_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Nav_profileActivity.this, MapsActivity.class), 103);
            }
        });


        EditServiceLocationCB = findViewById(R.id.EditServiceLocationCB);
        txt_headername.setText("Profile");
        img_back.setOnClickListener(this);
        btn_profile_submit.setOnClickListener(this);
        set_profile_image.setOnClickListener(this);

        EditServiceLocationCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    edit_username.setEnabled(true);
                    edit_phonenumber.setEnabled(true);
                    edit_address.setEnabled(true);
                    edit_email.setEnabled(true);
                    edit_alternatephonenumber.setEnabled(true);
                    edit_city.setEnabled(true);
                    edit_state.setEnabled(true);
                    edit_location.setEnabled(true);
                    edit_country.setEnabled(true);
                    edit_pincode.setEnabled(true);

                } else {
                    edit_email.setEnabled(false);
                    edit_username.setEnabled(false);
                    edit_phonenumber.setEnabled(false);
                    edit_address.setEnabled(false);
                    edit_alternatephonenumber.setEnabled(false);
                    edit_city.setEnabled(false);
                    edit_state.setEnabled(false);
                    edit_location.setEnabled(false);
                    edit_country.setEnabled(false);
                    edit_pincode.setEnabled(false);
                }


            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        } else if (v == set_profile_image) {
            selectImage();
        } else if (v == btn_profile_submit) {
            validation();
        }
    }

    private void validation() {

        File file;
        System.out.println("------------------- " + str_imagepath);
        if (str_imagepath != null) {
            file = new File(str_imagepath);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            multi_driver_image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            editProfile(strUser_ID,
                    edit_username.getText().toString(),
                    edit_email.getText().toString(),
                    edit_phonenumber.getText().toString(),
                    edit_alternatephonenumber.getText().toString(),
                    edit_address.getText().toString(),
                    edit_city.getText().toString(),
                    edit_state.getText().toString(),
                    edit_pincode.getText().toString(),
                    edit_country.getText().toString(),
                    edit_location.getText().toString(),
                    latitude,
                    longitude,
                    multi_driver_image);
        } else {
            editProfile(strUser_ID,
                    edit_username.getText().toString(),
                    edit_email.getText().toString(),
                    edit_phonenumber.getText().toString(),
                    edit_alternatephonenumber.getText().toString(),
                    edit_address.getText().toString(),
                    edit_city.getText().toString(),
                    edit_state.getText().toString(),
                    edit_pincode.getText().toString(),
                    edit_country.getText().toString(),
                    edit_location.getText().toString(),
                    latitude,
                    longitude,
                    multi_driver_image);
        }
    }

    private void selectImage() {
        ImagePicker.with(this)
                .crop()                    // Crop image(Optional)
                .compress(1024)            // Compress image size (max 1mb)
                .maxResultSize(1080, 1080) // Max image resolution (default is 1080x1080)
                .start();
    }

    // Handle the result from the image picker




    private void GetProfile(String user_id) {

        progressDialog = new ProgressDialog(Nav_profileActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().GetProfile(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("GetProfile" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            GetProfile_Response successResponse = gson.fromJson(responedata, GetProfile_Response.class);
                            edit_username.setText(successResponse.getResult().getUsername());
                            edit_email.setText(successResponse.getResult().getEmail());
                            edit_phonenumber.setText(successResponse.getResult().getMobile());
                            edit_location.setText(successResponse.getResult().getAddress());
                            edit_alternatephonenumber.setText(successResponse.getResult().getAlternateNo());
                            edit_city.setText(successResponse.getResult().getCity());
                            edit_state.setText(successResponse.getResult().getState());
                            edit_country.setText(successResponse.getResult().getCountry());
                            edit_pincode.setText(successResponse.getResult().getPincode());
                            edit_address.setText(successResponse.getResult().getLandmark());

                            if (successResponse.getResult().getImage() != null) {
                                if (successResponse.getResult().getImage().contains("http://vedicparampara.com/panel")) {
                                    multi_driver_image = MultipartBody.Part.createFormData("image", "" +
                                            successResponse.getResult().getImage().replace("http://vedicparampara.com/panel", ""));
                                }
                                System.out.println("asfasdf:::::  " + successResponse.getResult().getImage());

                            } else {
                                multi_driver_image = MultipartBody.Part.createFormData("image", "");
                            }

                            try {
                                Picasso.get()
                                        .load(successResponse.getResult().getImage())
                                        .error(R.drawable.ut)
                                        .into(set_profile_image);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    utlity.toast(Nav_profileActivity.this, "Some Error Occur!", "e");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                finish();
                utlity.toast(Nav_profileActivity.this, "Please check connection", "e");

                t.printStackTrace();
            }
        });

    }

    private void editProfile(String user_id, String username, String email, String mobile, String alternate_no, String landmark, String city, String state,
                             String pincode, String country, String address, double latitude, double longitude, MultipartBody.Part body) {

        progressDialog = new ProgressDialog(Nav_profileActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().UpdateProfile(user_id,
                username, email, mobile, alternate_no,
                address, city, state, landmark, country, pincode, String.valueOf(latitude), String.valueOf(longitude), body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("EditProfile" + object);
                        if (error.equals("1")) {
                            finish();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    utlity.toast(Nav_profileActivity.this, "Something went wrong!", "e");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                utlity.toast(Nav_profileActivity.this, "Please check connection", "e");
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 103) {
            try {
                assert data != null;

                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);


                EStoreBookingDetails_Activity eStoreBookingDetails_activity = new EStoreBookingDetails_Activity();
                List<Address> locations = eStoreBookingDetails_activity.getAddress(Nav_profileActivity.this, latitude, longitude);

                edit_pincode.setText("" + locations.get(0).getPostalCode());
                edit_country.setText("" + locations.get(0).getCountryName());
                edit_state.setText("" + locations.get(0).getAdminArea());
                edit_city.setText("" + locations.get(0).getSubAdminArea());

                edit_location.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality());
                edit_location.setFocusable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            set_profile_image.setImageURI(imageUri);
            str_imagepath = imageUri.getPath();
            Log.e("Imagepath", str_imagepath);
        }
    }
}
