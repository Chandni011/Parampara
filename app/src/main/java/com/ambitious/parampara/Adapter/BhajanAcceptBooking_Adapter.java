package com.ambitious.parampara.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Activity.others.PoojaDetails_Activity;
import com.ambitious.parampara.Activity.poojabooking.CompleteFragment;
import com.ambitious.parampara.Model.AcceptedBooking_Result;
import com.ambitious.parampara.Model.mandal.MandalAcceptedResult;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetProfile_Response;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;
import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by user1 on 11/25/2017.
 */

public class BhajanAcceptBooking_Adapter extends RecyclerView.Adapter<BhajanAcceptBooking_Adapter.ViewHolder> {
    private Activity activity;
    private List<MandalAcceptedResult> task;
    GetProfile_Response successResponse;
    TextView username;
    CircleImageView cv_userImage;
    String ldata, avg, strUser_ID, strusername, priceaa, bookingDate,
            experiance, bookingTime, user, date, time, puja, samagri, address, flat, colony, landmark;
    private TextView mTitile;
    private ImageView m1Rate;
    private ImageView m2Rate;
    private ImageView m3Rate;
    private ImageView m4Rate;
    private ImageView m5Rate;
    private Button mSubmitBtn;
    private int rate = 0;
    private AlertDialog deleteDialog;
    private EditText feedback_edt;
    CompleteFragment completeFragment;
    ImageView rate1, rate2, rate3, rate4, rate5;
    LinearLayout pandit_rating_ly;
    TextView rate_pandit;
    String[] separated;
    String dt, id;
    private String status;
    int flag;
    private ProgressDialog progressDialog;


