package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.databinding.MusicExitBinding;
import com.hjk.music_3.ui.activity.MusicActivity;

public class MusicExit extends AppCompatActivity {

    MusicExitBinding binding;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.music_exit);
        binding.setActivity(this);
    }
    public void logout(){
        Intent intent=new Intent(MusicExit.this, MusicActivity.class);
        startActivity(intent);
        MusicApplication.getInstance().getServiceInterface().pause();
    }

    public void cancel(){
       onBackPressed();
        onBackPressed();
    }
}