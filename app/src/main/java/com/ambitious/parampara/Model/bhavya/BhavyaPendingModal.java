package com.ambitious.parampara.Model.bhavya;

import com.ambitious.parampara.Model.mandal.BhajanPendingResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BhavyaPendingModal {

    @SerializedName("result")
    @Expose
    private List<BhavyaPendingResult> result = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("type")
    @Expose
    private String type;

    public List<BhavyaPendingResult> getResult() {
        return result;
    }

    public void setResult(List<BhavyaPendingResult> result) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
