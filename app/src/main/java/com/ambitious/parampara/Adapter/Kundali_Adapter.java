package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.Kundali_list;
import com.ambitious.parampara.R;

import java.util.List;

import static com.ambitious.parampara.R.id.username;

public class Kundali_Adapter extends RecyclerView.Adapter<Kundali_Adapter.ViewHolder> {
    private Activity activity;
    private List<Kundali_list> data;

    public Kundali_Adapter(Activity activity, List<Kundali_list> data){
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_kundali, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.username.setText(data.get(position).getname().trim());
        holder.dob.setText(data.get(position).getdob().trim());
        holder.bplace.setText(data.get(position).getplace_birth().trim());
        holder.price.setText(data.get(position).getprice().trim());
        holder.tob.setText(data.get(position).gettime_of_birth().trim());
        holder.currentaddress.setText(data.get(position).getaddress().trim());
        holder.gender.setText(data.get(position).getgender().trim());
        holder.contact.setText(data.get(position).getcontact().trim());
        holder.email.setText(data.get(position).getemail().trim());
        holder.description.setText(data.get(position).getdescription().trim());
        holder.language.setText(data.get(position).getlanguage().trim());

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView username, dob, bplace, price, tob, currentaddress, gender, contact, email, description, language;
        public ViewHolder(final View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            dob=itemView.findViewById(R.id.dob);
            bplace=itemView.findViewById(R.id.address);
            price=itemView.findViewById(R.id.price);
            tob=itemView.findViewById(R.id.tob);
            currentaddress=itemView.findViewById(R.id.currentaddress);
            gender=itemView.findViewById(R.id.gender);
            contact=itemView.findViewById(R.id.contact);
            email=itemView.findViewById(R.id.email);
            description=itemView.findViewById(R.id.description);
            language=itemView.findViewById(R.id.language);
        }
    }
}
