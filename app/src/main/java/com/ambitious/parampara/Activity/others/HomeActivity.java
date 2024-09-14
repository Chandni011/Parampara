package com.ambitious.parampara.Activity.others;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ambitious.parampara.Activity.mandal.BhavayAyojan_Booking;
import com.ambitious.parampara.Activity.poojabooking.MyPooja_Booking;
import com.ambitious.parampara.Adapter.ImageViewpagerAdapter;
import com.ambitious.parampara.Other.AppConfig;
import com.ambitious.parampara.R;
import com.ambitious.parampara.Response.GetProfile_Response;
import com.ambitious.parampara.Service.utlity;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.facebook.login.LoginManager;
import com.github.islamkhsh.CardSliderIndicator;
import com.github.islamkhsh.CardSliderViewPager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

//import cc.cloudist.acplibrary.ACProgressConstant;
//import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ambitious.parampara.Other.MySharedPref.DeleteData;
import static com.ambitious.parampara.Other.MySharedPref.getData;
import static com.ambitious.parampara.Other.MySharedPref.saveData;

public class HomeActivity extends YouTubeBaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final Integer[] XMEN = {R.drawable.banner, R.drawable.banner1, R.drawable.banner, R.drawable.banner1};
    ArrayList<String> images = new ArrayList<>();
    private RelativeLayout draw_head;
    private TextView user_namenav, current_day, currentdate;
    private ViewFlipper vf;
    private LinearLayout cart_ly, lay_e_store, lay_panditbooking, lay_dailypandit, lay_darshan, lay_templelocater, lay_bhajanmandal, lay_virtualservice, lay_horoscope, lay_pujaatTemple, lay_donation, lay_brahmanbhoj;
    private CircleImageView profile_image;
    private String strUser_ID;
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
    private CardSliderViewPager mViewPager;
    private CardSliderIndicator mIndicator;
    private BottomNavigationView navigation;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findID();
        finddate();
        strUser_ID = getData(getApplicationContext(), "userID", null);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);
        user_namenav = header.findViewById(R.id.user_name);
        profile_image = header.findViewById(R.id.profile_image);
        draw_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (item.getItemId() == R.id.navigation_order) {
                    startActivity(new Intent(HomeActivity.this, MyOrders_items.class));
                    Animatoo.animateSlideLeft(HomeActivity.this);
                } else if (item.getItemId() == R.id.navigation_contact_us) {
                    startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                    Animatoo.animateSlideLeft(HomeActivity.this);
                }
                return true;
            }
        });

        vf = findViewById(R.id.view_menu_flipper);
        for (int i = 0; i < XMEN.length; i++) {
            setFlipperImage(XMEN[i]);
        }

        try {
            if (getData(this, "isRating", null) != null) {
                if (getData(this, "isRating", null).equals("1")) {
                    if (!getData(HomeActivity.this, "pooja_id", null).equalsIgnoreCase("null")) {
                        showRateDialog("Pandit");

                    } else if (!getData(HomeActivity.this, "order_id", null).equalsIgnoreCase("null")) {

                        showRateDialog("Vender");

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirebaseMessaging.getInstance().unsubscribeFromTopic("global");
        FirebaseMessaging.getInstance().subscribeToTopic("UserAll")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "complete";
                        if (!task.isSuccessful()) {
                            msg = "uncomplete";
                        }
                        Log.d("ftc", msg);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        GetProfile(strUser_ID);
    }

    private void showRateDialog(String s) {

        try {
            LayoutInflater factory = LayoutInflater.from(this);
            final View itemView = factory.inflate(R.layout.dialog_rating, null);
            deleteDialog = new AlertDialog.Builder(this).create();
            deleteDialog.setView(itemView);
            feedback_edt = itemView.findViewById(R.id.feedback_edt);

            mTitile = (TextView) itemView.findViewById(R.id.titile);
            mTitile.setText("Rate This " + s);
            m1Rate = (ImageView) itemView.findViewById(R.id.rate_1);
            m1Rate.setOnClickListener(this);
            m2Rate = (ImageView) itemView.findViewById(R.id.rate_2);
            m2Rate.setOnClickListener(this);
            m3Rate = (ImageView) itemView.findViewById(R.id.rate_3);
            m3Rate.setOnClickListener(this);
            m4Rate = (ImageView) itemView.findViewById(R.id.rate_4);
            m4Rate.setOnClickListener(this);
            m5Rate = (ImageView) itemView.findViewById(R.id.rate_5);
            m5Rate.setOnClickListener(this);
            mSubmitBtn = (Button) itemView.findViewById(R.id.btn_submit);
            mSubmitBtn.setOnClickListener(this);

            deleteDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void finddate() {

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        currentdate.setText(currentDateTimeString);
    }

    private void findID() {
        mViewPager = (CardSliderViewPager) findViewById(R.id.viewPager);
        mIndicator = (CardSliderIndicator) findViewById(R.id.indicator);
        draw_head = findViewById(R.id.draw_head);
        lay_e_store = findViewById(R.id.lay_e_store);
        cart_ly = findViewById(R.id.cart_ly);
        lay_panditbooking = findViewById(R.id.lay_panditbooking);
        lay_dailypandit = findViewById(R.id.lay_dailypandit);
        lay_bhajanmandal = findViewById(R.id.lay_bhajanmandal);
        lay_virtualservice = findViewById(R.id.lay_virtualservice);
        lay_darshan = findViewById(R.id.lay_darshanbook);
        lay_brahmanbhoj = findViewById(R.id.lay_brahmanbhoj);
        lay_donation = findViewById(R.id.lay_donation);
        lay_pujaatTemple = findViewById(R.id.lay_pujaatTemple);
        lay_horoscope = findViewById(R.id.lay_horoscope);
        lay_templelocater = findViewById(R.id.lay_templelocater);
        current_day = findViewById(R.id.current_day);
        currentdate = findViewById(R.id.current_date);
        lay_templelocater.setOnClickListener(this);
        lay_darshan.setOnClickListener(this);
        lay_e_store.setOnClickListener(this);
        lay_panditbooking.setOnClickListener(this);
        lay_dailypandit.setOnClickListener(this);
        lay_bhajanmandal.setOnClickListener(this);
        lay_virtualservice.setOnClickListener(this);
        lay_brahmanbhoj.setOnClickListener(this);
        lay_donation.setOnClickListener(this);
        lay_pujaatTemple.setOnClickListener(this);
        lay_horoscope.setOnClickListener(this);
        cart_ly.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_myorder) {
            startActivity(new Intent(HomeActivity.this, MyOrders_items.class));
            Animatoo.animateSlideLeft(HomeActivity.this);


        } else if (id == R.id.nav_notification) {
            startActivity(new Intent(HomeActivity.this, notification.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(HomeActivity.this, RatingAndReviewFragment.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(HomeActivity.this, Nav_profileActivity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);


        } else if (id == R.id.nav_myBooking) {
            startActivity(new Intent(HomeActivity.this, MyPooja_Booking.class).putExtra("type", "Pooja"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Pooja");
        } else if (id == R.id.bhavya_ayojan) {
            startActivity(new Intent(HomeActivity.this, MyAyojan_booking.class).putExtra("type", "Bhavya"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Bhavya");
        } else if (id == R.id.bhajan_mandal_txt) {
            startActivity(new Intent(HomeActivity.this, MyMandal_bokking.class).putExtra("type", "Bhajan"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Bhajan");
        } else if (id == R.id.pramars_txt) {
            startActivity(new Intent(HomeActivity.this, MyParamarsh_booking.class).putExtra("type", "Pramars"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Pramars");
        } else if (id == R.id.kundali_request) {
            startActivity(new Intent(HomeActivity.this, MyKundali_booking.class).putExtra("type", "Kundali"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Kundali");
        }else if (id == R.id.daily_pandit) {
            startActivity(new Intent(HomeActivity.this, MyDpandit_booking.class).putExtra("type", "Daily Pandit"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Kundali");
        }else if (id == R.id.donation) {
            startActivity(new Intent(HomeActivity.this, MyDonation_booking.class).putExtra("type", "Donation"));
            Animatoo.animateSlideLeft(HomeActivity.this);
            saveData(getApplicationContext(), "BookType", "Kundali");
        } else if (id == R.id.nav_contactus) {
            startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_changepassword) {
            startActivity(new Intent(HomeActivity.this, ChangePassword.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_tc) {
            startActivity(new Intent(HomeActivity.this, TermsAndConditionActivity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_pp) {
            startActivity(new Intent(HomeActivity.this, PrivacyPolici.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        }else if (id == R.id.nav_aboutus) {
            startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (id == R.id.nav_logout) {

            new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Do you want Logout?")
                    .setConfirmText("Yes")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            LoginManager.getInstance().logOut();
                            DeleteData(getApplicationContext());
                            Intent intent = new Intent(HomeActivity.this, LoginMode_Activity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();


                        }
                    })
                    .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();


        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v == lay_e_store) {
            startActivity(new Intent(HomeActivity.this, E_Store_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_panditbooking) {
            startActivity(new Intent(HomeActivity.this, PanditBooking_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_dailypandit) {
            startActivity(new Intent(HomeActivity.this, DailyPandit_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_bhajanmandal) {
            startActivity(new Intent(HomeActivity.this, BhajanMandal_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_virtualservice) {
            startActivity(new Intent(HomeActivity.this, VirtualService_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_brahmanbhoj) {
            startActivity(new Intent(HomeActivity.this, Bhrambhanbhoj_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_donation) {
            startActivity(new Intent(HomeActivity.this, Donation_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_pujaatTemple) {
            startActivity(new Intent(HomeActivity.this, PujaAtTemple_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_horoscope) {
            startActivity(new Intent(HomeActivity.this, Horoscope_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_templelocater) {
            startActivity(new Intent(HomeActivity.this, MapsActivity.class).putExtra("mapLoc", "1"));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == lay_darshan) {
            startActivity(new Intent(HomeActivity.this, DarshanBooking_Activity.class));
            Animatoo.animateSlideLeft(HomeActivity.this);
        } else if (v == cart_ly) {
            startActivity(new Intent(HomeActivity.this, bhavvya_ajojana.class));
            Animatoo.animateSlideLeft(HomeActivity.this);

        }


        int id = v.getId();

        if (id == R.id.rate_1) {
            rate = 1;
            m1Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m2Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m3Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m4Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m5Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (id == R.id.rate_2) {
            rate = 2;
            m1Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m2Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m3Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m4Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m5Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (id == R.id.rate_3) {
            rate = 3;
            m1Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m2Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m3Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m4Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
            m5Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (id == R.id.rate_4) {
            rate = 4;
            m1Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m2Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m3Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m4Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m5Rate.setColorFilter(ContextCompat.getColor(this, R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (id == R.id.rate_5) {
            rate = 5;
            m1Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m2Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m3Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m4Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
            m5Rate.setColorFilter(ContextCompat.getColor(this, R.color.light_green), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (id == R.id.btn_submit) {
            if (rate > 0) {
                addRate(rate);
            } else if (deleteDialog != null) {
                deleteDialog.dismiss();
            }
        }


    }

    private void addRate(int rate) {
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = null;
        if (!getData(HomeActivity.this, "pooja_id", null).equalsIgnoreCase("null")) {


        } else if (!getData(HomeActivity.this, "order_id", null).equalsIgnoreCase("null")) {
            saveData(getApplicationContext(), "order_id", "null");

        }

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                deleteDialog.dismiss();

                if (response.isSuccessful()) {

                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("dashboard" + object);
                        if (error.equals("200")) {
                            JSONObject object2 = new JSONObject(object.getString("result"));
                            saveData(getApplicationContext(), "pooja_id", "null");

                            if (!getData(HomeActivity.this, "order_id", null).equalsIgnoreCase("null")) {

                                showRateDialog("Vender");
                            } else {
                                saveData(getApplicationContext(), "order_id", "null");
                                saveData(HomeActivity.this, "isRating", "0");
                            }

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
                progressDialog.dismiss();
                deleteDialog.dismiss();

                t.printStackTrace();
                utlity.toast(HomeActivity.this, "Please check your internet connection!", "e");
            }
        });


    }


    private void setFlipperImage(int res) {
        Log.i("Set Filpper Called", res + "");
        ImageView image = new ImageView(HomeActivity.this);
        image.setBackgroundResource(res);
        vf.addView(image);
    }

    private void GetProfile(String user_id) {

        Call<ResponseBody> call = AppConfig.loadInterface().GetProfile(user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                GetSlider();

                if (response.isSuccessful()) {
                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        System.out.println("GetProfile_home" + object);
                        if (error.equals("1")) {
                            Gson gson = new Gson();
                            GetProfile_Response successResponse = gson.fromJson(responedata, GetProfile_Response.class);

                            user_namenav.setText(successResponse.getResult().getUsername());

                            saveData(getApplicationContext(), "poojaCheck", successResponse.getResult().getPoojabooking_status());
                            saveData(getApplicationContext(), "ayojanCheck", successResponse.getResult().getAyojanbooking_status());
                            saveData(getApplicationContext(), "mandalCheck", successResponse.getResult().getMandalbooking_status());
                            saveData(getApplicationContext(), "country", successResponse.getResult().getCountry());
                            saveData(getApplicationContext(), "landmark", successResponse.getResult().getLandmark());



                            System.out.println(successResponse.getResult().getLattitude() +".........");
                            if (!successResponse.getResult().getLattitude().equals("")) {
                                saveData(getApplicationContext(), "lattitude", successResponse.getResult().getLattitude());
                                saveData(getApplicationContext(), "logitude", successResponse.getResult().getLogitude());
                            } else {
                                saveData(getApplicationContext(), "lattitude", "0.0");
                                saveData(getApplicationContext(), "logitude", "0.0");
                            }


                            try {
                                Picasso.get()
                                        .load(successResponse.getResult().getImage())
                                        .error(R.drawable.ut)
                                        .into(profile_image);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String regID = getData(getApplicationContext(), "regId", null);


                            FirebaseMessaging.getInstance().unsubscribeFromTopic("global");
                            FirebaseMessaging.getInstance().subscribeToTopic("user-" + successResponse.getResult().getMobile())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            String msg = "complete";
                                            if (!task.isSuccessful()) {
                                                msg = "uncomplete";
                                            }
                                            Log.d("ftc", msg);
                                        }
                                    });


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
                GetSlider();
                utlity.toast(HomeActivity.this, "Please check your internet connection!", "e");
                t.printStackTrace();
            }
        });

    }

    private void GetSlider() {
        images.clear();
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Prevents user from canceling the dialog
        progressDialog.show();
        Call<ResponseBody> call = AppConfig.loadInterface().GetSlider();


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {

                    try {
                        String responedata = response.body().string();
                        JSONObject object = new JSONObject(responedata);
                        String error = object.getString("status");
                        if (error.equals("1")) {
                            JSONArray array = new JSONArray(object.getString("result"));
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object1 = array.getJSONObject(i);
                                images.add(object1.optString("url"));

                            }

                            mViewPager.setAdapter(new ImageViewpagerAdapter(HomeActivity.this, images));
                            mIndicator.setIndicatorsToShow(images.size());

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
                progressDialog.dismiss();
                t.printStackTrace();
                utlity.toast(HomeActivity.this, "Please check your internet connection!", "e");
            }
        });


    }

    @Override
    public void onBackPressed() {

        new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want exit")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        System.exit(0);
                        finish();

                    }
                })
                .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
