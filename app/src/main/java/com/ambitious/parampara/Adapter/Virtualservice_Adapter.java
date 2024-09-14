package com.ambitious.parampara.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.MakingKundaliAct;
import com.ambitious.parampara.Model.GetVertualResult;
import com.ambitious.parampara.R;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.List;


/**
 * Created by user1 on 11/25/2017.
 */


public class Virtualservice_Adapter extends RecyclerView.Adapter<Virtualservice_Adapter.ViewHolder> {
    private Activity activity;
    private List<GetVertualResult> task;


    public Virtualservice_Adapter(Activity activity, List<GetVertualResult> task) {
        this.activity = activity;
        this.task = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertualservice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (task.get(position).getVirtualService() != null) {
            holder.vertualtxtName.setText(task.get(position).getVirtualService());
            holder.discription_txt.setText(task.get(position).getdescription());
        }

        holder.llmainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, MakingKundaliAct.class)
                        .putExtra("vertualID", task.get(position).getId()).putExtra("vertualName",
                                task.get(position).getVirtualService()).putExtra("vertuaPrice", task
                                .get(position).getPrice())
                );
                Animatoo.animateSlideLeft(activity);
            }
        });

    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView vertualtxtName, discription_txt;
        LinearLayout llmainlayout;

        public ViewHolder(final View itemView) {
            super(itemView);
            vertualtxtName = itemView.findViewById(R.id.vertual_nametxt);
            discription_txt = itemView.findViewById(R.id.discription_txt);
            llmainlayout = itemView.findViewById(R.id.ll_main_layout);
        }

    }
}