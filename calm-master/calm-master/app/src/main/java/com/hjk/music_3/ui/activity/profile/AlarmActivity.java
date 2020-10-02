package com.hjk.music_3.ui.activity.profile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.Receiver.Alarm_Receiver;
import com.hjk.music_3.Service.AlarmService;
import com.hjk.music_3.databinding.ActivityAlarmBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ToastUtils;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    ActivityAlarmBinding binding;
    UserViewModel userViewModel;
    AlarmManager alarmManager;
    Context context;
    Intent intent=getIntent();
    AlarmService alarmService=new AlarmService();
    String ST;
    int SH;
    int SM;
    PendingIntent pendingIntent;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setActivity(this);
        userViewModel.setAlarm(0);
        alarmService.setViewModel(userViewModel);
        this.context=this;
    }

    @Override
    public void onResume(){
        super.onResume();
        UserViewModel.getAlarm().observe(this,a->{
            if(a==0) {
                binding.cancel.setVisibility(View.GONE);
                binding.button8.setVisibility(View.GONE);
            }
            else {
                binding.cancel.setVisibility(View.VISIBLE);
                binding.button8.setVisibility(View.VISIBLE);
            }

        });

    }

    public void Intent_Alarm(){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog=new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int SelectedHour, int SelectedMinute) {
                String state="오전";

                if(SelectedHour>12){
                    SelectedHour-=12;
                    state="오후";
                }
                binding.SelectedTime.setText(state+" "+ SelectedHour+"시"+" "+ SelectedMinute+"분");
                ST=state;
                SH=SelectedHour;
                SM=SelectedMinute;
            }
        },hour,minute,false);
        timePickerDialog.setTitle("시간 설정");
        timePickerDialog.show();
        binding.button8.setVisibility(View.VISIBLE);
        userViewModel.setAlarm(1);

    }

    public void Set_Alarm(){

        Calendar calendar=Calendar.getInstance();
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        calendar.set(Calendar.HOUR_OF_DAY, SH);
        calendar.set(Calendar.MINUTE,SM);
        final Intent alarm_intent=new Intent(this.context, Alarm_Receiver.class);
        alarm_intent.putExtra("state","alarm on");
        ToastUtils.set(getApplicationContext(),ST+" "+SH+"시"+SM+"분에 깨워 드리겠습니다",2);
        pendingIntent=PendingIntent.getBroadcast(AlarmActivity.this,0,alarm_intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

    }

    public void Cancel_Alarm(){
        userViewModel.setAlarm(0);
        Toast.makeText(context, "알람을 해제 합니다.", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(pendingIntent);
        final Intent alarm_intent = new Intent(getApplicationContext(), Alarm_Receiver.class);
        alarm_intent.putExtra("state","alarm off");
        sendBroadcast(alarm_intent);
    }
}
