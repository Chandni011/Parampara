package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.R;


/**
 * Created by user1 on 11/25/2017.
 */

public class DailyPandit_Adapter extends RecyclerView.Adapter<DailyPandit_Adapter.ViewHolder> {
   private Activity activity;
 //  private List<Get_SubCategory_Result> task;


    public DailyPandit_Adapter(Activity activity) {
        this.activity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dailypandit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



       // holder.txt_sub_catName.setText(task.get(position).getServicesSubName());
       // holder.txt_price.setText("र  "+task.get(position).getPrice());


    }

    @Override
    public int getItemCount() {
        return 2;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
           /* activity.startActivity(new Intent(activity, PanditBooking_Desc.class));
            Animatoo.animateSlideLeft(activity);*/
        }
    }

    /*public void updateList(List<Get_SubCategory_Result> list) {
        task = list;
        notifyDataSetChanged();
    }*/
}