package com.ambitious.parampara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyPanditResult {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subscription_for")
    @Expose
    private String subscriptionFor;
    @SerializedName("subscription_type")
    @Expose
    private String subscriptionType;
    @SerializedName("price")
    @Expose
    private String price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriptionFor() {
        return subscriptionFor;
    }

    public void setSubscriptionFor(String subscriptionFor) {
        this.subscriptionFor = subscriptionFor;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
