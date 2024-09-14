package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donation_list {
    @SerializedName("donationListing_id")
    @Expose
    private String donationListing_id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("adderss")
    @Expose
    private String adderss;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("email")
    @Expose
    private String email;
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
    @SerializedName("cause")
    @Expose
    private String cause;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("amt")
    @Expose
    private String amt;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("entrydate")
    @Expose
    private String entrydate;
    @SerializedName("transection_id")
    @Expose
    private String transection_id;
    @SerializedName("donation_cause")
    @Expose
    private String donation_cause;

    public String getdonationListing_id() {
        return donationListing_id;
    }

    public void setdonationListing_id(String donationListing_id) {
        this.donationListing_id = donationListing_id;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getadderss() {
        return adderss;
    }

    public void setadderss(String adderss) {
        this.adderss = adderss;
    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile) {
        this.mobile = mobile;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getlongitude() {
        return longitude;
    }

    public void setlongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getlattitude() {
        return lattitude;
    }

    public void setlattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getpincode() {
        return pincode;
    }

    public void setpincode(String pincode) {
        this.pincode = pincode;
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getcause() {
        return cause;
    }

    public void setcause(String cause) {
        this.cause = cause;
    }

    public String getdiscription() {
        return discription;
    }

    public void setdiscription(String discription) {
        this.discription = discription;
    }

    public String getamt() {
        return amt;
    }

    public void setamt(String amt) {
        this.amt = amt;
    }

    public String getnote() {
        return note;
    }

    public void setnote(String note) {
        this.note = note;
    }

    public String getmode() {
        return mode;
    }

    public void setmode(String mode) {
        this.mode = mode;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getentrydate() {
        return entrydate;
    }

    public void setentrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    public String gettransection_id() {
        return transection_id;
    }

    public void settransection_id(String transection_id) {
        this.transection_id = transection_id;
    }

    public String getdonation_cause() {
        return donation_cause;
    }

    public void setdonation_cause(String donation_cause) {
        this.donation_cause = donation_cause;
    }

}
