package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.Product_details_Page;
import com.ambitious.parampara.Activity.others.SubCategory_Activity;
import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by user1 on 11/25/2017.
 */

public class SubCategory_Adapter extends RecyclerView.Adapter<SubCategory_Adapter.ViewHolder> {
    private Activity activity;
    private final ArrayList<GetSubCategory_Result> task;
    int count = 1;
    int tot, i1, gst, del, ab, ac;

    int Size = 0;
    HashMap hashMap, hashMapprice;
    /*public E_store_Adapter(Activity activity, List<Get_SubCategory_Result> task, HashMap hashMap, HashMap hashMapprice) {
        this.activity = activity;
        this.task = task;
        this.hashMap =hashMap;
        this.hashMapprice=hashMapprice;
    }*/

    public SubCategory_Adapter(Activity activity, ArrayList<GetSubCategory_Result> task) {
        this.activity = activity;
        this.task = task;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        i1 = Integer.parseInt(task.get(position).getPrice());
        gst = Integer.parseInt(task.get(position).getgst_no());
        del = Integer.parseInt(task.get(position).getdelivery_charge());
        ab = i1*gst/100;
        ac = ab + del;
        tot = ac + i1;
        //tot = tot * count;
        // holder.txt_sub_catName.setText(task.get(position).getServicesSubName());
        holder.txt_price.setText("र  " + tot /*task.get(position).getPrice()*/);
        holder.txt_subcatname.setText(task.get(position).getName());
        holder.shop_name.setText(task.get(position).getShopName());

        if (!task.get(position).getAvg_rating().isEmpty()) {
            holder.rating.setRating(Float.parseFloat(task.get(position).getAvg_rating()));

        }
        holder.txt_desc.setText("Stock : " + task.get(position).getQty() + " " + task.get(position).getQty_type());
        holder.quantity.setText("1");

        try {

            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                    .error(R.drawable.product_placeholder)
                    .placeholder(R.drawable.product_placeholder)
                    .into(holder.img_sub);

        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<GetSubCategory_Result> product = new ArrayList<>();
        if (MySharedPref.getProductCart(activity) != null) {
            product.addAll(MySharedPref.getProductCart(activity));
        }

        if (product.toString().contains("product_id='" + task.get(position).getProduct_id())) {
            holder.add_cart.setText("Added");
            holder.add_cart.setBackground(activity.getResources().getDrawable(R.drawable.btn_gray));
            holder.rel_addhelper.setVisibility(View.INVISIBLE);
        } else {
            holder.add_cart.setText("Add To Cart");
            holder.add_cart.setBackground(activity.getResources().getDrawable(R.drawable.btn_orange));
            holder.rel_addhelper.setVisibility(View.VISIBLE);
        }
        if (task.get(position).getEntrydt().equalsIgnoreCase("OOS")) {
            holder.add_cart.setText("Out Of Stock");
            holder.add_cart.setBackgroundColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int i1 = 0, gst, del, ab, ac;
        TextView txt_subcatname, txt_price, shop_name;
        EditText quantity;
        TextView txt_desc;
        RatingBar rating;
        ImageView img_sub, increaseInteger, remove_item;
        Button add_cart;
        RelativeLayout rel_addhelper;
//        this.tot = tot;

        public ViewHolder(final View itemView) {
            super(itemView);

            txt_price = itemView.findViewById(R.id.txt_price);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            rel_addhelper = itemView.findViewById(R.id.rel_addhelper);
            shop_name = itemView.findViewById(R.id.shop_name);
            rating = itemView.findViewById(R.id.txt_avg_rating);
            quantity = itemView.findViewById(R.id.iteam_amount);
            increaseInteger = itemView.findViewById(R.id.add_item);
            increaseInteger.setOnClickListener(this);
            remove_item = itemView.findViewById(R.id.remove_item);
            remove_item.setOnClickListener(this);

            txt_subcatname = itemView.findViewById(R.id.txt_subcatname);
            img_sub = itemView.findViewById(R.id.img_sub);
            add_cart = itemView.findViewById(R.id.add_to_cart);
            add_cart.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, Product_details_Page.class)
                            .putExtra("allData", task.get(getAdapterPosition()))
                            //.putExtra("price", "" + txt_price.getText().toString().replace("र  ", "").trim())
                            .putExtra("qnt", "" + task.get(getAdapterPosition()).getQty())
                            .putExtra("pri", "" + task.get(getAdapterPosition()).getPrice())
                            .putExtra("gstn", "" + task.get(getAdapterPosition()).getgst_no())
                            .putExtra("deli", "" + task.get(getAdapterPosition()).getdelivery_charge()));

                    Animatoo.animateSlideUp(activity);
                }
            });

        }


        @Override
        public void onClick(View view) {


            int orgPrice = Integer.parseInt(task.get(getLayoutPosition()).getPrice());
            int orgQnt = Integer.parseInt(task.get(getLayoutPosition()).getQty());


            i1 = Integer.parseInt(task.get(getAdapterPosition()).getPrice());
            count = Integer.parseInt(quantity.getText().toString());

            if (view == quantity) {
                quantity.setCursorVisible(true);
            } else if (view == increaseInteger) {
                count = Integer.parseInt(quantity.getText().toString());
                if (count < Integer.parseInt(task.get(getAdapterPosition()).getQty())) {
                    count++;
                    quantity.setText("" + count);
                    quantity.setCursorVisible(true);
                    i1 = Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                    gst = Integer.parseInt(task.get(getAdapterPosition()).getgst_no());
                    del = Integer.parseInt(task.get(getAdapterPosition()).getdelivery_charge());
                    ab = i1*gst/100;
                    ac = ab + del;
                    tot = ac + i1;
                    tot = tot * count;
                    if (i1 != 0) {
                        txt_price.setText("र  " + tot);
                    } else {
                        i1 = Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                        txt_price.setText("र  " + tot /*task.get(getAdapterPosition()).getPrice()*/);
                    }
                    if (add_cart.getText().toString().equalsIgnoreCase("Added")) {

                        ArrayList<GetSubCategory_Result> product = new ArrayList<>();
                        if (MySharedPref.getProductCart(activity) != null) {
                            product.addAll(MySharedPref.getProductCart(activity));
                        }

                        for (int i = 0; i < product.size(); i++) {
                            if (product.get(i).getProduct_id().equalsIgnoreCase(task.get(getAdapterPosition()).getProduct_id())) {
                                product.get(i).setQty(quantity.getText().toString().trim());
                                product.get(i).setPrice(txt_price.getText().toString().replace("र  ", "").trim());
                                product.get(i).setgst_no(task.get(getAdapterPosition()).getgst_no());
                                product.get(i).setdelivery_charge(task.get(getAdapterPosition()).getdelivery_charge());
                            }
                        }


                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "" + quantity.getText().toString().trim() + " Quantity Added", Toast.LENGTH_SHORT).show();
                            }
                        });


                        MySharedPref.addProductToCart(activity, product);
                        ((SubCategory_Activity) activity).addcartnumber();
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Please Add Quantity Below Stock", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (view == remove_item) {
                count = Integer.parseInt(quantity.getText().toString());
                if (count > 1) {
                    count--;
                    quantity.setText("" + count);
                    quantity.setCursorVisible(true);
                    //    int i1= Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                    i1 = Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                    gst = Integer.parseInt(task.get(getAdapterPosition()).getgst_no());
                    del = Integer.parseInt(task.get(getAdapterPosition()).getdelivery_charge());
                    ab = i1*gst/100;
                    ac = ab + del;
                    tot = ac + i1;
                    tot = tot * count;
                    if (i1 != 0) {
                        txt_price.setText("र  " + tot);

                    } else {
                        i1 = Integer.parseInt(task.get(getAdapterPosition()).getPrice());
                        txt_price.setText("र  " + tot/*task.get(getAdapterPosition()).getPrice()*/);

                    }

                    if (add_cart.getText().toString().equalsIgnoreCase("Added")) {
                        ArrayList<GetSubCategory_Result> product = new ArrayList<>();
                        if (MySharedPref.getProductCart(activity) != null) {
                            product.addAll(MySharedPref.getProductCart(activity));
                        }
                        for (int i = 0; i < product.size(); i++) {
                            if (product.get(i).getProduct_id().equalsIgnoreCase(task.get(getAdapterPosition()).getProduct_id())) {
                                product.get(i).setQty(quantity.getText().toString().trim());
                                product.get(i).setPrice(txt_price.getText().toString().replace("र  ", "").trim());
                                product.get(i).setgst_no(task.get(getAdapterPosition()).getgst_no());
                                product.get(i).setdelivery_charge(task.get(getAdapterPosition()).getdelivery_charge());
                            }
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, "" + quantity.getText().toString().trim() + " Quantity Added", Toast.LENGTH_SHORT).show();
                            }
                        });
                        MySharedPref.addProductToCart(activity, product);
                        ((SubCategory_Activity) activity).addcartnumber();
                    }
                    System.out.println("SdfsdF:::  " + i1);
                }
            } else if (view == add_cart) {
                if (add_cart.getText().toString().equalsIgnoreCase("Added")) {

                    activity.startActivity(new Intent(activity, Product_details_Page.class)
                            .putExtra("allData", task.get(getAdapterPosition()))
                            .putExtra("price", "" + txt_price.getText().toString().replace("र  ", "").trim())
                            .putExtra("qnt", "" + task.get(getAdapterPosition()).getQty()));

                    Animatoo.animateSlideUp(activity);


                } else {
                    ArrayList<GetSubCategory_Result> product2 = new ArrayList<>();
                    if (MySharedPref.getProductCart(activity) != null) {
                        product2.addAll(MySharedPref.getProductCart(activity));
                    }
                    if (MySharedPref.getData(activity, "cartSize", null) != null) {
                        Size = Integer.parseInt(MySharedPref.getData(activity, "cartSize", null));
                    }
                    Size++;
                    MySharedPref.saveData(activity, "cartSize", "" + Size);

                    System.out.println("afadsf12:::   " + task.get(getAdapterPosition()).toString());

                    GetSubCategory_Result getSubCategory_result = new GetSubCategory_Result();
                    getSubCategory_result.setQty_type("" + task.get(getLayoutPosition()).getQty_type());
                    getSubCategory_result.setName("" + task.get(getLayoutPosition()).getName());
                    getSubCategory_result.setShopName("" + task.get(getLayoutPosition()).getShopName());
                    getSubCategory_result.setCategory_id("" + task.get(getLayoutPosition()).getCategory_id());
                    getSubCategory_result.setCategory_name("" + task.get(getLayoutPosition()).getCategory_name());
                    getSubCategory_result.setAvg_rating("" + task.get(getLayoutPosition()).getAvg_rating());
                    getSubCategory_result.setEntrydt("" + task.get(getLayoutPosition()).getEntrydt());
                    getSubCategory_result.setImage("" + task.get(getLayoutPosition()).getImage());
                    getSubCategory_result.setStatus("" + task.get(getLayoutPosition()).getStatus());
                    getSubCategory_result.setDescription("" + task.get(getLayoutPosition()).getDescription());
                    getSubCategory_result.setVendorId("" + task.get(getLayoutPosition()).getVendorId());
                    getSubCategory_result.setProduct_id("" + task.get(getLayoutPosition()).getProduct_id());
                    getSubCategory_result.setPrice("" + task.get(getLayoutPosition()).getPrice());
                    //getSubCategory_result.setPrice(txt_price.getText().toString().replace("र  ", "").trim());
                    getSubCategory_result.setQty(quantity.getText().toString().trim());
                    getSubCategory_result.setgst_no("" + task.get(getLayoutPosition()).getgst_no());
                    getSubCategory_result.setdelivery_charge("" + task.get(getLayoutPosition()).getdelivery_charge());
                    getSubCategory_result.setFinal_amount(txt_price.getText().toString().replace("र ", "").trim());
                    product2.add(getSubCategory_result);

                    //product.ad

                    System.out.println("afadjjdsjddsf09:::   " + task.get(getAdapterPosition()).toString());

                    MySharedPref.addProductToCart(activity, product2);
                    ((SubCategory_Activity) activity).addcartnumber();

                    notifyDataSetChanged();
//                    add_cart.setText("Added");
//                    add_cart.setBackground(activity.getResources().getDrawable(R.drawable.btn_gray));
//
//                    rel_addhelper.setVisibility(View.INVISIBLE);
                }


            }


        }
    }
}