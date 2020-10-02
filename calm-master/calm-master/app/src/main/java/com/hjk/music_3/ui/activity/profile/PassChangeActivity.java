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
import com.hjk.music_3.utils.EncryptionUtils;
import com.hjk.music_3.utils.ToastUtils;

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
        String pwd=EncryptionUtils.encryptSHA256(binding.currentpass.getText().toString().trim());
        String update_pwd=EncryptionUtils.encryptSHA256(binding.chPass.getText().toString().trim());
        if(!(userViewModel.getCurrent_user().getValue().getPwd().trim().equals(pwd))){
            System.out.println(userViewModel.getCurrent_user().getValue().getPwd());
            System.out.println(pwd);
            ToastUtils.set(getApplicationContext(),"현재 비밀번호가 다릅니다",2);

        }
        else{
            ToastUtils.set(getApplicationContext(),"비밀번호가 변경 되었습니다",2);
            userViewModel.getCurrent_user().getValue().setPwd(update_pwd);
            userViewModel.pass_change(userViewModel.getCurrent_user().getValue());
        }





    }
}
