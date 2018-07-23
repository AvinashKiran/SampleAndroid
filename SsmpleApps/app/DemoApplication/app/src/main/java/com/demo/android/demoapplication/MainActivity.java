package com.demo.android.demoapplication;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        initUI();
        setUpViewPager();
        setupTabIcons();
    }


    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.ic_timer);
        mTabLayout.getTabAt(1).setIcon(R.drawable.ic_meeting);
        mTabLayout.getTabAt(2).setIcon(R.drawable.ic_videos);
    }

    private void setUpViewPager() {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new TimerFragment(), getString(R.string.timer));
        pagerAdapter.addFragment(new MeetinsFragment(), getString(R.string.meeting));
        pagerAdapter.addFragment(new VideosFragment(), getString(R.string.videos));

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, ">>>>>>>>>>>>>>>>>>> onBackPressed >>>>>>>>>>>>>>>>>>: " +tellFragments());
        if (tellFragments()) { // From MeetingProgressFrag Skip
            super.onBackPressed();
        }
    }

    private boolean tellFragments(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        Fragment currentFrag = null;

        for(Fragment f : fragments){
            if(f != null && f instanceof BaseTimerContainerFragment) {
                ((BaseTimerContainerFragment) f).onBackPressed();

                currentFrag = f;

            }
        }

        if (currentFrag instanceof MeetingProgressFragment) {
            return false;
        }

        return true;
    }

}
