package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.PanditBooking_Desc;
import com.ambitious.parampara.Model.Pooja_Result;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


/**
 * Created by user1 on 11/25/2017.
 */

public class PanditList_Adapter extends RecyclerView.Adapter<PanditList_Adapter.ViewHolder> {
   private Activity activity;
   private List<Pooja_Result> task;
    int count = 1;
    HashMap hashMap,hashMapprice;


    public PanditList_Adapter(Activity activity , List<Pooja_Result> task) {
        this.activity = activity;
        this.task=task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_panditbooking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.txt_poojaname.setText(task.get(position).getPoojaName());
        holder.txt_poojades.setText(task.get(position).getDescription());

        holder.txt_samagri.setText("Rs "+task.get(position).getPriceWithItem()+" /-");
        holder.txt_withoutsamagri.setText("Rs "+task.get(position).getPriceWithoutItem()+" /-");

        try {
            String names = task.get(position).getImage();
            String[] namesList = names.split(",");
            String name1 = namesList [0];

            System.out.println("ASDFdsf:::::   "+task.get(position).toString());

            Picasso.get().load("http://vedicparampara.com/panel/"+name1).error(R.drawable.sathiya).placeholder(R.drawable.ic_processing).into(holder.img_pooja);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView txt_poojaname, txt_poojades, txt_samagri, txt_withoutsamagri;
        Button buy;
        ImageView img_pooja;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txt_poojaname = itemView.findViewById(R.id.txt_poojaname);
            txt_poojades = itemView.findViewById(R.id.txt_poojades);
            txt_samagri = itemView.findViewById(R.id.txt_samagri);
            txt_withoutsamagri = itemView.findViewById(R.id.txt_withoutsamagri);
            img_pooja = itemView.findViewById(R.id.img_pooja);
            buy = itemView.findViewById(R.id.buy);
            buy.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (view == buy) {
                activity.startActivity(new Intent(activity, PanditBooking_Desc.class)
                        .putExtra("poojaID", task.get(getAdapterPosition()).getPoojaId())
                        .putExtra("poojaImage", task.get(getAdapterPosition()).getImage())
                        .putExtra("Poojaname", task.get(getAdapterPosition()).getPoojaName())
                        .putExtra("Poojasamagriprice", task.get(getAdapterPosition()).getPriceWithItem())
                        .putExtra("Poojawithoutsamagriprice", task.get(getAdapterPosition()).getPriceWithoutItem())
                        .putExtra("poojades", task.get(getAdapterPosition()).getDescription()));

                Animatoo.animateSlideLeft(activity);

            }
        }
    }


    /*public void updateList(List<Get_SubCategory_Result> list) {
        task = list;
        notifyDataSetChanged();
    }*/
}