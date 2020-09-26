package com.hjk.music_3.ui.activity.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivitySleepTimerBinding;

public class AlarmSleepActivity extends AppCompatActivity {
    ActivitySleepTimerBinding binding;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_sleep_timer);

    }
}
