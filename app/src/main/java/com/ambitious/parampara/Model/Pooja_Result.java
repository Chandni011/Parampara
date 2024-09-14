package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pooja_Result {
    @SerializedName("pooja_id")
    @Expose
    private String poojaId;
    @SerializedName("pooja_name")
    @Expose
    private String poojaName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price_with_item")
    @Expose
    private String priceWithItem;
    @SerializedName("price_without_item")
    @Expose
    private String priceWithoutItem;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("entrydt")
    @Expose
    private String entrydt;

    public String getPoojaId() {
        return poojaId;
    }

    public void setPoojaId(String poojaId) {
        this.poojaId = poojaId;
    }

    public String getPoojaName() {
        return poojaName;
    }

    public void setPoojaName(String poojaName) {
        this.poojaName = poojaName;
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

    public String getPriceWithItem() {
        return priceWithItem;
    }

    public void setPriceWithItem(String priceWithItem) {
        this.priceWithItem = priceWithItem;
    }

    public String getPriceWithoutItem() {
        return priceWithoutItem;
    }

    public void setPriceWithoutItem(String priceWithoutItem) {
        this.priceWithoutItem = priceWithoutItem;
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
