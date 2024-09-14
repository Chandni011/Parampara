package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetVertualResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("virtual_service")
    @Expose
    private String virtualService;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVirtualService() {
        return virtualService;
    }

    public void setVirtualService(String virtualService) {
        this.virtualService = virtualService;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }


}
