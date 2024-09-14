package com.ambitious.parampara.Activity.mandal;

import androidx.annotation.NonNull;
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

public class BhavayAyojan_Booking extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_bhavay_ayojan__booking);

        Intent extras = getIntent();
        if (extras != null) {
            type = extras.getStringExtra("type");
        }

        findID();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BhajananPendding(), "Pending Booking");
        adapter.addFragment(new BhajanAcceptFragment(0), "Accept Booking");
        adapter.addFragment(new BhajanComplete(), "Complete Booking");
        adapter.addFragment(new BhajanAcceptFragment(1), "Canceled Booking");
        viewPager.setAdapter(adapter);
    }

    private void findID() {

        viewPager = findViewById(R.id.viewpager_bhavyAyojan);
        tabLayout = findViewById(R.id.tabs_bhavyAyojan);
        img_back = findViewById(R.id.img_back);
        txt_headername = findViewById(R.id.txt_headername);
        txt_headername.setText("Bhajan Ayojan");
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
        startActivity(new Intent(BhavayAyojan_Booking.this, HomeActivity.class));
        finish();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
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