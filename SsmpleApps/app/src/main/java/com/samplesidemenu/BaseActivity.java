package com.samplesidemenu;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utils.slidingmenu.SlidingMenu;
import com.utils.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity implements
        View.OnClickListener {

    private SlidingMenu mSlidingMenu;
    public FrameLayout mMiddleLayout;
    public ImageView mbtnMenu, imgAvatar;
    public FrameLayout mProgressLayout;
    private View decorView;
    private Button mbtnTrips, mbtnProfile, mbtnAddtrip;
    private TextView mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSlidingMenu();
        getLayoutReferences();
        setClickListeners();
        getView();
        loadTripsFragment();

    }

    private void getLayoutReferences() {
        mMiddleLayout = (FrameLayout) findViewById(R.id.mainContent);
        mTitle = (TextView) findViewById(R.id.txtTitle);
        mbtnMenu = (ImageView) findViewById(R.id.btnMenu);
        mProgressLayout = (FrameLayout) findViewById(R.id.layoutWait);
        mbtnAddtrip = (Button) findViewById(R.id.btnAddTrip);
        mbtnTrips = (Button) findViewById(R.id.btntrips);
        mbtnProfile = (Button) findViewById(R.id.btnProfile);

    }


    public void addContentLayout(int id) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(id, null);
        mMiddleLayout.addView(view);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    private void getView() {
        decorView = getWindow().getDecorView();
        decorView
                .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int i) {
                        int height = decorView.getHeight();
                    }
                });
    }


    private void setClickListeners() {
        mbtnMenu.setOnClickListener(this);
        mbtnTrips.setOnClickListener(this);
        mbtnProfile.setOnClickListener(this);
        mbtnAddtrip.setOnClickListener(this);
    }

    private void loadProfileFragment() {
        btnMenuClicked();
        ProfileFragment mObjFragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, mObjFragment)
                .commitAllowingStateLoss();

    }


    private void setSlidingMenu() {
        setBehindContentView(R.layout.sliding_left_menu);
        setSlidingActionBarEnabled(true);
        setContentView(R.layout.base_layout);
        mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowWidthRes(R.dimen.slider_shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.slider_shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        mSlidingMenu.setFadeDegree(0.35f);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMenu:
                btnMenuClicked();
                break;
            case R.id.btntrips:
                btnMenuClicked();
                loadTripsFragment();
                break;
            case R.id.btnProfile:
                btnMenuClicked();
                loadProfileFragment();
                break;
            case R.id.btnAddTrip:
                btnMenuClicked();
                loadAddTripFragment();
                break;
        }

    }


    private void loadTripsFragment() {
        clearStack();
        TripsFragment mObjFragment = new TripsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, mObjFragment)
                .commitAllowingStateLoss();
    }

    private void loadAddTripFragment() {
        clearStack();
        AddTripFragment mObjFragment = new AddTripFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContent, mObjFragment)
                .commitAllowingStateLoss();
    }

    public void clearStack() {
        for (int i = 0; i < getSupportFragmentManager()
                .getBackStackEntryCount(); ++i) {
            getSupportFragmentManager().popBackStack();
        }
    }


    public void btnMenuClicked() {
        mSlidingMenu.toggle();
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getWindow() != null
                && getWindow().getDecorView() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getWindow()
                    .getDecorView().getWindowToken(), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
