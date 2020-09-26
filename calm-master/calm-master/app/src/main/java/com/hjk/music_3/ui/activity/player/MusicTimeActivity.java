package com.hjk.music_3.ui.activity.player;

import android.app.Activity;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.databinding.ActivityMusictimeBinding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.SecondUtils;
import com.hjk.music_3.utils.ToastUtils;

public class MusicTimeActivity extends AppCompatActivity {
    MusicViewModel musicViewModel;
    ActivityMusictimeBinding binding;

    int time;
    Thread thread;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_musictime);
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        binding.setActivity(this);
        binding.Minute.setMinValue(1);
        binding.Minute.setMaxValue(59);
    }

    public void setTime(){
        thread=new Thread();
        time= binding.Minute.getValue();
        ToastUtils.set(getApplicationContext(),time+"분 후에 음악이 종료 됩니다",2);
        time=time*60;
        musicViewModel.setTimer(true);
        musicViewModel.setRem_Time(time);
        onBackPressed();
    }
}
