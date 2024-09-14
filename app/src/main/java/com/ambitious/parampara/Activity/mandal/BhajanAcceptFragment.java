package com.ambitious.parampara.Activity.mandal;

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
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.AcceptedBooking_Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

public class BhajanAcceptFragment extends Fragment {

    View view;
    RecyclerView rec_acceptbooking;
    String strUser_ID, bookingTp;
    int flag, check = 0;
    private SwipeRefreshLayout swipw_accept;
    ImageView empty;

    public BhajanAcceptFragment(int flag) {
        this.flag = flag;
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bhajan_accept, container, false);

        strUser_ID = getData(getContext(), "userID", null);

        rec_acceptbooking = view.findViewById(R.id.rec_acceptbooking);
        swipw_accept = view.findViewById(R.id.swipw_accept);
        empty = view.findViewById(R.id.empty);

        acceptCall(strUser_ID, "accepted");
        swipw_accept.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                acceptCall(strUser_ID, "accepted");
            }
        });
        return view;
    }

    private void acceptCall(String user_id, String status) {
        swipw_accept.setRefreshing(true);
        Call<ResponseBody> call;
        if (flag == 0) {
            call = AppConfig.loadInterface().acceptbooking(user_id, 2, status);
        } else {
            call = AppConfig.loadInterface().acceptbooking(user_id, 2, "Canceled");
        }
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipw_accept.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("PreviousFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            AcceptedBooking_Response successResponse = gson.fromJson(responedata, AcceptedBooking_Response.class);
                            rec_acceptbooking.setHasFixedSize(true);
                            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                            rec_acceptbooking.setLayoutManager(mLayoutManager1);
                            rec_acceptbooking.setItemAnimator(new DefaultItemAnimator());

                            AcceptBooking_Adapter adapter;
                            if (flag == 1) {
                                adapter = new AcceptBooking_Adapter(getActivity(), successResponse.getResult(), 1);
                            } else {
                                adapter = new AcceptBooking_Adapter(getActivity(), successResponse.getResult(), 0);
                            }
                            rec_acceptbooking.setAdapter(adapter);
                        } else {
                            empty.setVisibility(View.VISIBLE);
                            rec_acceptbooking.setVisibility(View.GONE);
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
                swipw_accept.setRefreshing(false);
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}