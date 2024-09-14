package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.Order_details_activity;
import com.ambitious.parampara.Model.orderModel;
import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;


/**
 * Created by Bikram on 20/04/2020.
 */

public class myOrderAdapter extends RecyclerView.Adapter<myOrderAdapter.myViewHolder> {
    private Activity activity;
    private List<orderModel.order> task;
java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();

    int amt, admin_amt, final_amt;
    String amount;


    /*public E_store_Adapter(Activity activity, List<Get_SubCategory_Result> task, HashMap hashMap, HashMap hashMapprice) {
        this.activity = activity;
        this.task = task;
        this.hashMap =hashMap;
        this.hashMapprice=hashMapprice;
    }*/

    public myOrderAdapter(Activity activity, List<orderModel.order> task) {
        this.activity = activity;
        this.task = task;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        orderModel.order order = task.get(position);

        amt = Integer.parseInt(order.getAmount());
        admin_amt = Integer.parseInt(order.getAdmin_amount());

        final_amt = amt + admin_amt;

        amount = Integer.toString(final_amt);

        holder.mPriceProduct.setText(symbol+" "+amount);
        holder.mNameProduct.setText(order.getName());
        holder.mNameShop.setText("Shop Name  : "+order.getShopName());
        holder.mProductqnt.setText("(Qty : "+order.getQuantity()+")");
        holder.mProductDate.setText(formatDateFromString("yyyy-MM-dd hh:mm:ss","ddMMM yyyy hh:mm a",order.getEntry_date()));

        try {
            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + order.getImage())
                    .error(R.drawable.ic_product)
                    .into(holder.mImgProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        0=place,1=accept,2=packed,3=deliver,4=delivered,5=cancel by user,6=cancel by vender,7=cancel by admin,8=expierd
        switch (Integer.parseInt(order.getStatus())) {
            case 0:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_yellow));
                holder.mTxtStatus.setText("Placed");
                break;
            case 1:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_green));
                holder.mTxtStatus.setText("Accepted");
                break;
            case 2:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_cyan));
                holder.mTxtStatus.setText("Packed");
                break;
            case 3:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_blue));
                holder.mTxtStatus.setText("Deliver");
                break;

            case 4:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_purple));
                holder.mTxtStatus.setText("Delivered");
                break;

            case 5:
            case 6:
            case 7:
            case 8:
                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_red));
                holder.mTxtStatus.setText("Canceled");
//                break;
//            case 6:
//                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_red));
//                holder.mTxtStatus.setText("Cancel\nBy\nSaller");
//                break;
//            case 7:
//                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_red));
//                holder.mTxtStatus.setText("Cancel\nBy\nAdmin");
//                break;
//            case 8:
//                holder.mLyStatus.setBackground(activity.getResources().getDrawable(R.drawable.right_round_red));
//                holder.mTxtStatus.setText("Expired");
                break;
        }


    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtStatus;
        private RelativeLayout mLyStatus;
        private TextView mNameProduct;
        private TextView mPriceProduct;
        private TextView mNameShop;
        private TextView mProductqnt;
        private CircleImageView mImgProduct;
        private TextView mProductDate;
        private LinearLayout m4Ly;
        private View mLine2;


        public myViewHolder(final View itemView) {
            super(itemView);
            mTxtStatus = (TextView) itemView.findViewById(R.id.status_txt);
            mProductqnt = (TextView) itemView.findViewById(R.id.product_qnty);
            mLyStatus = (RelativeLayout) itemView.findViewById(R.id.status_ly);
            mNameProduct = (TextView) itemView.findViewById(R.id.product_name);
            mPriceProduct = (TextView) itemView.findViewById(R.id.product_price);
            mNameShop = (TextView) itemView.findViewById(R.id.shop_name);
            mImgProduct = (CircleImageView) itemView.findViewById(R.id.product_img);
            mProductDate = (TextView) itemView.findViewById(R.id.date_product);
            m4Ly = (LinearLayout) itemView.findViewById(R.id.ly_4);
            mLine2 = (View) itemView.findViewById(R.id.line2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.startActivity(new Intent(activity, Order_details_activity.class)
                    .putExtra("orderModel", (Serializable) task.get(getAdapterPosition())));


                }
            });

        }

    }
    private  String formatDateFromString(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            Log.e(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }


}