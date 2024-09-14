package com.ambitious.parampara.Activity.poojabooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ambitious.parampara.Activity.others.HomeActivity;
import com.ambitious.parampara.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyPooja_Booking extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rec_my_order;
    private ImageView img_back;
    private TextView txt_headername;
    RecyclerView.Adapter adapter = null;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pooja_booking);

        Intent extras = getIntent();
        if (extras != null) {
            type = extras.getStringExtra("type");
        }

        findID();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void findID() {
        viewPager = findViewById(R.id.viewpager_myorder);
        tabLayout = findViewById(R.id.tabs_myorder);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);

        if (type.equals("Pooja")) {
            txt_headername.setText("Pooja Booking");
        } else if (type.equals("Bhavya")) {
            txt_headername.setText("Bhavya Ayojan");
        } else if (type.equals("Bhajan")) {
            txt_headername.setText("Bhajan mandal");
        } else if (type.equals("Pramars")) {
            txt_headername.setText("Pramars");
        } else {
            txt_headername.setText("Kundali Request");
        }


        img_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MyPooja_Booking.this, HomeActivity.class));
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PendingFragment(), "Pending Booking");
        adapter.addFragment(new AcceptFragment(0), "Accept Booking");
        adapter.addFragment(new CompleteFragment(), "Complete Booking");
        adapter.addFragment(new AcceptFragment(1), "Canceled Booking");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
