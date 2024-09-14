package com.ambitious.parampara.Model;

public class notificationModel {
    private String Activity;
    private String status;
    private String Type;
    private String body;

    public notificationModel(String activity, String status, String type, String body, String image, String title) {
        this.Activity = activity;
        this.status = status;
        this.Type = type;
        this.body = body;
        this.Image = image;
        this.title = title;
    }

    private String Image;
    private String title;

    @Override
    public String toString() {
        return "notificationModel{" +
                "Activity='" + Activity + '\'' +
                ", status='" + status + '\'' +
                ", Type='" + Type + '\'' +
                ", body='" + body + '\'' +
                ", Image='" + Image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




}
