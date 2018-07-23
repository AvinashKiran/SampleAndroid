package com.demo.android.demoapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MeetinsFragment extends Fragment {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    public MeetinsFragment() {
        // Required empty public constructor
    }

    public static MeetinsFragment newInstance(String param1, String param2) {
        MeetinsFragment fragment = new MeetinsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v = inflater.inflate(R.layout.fragment_meetins, container, false);

//        initUI(v);
//        setUpViewPager();
        return v;
    }

    private void initUI(View v) {
        mViewPager = v.findViewById(R.id.viewPager);
        mTabLayout = v.findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    private void setUpViewPager() {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new GoMoJoFragment(), "GO MOJO");
        pagerAdapter.addFragment(new MeetinsFragment(), "START WITH DATA");

        mViewPager.setAdapter(pagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}