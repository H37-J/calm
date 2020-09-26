package com.hjk.music_3.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hjk.music_3.Service.AlarmService;

public class Alarm_Receiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent){
        this.context=context;

        String state=intent.getExtras().getString("state");

        Intent service_intent=new Intent(context, AlarmService.class);

        service_intent.putExtra("state",state);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            this.context.startForegroundService(service_intent);
        }else{
            this.context.startService(service_intent);
        }
    }
}
