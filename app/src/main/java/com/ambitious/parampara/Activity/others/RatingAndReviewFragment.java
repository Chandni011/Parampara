package com.ambitious.parampara.Activity.others;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ambitious.parampara.Adapter.RatingAdapter;
import com.ambitious.parampara.Model.orderModel;
import com.ambitious.parampara.Model.rating_model;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.AcceptedBooking_Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class RatingAndReviewFragment extends AppCompatActivity implements View.OnClickListener {

    private CircleImageView profile_img;
    private TextView username, mobile;
    private RecyclerView mViewRecycle;
    ArrayList<String> user_name = new ArrayList<>();
    ArrayList<String> review = new ArrayList<>();
    ArrayList<String> rating = new ArrayList<>();
    ArrayList<String> pic = new ArrayList<>();
    private TextView avg_rate, p1_count, p2_count, p3_count, p4_count, p5_count;
    ProgressBar progressBar5, progressBar4, progressBar3, progressBar2, progressBar1;

    private RatingAdapter adapter;
    private ImageView back;
    String uid;
    private TextView mHeadernameTxt;
    private RelativeLayout mHeaderLay;
    private View mLineVender;
    private LinearLayout mLyVender;
    private View mLinePandit;
    private LinearLayout mLyPandit;
    private NestedScrollView mContentNested;
    float i2;
    private ArrayList<com.ambitious.parampara.Model.rating_model> rating_model = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_rating);
        initView();
        uid = getData(getApplicationContext(), "userID", null);

        getOrders();
    }

    private void initView() {
        try {
            back = (ImageView) findViewById(R.id.img_back);
            back.setOnClickListener(this);
            mViewRecycle = (RecyclerView) findViewById(R.id.recycler_rating);
            profile_img = (CircleImageView) findViewById(R.id.image);
            username = (TextView) findViewById(R.id.user_name);
            mobile = (TextView) findViewById(R.id.mobile);
            avg_rate = (TextView) findViewById(R.id.avg_rate);
            progressBar1 = (ProgressBar) findViewById(R.id.p1);
            progressBar2 = (ProgressBar) findViewById(R.id.p2);
            progressBar3 = (ProgressBar) findViewById(R.id.p3);
            progressBar4 = (ProgressBar) findViewById(R.id.p4);
            progressBar5 = (ProgressBar) findViewById(R.id.p5);
            p1_count = (TextView) findViewById(R.id.p1ct);
            p2_count = (TextView) findViewById(R.id.p2ct);
            p3_count = (TextView) findViewById(R.id.p3ct);
            p4_count = (TextView) findViewById(R.id.p4ct);
            p5_count = (TextView) findViewById(R.id.p5ct);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RatingAndReviewFragment.this, RecyclerView.VERTICAL, false);
            mViewRecycle.setLayoutManager(linearLayoutManager);
//            user_name.add("Mr.XYZ");
//            review.add("jhjhjghjjhghjghjghjgjg");
//            rating.add("1");
            adapter = new RatingAdapter(RatingAndReviewFragment.this, rating_model);
            mViewRecycle.setAdapter(adapter);
            mHeadernameTxt = (TextView) findViewById(R.id.txt_headername);
            mHeaderLay = (RelativeLayout) findViewById(R.id.lay_header);
            mLineVender = (View) findViewById(R.id.vender_line);
            mLyVender = (LinearLayout) findViewById(R.id.vender_ly);
            mLyVender.setOnClickListener(this);
            mLinePandit = (View) findViewById(R.id.pandit_line);
            mLyPandit = (LinearLayout) findViewById(R.id.pandit_ly);
            mLyPandit.setOnClickListener(this);
            mContentNested = (NestedScrollView) findViewById(R.id.nested_content);
            String allData = getData(RatingAndReviewFragment.this, "ldata", null);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(allData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            username.setText("" + jsonObject.getString("username"));
            mobile.setText("" + jsonObject.getString("mobile"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.vender_ly) {
            getOrders();
            mLineVender.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mLinePandit.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (id == R.id.pandit_ly) {
            acceptCall(getData(getApplicationContext(), "userID", null), "completed");
            mLineVender.setBackgroundColor(getResources().getColor(R.color.white));
            mLinePandit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (id == R.id.img_back) {
            onBackPressed();
        }
    }


    private void getOrders() {
        rating_model.clear();
        Call<ResponseBody> call = AppConfig.loadInterface().get_order(getData(getApplicationContext(), "userID", null));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("RatingFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            orderModel orderModel = gson.fromJson(responedata, orderModel.class);

                            int five = 0;
                            int four = 0;
                            int three = 0;
                            int two = 0;
                            int one = 0;


                            for (int i = 0; i < orderModel.getResult().size(); i++) {

                                if (orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("5")) {
                                    five++;
                                } else if (orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("4")) {
                                    four++;
                                } else if (orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("3")) {
                                    three++;
                                } else if (orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("2")) {
                                    two++;
                                } else if (orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("1")) {
                                    one++;
                                }


                                if (!orderModel.getResult().get(i).getVender_rating().equalsIgnoreCase("0") || orderModel.getResult().get(i).getVendor_feedback().length() > 2)
                                    rating_model.add(new rating_model(orderModel.getResult().get(i).getVendor_name(),
                                            orderModel.getResult().get(i).getVendor_feedback(),
                                            orderModel.getResult().get(i).getVender_image(),
                                            orderModel.getResult().get(i).getVender_rating()
                                    ));


                            }
                            p1_count.setText("" + one);
                            p2_count.setText("" + two);
                            p3_count.setText("" + three);
                            p4_count.setText("" + four);
                            p5_count.setText("" + five);

                            int total = one + two + three + four + five;

                            int totalfive = five * 5;
                            int totalfour = four * 4;
                            int totalthree = three * 3;
                            int totaltwo = two * 2;
                            int totalone = one;

                            int totalrate = totalfive + totalfour + totalthree + totaltwo + totalone;
                           if(total==0){
                              i2 = 0;
                           }
                           else
                           {
                               i2 = totalrate / total;
                           }

                            System.out.println("afasdfas::::  " + total + "  " + totalrate + "  " + i2);
                            avg_rate.setText("" + new DecimalFormat("##.##").format(i2));


                            progressBar5.setMax(total);
                            progressBar5.setProgress(five);

                            progressBar4.setMax(total);
                            progressBar4.setProgress(four);

                            progressBar3.setMax(total);
                            progressBar3.setProgress(three);

                            progressBar2.setMax(total);
                            progressBar2.setProgress(two);

                            progressBar1.setMax(total);
                            progressBar1.setProgress(one);


                            adapter.notifyDataSetChanged();


                        } else {

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {


                }
            }




            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                t.printStackTrace();
                Toast.makeText(RatingAndReviewFragment.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void acceptCall(String user_id,String status) {
        rating_model.clear();

        Call<ResponseBody> call = AppConfig.loadInterface().acceptbooking(user_id,1,status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("PreviousFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            AcceptedBooking_Response successResponse = gson.fromJson(responedata, AcceptedBooking_Response.class);

                            int five = 0;
                            int four = 0;
                            int three = 0;
                            int two = 0;
                            int one = 0;


                            for (int i = 0; i < successResponse.getResult().size(); i++) {

                                if (successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("5")) {
                                    five++;
                                } else if (successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("4")) {
                                    four++;
                                } else if (successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("3")) {
                                    three++;
                                } else if (successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("2")) {
                                    two++;
                                } else if (successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("1")) {
                                    one++;
                                }


                                if (!successResponse.getResult().get(i).getPandit_rating().equalsIgnoreCase("0") ||successResponse.getResult().get(i).getPandit_feedback().length() > 2)
                                    rating_model.add(new rating_model(successResponse.getResult().get(i).getPanditName(),
                                            successResponse.getResult().get(i).getPandit_feedback(),
                                            successResponse.getResult().get(i).getPandit_image(),
                                            successResponse.getResult().get(i).getPandit_rating()
                                    ));


                            }
                            p1_count.setText("" + one);
                            p2_count.setText("" + two);
                            p3_count.setText("" + three);
                            p4_count.setText("" + four);
                            p5_count.setText("" + five);

                            int total = one + two + three + four + five;

                            int totalfive = five * 5;
                            int totalfour = four * 4;
                            int totalthree = three * 3;
                            int totaltwo = two * 2;
                            int totalone = one;

                            int totalrate = totalfive + totalfour + totalthree + totaltwo + totalone;
                            float i2 = totalrate / total;
                            System.out.println("afasdfas::::  " + total + "  " + totalrate + "  " + i2);
                            avg_rate.setText("" + new DecimalFormat("##.##").format(i2));


                            progressBar5.setMax(total);
                            progressBar5.setProgress(five);

                            progressBar4.setMax(total);
                            progressBar4.setProgress(four);

                            progressBar3.setMax(total);
                            progressBar3.setProgress(three);

                            progressBar2.setMax(total);
                            progressBar2.setProgress(two);

                            progressBar1.setMax(total);
                            progressBar1.setProgress(one);


                            adapter.notifyDataSetChanged();

                        } else {
                            // Toast.makeText(getActivity(), "Data Not Available", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //Snackbar.make(parentView, R.string.string_upload_fail, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RatingAndReviewFragment.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}