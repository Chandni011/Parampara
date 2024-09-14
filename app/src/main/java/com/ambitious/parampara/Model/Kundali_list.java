package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

public class Kundali_list {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_time")
    @Expose
    private String date_time;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("place_birth")
    @Expose
    private String place_birth;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("time_of_birth")
    @Expose
    private String time_of_birth;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("language")
    @Expose
    private String language;

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getuser_id() {
        return user_id;
    }

    public void setuser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getdate_time() {
        return date_time;
    }

    public void setdate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getdob() {
        return dob;
    }

    public void setdob(String dob) {
        this.dob = dob;
    }

    public String getplace_birth() {
        return place_birth;
    }

    public void setplace_birth(String place_birth) {
        this.place_birth = place_birth;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String gettime_of_birth() {
        return time_of_birth;
    }

    public void settime_of_birth(String time_of_birth) {
        this.time_of_birth = time_of_birth;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String getlanguage() {
        return language;
    }

    public void setlanguage(String language) {
        this.language = language;
    }
}
