package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.notification;
import com.ambitious.parampara.Model.notificationModel;
import com.ambitious.parampara.R;

import java.util.List;


/**
 * Created by Bikram on 23/04/2020.
 */

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.ViewHolder> {
    private Activity activity;
    private List<notificationModel> task;

    int flag = 0;



    public notificationAdapter(Activity activity, List<notificationModel> task) {
        this.activity = activity;
        this.task = task;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mTitle.setText(task.get(position).getTitle());
        holder.mDec.setText(task.get(position).getBody());

    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mDec;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mDec = (TextView) itemView.findViewById(R.id.dec);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(task.get(getAdapterPosition()).getActivity()!=null) {
                        try {
                            Class<?> c = Class.forName(task.get(getAdapterPosition()).getActivity());
                            Intent intent = new Intent(activity, c);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.startActivity(intent);
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    ((notification)activity).removeNotification(getAdapterPosition());
                }
            });

        }

    }


}