package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.Donation_list;
import com.ambitious.parampara.Model.Kundali_list;
import com.ambitious.parampara.R;

import java.util.List;

public class Donation_Adapter extends RecyclerView.Adapter<Donation_Adapter.ViewHolder> {
    private Activity activity;
    private List<Donation_list> data;

    public Donation_Adapter(Activity activity, List<Donation_list> data){
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_donation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.cause.setText(data.get(position).getdonation_cause().trim());
        holder.amt.setText(data.get(position).getamt().trim());
        holder.mode.setText(data.get(position).getmode().trim());
        holder.transection_id.setText(data.get(position).gettransection_id().trim());
        holder.name.setText(data.get(position).getuserName().trim());
        holder.mobile.setText(data.get(position).getmobile().trim());
        holder.email.setText(data.get(position).getemail().trim());
        //holder.address.setText(data.get(position).getadderss().trim()/*+", "+data.get(position).getcity().trim()+", "+data.get(position).getpincode().trim()*/);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView cause, amt, mode, transection_id, name, mobile, email, address;
        public ViewHolder(final View itemView) {
            super(itemView);
            cause=itemView.findViewById(R.id.cause);
            amt=itemView.findViewById(R.id.amt);
            mode=itemView.findViewById(R.id.mode);
            transection_id=itemView.findViewById(R.id.transection_id);
            name=itemView.findViewById(R.id.name);
            mobile=itemView.findViewById(R.id.mobile);
            email=itemView.findViewById(R.id.email);
            //address=itemView.findViewById(R.id.address);
        }
    }
}
