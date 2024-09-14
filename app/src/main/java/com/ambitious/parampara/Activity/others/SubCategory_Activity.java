package com.ambitious.parampara.Activity.others;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ambitious.parampara.Adapter.SubCategory_Adapter;
import com.ambitious.parampara.Model.GetSubCategory_Result;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetSubCategory_Response;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategory_Activity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ImageView img_back, img_cart, img_search, back_search, clear_txt_search;
    private TextView txt_headername, item_cart;
    RecyclerView rec_subCat;
    private SwipeRefreshLayout swipe_subcat;
    String str_catID, str_catName;
    RelativeLayout search_ly, crt_round_rel;
    EditText search_text;
    ArrayList<GetSubCategory_Result> filter = new ArrayList<>();
    ArrayList<GetSubCategory_Result> dataItem = new ArrayList<>();
    private SubCategory_Adapter adapter;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        findID();
        if (getIntent().getExtras() != null) {

            str_catID = getIntent().getExtras().getString("catID");
            str_catName = getIntent().getExtras().getString("catName");
            txt_headername.setText(str_catName);
        }

    }

    private void findID() {
        //07314089786
        result = findViewById(R.id.search_result);

        img_search = findViewById(R.id.img_search);
        item_cart = findViewById(R.id.item_cart);
        crt_round_rel = findViewById(R.id.rel_cart_number);
        img_search.setOnClickListener(this);
        back_search = findViewById(R.id.back_search);
        back_search.setOnClickListener(this);
        clear_txt_search = findViewById(R.id.clear_txt_search);
        clear_txt_search.setOnClickListener(this);
        search_ly = findViewById(R.id.search_ly);
        search_text = findViewById(R.id.search_text);


        img_cart = findViewById(R.id.img_cart);
        img_cart.setOnClickListener(this);


        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        swipe_subcat = findViewById(R.id.swipe_subcat);
        rec_subCat = findViewById(R.id.rec_subCat);
        // txt_headername.setText("E-Store");
        img_back.setOnClickListener(this);

        swipe_subcat.setOnRefreshListener(this);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    dataItem.clear();
                    if (s.toString().length() > 1) {
                        for (int i = 0; i < filter.size(); i++) {
                            try {
                                if (filter.get(i).getName().toString().toLowerCase().contains(s.toString().toLowerCase()) ||
                                        filter.get(i).getCategory_name().toString().toLowerCase().contains(s.toString().toLowerCase()) ||
                                        filter.get(i).getShopName().toString().toLowerCase().contains(s.toString().toLowerCase())) {


                                    dataItem.add(filter.get(i));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        dataItem.addAll(filter);
                    }
                    adapter.notifyDataSetChanged();

                }
                if (dataItem.size() <= 0) {
                    result.setVisibility(View.VISIBLE);
                } else {
                    result.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.img_back) {
            onBackPressed();
        } else if (id == R.id.img_cart) {
            startActivity(new Intent(SubCategory_Activity.this, MyCart_items.class));
            Animatoo.animateSlideLeft(SubCategory_Activity.this);
        } else if (id == R.id.img_search) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(R.id.search_ly, 2, true, true);
            } else {
                search_ly.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.clear_txt_search) {
            search_text.setText("");
        } else if (id == R.id.back_search) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(R.id.search_ly, 2, true, false);
            } else {
                search_ly.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (search_ly.isShown()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circleReveal(R.id.search_ly, 2, true, false);
            } else {
                search_ly.setVisibility(View.GONE);
            }
        } else {
            super.onBackPressed();
            Animatoo.animateSlideRight(SubCategory_Activity.this);

        }
    }

    private void get_productCall() {
        swipe_subcat.setRefreshing(true);
        System.out.println("safdsdaf::  " + str_catID);
        Call<ResponseBody> call = AppConfig.loadInterface().get_product(str_catID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipe_subcat.setRefreshing(false);
                dataItem.clear();
                filter.clear();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("subcate" + object + " " + responedata);
                        if (error.equals("1")) {
                            try {
                                Gson gson = new Gson();
                                GetSubCategory_Response successResponse = gson.fromJson(responedata, GetSubCategory_Response.class);
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(SubCategory_Activity.this, 2);
                                rec_subCat.setHasFixedSize(false);
                                rec_subCat.setLayoutManager(mLayoutManager);
                                rec_subCat.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(0), true));
                                rec_subCat.setItemAnimator(new DefaultItemAnimator());


                                dataItem.addAll(successResponse.getResult());
                                filter.addAll(successResponse.getResult());

                                ArrayList<GetSubCategory_Result> product2 = new ArrayList<>();
                                if (MySharedPref.getProductCart(SubCategory_Activity.this) != null) {
                                    product2.addAll(MySharedPref.getProductCart(SubCategory_Activity.this));
                                }
                                for (int i = 0; i < product2.size(); i++) {

                                    for (int j = 0; j < dataItem.size(); j++) {
                                        if (dataItem.get(j).getProduct_id().equalsIgnoreCase(product2.get(i).getProduct_id())) {
                                            if (Integer.parseInt(dataItem.get(j).getQty()) < Integer.parseInt(product2.get(i).getQty())) {
                                                dataItem.get(j).setEntrydt("OOS");
                                                filter.get(j).setEntrydt("OOS");
                                            }
                                        }
                                    }


                                }

                                adapter = new SubCategory_Adapter(SubCategory_Activity.this, dataItem);
                                rec_subCat.setAdapter(adapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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

                if (dataItem.size() <= 0) {
                    result.setVisibility(View.VISIBLE);
                } else {
                    result.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_subcat.setRefreshing(false);
                t.printStackTrace();
                Toast.makeText(SubCategory_Activity.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                if (dataItem.size() <= 0) {
                    result.setVisibility(View.VISIBLE);
                } else {
                    result.setVisibility(View.GONE);
                }
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
        addcartnumber();
    }


    public void addcartnumber() {
        if (MySharedPref.getData(SubCategory_Activity.this, "cartSize", null) != null) {
            MySharedPref.setAnim(crt_round_rel, SubCategory_Activity.this);
            item_cart.setText(MySharedPref.getData(SubCategory_Activity.this, "cartSize", null));

        }
    }

    @Override
    public void onRefresh() {
        dataItem.clear();
        filter.clear();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        get_productCall();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column; //spacing / spanCount; // spacing - column  ((1f / spanCount) * spacing)
                outRect.right = (column + 1);   //spacing / spanCount; // (column + 1)  ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column;   //spacing / spanCount; // column  ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1);  //spacing / spanCount; // spacing - (column + 1)  ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int viewID, int posFromRight, boolean containsOverflow, final boolean isShow) {
        final View myView = findViewById(viewID);

        int width = myView.getWidth();

        if (posFromRight > 0)
            width -= (posFromRight * getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) / 2);

        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material);

        int cx = width;
        int cy = myView.getHeight() / 2;

        Animator anim;
        if (isShow)
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // make the view visible and start the animation
        if (isShow)
            myView.setVisibility(View.VISIBLE);

        // start the animation
        anim.start();
    }

}
