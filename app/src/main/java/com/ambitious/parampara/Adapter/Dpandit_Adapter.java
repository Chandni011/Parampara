package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.Dpandit_list;
import com.ambitious.parampara.Model.Mandal_list;
import com.ambitious.parampara.R;

import java.util.List;

public class Dpandit_Adapter extends RecyclerView.Adapter<Dpandit_Adapter.ViewHolder> {
    private Activity activity;
    private List<Dpandit_list> data;

    public Dpandit_Adapter(Activity activity, List<Dpandit_list> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_dpandit, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(data.get(position).getuser_name().trim());
        holder.bookingdate.setText(data.get(position).getdate().trim());
        holder.bookingtime.setText(data.get(position).gettime().trim());
        holder.email.setText(data.get(position).getemail().trim());
        holder.mobile.setText(data.get(position).getmobile().trim());
        holder.address.setText(data.get(position).getaddress().trim() + " " + data.get(position).getpincode().trim());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView username, bookingdate, bookingtime, address, email, mobile;
        public ViewHolder(final View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            bookingdate=itemView.findViewById(R.id.bookingdate);
            bookingtime=itemView.findViewById(R.id.bookingtime);
            address=itemView.findViewById(R.id.address);
            email=itemView.findViewById(R.id.email);
            mobile=itemView.findViewById(R.id.mobile);
        }
    }
}
