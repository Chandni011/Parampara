package com.ambitious.parampara.Activity.others;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ambitious.parampara.Adapter.Category_Adapter;
import com.ambitious.parampara.Model.GetCategory_Result;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.Other.MySharedPref;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetCategory_Response;
import com.ambitious.parampara.Service.utlity;
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

public class E_Store_Activity extends AppCompatActivity implements View.OnClickListener {


    private ImageView img_back, img_cart, img_search, back_search, clear_txt_search;
    private TextView txt_headername,item_cart;
    RecyclerView rec_cate;
    private SwipeRefreshLayout swipe_e_staore;
    RelativeLayout search_ly;
    EditText search_text;
    ArrayList<GetCategory_Result> filter = new ArrayList<>();
    ArrayList<GetCategory_Result> dataItem = new ArrayList<>();
    private Category_Adapter adapter;
    private RelativeLayout crt_round_rel;
    private TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_store);
        findID();
    }

    private void findID() {
        img_back = findViewById(R.id.img_back);
        item_cart = findViewById(R.id.item_cart);
        crt_round_rel = findViewById(R.id.rel_cart_number);
        img_search = findViewById(R.id.img_search);
        img_search.setOnClickListener(this);
        back_search = findViewById(R.id.back_search);
        result =findViewById(R.id.search_result);
        back_search.setOnClickListener(this);
        clear_txt_search = findViewById(R.id.clear_txt_search);
        clear_txt_search.setOnClickListener(this);
        search_ly = findViewById(R.id.search_ly);
        search_text = findViewById(R.id.search_text);


        img_cart = findViewById(R.id.img_cart);
        img_cart.setOnClickListener(this);


        txt_headername = findViewById(R.id.txt_headername);
        swipe_e_staore = findViewById(R.id.swipe_e_staore);
        rec_cate = findViewById(R.id.rec_cate);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(E_Store_Activity.this,2);
        rec_cate.setLayoutManager(gridLayoutManager);
        txt_headername.setText("E-Store");
        img_back.setOnClickListener(this);
        getCategory();
        swipe_e_staore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategory();
            }
        });

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
                                if (filter.get(i).getCategoryName().toString().toLowerCase().contains(s.toString().toLowerCase())) {
                                    dataItem.add(filter.get(i));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    } else {
                        dataItem.addAll(filter);
                    }
                    adapter.notifyDataSetChanged();

                }
               if (dataItem.size()<=0){
                   result.setVisibility(View.VISIBLE);
               }else {
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
            startActivity(new Intent(E_Store_Activity.this, MyCart_items.class));
            Animatoo.animateSlideLeft(E_Store_Activity.this);
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
            Animatoo.animateSlideRight(E_Store_Activity.this);

        }
    }

    private void getCategory() {
        swipe_e_staore.setRefreshing(true);
        Call<ResponseBody> call = AppConfig.loadInterface().get_category();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                swipe_e_staore.setRefreshing(false);
                dataItem.clear();
                filter.clear();
                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("PreviousFragment_" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            GetCategory_Response successResponse = gson.fromJson(responedata, GetCategory_Response.class);
                            dataItem.addAll(successResponse.getResult());
                            filter.addAll(successResponse.getResult());
                            adapter = new Category_Adapter(E_Store_Activity.this, dataItem);
                            rec_cate.setAdapter(adapter);
                        } else {
                            utlity.toast(E_Store_Activity.this, "Data Not Available","e");
                        }
                        if (dataItem.size()<=0){
                            result.setVisibility(View.VISIBLE);
                        }else {
                            result.setVisibility(View.GONE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    utlity.toast(E_Store_Activity.this, "Upload Failed","e");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                swipe_e_staore.setRefreshing(false);
                if (dataItem.size()<=0){
                    result.setVisibility(View.VISIBLE);
                }else {
                    result.setVisibility(View.GONE);
                }
                t.printStackTrace();
                utlity.toast(E_Store_Activity.this, "Please Check Internet Connection","e");
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        swipe_e_staore.setRefreshing(true);
        getCategory();
        if(MySharedPref.getData(E_Store_Activity.this,"cartSize",null)!=null){
            MySharedPref.setAnim(crt_round_rel,E_Store_Activity.this);

            item_cart.setText(MySharedPref.getData(E_Store_Activity.this,"cartSize",null));
        }
    }
}
