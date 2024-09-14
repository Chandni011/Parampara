package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BhavyaAjojanResultModal {
    @SerializedName("ayojan_id")
    @Expose
    private String ayojanId;
    @SerializedName("ayojan_name")
    @Expose
    private String ayojanName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("Convenience_Fee")
    @Expose
    private String convenienceFee;
    @SerializedName("GST")
    @Expose
    private String gST;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("entrydt")
    @Expose
    private String entrydt;

    public String getAyojanId() {
        return ayojanId;
    }

    public void setAyojanId(String ayojanId) {
        this.ayojanId = ayojanId;
    }

    public String getAyojanName() {
        return ayojanName;
    }

    public void setAyojanName(String ayojanName) {
        this.ayojanName = ayojanName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getConvenienceFee() {
        return convenienceFee;
    }

    public void setConvenienceFee(String convenienceFee) {
        this.convenienceFee = convenienceFee;
    }

    public String getGST() {
        return gST;
    }

    public void setGST(String gST) {
        this.gST = gST;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntrydt() {
        return entrydt;
    }

    public void setEntrydt(String entrydt) {
        this.entrydt = entrydt;
    }
}
