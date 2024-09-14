package com.ambitious.parampara.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;


public class BroadcastNotiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
            // gcm successfully registered
            // now subscribe to `global` topic to receive app wide notifications
            FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

            displayFirebaseRegId();

        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
            // new push notification is received

            String message = intent.getStringExtra("message");

            Toast.makeText(AppController.getInstance(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
        }
        displayFirebaseRegId();
    }

    public void displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
        String regId = getData(AppController.getInstance(), "regId", null);
        Log.e("REG_ID", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            System.out.println("Firebase Reg Id: " + regId);
            saveData(AppController.getInstance(),"regId",regId);
        }
//            txtRegId.setText("Firebase Reg Id: " + regId);
        else {
            System.out.println("Firebase Reg Id is not received yet!");
        }
//            txtRegId.setText("Firebase Reg Id is not received yet!");
    }
}
