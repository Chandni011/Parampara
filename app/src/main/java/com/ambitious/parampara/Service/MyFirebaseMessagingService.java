package com.ambitious.parampara.Service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ambitious.parampara.Activity.others.HomeActivity;
import com.ambitious.parampara.Activity.others.SplashActivity;
import com.ambitious.parampara.Model.notificationModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.ambitious.parampara.Other.MySharedPref.addnotificationModel;
import static com.ambitious.parampara.Other.MySharedPref.getnotificationModel;
import static com.ambitious.parampara.Other.MySharedPref.saveData;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtils notificationUtils;
    private ArrayList<notificationModel> data = new ArrayList<>();


    @Override
    public void onNewToken(@NonNull String refreshedToken) {
        super.onNewToken(refreshedToken);

        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);

    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("delex", 0);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("regId", token);
//        editor.commit();
        System.out.println("----------token---------- " + token);
        saveData(getApplicationContext(), "regId", token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        System.out.println("------------------------In MyFirebaseMessagingService---------------------------------");
        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            System.out.println("dfsgfdsg:::  " + remoteMessage.getData().toString());

            handleNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), "" + remoteMessage.getNotification().getImageUrl(), "");
            notificationModel notificationModel = new notificationModel("com.ambitious.paramparapartner.Activity.SplashActivity", "2", "Vender", "" + remoteMessage.getNotification().getBody(), "" + remoteMessage.getNotification().getImageUrl(), "" + remoteMessage.getNotification().getTitle());

            data.clear();
            if (getnotificationModel(getApplicationContext()) != null) {
                data.addAll(getnotificationModel(getApplicationContext()));
            }
            data.add(notificationModel);
            addnotificationModel(getApplicationContext(), data);

        }


        if (remoteMessage.getData().size() > 0) {


            JsonParser parser = new JsonParser();
            JsonElement mJson = parser.parse(remoteMessage.getData().get("data"));
            Gson gson = new Gson();
            notificationModel notificationModel = gson.fromJson(mJson, notificationModel.class);

            data.clear();
            if (getnotificationModel(getApplicationContext()) != null) {
                data.addAll(getnotificationModel(getApplicationContext()));
            }
            data.add(notificationModel);
            addnotificationModel(getApplicationContext(), data);

            System.out.println("SDFdsgfdsafaasdf:::::  " + getnotificationModel(getApplicationContext()));

            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().get("data"));
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message, String Type, String ImageUrl, String TimeStamp) {
        Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);

        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        }
        if (ImageUrl != null) {
            showNotificationMessageWithBigImage(getApplicationContext(), Type, message, TimeStamp, resultIntent, ImageUrl);
        } else {
            showNotificationMessage(getApplicationContext(), Type, message, TimeStamp, resultIntent);

        }
    }


    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());


        try {
            if (json.toString().contains("order_id")) {
                saveData(getApplicationContext(), "order_id", json.getString("order_id"));
                saveData(getApplicationContext(), "isRating", "1");

            } else {
                saveData(getApplicationContext(), "isRating", "0");
                saveData(getApplicationContext(), "order_id", "null");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (json.toString().contains("pooja_id")) {
                if (json.getString("status").equalsIgnoreCase("completed")) {
                    saveData(getApplicationContext(), "pooja_id", json.getString("pooja_id"));
                    saveData(getApplicationContext(), "isRating", "1");
                } else {
                    saveData(getApplicationContext(), "isRating", "0");
                    saveData(getApplicationContext(), "pooja_id", "null");

                }

            } else {
                saveData(getApplicationContext(), "isRating", "0");
                saveData(getApplicationContext(), "pooja_id", "null");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!json.getString("Image").equalsIgnoreCase("null")) {


                if (!json.getString("Activity").equalsIgnoreCase("null")) {
                    try {
                        Class<?> c = Class.forName(json.getString("Activity").toString());
                        Intent intent = new Intent(this, c);

                        showNotificationMessageWithBigImage(getApplicationContext(), json.getString("title"), json.getString("body"), "", intent, json.getString("Image"));

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(":SDFsafdasf:::  " + e.toString());
                    }
                } else {
                    Intent intent = new Intent(this, SplashActivity.class);
                    showNotificationMessageWithBigImage(getApplicationContext(), json.getString("title"), json.getString("body"), "", intent, json.getString("Image"));

                }

            } else {
                if (!json.getString("Activity").equalsIgnoreCase("null")) {

                    try {
                        Class<?> c = Class.forName(json.getString("Activity").toString());
                        Intent intent = new Intent(this, c);
                        showNotificationMessage(getApplicationContext(), json.getString("title"), json.getString("body"), "", intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(":SDFsafdasf:::  " + e.toString());

                    }
                } else {
                    Intent intent = new Intent(this, SplashActivity.class);
                    System.out.println("asfsadf:::");
                    showNotificationMessage(getApplicationContext(), json.getString("title"), json.getString("body"), "", intent);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent);
    }

    /*
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, intent, imageUrl);
    }
}