package com.ambitious.parampara.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.ambitious.parampara.Model.DailyPanditResult;
import com.ambitious.parampara.R;

import java.util.Currency;
import java.util.List;

public class DailyPanditListAdapter extends RecyclerView.Adapter<DailyPanditListAdapter.ViewHolder> {
    private Activity activity;
    private List<DailyPanditResult> task;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();

    DailyPanditItem panditItem;

    public interface DailyPanditItem {
        void panditItemDetails(String id, String name, String price);
    }

    public DailyPanditListAdapter(Activity activity, List<DailyPanditResult> task, DailyPanditItem dailyPanditItem) {
        this.activity = activity;
        this.task = task;
        this.panditItem = dailyPanditItem;
    }

    @Override
    public DailyPanditListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscription_layout, parent, false);
        return new DailyPanditListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DailyPanditListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (task.get(position).getSubscriptionType() != null) {

            if (task.get(position).getSubscriptionType().equals("1")) {
                holder.vertualtxtName.setText("Monthly");
            } else if (task.get(position).getSubscriptionType().equals("2")) {
                holder.vertualtxtName.setText("2 Monthly");
            } else if (task.get(position).getSubscriptionType().equals("3")) {
                holder.vertualtxtName.setText("Quaterly");
            } else if (task.get(position).getSubscriptionType().equals("4")) {
                holder.vertualtxtName.setText("Half Yearly");
            } else {
                holder.vertualtxtName.setText("Yearly");
            }
        }


        if (task.get(position).getPrice() != null) {
            holder.pricetxt.setText(symbol + " " + task.get(position).getPrice());
        }


        holder.llmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panditItem.panditItemDetails(task.get(position).getId(), task.get(position).getSubscriptionFor(),
                        task.get(position).getPrice());
            }
        });

    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vertualtxtName, pricetxt;
        LinearLayout llmainlayout;
        ImageView ayojanImg;

        public ViewHolder(final View itemView) {
            super(itemView);
            vertualtxtName = itemView.findViewById(R.id.vertual_nametxt);
            llmainlayout = itemView.findViewById(R.id.ll_main_layout);
            ayojanImg = itemView.findViewById(R.id.ayojan_img);
            pricetxt = itemView.findViewById(R.id.price_txt);
        }

    }
}
