package com.hjk.music_3.ui.activity.profile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.data.remote.api.RetrofitService;
import com.hjk.music_3.data.remote.api.UserService;
import com.hjk.music_3.databinding.ActivityPasschBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class PassChangeActivity extends AppCompatActivity {

    UserService userService;
    ActivityPasschBinding binding;
    static UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_passch);
        userService= RetrofitService.getRetro().create(UserService.class);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setActivity(this);
    }

    public void update(){
        if(!(userViewModel.getCurrent_user().getValue().getPwd().trim().equals(binding.currentpass.getText().toString().trim()))){
            System.out.println(userViewModel.getCurrent_user().getValue().getPwd());
            System.out.println(binding.currentpass.getText().toString());
            Toast.makeText(getApplicationContext(),"현재 비밀번호가 틀립니다.",Toast.LENGTH_SHORT).show();;
        }
        else{

        }





    }
}
