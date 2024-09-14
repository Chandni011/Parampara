package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.nativePlace;
import com.ambitious.parampara.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Bikram on 23/04/2020.
 */

public class map_place_adapter extends RecyclerView.Adapter<map_place_adapter.ViewHolder> {
    private Activity activity;
    private ArrayList<nativePlace.Results> task;

    int flag = 0;



    public map_place_adapter(Activity activity, ArrayList<nativePlace.Results> task) {
        this.activity = activity;
        this.task = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_map_place, parent, false);
        return new ViewHolder(view);
    }

    private void initView(@NonNull final View itemView) {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String opnnow="";

        if(task.get(position).getOpening_hours()!=null) {
            if (task.get(position).getOpening_hours().getOpen_now()) {
                opnnow = "OPEN";
            } else {
                opnnow = "CLOSE";
            }
            holder.mNameProduct.setText(task.get(position).getName()+"  ("+opnnow+")");

        }else
        {
            holder.mNameProduct.setText(task.get(position).getName());


        }

        holder.mPriceProduct.setText(task.get(position).getVicinity());


//        String distance = String.valueOf(distance(((TempleLocator_Activity)activity).latitiude,((TempleLocator_Activity)activity).longitude,task.get(position).getGeometry().getLocation().getLat(),task.get(position).getGeometry().getLocation().getLat()));
//        holder.distance.setText(distance+" Km");

//        holder.name.setText(task.get(position).getPooja_name());


    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView mImgProduct;
        private ImageView mDirection;
        private TextView mNameProduct;
        private TextView mPriceProduct;
        private LinearLayout mLy;
        private View mLine;
        private TextView mInCheck;
        private LinearLayout mProductEdit;
        private TextView mOutCheck;
        private TextView distance;
        private LinearLayout mProductDelete;
        private LinearLayout mLL;
        private View mLine2;
        private RelativeLayout m1Rl;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImgProduct = (CircleImageView) itemView.findViewById(R.id.product_img);
            mDirection = (ImageView) itemView.findViewById(R.id.direction);
            mNameProduct = (TextView) itemView.findViewById(R.id.product_name);
            distance = (TextView) itemView.findViewById(R.id.distance);
            mPriceProduct = (TextView) itemView.findViewById(R.id.product_price);
            mLy = (LinearLayout) itemView.findViewById(R.id.ly);
            mLine = (View) itemView.findViewById(R.id.line);
            mInCheck = (TextView) itemView.findViewById(R.id.check_in);
            mProductEdit = (LinearLayout) itemView.findViewById(R.id.edit_product);
            mOutCheck = (TextView) itemView.findViewById(R.id.Check_out);
            mProductDelete = (LinearLayout) itemView.findViewById(R.id.delete_product);
            mLL = (LinearLayout) itemView.findViewById(R.id.lL);
            mLine2 = (View) itemView.findViewById(R.id.line2);
            m1Rl = (RelativeLayout) itemView.findViewById(R.id.rl_1);

            mDirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("asfdasd::: "+task.get(getAdapterPosition()).getGeometry().getLocation());
                    startNavigation(activity,""+task.get(getAdapterPosition()).getGeometry().getLocation().getLat()+","+task.get(getAdapterPosition()).getGeometry().getLocation().getLng());
                }
            });



        }

    }
    private void startNavigation(Context context, String loc) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + loc);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }
    private  double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;

        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



}