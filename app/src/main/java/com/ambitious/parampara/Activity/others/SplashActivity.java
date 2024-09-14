package com.ambitious.parampara.Activity.others;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ambitious.parampara.Other.GPSTracker;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class SplashActivity extends AppCompatActivity {
    String ldata,str_passnger_type;
    View view;
    Activity activity;
    Context context;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activity = this;
        context = getApplicationContext();
        ldata = getData(getApplicationContext(), "ldata", null);
        System.out.println("***ldata" + ldata);
        if (ldata != null) {
            try {
                JSONObject jsonObject = new JSONObject(ldata);
                System.out.println("Ldataint" + ldata);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    if (ldata != null) {
                        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(SplashActivity.this);
                        finish();

                    } else {

                        Intent intent = new Intent(SplashActivity.this, LoginMode_Activity.class);
                        startActivity(intent);
                        Animatoo.animateSlideLeft(SplashActivity.this);
                        finish();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
      /*  if (checkPermission()) {
            proceed();

        } else {
            requestPermission();
        }
*/
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.ambitious.parampara",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    proceed();
                } else {
                    //   requestPermission();
                    proceed();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void proceed() {
        //**************************** Location ( Latitude & longitude ) *********************************
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        } else {
            checkGPS();
        }

    }

    private void checkGPS() {
        GPSTracker gpsTracker = new GPSTracker(SplashActivity.this);
        if (gpsTracker.canGetLocation()) {
            gpsTracker.getLatitude();
            gpsTracker.getLongitude();
            gpsTracker.getLocation();
            saveData(getApplicationContext(), "LAT", String.valueOf(gpsTracker.getLatitude()));
            saveData(getApplicationContext(), "LON", String.valueOf(gpsTracker.getLongitude()));


            Thread t = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                        if (ldata != null) {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(SplashActivity.this);
                            finish();

                        } else {

                            Intent intent = new Intent(SplashActivity.this, LoginMode_Activity.class);
                            startActivity(intent);
                            Animatoo.animateSlideLeft(SplashActivity.this);
                            finish();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();

        } else
            showSettingsAlert();
    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("GPS settings");
        alertDialog.setMessage("To continue allow your device to activate the location service");
        alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        //Set negative button background
        nbutton.setBackgroundColor(Color.WHITE);
        //Set negative button text color
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button background
        pbutton.setBackgroundColor(Color.WHITE);
        //Set positive button text color
        pbutton.setTextColor(Color.BLACK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            checkGPS();
        } else checkGPS();
    }
}
