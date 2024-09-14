package com.ambitious.parampara.Other;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Model.notificationModel;
import com.ambitious.parampara.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class MySharedPref {

    public static SharedPreferences sp;
    public static ArrayList<notificationModel> dataitem = new ArrayList<>();
    public static ArrayList<GetSubCategory_Result> products = new ArrayList<>();

    public static void saveData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getData(Context context, String key, String value) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public static void DeleteData(Context context) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }

    public static void NullData(Context context, String key) {
        sp = context.getSharedPreferences("parampara", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, null);
        editor.commit();
    }


    public static void addnotificationModel(Context context, ArrayList<notificationModel> dataitem2) {

        SharedPreferences prefs = context.getSharedPreferences("parampara", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataitem2);
        editor.putString("Notification", json);
        editor.apply();
    }


    public static ArrayList<notificationModel> getnotificationModel(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("parampara", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("Notification", null);
        Type type = new TypeToken<ArrayList<notificationModel>>() {
        }.getType();

        if (dataitem == null) {
            dataitem = new ArrayList<>();
        } else {
            dataitem.clear();
        }
        dataitem = gson.fromJson(json, type);

        return dataitem;
    }


    public static void addProductToCart(Context context, ArrayList<GetSubCategory_Result> dataitem2) {

        products.clear();
        System.out.println("jhhhhjhjk :::::::");
        products.addAll(dataitem2);

        SharedPreferences prefs = context.getSharedPreferences("parampara", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(products);
        editor.putString("products", json);
        editor.apply();
    }


    public static ArrayList<GetSubCategory_Result> getProductCart(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("parampara", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("products", null);
        Type type = new TypeToken<ArrayList<GetSubCategory_Result>>() {
        }.getType();
        products = gson.fromJson(json, type);

        if (products == null) {
            products = new ArrayList<>();
        }
        return products;
    }


    public static void setAnim(View view, Context context) {
        final Animation myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        view.startAnimation(myAnim);
    }

    public static class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        public MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }


}
