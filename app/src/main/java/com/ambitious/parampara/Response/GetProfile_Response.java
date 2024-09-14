package com.ambitious.parampara.Response;

import com.ambitious.parampara.Model.GetProfile_Result;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfile_Response {
    @SerializedName("result")
    @Expose
    private GetProfile_Result result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public GetProfile_Result getResult() {
        return result;
    }

    public void setResult(GetProfile_Result result) {
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
