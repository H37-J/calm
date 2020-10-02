package com.hjk.music_3.ui.activity.profile;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityMembershipBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class ActivityMemberShip extends AppCompatActivity {
    ActivityMembershipBinding binding;

    @Override
    public void onCreate(Bundle save) {
        super.onCreate(save);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_membership);
        binding.setActivity(this);
        System.out.println(UserViewModel.getCurrent_user().getValue().getSubscription_status()+"fdsfs");
        if(UserViewModel.getCurrent_user().getValue().getSubscription_status().equals("active")){
            binding.sta.setText("당신은 프리미엄 회원입니다!");
        }
        else{
            binding.sta.setText("아직 구독중이 아니십니다");
        }

        binding.date.setText("회원님은" + UserViewModel.getCurrent_user().getValue().getSubscription_start_date()+
                "에\n 구독을 시작하였습니다!");


        if(UserViewModel.getCurrent_user().getValue().getSubscription_start_date().equals("null")){
            binding.date.setVisibility(View.GONE);
        }
    }

}
