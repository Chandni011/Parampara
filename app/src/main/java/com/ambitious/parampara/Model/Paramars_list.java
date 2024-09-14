package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

public class Paramars_list {
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
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("preffered_timing")
    @Expose
    private String preffered_timing;
    @SerializedName("call_option")
    @Expose
    private String call_option;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dob_time")
    @Expose
    private String dob_time;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("prefered_language")
    @Expose
    private String prefered_language;
    @SerializedName("description")
    @Expose
    private String description;

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

    public String getquestion() {
        return question;
    }

    public void setquestion(String question) {
        this.question = question;
    }

    public String getpreffered_timing() {
        return preffered_timing;
    }

    public void setpreffered_timing(String preffered_timing) {
        this.preffered_timing = preffered_timing;
    }

    public String getcall_option() {
        return call_option;
    }

    public void setcall_option(String call_option) {
        this.call_option = call_option;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

    public String getdob_time() {
        return dob_time;
    }

    public void setdob_time(String dob_time) {
        this.dob_time = dob_time;
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

    public String getcontact() {
        return contact;
    }

    public void setcontact(String contact) {
        this.contact = contact;
    }

    public String getprefered_language() {
        return prefered_language;
    }

    public void setprefered_language(String prefered_language) {
        this.prefered_language = prefered_language;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

}
