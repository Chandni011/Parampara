package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ayojan_list {
    @SerializedName("booking_id")
    @Expose
    private String booking_Id;
    @SerializedName("ayojan_id")
    @Expose
    private String ayojan_id;
    @SerializedName("ayojan_name")
    @Expose
    private String ayojan_name;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("user_name")
    @Expose
    private String user_name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("lattitude")
    @Expose
    private String lattitude;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("entry_date")
    @Expose
    private String entry_date;
    @SerializedName("pay_type")
    @Expose
    private String pay_type;
    @SerializedName("panditid")
    @Expose
    private String panditid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("accepted_by")
    @Expose
    private String accepted_by;
    @SerializedName("accepted_by_name")
    @Expose
    private String accepted_by_name;
    @SerializedName("user_rating")
    @Expose
    private String user_rating;
    @SerializedName("pandit_rating")
    @Expose
    private String pandit_rating;
    @SerializedName("pandit_feedback")
    @Expose
    private String pandit_feedback;
    @SerializedName("user_feedback")
    @Expose
    private String user_feedback;
    @SerializedName("transection_id")
    @Expose
    private String transection_id;

    public String getuser_name() {
        return user_name;
    }

    public void setuser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getayojan_id() {
        return ayojan_id;
    }

    public void setayojan_id(String ayojan_id) {
        this.ayojan_id = ayojan_id;
    }

    public String getayojan_name() {
        return ayojan_name;
    }

    public void setayojan_name(String ayojan_name) {
        this.ayojan_name = ayojan_name;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getpincode() {
        return pincode;
    }

    public void setpincode(String pincode) {
        this.pincode = pincode;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }
}
