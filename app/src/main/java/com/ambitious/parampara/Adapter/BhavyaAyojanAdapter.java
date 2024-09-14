package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.BhavyaResultModal;
import com.ambitious.parampara.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.facebook.FacebookSdk.getApplicationContext;

public class BhavyaAyojanAdapter extends RecyclerView.Adapter<BhavyaAyojanAdapter.ViewHolder> {
    private String strImage;
    private String strusername;
    private Activity activity;
    private List<BhavyaResultModal> task;
    TextView username;
    CircleImageView cv_userImage;


    public BhavyaAyojanAdapter(Activity activity, List<BhavyaResultModal> task) {
        this.activity = activity;
        this.task = task;
        String ldata = getData(getApplicationContext(), "ldata", null);
        System.out.println("***ldata" + ldata);
        if (ldata != null) {
            try {
                JSONObject jsonObject = new JSONObject(ldata);
                System.out.println("Ldataint" + ldata);
                strusername = jsonObject.getString("username");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BhavyaAyojanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accepted, parent, false);
        return new BhavyaAyojanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BhavyaAyojanAdapter.ViewHolder holder, final int position) {
//        holder.booking_id.setText("Booking Id : "+task.get(position).getPoojaBookingId());
//        holder.poojaname.setText("Pooja : "+task.get(position).getPoojaName());
//        String[] separated = task.get(position).getDate().split("-");
//        holder.bookingdate.setText("Booking Date : "+separated[2]+"-"+separated[1]+"-"+separated[0]);
//        holder.bookingtime.setText("Booking Time : "+task.get(position).getTime());
//        holder.price.setText("Price : ₹ "+task.get(position).getPrice());
//        if (task.get(position).getWithItem().equals("1")){
//            holder.withinclue.setText("Samagri Include");
//        }else {
//            holder.withinclue.setText("Without Samagri");
//        }
//
//        username.setText("Name : "+strusername);
//        Log.e("try"," "+task.get(position).getImage());
//        try {
//            Picasso.with(activity).load("https://parapara-dev.herokuapp.com//"+task.get(position).getImage()).error(R.drawable.ut).placeholder(R.drawable.ut).into(cv_userImage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView poojaname, bookingdate, bookingtime, price, withinclue, booking_id;


        public ViewHolder(final View itemView) {
            super(itemView);
            cv_userImage = itemView.findViewById(R.id.cv_userImage);
            username = itemView.findViewById(R.id.username);
            poojaname = itemView.findViewById(R.id.poojaname);
            bookingdate = itemView.findViewById(R.id.bookingdate);
            bookingtime = itemView.findViewById(R.id.bookingtime);

            price = itemView.findViewById(R.id.price);
            withinclue = itemView.findViewById(R.id.withinclue);
            booking_id = itemView.findViewById(R.id.booking_id);
            username.setVisibility(View.GONE);
            withinclue.setVisibility(View.GONE);
        }


    }

}