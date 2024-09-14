package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Model.mandal.BhajanPendingResult;
import com.ambitious.parampara.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by user1 on 11/25/2017.
 */

public class BhanajaPendingBooking_Adapter extends RecyclerView.Adapter<BhanajaPendingBooking_Adapter.ViewHolder> {
    private String strImage;
    private String strusername;
    private Activity activity;
    private List<BhajanPendingResult> task;
    TextView username;
    CircleImageView cv_userImage;


    public BhanajaPendingBooking_Adapter(Activity activity, List<BhajanPendingResult> task) {
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
    public BhanajaPendingBooking_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accepted, parent, false);
        return new BhanajaPendingBooking_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BhanajaPendingBooking_Adapter.ViewHolder holder, final int position) {
        holder.booking_id.setText("Booking Id : " + task.get(position).getBookingId());
        holder.poojaname.setText("Bhajan : " + task.get(position).getMandaliName());
        String[] separated = task.get(position).getDate().split("-");
        holder.bookingdate.setText("Booking Date : " + separated[2] + "-" + separated[1] + "-" + separated[0]);
        holder.bookingtime.setText("Booking Time : " + task.get(position).getTime());
       holder.price.setText("Price : ₹ " + task.get(position).getAmount());

//        if (task.get(position).getWithItem().equals("1")) {
//            holder.withinclue.setText("Samagri Include");
//        } else {
//            holder.withinclue.setText("Without Samagri");
//        }

        username.setText("Name : " + strusername);
        Log.e("try", " " + task.get(position).getImage());
        try {
            Picasso.get()
                    .load("http://vedicparampara.com/panel/" + task.get(position).getImage())
                    .error(R.drawable.ut)
                    .placeholder(R.drawable.ut)
                    .into(cv_userImage);

        } catch (Exception e) {
            e.printStackTrace();
        }
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


