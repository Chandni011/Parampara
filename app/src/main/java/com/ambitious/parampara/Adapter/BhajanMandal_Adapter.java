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

import com.ambitious.parampara.Activity.others.Mandal_desc;
import com.ambitious.parampara.Activity.others.PanditBooking_Desc;
import com.ambitious.parampara.Model.BhajanMandalResult;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.Currency;
import java.util.List;

public class BhajanMandal_Adapter extends RecyclerView.Adapter<BhajanMandal_Adapter.ViewHolder> {
    private Activity activity;
    private List<BhajanMandalResult> task;
    java.util.Currency currency = Currency.getInstance("INR");
    String symbol = currency.getSymbol();


    public BhajanMandal_Adapter(Activity activity, List<BhajanMandalResult> task) {
        this.activity = activity;
        this.task = task;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bhajanmandal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        if (task.get(position).getMandaliName() != null) {
            holder.nametxt.setText(task.get(position).getMandaliName());
        }

        if (task.get(position).getDescription() != null) {
            holder.descriptiontxt.setText(task.get(position).getDescription());
        }


        if (task.get(position).getImage() != null) {
            System.out.println("http://vedicparampara.com/panel/"+task.get(position).getImage()+"..img");

            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                    .error(R.drawable.satyanarayanpuja)
                    .placeholder(R.drawable.ic_processing)
                    .into(holder.imageView);
        }


        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, Mandal_desc.class)
                        .putExtra("poojaID", task.get(position).getMandalId()).putExtra("Poojaname",
                                task.get(position).getMandaliName()).putExtra("Poojasamagriprice", task
                                .get(position).getPrice()).putExtra("Poojawithoutsamagriprice", task
                                .get(position).getConvenienceFee()).putExtra("poojaImage", task
                                .get(position).getImage()).putExtra("poojades", task
                                .get(position).getDescription()).putExtra("type", "new")
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