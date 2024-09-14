package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.SubCategory_Activity;
import com.ambitious.parampara.Model.GetCategory_Result;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by user1 on 11/25/2017.
 */

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.ViewHolder> {
    private Activity activity;
    private List<GetCategory_Result> task;

    /*public E_store_Adapter(Activity activity, List<Get_SubCategory_Result> task, HashMap hashMap, HashMap hashMapprice) {
        this.activity = activity;
        this.task = task;
        this.hashMap =hashMap;
        this.hashMapprice=hashMapprice;
    }*/

    public Category_Adapter(Activity activity, List<GetCategory_Result> task) {
        this.activity = activity;
        this.task = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        // holder.txt_sub_catName.setText(task.get(position).getServicesSubName());
        // holder.txt_price.setText("र  "+task.get(position).getPrice());
        if (task.get(position).getImage() != null) {
            try {
                Picasso.get()
                        .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                        .placeholder(R.drawable.product_placeholder)
                        .error(R.drawable.product_placeholder)
                        .into(holder.imge);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        holder.txt_categroy.setText(task.get(position).getCategoryName());
    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_categroy;

        ImageView imge;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_categroy = itemView.findViewById(R.id.txt_categroy);
            imge = itemView.findViewById(R.id.image);


        }

        @Override
        public void onClick(View view) {
            activity.startActivity(new Intent(activity, SubCategory_Activity.class)
                    .putExtra("catID", task.get(getAdapterPosition()).getCategoryId())
                    .putExtra("catName", task.get(getAdapterPosition()).getCategoryName())
            );
            Animatoo.animateSlideLeft(activity);

        }
    }

}