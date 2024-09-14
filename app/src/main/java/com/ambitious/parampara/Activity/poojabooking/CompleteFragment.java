package com.ambitious.parampara.Activity.poojabooking;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ambitious.parampara.Adapter.AcceptBooking_Adapter;
import com.ambitious.parampara.Adapter.AyojanAcceptBooking_Adapter;
import com.ambitious.parampara.Adapter.BhajanAcceptBooking_Adapter;
import com.ambitious.parampara.Model.bhavya.BhavyaAcceptedModal;
import com.ambitious.parampara.Model.mandal.MandalAcceptedModal;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.AcceptedBooking_Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteFragment extends Fragment {
    View view;
    RecyclerView rec_complete;
    String strUser_ID, bookingTp;
    private SwipeRefreshLayout swipe_compe;
    ImageView empty;
    int check = 0;

    public CompleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_complete, container, false);
        rec_complete = view.findViewById(R.id.rec_complete);
        swipe_compe = view.findViewById(R.id.swipe_compe);
        empty = view.findViewById(R.id.empty);
        strUser_ID = getData(getContext(), "userID", null);
        bookingTp = getData(getContext(), "BookType", null);

        if (bookingTp.equals("Pooja")) {
            check = 1;
        } else if (bookingTp.equals("Bhavya")) {
            check = 2;
        } else if (bookingTp.equals("Bhajan")) {
            check = 3;
        } else if (bookingTp.equals("Pramars")) {
            check = 4;
        } else {
            check = 5;
        }

        acceptCall(strUser_ID, "completed");
        swipe_compe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                acceptCall(strUser_ID, "completed");
            }
        });
        return view;
    }

    private void acceptCall(String user_id, String status) {
        swipe_compe.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().acceptbooking(user_id, check, status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipe_compe.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("PreviousFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();

                            if (check == 1) {
                                AcceptedBooking_Response successResponse = gson.fromJson(responedata, AcceptedBooking_Response.class);
                                rec_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_complete.setLayoutManager(mLayoutManager1);
                                rec_complete.setItemAnimator(new DefaultItemAnimator());
                                AcceptBooking_Adapter adapter = new AcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                                rec_complete.setAdapter(adapter);
                            } else if (check == 2) {
                                BhavyaAcceptedModal successResponse = gson.fromJson(responedata, BhavyaAcceptedModal.class);
                                rec_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_complete.setLayoutManager(mLayoutManager1);
                                rec_complete.setItemAnimator(new DefaultItemAnimator());
                                AyojanAcceptBooking_Adapter adapter = new AyojanAcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                                rec_complete.setAdapter(adapter);
                            } else if (check == 3) {
                                MandalAcceptedModal successResponse = gson.fromJson(responedata, MandalAcceptedModal.class);
                                rec_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_complete.setLayoutManager(mLayoutManager1);
                                rec_complete.setItemAnimator(new DefaultItemAnimator());
                                BhajanAcceptBooking_Adapter adapter = new BhajanAcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                                rec_complete.setAdapter(adapter);
                            } else if (check == 4) {
                                AcceptedBooking_Response successResponse = gson.fromJson(responedata, AcceptedBooking_Response.class);
                                rec_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_complete.setLayoutManager(mLayoutManager1);
                                rec_complete.setItemAnimator(new DefaultItemAnimator());
                                AcceptBooking_Adapter adapter = new AcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                                rec_complete.setAdapter(adapter);
                            } else if (check == 5) {
                                AcceptedBooking_Response successResponse = gson.fromJson(responedata, AcceptedBooking_Response.class);
                                rec_complete.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_complete.setLayoutManager(mLayoutManager1);
                                rec_complete.setItemAnimator(new DefaultItemAnimator());
                                AcceptBooking_Adapter adapter = new AcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                                rec_complete.setAdapter(adapter);
                            }

                        } else {
                            empty.setVisibility(View.VISIBLE);
                            rec_complete.setVisibility(View.GONE);
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
                swipe_compe.setRefreshing(false);
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
