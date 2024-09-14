package com.ambitious.parampara.Model;

import java.io.Serializable;
import java.util.List;

public class orderModel  implements Serializable {
    /**
     * result : [{"id":"1","user_id":"46","vendorId":"8","user_name":"bikramadityae","category_id":"1","category_name":"Fruit","product_id":"1","product_name":"burger","address":"Ramesh Appoji 7th Avenue Green Villae Layout, near Kamdhenu Super Market, Halanayakanahalli, Karnataka 560035, Indiasamrudhi 301","state":"Karnataka","city":"Bangalore Urban","pincode":"215","latitude":"12.9022189","longitude":"77.6836679","status":"0","updateTime":"0000-00-00 00:00:00","amount":"868","quantity":"2","payment_mode":"cash","entry_date":"0000-00-00 00:00:00","name":"burger","description":"Kapoor is used to cure everything","image":"uploads/product/95707burger.jpg"},{"id":"2","user_id":"46","vendorId":"8","user_name":"bikramadityae","category_id":"1","category_name":"Fruit","product_id":"1","product_name":"burger","address":"Ramesh Appoji 7th Avenue Green Villae Layout, near Kamdhenu Super Market, Halanayakanahalli, Karnataka 560035, Indiasamrudhi 301","state":"Karnataka","city":"Bangalore Urban","pincode":"215","latitude":"12.9022189","longitude":"77.6836679","status":"0","updateTime":"0000-00-00 00:00:00","amount":"868","quantity":"2","payment_mode":"cash","entry_date":"0000-00-00 00:00:00","name":"burger","description":"Kapoor is used to cure everything","image":"uploads/product/95707burger.jpg"}]
     * message : success
     * status : 1
     */

    private String message;
    private int status;
    private List<order> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<order> getResult() {
        return result;
    }

    public void setResult(List<order> result) {
        this.result = result;
    }

    public class order implements Serializable {
        /**
         * id : 1
         * user_id : 46
         * vendorId : 8
         * user_name : bikramadityae
         * category_id : 1
         * category_name : Fruit
         * product_id : 1
         * product_name : burger
         * address : Ramesh Appoji 7th Avenue Green Villae Layout, near Kamdhenu Super Market, Halanayakanahalli, Karnataka 560035, Indiasamrudhi 301
         * state : Karnataka
         * city : Bangalore Urban
         * pincode : 215
         * latitude : 12.9022189
         * longitude : 77.6836679
         * status : 0
         * updateTime : 0000-00-00 00:00:00
         * amount : 868
         * quantity : 2
         * payment_mode : cash
         * entry_date : 0000-00-00 00:00:00
         * name : burger
         * description : Kapoor is used to cure everything
         * image : uploads/product/95707burger.jpg
         */

        private String id;
        private String user_id;
        private String vendorId;
        private String user_name;
        private String category_id;
        private String category_name;
        private String product_id;
        private String product_name;
        private String address;
        private String state;
        private String city;
        private String pincode;
        private String latitude;
        private String longitude;
        private String status;
        private String updateTime;
        private String amount;
        private String admin_amount;
        private String quantity;
        private String payment_mode;
        private String entry_date;
        private String name;
        private String description;
        private String image;
        private String vendor_name;

        public String getVendor_feedback() {
            return vendor_feedback;
        }

        public void setVendor_feedback(String vendor_feedback) {
            this.vendor_feedback = vendor_feedback;
        }

        private String vendor_feedback;

        public String getVendor_name() {
            return vendor_name;
        }

        public void setVendor_name(String vendor_name) {
            this.vendor_name = vendor_name;
        }

        public String getVender_image() {
            return vender_image;
        }

        public void setVender_image(String vender_image) {
            this.vender_image = vender_image;
        }

        private String vender_image;

        public String getVender_rating() {
            return vender_rating;
        }

        public void setVender_rating(String vender_rating) {
            this.vender_rating = vender_rating;
        }

        private String vender_rating;

        public String getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(String user_rating) {
            this.user_rating = user_rating;
        }

        private String user_rating;
        /**
         * shopName : laxmi bhandar
         */

        private String shopName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getVendorId() {
            return vendorId;
        }

        public void setVendorId(String vendorId) {
            this.vendorId = vendorId;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAdmin_amount() {
            return admin_amount;
        }

        public void setAdmin_amount(String admin_amount) {
            this.admin_amount = admin_amount;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        public String getEntry_date() {
            return entry_date;
        }

        public void setEntry_date(String entry_date) {
            this.entry_date = entry_date;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
