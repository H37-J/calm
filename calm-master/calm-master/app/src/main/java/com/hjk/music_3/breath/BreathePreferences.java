package com.hjk.music_3.breath;


import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BreathePreferences {

    static final String SELECTED_PRESET_KEY = "selectedPreset";
    static final String SELECTED_INHALE_DURATION_KEY = "selectedInhaleDuration";
    static final String SELECTED_EXHALE_DURATION_KEY = "selectedExhaleDuration";
    static final String SELECTED_HOLD_DURATION_KEY = "selectedHoldDuration";
    private static final String BREATHE_PREFS = "BreathePreferences";

    private static BreathePreferences instance;
    private SharedPreferences prefs;

    private BreathePreferences(@NonNull Context context) {
        prefs = context.getSharedPreferences(BREATHE_PREFS, Context.MODE_PRIVATE);
    }
    public static void init(@NonNull Context context) {
        if (instance == null) {
            instance = new BreathePreferences(context);
        }
    }
    public static BreathePreferences getInstance() {
        if (instance == null) {
            Log.e(BreathePreferences.class.getSimpleName(), "Call init() first");
        }
        return instance;
    }
    public void putInt(@NonNull String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }
    @Nullable
    public int getInt(@NonNull String key) {
        return prefs.getInt(key, -1);
    }


}
