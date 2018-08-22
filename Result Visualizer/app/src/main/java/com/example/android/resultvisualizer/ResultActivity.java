package com.example.android.resultvisualizer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private static String rn;

    private int q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if (getIntent().hasExtra(Intent.EXTRA_TEXT))
            rn = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (getIntent().hasExtra("quality"))
            q = getIntent().getIntExtra("quality", 50);
        AppBarLayout abl = findViewById(R.id.appbar);
        ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).setBehavior(new FixAppBarLayoutBehavior());
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Result : " + rn.substring(5));
        toolbar.setTitleTextAppearance(this, R.style.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mViewPager = findViewById(R.id.container);
        final SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        LinearLayout newTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab.findViewById(R.id.tab)).setText(("Stats"));
        ((ImageView) newTab.findViewById(R.id.img)).setImageResource(R.drawable.ic_stats_tab);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(newTab);
        LinearLayout newTab1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab1.findViewById(R.id.tab)).setText(("Summary"));
        ((ImageView) newTab1.findViewById(R.id.img)).setImageResource(R.drawable.ic_summary_tab);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(newTab1);
        LinearLayout newTab2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.tab, null);
        ((TextView) newTab2.findViewById(R.id.tab)).setText(("Graphs"));
        ((ImageView) newTab2.findViewById(R.id.img)).setImageResource(R.drawable.ic_graph_tab);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(newTab2);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                ((TextView) Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tab)).setTextColor(Color.WHITE);
                ((ImageView) tab.getCustomView().findViewById(R.id.img)).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                ((TextView) Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tab)).setTextColor(Color.parseColor("#88FFFFFF"));
                ((ImageView) tab.getCustomView().findViewById(R.id.img)).setColorFilter(Color.parseColor("#88FFFFFF"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
                FragmentManager manager = getSupportFragmentManager();
                Fragment fragment = adapter.getFragment(mViewPager, tab.getPosition(), manager);
                if (fragment != null) {
                    View view = fragment.getView();
                    if (tab.getPosition() == 2)
                        Objects.requireNonNull(view).findViewById(R.id.scroll).scrollTo(0, 0);
                    else {
                        ListView listView = Objects.requireNonNull(view).findViewById(R.id.list);
                        listView.setSelection(0);
                    }
                }
            }
        });
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(2);
        mViewPager.setCurrentItem(1);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return StatsFragment.newInstance(rn);
            else if (position == 1)
                return SummaryFragment.newInstance(rn);
            else
                return GraphFragment.newInstance(rn, getApplicationContext(), q);
        }

        @Override
        public int getCount() {
            return 3;
        }

        public Fragment getFragment(ViewPager container, int position, FragmentManager fm) {
            String name = makeFragmentName(container.getId(), position);
            return fm.findFragmentByTag(name);
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }

    }
}

