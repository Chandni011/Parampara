package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.MyCart_items;
import com.ambitious.parampara.Activity.others.Product_details_Page;
import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class cartItem_Adapter extends RecyclerView.Adapter<cartItem_Adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<GetSubCategory_Result> task;
    private ArrayList<GetSubCategory_Result> product;
    private ImageView rate1;
    private ImageView rate2;
    private ImageView rate3;
    private ImageView rate4;
    int TotalQunty, i1, gst, del, ab, ac, tot, ad;
    private ImageView rate5;


    public cartItem_Adapter(Activity activity, ArrayList<GetSubCategory_Result> task ,ArrayList<GetSubCategory_Result> product) {
        this.activity = activity;
        this.task = task;
        this.product = product;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        i1 = Integer.parseInt(task.get(position).getPrice());
        gst = Integer.parseInt(task.get(position).getgst_no());
        del = Integer.parseInt(task.get(position).getdelivery_charge());
        TotalQunty = Integer.parseInt(task.get(position).getQty());
        ab = i1*gst/100;
        ac = i1 + del;
        tot = ac + ab;

        ad = TotalQunty * tot;

        holder.mQntyProduct.setText("Qty : " + task.get(position).getQty() + " " + task.get(position).getQty_type());
        holder.mNameTxt.setText(task.get(position).getName());
        //holder.Total_price.setText("Rs " +task.get(position).getPrice());
        holder.Total_price.setText("Rs " +ad);
        holder.txt_price_org.setText("Price : "+"Rs " + Integer.parseInt(task.get(position).getPrice()));
        holder.mCategoryTxt.setText(task.get(position).getCategory_name());
        holder.mSellerTxt.setText("Seller : " + task.get(position).getShopName());
        if (task.get(position).getImage() != null) {
            try {
                Picasso.get()
                        .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                        .placeholder(R.drawable.product_placeholder)
                        .error(R.drawable.product_placeholder)
                        .into(holder.mImagePro);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!task.get(position).getAvg_rating().isEmpty()) {

            double a = Double.parseDouble(task.get(position).getAvg_rating());

            rating((int) Math.ceil(a));
        }

        if(task.get(position).getEntrydt().equalsIgnoreCase("OOS")){
            holder.mOfStockOut.setVisibility(View.VISIBLE);
        }else {
            holder.mOfStockOut.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return task.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTxt, Total_price;
        private TextView mCategoryTxt;
        private TextView mSellerTxt;
        private TextView txt_price_org;
        private ImageView mImagePro;
        private TextView mQntyProduct;
        private RelativeLayout m2Rec;
        private TextView mOfStockOut;
        private View mLine;
        private TextView mInCheck;

        private LinearLayout mRatingLyPandit;
        private LinearLayout mProductEdit;
        private TextView mOutCheck;
        private LinearLayout mProductDelete;
        private LinearLayout mLL;
        private RelativeLayout m1Rec;

        public ViewHolder(final View itemView) {
            super(itemView);
            mNameTxt = (TextView) itemView.findViewById(R.id.txt_name);
            Total_price = (TextView) itemView.findViewById(R.id.Total_price);
            txt_price_org = (TextView) itemView.findViewById(R.id.txt_price_org);
            mCategoryTxt = (TextView) itemView.findViewById(R.id.txt_category);
            mSellerTxt = (TextView) itemView.findViewById(R.id.txt_seller);
            mImagePro = (ImageView) itemView.findViewById(R.id.pro_image);
            mQntyProduct = (TextView) itemView.findViewById(R.id.product_qnty);
            m2Rec = (RelativeLayout) itemView.findViewById(R.id.rec_2);
            mOfStockOut = (TextView) itemView.findViewById(R.id.out_ofStock);
            mLine = (View) itemView.findViewById(R.id.line);
            mInCheck = (TextView) itemView.findViewById(R.id.check_in);
            rate1 = (ImageView) itemView.findViewById(R.id.rate_1);
            rate2 = (ImageView) itemView.findViewById(R.id.rate_2);
            rate3 = (ImageView) itemView.findViewById(R.id.rate_3);
            rate4 = (ImageView) itemView.findViewById(R.id.rate_4);
            rate5 = (ImageView) itemView.findViewById(R.id.rate_5);
            mRatingLyPandit = (LinearLayout) itemView.findViewById(R.id.pandit_rating_ly);
            mProductEdit = (LinearLayout) itemView.findViewById(R.id.edit_product);
            mOutCheck = (TextView) itemView.findViewById(R.id.Check_out);
            mProductDelete = (LinearLayout) itemView.findViewById(R.id.delete_product);

            mLL = (LinearLayout) itemView.findViewById(R.id.lL);
            m1Rec = (RelativeLayout) itemView.findViewById(R.id.rec_1);

            mProductDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Do you want delete?")
                            .setConfirmText("Yes")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    ((MyCart_items) activity).removeItem(getAdapterPosition());

                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }
            });

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String qnt="";
                    String price="";
                    for(int i =0;i<product.size();i++){

                        if(product.get(i).getProduct_id().equalsIgnoreCase(task.get(getAdapterPosition()).getProduct_id())){
                            qnt = product.get(i).getQty();
                            price = product.get(i).getPrice();
                        }
                    }

                    activity.startActivity(new Intent(activity, Product_details_Page.class)
                            .putExtra("allData", task.get(getAdapterPosition()))
                            .putExtra("price", "" + price)
                            .putExtra("qnt", "" +qnt));

                    //category name include orginal qunatitiy....
                    Animatoo.animateSlideUp(activity);
                }
            });*/


        }

    }


    private void rating(int i) {
        switch (i) {
            case 1:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 2:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 3:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 4:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 5:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
        }
    }


}