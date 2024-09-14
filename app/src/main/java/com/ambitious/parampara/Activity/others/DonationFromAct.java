package com.ambitious.parampara.Activity.others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Service.utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
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

import static android.view.View.VISIBLE;
import static com.ambitious.parampara.Other.MySharedPref.getData;

public class DonationFromAct extends AppCompatActivity {

    String seletItem, selectedID, selectedDes, selecteNote, currentDate, paymode, trasId, userAdd;
    EditText userName, userAddress, userState, userContry, userPincode, userLanmark, userAmm, userNote, usercontact, useremail;
    Button payamt;
    TextView txt_headername;
    private ImageView img_back;
    private double latitude;
    private double longitude;
    private String sId, sName, sAddress, sState, sContry, slat, slng, slandmark, spincode, Ammount;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    CardView cv_online;
    ImageView img_online;
    private ProgressDialog progressDialog;

    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_from);

        sId = getData(getApplicationContext(), "userID", null);
        //Toast.makeText(this, "hii"+sId, Toast.LENGTH_SHORT).show();
        sName = getData(getApplicationContext(), "userName", null);
        sAddress = getData(getApplicationContext(), "address", null);
        sState = getData(getApplicationContext(), "city", null);
        sContry = getData(getApplicationContext(), "country", null);
        slat = getData(getApplicationContext(), "lattitude", null);
        slng = getData(getApplicationContext(), "logitude", null);
        slandmark = getData(getApplicationContext(), "landmark", null);
        spincode = getData(getApplicationContext(), "pincode", null);

        Intent extras = getIntent();
        if (extras != null) {
            seletItem = extras.getStringExtra("selectItem");
            selectedID = extras.getStringExtra("selectedID");
            selectedDes = extras.getStringExtra("des");
            //selecteNote = extras.getStringExtra("Note");
        }

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentDate = df.format(c);

        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("");

        paymode = "paytm";

        userName = findViewById(R.id.edit_username);
        userName.setText(sName);
        userAddress = findViewById(R.id.edit_userAddress);
        userAddress.setText(sAddress);
        userAdd = userAddress.getText().toString().trim();
        userState = findViewById(R.id.edit_userState);
        userState.setText(sState);
        userContry = findViewById(R.id.edit_usercontry);
        userContry.setText(sContry);
        userPincode = findViewById(R.id.edit_userpincode);
        userPincode.setText(spincode);
        //userLanmark = findViewById(R.id.edit_userlanmark);
        userNote = findViewById(R.id.edit_usernote);
        //selecteNote = userNote.getText().toString().trim();
        userAmm = findViewById(R.id.edit_useramm);
        usercontact = findViewById(R.id.edit_usercontact);
        useremail = findViewById(R.id.edit_useremail);
        Ammount = userAmm.getText().toString().trim();
        cv_online = findViewById(R.id.cv_online);
        img_online = findViewById(R.id.img_online);
        //userLanmark.setText(slandmark);
        //descriptiontxt = findViewById(R.id.descption_txt);
        payamt = findViewById(R.id.pay_donetion);

        final Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 10; ++i) {
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        }

        trasId = String.valueOf(sb);

        cv_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_online.setVisibility(VISIBLE);
            }
        });

        img_back = findViewById(R.id.img_back);

//        if(img_online.getVisibility() == VISIBLE){
//            payamt.setEnabled(true);
//        }else {
//            payamt.setEnabled(false);
//            Toast.makeText(this, "Please Confirm Your Payment Option", Toast.LENGTH_SHORT).show();
//        }

        userAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DonationFromAct.this, MapsActivity.class), 103);
            }
        });


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == img_back) {
                    onBackPressed();
                }
            }
        });

        //amttxt.setText(symbol + " " + amt);
        //descriptiontxt.setText(selectedDes);


        payamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(getApplicationContext(), NewPaymentAct.class)
                        .putExtra("pID", selectedID).putExtra("pName",
                                seletItem).putExtra("pPrice", amt)
                        .putExtra("des", selectedDes)
                        .putExtra("entrydate", currentDate)
                        .putExtra("type", "donation")
                        .putExtra("userID", getData(getApplicationContext(), "userName", null)));*/
                String Cause = selectedID;
                String Description = selectedDes;
                String Note = userNote.getText().toString().trim();
                String date = currentDate;
                String Id = sId;
                String Name = userName.getText().toString().trim();
                String Add = userAddress.getText().toString().trim();
                String City = userState.getText().toString().trim() + userContry.getText().toString().trim();
                String Pincode = userPincode.getText().toString().trim();
                String Longitude = Double.toString(longitude);
                String Lattitude = Double.toString(latitude);
                String Ammoun = userAmm.getText().toString().trim();
                String Paymentmode = "Paytm";
                String Status = "1";
                String TransactionId = trasId;
                String Contact = usercontact.getText().toString().trim();
                String Email = useremail.getText().toString().trim();
                if(!Name.isEmpty() && !Add.isEmpty() && !City.isEmpty() && !Pincode.isEmpty() && !Ammoun.isEmpty() && !Contact.isEmpty() && !Email.isEmpty()){
                    Donate(Id, Name, Add, Longitude, Lattitude, Pincode, City, Cause, Description, Ammoun, Note, Paymentmode, Status, date, TransactionId, Contact, Email);
                }
            }
        });
    }

    /*private void Donate(String id, String name, String add, String longitude, String lattitude, String pincode, String city, String cause, String description, String ammoun, String note, String paymentmode, String status, String date, String transactionId) {
        final ACProgressFlower progressDialog = new ACProgressFlower.Builder(DonationFromAct.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Loading...")
                .fadeColor(Color.DKGRAY).build();
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().donation_pay(id, name, add, longitude, lattitude, pincode, city, cause, description, ammoun, note, paymentmode, status, date, transactionId);
    }*/

    private void Donate(String id, String name, String add, String longitude, String lattitude, String pincode, String city, String cause, String description, String ammoun, String note, String paymentmode, String status, String date, String transactionId, String contact, String email) {
        progressDialog = new ProgressDialog(DonationFromAct.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().donation_pay(id, name, add, longitude, lattitude, pincode, city, cause, description, ammoun, note, paymentmode, status, date, transactionId, contact, email);
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
                            utlity.toast(DonationFromAct.this, object.getString("message"), "s");
                            Intent i = new Intent(DonationFromAct.this, BookingSuccesActivity.class);
                            i.putExtra("check", "donation");
                            i.putExtra("tranid", trasId);
                            startActivity(i);
                            finish();
                        } else {
                            utlity.toast(DonationFromAct.this, object.getString("message"), "e");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(DonationFromAct.this, "Something went worng!", "e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(DonationFromAct.this, "Please Check Internet Connection", "e");
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


                List<Address> locations = getAddress(DonationFromAct.this, latitude, longitude);

                String city = locations.get(0).getSubAdminArea();
                String state = locations.get(0).getAdminArea();
                userPincode.setText("" + locations.get(0).getPostalCode());
                userContry.setText("" + locations.get(0).getCountryName());
                userAddress.setText(locations.get(0).getFeatureName() + ", " + locations.get(0).getLocality());
                userAddress.setFocusable(true);
                userState.setText(locations.get(0).getSubAdminArea() + ", " + locations.get(0).getAdminArea());

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private List<Address> getAddress(DonationFromAct context, double latitude, double longitude) {
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

}
