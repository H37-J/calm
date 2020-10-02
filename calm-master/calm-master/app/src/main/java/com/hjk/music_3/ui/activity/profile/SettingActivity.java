package com.hjk.music_3.ui.activity.profile;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivitySettingBinding;
import com.hjk.music_3.ui.activity.login.LoginActivity;
import com.hjk.music_3.ui.activity.login.LoginActivity_php;
import com.hjk.music_3.utils.ToastUtils;

public class SettingActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    ActivitySettingBinding binding;
    Intent intent=getIntent();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding=DataBindingUtil.setContentView(this,R.layout.activity_setting);
        binding.setActivity(this);
        binding.switch1.setOnCheckedChangeListener(new saveSwitchListener());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void Intent_MyInfo(){
        intent=new Intent(SettingActivity.this,MyInfoActivity.class);
        startActivity(intent);
    }

    public void Intent_PassChange(){
        intent=new Intent(SettingActivity.this,PassChangeActivity.class);
        startActivity(intent);
    }

    public void Intent_LogOut(){
        Intent intent=new Intent(SettingActivity.this, LoginActivity.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
    }

    public void Intent_AlarmSleep(){
        intent=new Intent(SettingActivity.this, AlarmSleepActivity.class);
        startActivity(intent);
    }

    public void Intent_ActivityMember(){
        intent=new Intent(SettingActivity.this, ActivityMemberShip.class);
        startActivity(intent);
    }

    public void Intent_appinfo(){
        intent=new Intent(SettingActivity.this, ActivityAppInfo.class);
        startActivity(intent);
    }

    public void Intent_Alarm(){
        intent=new Intent(SettingActivity.this, AlarmActivity.class);
        startActivity(intent);
    }

    class saveSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
                ToastUtils.set(getApplicationContext(),"방해 금지모드 설",2);
            }
            else
                changeInterruptionFiler(NotificationManager.INTERRUPTION_FILTER_ALL);
            ToastUtils.set(getApplicationContext(),"방해 금지모드 해제",2);
        }
    }

    protected void changeInterruptionFiler(int interruptionFilter) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mNotificationManager.isNotificationPolicyAccessGranted()) {
                mNotificationManager.setInterruptionFilter(interruptionFilter);
            } else {

                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                startActivity(intent);
            }
        }
    }
}