    public BhajanAcceptBooking_Adapter(Activity activity, List<MandalAcceptedResult> task, int flag) {
        this.activity = activity;
        this.task = task;
        this.flag = flag;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_accepted, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        user = task.get(position).getPanditName();
        priceaa = task.get(position).getAmount();
        puja = task.get(position).getMandaliName();
        date = task.get(position).getDate();
        time = task.get(position).getTime();
        status = task.get(position).getStatus();
        bookingDate = task.get(position).getEntryDate();
        bookingTime = task.get(position).getEntryDate();
        experiance = "6 month";

        //  convePrice = task.get(position).get();

        address = task.get(position).getAddress();
        holder.username.setText("Name : " + task.get(position).getPanditName());
        holder.poojaname.setText("Bhajan : " + task.get(position).getMandaliName());
        separated = task.get(position).getDate().split("-");
        holder.bookingdate.setText("Booking Date : " + separated[2] + "-" + separated[1] + "-" + separated[0]);
        holder.bookingtime.setText("Booking Time : " + task.get(position).getTime());
        holder.price.setText("Price : ₹ " + task.get(position).getAmount());
        holder.booking_id.setText("Booking Id : " + task.get(position).getBookingId());
        dt = task.get(position).getAmount();
        id = task.get(position).getBookingId();
//        if (task.get(position).getWithItem().equals("1")) {
//            samagri = "With Samagri";
//            holder.withinclue.setText("Click for details");
//        } else {
        holder.withinclue.setText("Click for details");
        samagri = "Without Samagri";
        // }
        if (flag == 1) {
            if (task.get(position).getStatus().equalsIgnoreCase("Canceled By User")) {
                holder.withinclue.setText("Canceled By You");
            } else {
                holder.withinclue.setText("" + task.get(position).getStatus());
            }
            holder.withinclue.setTextColor(Color.RED);

        }

        System.out.println("AFdasf::::  " + task.get(position).toString());
        if (task.get(position).getStatus().equalsIgnoreCase("completed") && Integer.parseInt(task.get(position).getUserRating()) <= 0) {
            holder.rate_user.setVisibility(View.VISIBLE);
        } else {
            holder.rate_user.setVisibility(View.GONE);
        }


        pandit_rating_ly.setVisibility(View.VISIBLE);
        rate_pandit.setVisibility(View.GONE);
        try {
            if (!task.get(position).getAvgRating().isEmpty()) {
                double a = Double.parseDouble(task.get(position).getAvgRating());
                ratePandit((int) Math.ceil(a));
                avg = task.get(position).getAvgRating();
            } else {
                avg = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return task.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView poojaname, booking_id, bookingdate, bookingtime, price, withinclue, username, rate_user;


        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cv_userImage = itemView.findViewById(R.id.cv_userImage);
            username = itemView.findViewById(R.id.username);
            username.setVisibility(View.GONE);
            pandit_rating_ly = itemView.findViewById(R.id.pandit_rating_ly);
            rate_pandit = itemView.findViewById(R.id.rate_pandit);

            rate1 = itemView.findViewById(R.id.rate_1);
            rate2 = itemView.findViewById(R.id.rate_2);
            rate3 = itemView.findViewById(R.id.rate_3);
            rate4 = itemView.findViewById(R.id.rate_4);
            rate5 = itemView.findViewById(R.id.rate_5);


            poojaname = itemView.findViewById(R.id.poojaname);
            bookingdate = itemView.findViewById(R.id.bookingdate);
            bookingtime = itemView.findViewById(R.id.bookingtime);
            price = itemView.findViewById(R.id.price);
            withinclue = itemView.findViewById(R.id.withinclue);
            booking_id = itemView.findViewById(R.id.booking_id);
            ldata = getData(getApplicationContext(), "ldata", null);
            System.out.println("***ldata" + ldata);
            if (ldata != null) {
                try {
                    JSONObject jsonObject = new JSONObject(ldata);
                    System.out.println("Ldataint" + ldata);
                    strUser_ID = jsonObject.getString("user_id");
                    strusername = jsonObject.getString("username");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            withinclue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (flag != 1) {
                        activity.startActivity(new Intent(activity, PoojaDetails_Activity.class)
                                .putExtra("user", user)
                                .putExtra("puja", puja)
                                .putExtra("status", status)
                                .putExtra("date", separated[2] + "-" + separated[1] + "-" + separated[0])
                                .putExtra("time", time)
                                .putExtra("samagri", samagri)
                                .putExtra("address", address)
                                .putExtra("cost", dt)
                                .putExtra("bookDate", bookingDate)
                                .putExtra("bookTime", bookingTime)
                                .putExtra("experice", experiance)
                                .putExtra("convePrice", "0")
                                .putExtra("avg", avg)
                                .putExtra("orderid", id));

                        Animatoo.animateSlideLeft(activity);
                    }

                }
            });

            rate_user = itemView.findViewById(R.id.rate_user);
            rate_user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRateDialog(getAdapterPosition());
                }
            });


        }

        @Override
        public void onClick(View view) {

        }
    }


    private void showRateDialog(final int pos) {

        try {
            LayoutInflater factory = LayoutInflater.from(activity);
            final View itemView = factory.inflate(R.layout.dialog_rating, null);
            deleteDialog = new AlertDialog.Builder(activity).create();
            deleteDialog.setView(itemView);
            feedback_edt = itemView.findViewById(R.id.feedback_edt);

            mTitile = (TextView) itemView.findViewById(R.id.titile);
            m1Rate = (ImageView) itemView.findViewById(R.id.rate_1);
            m1Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rate = 1;
                    m1Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                    m2Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                    m3Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                    m4Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                    m5Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                }
            });
            m2Rate = (ImageView) itemView.findViewById(R.id.rate_2);
            m2Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        rate = 2;
                        m1Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m2Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m3Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                        m4Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                        m5Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                    }
                }
            });
            m3Rate = (ImageView) itemView.findViewById(R.id.rate_3);
            m3Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        rate = 3;
                        m1Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m2Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m3Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m4Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                        m5Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                    }
                }
            });
            m4Rate = (ImageView) itemView.findViewById(R.id.rate_4);
            m4Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        rate = 4;
                        m1Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m2Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m3Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m4Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m5Rate.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);


                    }
                }
            });
            m5Rate = (ImageView) itemView.findViewById(R.id.rate_5);
            m5Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        rate = 5;
                        m1Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m2Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m3Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m4Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                        m5Rate.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);

                    }
                }
            });
            mSubmitBtn = (Button) itemView.findViewById(R.id.btn_submit);
            mSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rate > 0) {
                        addRate(rate, pos);
                    } else if (deleteDialog != null) {
                        deleteDialog.dismiss();
                        saveData(activity, "isRating", "0");

                    }
                }
            });

            deleteDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRate(final int rate, final int pos) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Disable cancel
        progressDialog.setIndeterminate(true); // Set as indeterminate
        progressDialog.show();

        Call<ResponseBody> call = AppConfig.loadInterface().addPoojaRating("" + task.get(pos).getBookingId(), "" + rate, "" + task.get(pos).getUserId(), "" + feedback_edt.getText().toString(), "user");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                deleteDialog.dismiss();
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("dashboard" + object);
                        if (error.equals("200")) {

                            task.get(pos).setUserRating("" + rate);
                            notifyDataSetChanged();
                        }

                    } catch (IOException e) {

                        e.printStackTrace();
                    } catch (JSONException e) {

                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                deleteDialog.dismiss();
                progressDialog.dismiss();

                t.printStackTrace();
                Toast.makeText(activity, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void ratePandit(int i) {
        switch (i) {
            case 1:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 2:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 3:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 4:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
            case 5:
                rate1.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate2.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate3.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate4.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
                rate5.setColorFilter(ContextCompat.getColor(activity, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);

                break;
        }
    }

}


