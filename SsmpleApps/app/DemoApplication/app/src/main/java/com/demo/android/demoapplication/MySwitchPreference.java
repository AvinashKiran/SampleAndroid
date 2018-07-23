package com.demo.android.demoapplication;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DELL on 7/22/2018.
 */

public class MySwitchPreference extends SwitchPreference {

    public MySwitchPreference(Context context) {
        super(context);
    }
    public MySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MySwitchPreference(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
//        setLayoutResource(R.layout.custom_pref_switch);
        setWidgetLayoutResource(R.layout.custom_pref_switch);
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        TextView titleView = (TextView) view.findViewById(android.R.id.toggle);
        titleView.setTextColor(Color.WHITE);
    }
}