package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityAppInfoBinding;

public class ActivityAppInfo extends AppCompatActivity {
    ActivityAppInfoBinding binding;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_app_info);
        binding.setActivity(this);
    }
    public void Intent1(){
        Intent intent=new Intent(ActivityAppInfo.this, ActivityWebView.class);
        intent.putExtra("url","https://nowglobalhealing.com/privacy-policy/");
        startActivity(intent);
    }
    public void Intent2(){
        Intent intent=new Intent(ActivityAppInfo.this, ActivityWebView.class);
        intent.putExtra("url","https://nowglobalhealing.com/terms-of-use/");
        startActivity(intent);
    }
}
