package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityProfileBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    UserViewModel userViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        binding.setActivity(this);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

        int time=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_time());

        binding.time.setText(Integer.toString(time)+"분");
        binding.his.setText(userViewModel.getCurrent_user().getValue().getSave_history()+"회");
        binding.day.setText(userViewModel.getCurrent_user().getValue().getSave_day()+"일");
    }

    public void Intent_Calendar(){
        Intent intent=new Intent(this,ProfileCalendar.class);
        startActivity(intent);
    }
}
