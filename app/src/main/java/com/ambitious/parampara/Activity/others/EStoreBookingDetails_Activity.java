package com.ambitious.parampara.Activity.others;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class EStoreBookingDetails_Activity extends AppCompatActivity {

    Button proceed;
    EditText addresstxt, landmarktxt, pincodetxt, colonytxt, locationtxt;
    String transID, prod_price;
    TextView product_name, amount, txt_headername, estore_txt_quanity, estore_txt_amount;
    private double latitude;
    private double longitude;
    private String country, landmark, city, state, pincode, address, lat, lng, userName, totalCommistion, totalAmt, currentDate;
    ImageView img_back, img_online, img_cash;
    LinearLayout is_samagri;
    CardView cv_online, cv_cash;
    int Payment_mode = 0;
    int flag = 0;
    private String qnt = "";
    ArrayList<GetSubCategory_Result> product = new ArrayList<>();
    ArrayList<GetSubCategory_Result> product2 = new ArrayList<>();
    private ProgressDialog progressDialog;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    StringBuilder sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_store_booking_details_);

        userName = getData(getApplicationContext(), "userName", null);
        city = getData(getApplicationContext(), "city", null);
        country = getData(getApplicationContext(), "country", null);
        landmark = getData(getApplicationContext(), "landmark", null);

        pincode = getData(getApplicationContext(), "pincode", null);
        address = getData(getApplicationContext(), "address", null);
        lat = getData(getApplicationContext(), "lattitude", null);
        lng = getData(getApplicationContext(), "logitude", null);

        findId();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // SimpleDateFormat tim = new SimpleDateFormat("HH:mm aa");
        currentDate = df.format(c);

        if (getIntent().getExtras() != null) {
            prod_price = getIntent().getExtras().getString("price");
            qnt = getIntent().getExtras().getString("qnt");
            amount.setText("Rs " + prod_price);
            estore_txt_quanity.setText("" + qnt);

        }
        if (getIntent().getSerializableExtra("allData") != null) {
            product.addAll((Collection<? extends GetSubCategory_Result>) getIntent().getSerializableExtra("allData"));
            product2.addAll(product);
        }


        if (address != null) {
            locationtxt.setText(address);
        }

        if (city != null) {
            addresstxt.setText(city);
        }
        if (pincode != null) {
            pincodetxt.setText(pincode);
        }

        if (lat != null) {
            latitude = Double.parseDouble(lat);
        }

        if (lng != null) {
            longitude = Double.parseDouble(lng);
        }

        if (country != null) {
            colonytxt.setText(country);
        }

        if (landmark != null) {
            landmarktxt.setText(landmark);
        }

        getcommisionApi();


        final Random random = new Random();
        sb = new StringBuilder(8);
        for (int i = 0; i < 10; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        transID = String.valueOf(sb);


    }


    private void findId() {

        addresstxt = findViewById(R.id.edit_addres);
        is_samagri = findViewById(R.id.is_samagri);
        cv_cash = findViewById(R.id.cc_cash);

        cv_online = findViewById(R.id.cv_online);
        img_cash = findViewById(R.id.img_cash);
        img_online = findViewById(R.id.img_online);
        cv_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_mode = 1;
                img_cash.setVisibility(View.VISIBLE);
                img_online.setVisibility(View.GONE);

            }
        });
        cv_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_mode = 2;
                img_cash.setVisibility(View.GONE);
                img_online.setVisibility(View.VISIBLE);
            }
        });


        is_samagri.setVisibility(View.GONE);
        estore_txt_quanity = findViewById(R.id.estore_txt_quanity);
        estore_txt_amount = findViewById(R.id.estore_txt_amount);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Place Order");
        locationtxt = findViewById(R.id.edit_location);

        locationtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(EStoreBookingDetails_Activity.this, MapsActivity.class), 103);
            }
        });


        landmarktxt = findViewById(R.id.edit_lanmark);
        pincodetxt = findViewById(R.id.edit_pincode);
        colonytxt = findViewById(R.id.edit_street);
        proceed = findViewById(R.id.estore_btn_booking);
        amount = findViewById(R.id.estore_txt_amount);
        product_name = findViewById(R.id.prodname);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });

    }

    private void validation() {
        boolean res = true;
        if (addresstxt.getText().toString().equalsIgnoreCase("")) {
            res = false;
            addresstxt.setError("Please Enter Address");
        }
        if (locationtxt.getText().toString().equalsIgnoreCase("")) {
            res = false;
            locationtxt.setError("Please Choose Location");
        }
        if (landmarktxt.getText().toString().equalsIgnoreCase("")) {
            res = false;
            landmarktxt.setError("Please Enter Landmark");
        }
        if (colonytxt.getText().toString().equalsIgnoreCase("")) {
            res = false;
            colonytxt.setError("Please Enter Street");
        }

        if (pincodetxt.getText().toString().length() < 5 && pincodetxt.getText().toString().length() > 6) {
            res = false;
            pincodetxt.setError("Please Enter Valid Pincode");
        }
        if (Payment_mode <= 0) {
            res = false;
            Toast.makeText(this, "Please Select Payment Mode", Toast.LENGTH_SHORT).show();
        }
        if (res) {
            progressDialog = new ProgressDialog(EStoreBookingDetails_Activity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false); // Prevents user from canceling the dialog
            progressDialog.show();

            addOrder(0);

        }
    }


    private void getcommisionApi() {
        Call<ResponseBody> call = AppConfig.loadInterface().get_commission();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            JSONArray jsonArr = object.getJSONArray("result");
                            JSONObject jsonObject = jsonArr.getJSONObject(0);
                            String commision = jsonObject.getString("commision");
                            //String price = product.get(0).getPrice();
                            String price = prod_price;
                            int comiisissson = Integer.parseInt(commision);
                            int pricesss = Integer.parseInt(price);

                            int totalComission = pricesss / 100 * comiisissson;
                            totalCommistion = String.valueOf(totalComission);
                            totalAmt = String.valueOf(pricesss - totalComission);

                            //totalAmt = product.get(pos).getPrice();

                        } else {
                            utlity.toast(EStoreBookingDetails_Activity.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(EStoreBookingDetails_Activity.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                utlity.toast(EStoreBookingDetails_Activity.this, "Please Check Internet Connection", "e");
            }
        });
    }


    private void addOrder(final int pos) {
        if (!product.get(pos).getEntrydt().equalsIgnoreCase("OOS")) {

            String allData = getData(EStoreBookingDetails_Activity.this, "ldata", null);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(allData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Call<ResponseBody> call = null;
            try {
                call = AppConfig.loadInterface().add_order(
                        "" + getData(EStoreBookingDetails_Activity.this, "userID", null),
                        "" + jsonObject.getString("username").toString(),
                        "" + product.get(pos).getCategory_id(),
                        "" + product.get(pos).getCategory_name(),
                        "" + product.get(pos).getProduct_id(),
                        "" + product.get(pos).getName(),
                        "" + locationtxt.getText().toString() + addresstxt.getText().toString(),
                        "" + state,
                        "" + city,
                        "" + pincodetxt.getText().toString(),
                        "" + latitude,
                        "" + longitude,
                        "0",
                        "" + currentDate,
                        "" + totalAmt,
                        "" + totalCommistion,
                        "" + product.get(pos).getQty(),
                        "cash",
                        "" + currentDate,
                        "" + product.get(pos).getVendorId(),
                        "" + transID
                );
                System.out.println("safddsaf::::  " + call.request().toString());
            } catch (JSONException e) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                e.printStackTrace();
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        try {
                            String responedata = response.body().string();
                            JSONObject object = new JSONObject(responedata);
                            String error = object.getString("status");
                            if (error.equals("1")) {
                                removeItem(0);
                                int ppos = pos + 1;
                                if (product.size() > ppos) {
                                    System.out.println("afadsf::::   ");
                                    addOrder(ppos);
                                } else {
                                    System.out.println("sdafasdf::::  ");
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }
                                    utlity.toast(EStoreBookingDetails_Activity.this, "Successfully Placed Order", "s");
                                    startActivity(new Intent(EStoreBookingDetails_Activity.this,
                                            OrderSuccessPayment.class).putExtra("tranid", transID));
                                    finish();
                                }
                            } else {
                                utlity.toast(EStoreBookingDetails_Activity.this, "Something went wrong  !!", "e");
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            utlity.toast(EStoreBookingDetails_Activity.this, "Please Check Internet Connection  !!", "e");
                            onBackPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            utlity.toast(EStoreBookingDetails_Activity.this, "Please Check Internet Connection  !!", "e");
                            onBackPressed();
                        }
                    } else {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        utlity.toast(EStoreBookingDetails_Activity.this, "Please Check Internet Connection  !!", "e");
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    t.printStackTrace();
                    utlity.toast(EStoreBookingDetails_Activity.this, "Please Check Internet Connection  !!", "e");
                    onBackPressed();

                }
            });
        }

    }


    private void removeItem(int pos) {

        product2.remove(pos);
        MySharedPref.addProductToCart(EStoreBookingDetails_Activity.this, product2);
        MySharedPref.saveData(EStoreBookingDetails_Activity.this, "cartSize", "" + product2.size());


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 103) {

            try {
                assert data != null;
                latitude = data.getDoubleExtra("latitude", 0);
                longitude = data.getDoubleExtra("longitude", 0);


                List<Address> locations = getAddress(EStoreBookingDetails_Activity.this, latitude, longitude);

                    city = locations.get(0).getSubAdminArea();
                    state = locations.get(0).getAdminArea();
                    pincodetxt.setText("" + locations.get(0).getPostalCode());
                    colonytxt.setText("" + locations.get(0).getCountryName());
                    locationtxt.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality());
                    locationtxt.setFocusable(true);
                    addresstxt.setText(locations.get(0).getSubAdminArea() + ", " + locations.get(0).getAdminArea());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public List<Address> getAddress(Context context, double latitude, double longitude) {
        List<Address> addresses = null;
        String address1 = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address1 = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideDown(EStoreBookingDetails_Activity.this);

    }
}
