package com.ambitious.parampara.Activity.poojabooking;

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

import com.ambitious.parampara.Adapter.AyojanPendingBooking_Adapter;
import com.ambitious.parampara.Adapter.BhanajaPendingBooking_Adapter;
import com.ambitious.parampara.Adapter.PendingBooking_Adapter;
import com.ambitious.parampara.Model.bhavya.BhavyaPendingModal;
import com.ambitious.parampara.Model.mandal.MandalPendingModal;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.PendingBooking_Response;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.getData;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment {

    View view;
    RecyclerView rec_pending;
    String strUser_ID, bookingTp;
    private SwipeRefreshLayout swipe_pending;
    ImageView empty;
    int check = 0;

    public PendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pending, container, false);
        findID();
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


        pendingCall(strUser_ID);
        swipe_pending.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pendingCall(strUser_ID);
            }
        });
        return view;
    }


    private void findID() {
        rec_pending = view.findViewById(R.id.rec_pending);
        swipe_pending = view.findViewById(R.id.swipe_pending);
        empty = view.findViewById(R.id.empty);
    }


    private void pendingCall(String user_id) {
        swipe_pending.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().acceptbooking(user_id, check, "pending");

        System.out.println("AFsdf::::" + call.request().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipe_pending.setRefreshing(false);
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");

                        if (error.equals("1")) {
                            Gson gson = new Gson();

                            if (check == 1) {
                                PendingBooking_Response successResponse = gson.fromJson(responedata, PendingBooking_Response.class);
                                rec_pending.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_pending.setLayoutManager(mLayoutManager1);
                                rec_pending.setItemAnimator(new DefaultItemAnimator());
                                PendingBooking_Adapter adapter = new PendingBooking_Adapter(getActivity(), successResponse.getResult());
                                rec_pending.setAdapter(adapter);
                            } else if (check == 2) {
                                BhavyaPendingModal successResponse = gson.fromJson(responedata, BhavyaPendingModal.class);
                                rec_pending.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_pending.setLayoutManager(mLayoutManager1);
                                rec_pending.setItemAnimator(new DefaultItemAnimator());
                                AyojanPendingBooking_Adapter adapter = new AyojanPendingBooking_Adapter(getActivity(), successResponse.getResult());
                                rec_pending.setAdapter(adapter);
                            }else if (check == 3) {
                                MandalPendingModal successResponse = gson.fromJson(responedata, MandalPendingModal.class);
                                rec_pending.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_pending.setLayoutManager(mLayoutManager1);
                                rec_pending.setItemAnimator(new DefaultItemAnimator());
                                BhanajaPendingBooking_Adapter adapter = new BhanajaPendingBooking_Adapter(getActivity(), successResponse.getResult());
                                rec_pending.setAdapter(adapter);
                            }else if (check == 4) {
                                PendingBooking_Response successResponse = gson.fromJson(responedata, PendingBooking_Response.class);
                                rec_pending.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_pending.setLayoutManager(mLayoutManager1);
                                rec_pending.setItemAnimator(new DefaultItemAnimator());
                                PendingBooking_Adapter adapter = new PendingBooking_Adapter(getActivity(), successResponse.getResult());
                                rec_pending.setAdapter(adapter);
                            } else if (check == 5) {
                                PendingBooking_Response successResponse = gson.fromJson(responedata, PendingBooking_Response.class);
                                rec_pending.setHasFixedSize(true);
                                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
                                rec_pending.setLayoutManager(mLayoutManager1);
                                rec_pending.setItemAnimator(new DefaultItemAnimator());
                                PendingBooking_Adapter adapter = new PendingBooking_Adapter(getActivity(), successResponse.getResult());
                                rec_pending.setAdapter(adapter);
                            }

                        } else {
                            empty.setVisibility(View.VISIBLE);
                            rec_pending.setVisibility(View.GONE);
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
                swipe_pending.setRefreshing(false);
                t.printStackTrace();
                Toast.makeText(getActivity(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
