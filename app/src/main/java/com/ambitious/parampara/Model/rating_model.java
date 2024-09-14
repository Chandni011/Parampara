package com.ambitious.parampara.Model;

public class rating_model {
    public rating_model(String user_name, String review, String pic, String rating) {
        this.user_name = user_name;
        this.review = review;
        this.pic = pic;
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    String user_name;
    String review;
    String pic;
    String rating;

}
