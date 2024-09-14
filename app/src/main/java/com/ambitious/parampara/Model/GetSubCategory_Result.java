package com.ambitious.parampara.Model;

import java.io.Serializable;

public class GetSubCategory_Result implements Serializable {


    /**
     * product_id : 13
     * vendorId : 8
     * category_id : 2
     * category_name : Samagri
     * name : product 2
     * price : 200
     * description : 
     * image : 
     * status : 1
     * entrydt : 2020-04-30 10:15:36
     * qty : 34
     * qty_type : 123
     * avg_rating : 3
     * shopName : test
     */

    private String product_id;
    private String vendorId;
    private String category_id;
    private String category_name;
    private String name;
    private String price;
    private String description;
    private String image;
    private String status;

    @Override
    public String toString() {
        return "GetSubCategory_Result{" +
                "product_id='" + product_id + '\n' +
                ", vendorId='" + vendorId + '\n' +
                ", category_id='" + category_id + '\n' +
                ", category_name='" + category_name + '\n' +
                ", name='" + name + '\n' +
                ", price='" + price + '\n' +
                ", description='" + description + '\n' +
                ", image='" + image + '\n' +
                ", status='" + status + '\n' +
                ", entrydt='" + entrydt + '\n' +
                ", qty='" + qty + '\n' +
                ", qty_type='" + qty_type + '\n' +
                ", avg_rating='" + avg_rating + '\n' +
                ", shopName='" + shopName + '\n' +
                ", shopName='" + gst_no + '\n' +
                ", shopName='" + delivery_charge + '\n' +
                '}';
    }

    private String entrydt;
    private String qty;
    private String qty_type;
    private String avg_rating;
    private String shopName;
    private String gst_no;
    private String delivery_charge;
    private String final_amount;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQty_type() {
        return qty_type;
    }

    public void setQty_type(String qty_type) {
        this.qty_type = qty_type;
    }

    public String getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(String avg_rating) {
        this.avg_rating = avg_rating;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getgst_no() {
        return gst_no;
    }

    public void setgst_no(String gst_no) {
        this.gst_no = gst_no;
    }

    public String getdelivery_charge() {
        return delivery_charge;
    }

    public void setdelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getFinal_amount(){
        return final_amount;
    }

    public void setFinal_amount(String final_amount){
       this.final_amount = final_amount;
    }
}
