package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.Ayojan_list;
import com.ambitious.parampara.Model.Mandal_list;
import com.ambitious.parampara.R;

import java.util.List;

public class Mandal_Adapter extends RecyclerView.Adapter<Mandal_Adapter.ViewHolder>{
    private Activity activity;
    private List<Mandal_list> data;

    public Mandal_Adapter(Activity activity, List<Mandal_list> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_bhajan, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.username.setText(data.get(position).getuser_name().trim());
        holder.Ayojanname.setText(data.get(position).getmandali_name().trim());
        holder.bookingdate.setText(data.get(position).getdate().trim());
        holder.bookingtime.setText(data.get(position).gettime().trim());
        holder.address.setText(data.get(position).getaddress().trim()+", "+data.get(position).getcity()+", "+data.get(position).getpincode().trim());
        holder.price.setText(data.get(position).getamount().trim());

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView username, Ayojanname, bookingdate, bookingtime, address, price;
        public ViewHolder(final View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            Ayojanname=itemView.findViewById(R.id.Ayojanname);
            bookingdate=itemView.findViewById(R.id.bookingdate);
            bookingtime=itemView.findViewById(R.id.bookingtime);
            address=itemView.findViewById(R.id.address);
            price=itemView.findViewById(R.id.price);
        }
    }
}
