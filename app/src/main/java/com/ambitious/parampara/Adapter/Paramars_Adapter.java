package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.Ayojan_list;
import com.ambitious.parampara.Model.Paramars_list;
import com.ambitious.parampara.R;

import java.util.List;

public class Paramars_Adapter extends RecyclerView.Adapter<Paramars_Adapter.ViewHolder> {
    private Activity activity;
    private List<Paramars_list> data;

    public Paramars_Adapter(Activity activity, List<Paramars_list> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_paramars, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.username.setText(data.get(position).getname().trim());
        holder.dob.setText(data.get(position).getdob().trim());
        holder.bplace.setText(data.get(position).getplace_birth().trim());
        holder.quetion.setText(data.get(position).getquestion().trim());
        holder.calloption.setText(data.get(position).getcall_option().trim());
        holder.preftime.setText(data.get(position).getpreffered_timing().trim());
        holder.price.setText(data.get(position).getprice().trim());
        holder.tob.setText(data.get(position).getdob_time().trim());
        holder.gender.setText(data.get(position).getgender().trim());
        holder.contact.setText(data.get(position).getcontact().trim());
        holder.email.setText(data.get(position).getemail().trim());
        holder.language.setText(data.get(position).getprefered_language().trim());
        holder.description.setText(data.get(position).getdescription().trim());

    }

    @Override
    public int getItemCount() {
        return data.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView username, dob, bplace, quetion, calloption, preftime, price, tob, gender, contact, email, language, description;
        public ViewHolder(final View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.username);
            dob=itemView.findViewById(R.id.dob);
            bplace=itemView.findViewById(R.id.bplace);
            quetion=itemView.findViewById(R.id.question);
            calloption=itemView.findViewById(R.id.calloption);
            preftime=itemView.findViewById(R.id.preftime);
            price=itemView.findViewById(R.id.price);
            tob=itemView.findViewById(R.id.tob);
            gender=itemView.findViewById(R.id.gender);
            contact=itemView.findViewById(R.id.contact);
            email=itemView.findViewById(R.id.email);
            language=itemView.findViewById(R.id.language);
            description=itemView.findViewById(R.id.description);
        }
    }
}
