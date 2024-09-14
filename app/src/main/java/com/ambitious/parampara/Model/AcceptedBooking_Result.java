package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptedBooking_Result {
    @SerializedName("pooja_booking_id")
    @Expose
    private String poojaBookingId;

    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("pooja_id")
    @Expose
    private String poojaId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("accepted_by_name")
    @Expose
    private String acceptedbyname;
    @SerializedName("flat")
    @Expose
    private String flat;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("colony")
    @Expose
    private String colony;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("with_item")
    @Expose
    private String withItem;
    @SerializedName("samagri")
    @Expose
    private String samagri;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("accepted_by")
    @Expose
    private String acceptedBy;
    @SerializedName("forwarded_by")
    @Expose
    private String forwardedBy;
    @SerializedName("entrydt")
    @Expose
    private String entrydt;
    @SerializedName("pooja_name")
    @Expose
    private String poojaName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }

    @SerializedName("user_rating")
    @Expose
    private String user_rating;

    public String getPandit_rating() {
        return pandit_rating;
    }

    public void setPandit_rating(String pandit_rating) {
        this.pandit_rating = pandit_rating;
    }

    @SerializedName("pandit_rating")
    @Expose
    private String pandit_rating;

    @SerializedName("avg_rating")
    @Expose
    private String avg_rating;

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getPanditName() {
        return panditName;
    }

    public void setPanditName(String panditName) {
        this.panditName = panditName;
    }

    @SerializedName("panditName")
    @Expose
    private String panditName;

    @SerializedName("pandit_image")
    @Expose
    private String pandit_image;

    public String getPandit_image() {
        return pandit_image;
    }

    public void setPandit_image(String pandit_image) {
        this.pandit_image = pandit_image;
    }

    public String getPandit_feedback() {
        return pandit_feedback;
    }

    public void setPandit_feedback(String pandit_feedback) {
        this.pandit_feedback = pandit_feedback;
    }

    @SerializedName("pandit_feedback")
    @Expose
    private String pandit_feedback;

    public String getPoojaBookingId() {
        return poojaBookingId;
    }

    public void setPoojaBookingId(String poojaBookingId) {
        this.poojaBookingId = poojaBookingId;
    }

    public String getPoojaId() {
        return poojaId;
    }

    public void setPoojaId(String poojaId) {
        this.poojaId = poojaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWithItem() {
        return withItem;
    }

    public void setWithItem(String withItem) {
        this.withItem = withItem;
    }

    public String getSamagri() {
        return samagri;
    }

    public void setSamagri(String samagri) {
        this.samagri = samagri;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AcceptedBooking_Result{" +
                "poojaBookingId='" + poojaBookingId + '\'' +
                ", poojaId='" + poojaId + '\'' +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", acceptedbyname='" + acceptedbyname + '\'' +
                ", flat='" + flat + '\'' +
                ", landmark='" + landmark + '\'' +
                ", colony='" + colony + '\'' +
                ", pin='" + pin + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", withItem='" + withItem + '\'' +
                ", samagri='" + samagri + '\'' +
                ", price='" + price + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", status='" + status + '\'' +
                ", acceptedBy='" + acceptedBy + '\'' +
                ", forwardedBy='" + forwardedBy + '\'' +
                ", entrydt='" + entrydt + '\'' +
                ", poojaName='" + poojaName + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", user_rating='" + user_rating + '\'' +
                ", pandit_rating='" + pandit_rating + '\'' +
                ", avg_rating='" + avg_rating + '\'' +
                ", panditName='" + panditName + '\'' +
                '}';
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public String getForwardedBy() {
        return forwardedBy;
    }

    public void setForwardedBy(String forwardedBy) {
        this.forwardedBy = forwardedBy;
    }

    public String getEntrydt() {
        return entrydt;
    }

    public void setEntrydt(String entrydt) {
        this.entrydt = entrydt;
    }

    public String getPoojaName() {
        return poojaName;
    }

    public void setPoojaName(String poojaName) {
        this.poojaName = poojaName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAcceptedbyname() {
        return acceptedbyname;
    }

    public void setAcceptedbyname(String acceptedbyname) {
        this.acceptedbyname = acceptedbyname;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
