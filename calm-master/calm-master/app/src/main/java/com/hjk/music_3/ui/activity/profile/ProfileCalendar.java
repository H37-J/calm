package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.phprequest.UpdateRequestMusic;
import com.hjk.music_3.databinding.ActivityProfileCalendarBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ToastUtils;

public class ProfileCalendar extends AppCompatActivity {
    ActivityProfileCalendarBinding binding;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_profile_calendar);
        binding.setActivity(this);
    }

    public void add(){
        Response.Listener<String> responseListener2= new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
            }
        };


        //php
        int count=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history())+1;
        UserViewModel.getCurrent_user().getValue().setSave_history(Integer.toString(count));

        UpdateRequestMusic updateRequestMusic=new UpdateRequestMusic( UserViewModel.getCurrent_user().getValue().getId(),UserViewModel.getCurrent_user().getValue().getSave_day(),Integer.toString(count),UserViewModel.getCurrent_user().getValue().getSave_time(),UserViewModel.getCurrent_user().getValue().getLast_login(), responseListener2);
        RequestQueue queue2= Volley.newRequestQueue(ProfileCalendar.this);
        queue2.add(updateRequestMusic);

        Intent intent=new Intent(ProfileCalendar.this,ProfileActivity.class);
        startActivity(intent);

        ToastUtils.set(getApplicationContext(),"세션이 추가 되었습니다",2);

    }
}
