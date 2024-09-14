package com.ambitious.parampara.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.Ayojan_desc;
import com.ambitious.parampara.Activity.others.PanditBooking_Desc;
import com.ambitious.parampara.Model.BhavyaAjojanResultModal;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.Currency;
import java.util.List;

public class BhavyaAjojanAdapter extends RecyclerView.Adapter<BhavyaAjojanAdapter.ViewHolder> {
    private Activity activity;
    private List<BhavyaAjojanResultModal> task;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();


    public BhavyaAjojanAdapter(Activity activity, List<BhavyaAjojanResultModal> task) {
        this.activity = activity;
        this.task = task;

    }

    @Override
    public BhavyaAjojanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bhajanmandal, parent, false);
        return new BhavyaAjojanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BhavyaAjojanAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (task.get(position).getAyojanName() != null) {
            holder.nametxt.setText(task.get(position).getAyojanName());
        }

        if (task.get(position).getDescription() != null) {
            holder.descriptiontxt.setText(task.get(position).getDescription());
        }


        if (task.get(position).getImage() != null) {
            System.out.println(task.get(position).getImage()+"..img");
            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                    .error(R.drawable.satyanarayanpuja)
                    .placeholder(R.drawable.ic_processing)
                    .into(holder.imageView);
        }


        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Ayojan_desc.class)
                        .putExtra("poojaID", task.get(position).getAyojanId())
                        .putExtra("Poojaname", task.get(position).getAyojanName())
                        .putExtra("Poojasamagriprice", task.get(position).getPrice())
                        //.putExtra("Poojawithoutsamagriprice", task.get(position).getConvenienceFee())
                        .putExtra("poojaImage", task.get(position).getImage())
                        .putExtra("poojades", task.get(position).getDescription())
                        .putExtra("poojaconvenience", task.get(position).getConvenienceFee())
                        .putExtra("poojagst", task.get(position).getGST())
                        .putExtra("type", "1")
                );

                Animatoo.animateSlideLeft(activity);
            }
        });


        holder.pricetxt.setText("Price :" + symbol + " " + task.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nametxt, descriptiontxt, pricetxt;
        ImageView imageView;
        LinearLayout llmainlayout;
        Button buybtn;

        public ViewHolder(final View itemView) {
            super(itemView);
            nametxt = itemView.findViewById(R.id.item_nametxt);
            imageView = itemView.findViewById(R.id.imageview);
            descriptiontxt = itemView.findViewById(R.id.discription_txt);
            llmainlayout = itemView.findViewById(R.id.ll_main_layout);
            pricetxt = itemView.findViewById(R.id.price_txt);
            buybtn = itemView.findViewById(R.id.buy);
        }

    }
}