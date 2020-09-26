package com.hjk.music_3.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;


import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.databinding.ActivityLoginBinding;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import com.hjk.music_3.utils.ToastUtils;


public class LoginActivity extends AppCompatActivity {
    User user=new User();
    UserViewModel userViewModel;
    ActivityLoginBinding binding;
    Intent intent = getIntent();
    int save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setActivity(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();

        if(userViewModel.load_save_login()==1)
            binding.switch1.setChecked(true);
        binding.switch1.setOnCheckedChangeListener(new saveSwitchListener());
    }

    @Override
    public void onRestart(){
        super.onRestart();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.init();
    }

    public void Intent_Sign() {
        intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login() {
        String Email = binding.Email.getText().toString();
        String Pwd = binding.Password.getText().toString();
        if(Email.length()==0){
            ToastUtils.set(getApplicationContext(),"이메일을 입력해주세요",2);
            return;
        }
        if(Pwd.length()==0){
            ToastUtils.set(getApplicationContext(),"비밀번호를 입력해주세요",2);
            return;
        }



        userViewModel.getLogin(Email).observe(this, u -> {
            if (u != null) {
                if (u.getId().equals(Email) && u.getPwd().equals(Pwd)) {
                    userViewModel.setCurrent_user(u); //로그인 한 유저 정보 저장
                    user=UserViewModel.returnUser(Email,Pwd,u.getName(),u.getSave_day(),u.getSave_history(),u.getSave_time(),u.getLast_login());
                    UserViewModel.insert_user(user);
                    userViewModel.save_login(save); //자동로그인 여부
                    Toast.makeText(this, "로그인 완료", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, MusicActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class saveSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                save = 1;
                ToastUtils.set(getApplicationContext(),"자동 로그인 설정",2);
            }
            else {
                save = 0;
                ToastUtils.set(getApplicationContext(), "자동 로그인 해제", 2);
            }
        }

    }
}
