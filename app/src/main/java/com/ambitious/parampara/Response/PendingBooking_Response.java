package com.ambitious.parampara.Response;

import com.ambitious.parampara.Model.PendingBooking_Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingBooking_Response {
    @SerializedName("result")
    @Expose
    private List<PendingBooking_Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<PendingBooking_Result> getResult() {
        return result;
    }

    public void setResult(List<PendingBooking_Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
