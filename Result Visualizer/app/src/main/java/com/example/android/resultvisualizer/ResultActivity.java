package com.example.android.resultvisualizer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private static String rn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if (getIntent().hasExtra(Intent.EXTRA_TEXT))
            rn = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        AppBarLayout abl = findViewById(R.id.appbar);
        ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Result : " + rn.substring(5));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        LinearLayout newTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab.findViewById(R.id.tab)).setText("Stats");
        ((ImageView) newTab.findViewById(R.id.img)).setImageResource(R.drawable.ic_stats_tab);
        tabLayout.getTabAt(0).setCustomView(newTab);
        LinearLayout newTab1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab1.findViewById(R.id.tab)).setText("Summary");
        ((ImageView) newTab1.findViewById(R.id.img)).setImageResource(R.drawable.ic_summary_tab);
        tabLayout.getTabAt(1).setCustomView(newTab1);
        LinearLayout newTab2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab2.findViewById(R.id.tab)).setText("Graphs");
        ((ImageView) newTab2.findViewById(R.id.img)).setImageResource(R.drawable.ic_graph_tab);
        tabLayout.getTabAt(2).setCustomView(newTab2);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                ((TextView)tab.getCustomView().findViewById(R.id.tab)).setTextColor(Color.BLACK);
                ((ImageView)tab.getCustomView().findViewById(R.id.img)).setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                ((TextView)tab.getCustomView().findViewById(R.id.tab)).setTextColor(Color.WHITE);
                ((ImageView)tab.getCustomView().findViewById(R.id.img)).setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(1);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return StatsFragment.newInstance(rn, getApplicationContext());
            else if (position == 1)
                return SummaryFragment.newInstance(rn, getApplicationContext());
            else
                return GraphFragment.newInstance(rn, getApplicationContext());
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

