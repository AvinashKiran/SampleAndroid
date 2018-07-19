package com.demo.android.demoapplication;

/**
 * Created by DELL on 7/16/2018.
 */


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Lincoln on 05/05/16.
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "androidhive-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";


    private static final String IS_SOUND_ENABLED = "isSoundEnable";
    private static final String IS_VIBRATION_ENABLED = "isVibrationEnable";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setSoundEnable(boolean isEnabled) {
        editor.putBoolean(IS_SOUND_ENABLED, isEnabled);
        editor.commit();
    }

    public boolean isSoundEnabled() {
        return pref.getBoolean(IS_SOUND_ENABLED, true);
    }

    public void setVibrationEnabled(boolean isEnabled) {
        editor.putBoolean(IS_VIBRATION_ENABLED, isEnabled);
        editor.commit();
    }

    public boolean isVibrationEnabled() {
        return pref.getBoolean(IS_VIBRATION_ENABLED, true);
    }

}