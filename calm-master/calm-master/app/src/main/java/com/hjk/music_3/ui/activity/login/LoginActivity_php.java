package com.hjk.music_3.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.phprequest.LoginRequest;
import com.hjk.music_3.phprequest.UpdateRequestMusic;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.databinding.ActivityLoginBinding;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.TimeUtils;
import com.hjk.music_3.utils.ToastUtils;

import org.json.JSONObject;

public class LoginActivity_php extends AppCompatActivity {

    ActivityLoginBinding binding;
    UserViewModel userViewModel;
    BackViewModel backViewModel;

    int count;  int save;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        backViewModel=ViewModelProviders.of(this).get(BackViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        if(userViewModel.load_save_login()==1) {
            binding.switch1.setChecked(true);
            save=1;
        }
        binding.switch1.setOnCheckedChangeListener(new saveSwitchListener());
        binding.setActivity2(this);

    }

    public void login(){
        String userId=binding.Email.getText().toString();
        String userPassword=binding.Password.getText().toString();

        Response.Listener<String> responseListener=new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    User user2=new User();
                    user2.setId(userId);
                    user2.setPwd(userPassword);

                    userViewModel.insert_user(user2);

                    if(success){
                        String userId=jsonObject.getString("userId");
                        String userPassword=jsonObject.getString("userPassword");
                        String save_back=jsonObject.getString("save_back");
                        String name=jsonObject.getString("display_name");
                        String save_day=jsonObject.getString("save_day");
                        String save_history=jsonObject.getString("save_history");
                        String save_time=jsonObject.getString("save_time");
                        String like_music=jsonObject.getString("like_music");

                        String last_login=jsonObject.getString("last_login");

                        if(Integer.parseInt(last_login)==0){
                            count=1;
                        }
                        if(Integer.parseInt(last_login)== Integer.parseInt(TimeUtils.getDayCom())-1){
                           count=Integer.parseInt(save_day)+1;
                        }
                        else{
                            count=1;
                        }

                        userViewModel.save_login(save);
                        User user=new User();
                        user.setId(userId);
                        user.setPwd(userPassword);
                        user.setName(name);
                        user.setSave_day(Integer.toString(count));
                        user.setSave_history(save_history);
                        user.setSave_time(save_time);
                        user.setLast_login(TimeUtils.getDayCom());
                        user.setLike_music(like_music);


                        if(save_back!="null")
                        backViewModel.set_current_back(backViewModel.getBack().getValue().get(Integer.parseInt(save_back)));


                        userViewModel.setCurrent_user(user);


                        Response.Listener<String> responseListener2= new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){

                            }
                        };

                        UpdateRequestMusic updateRequestMusic=new UpdateRequestMusic(userId,Integer.toString(count),save_history,save_time,last_login, responseListener2);
                        RequestQueue queue2= Volley.newRequestQueue(LoginActivity_php.this);
                        queue2.add(updateRequestMusic);



                        ToastUtils.set(getApplicationContext(),"로그인 성공",2);
                        Intent intent=new Intent(LoginActivity_php.this, MusicActivity.class);
                        startActivity(intent);
                    }else{
                        ToastUtils.set(getApplicationContext(),"로그인 실패",2);
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };



        LoginRequest loginRequest=new LoginRequest(userId,userPassword,responseListener);
        RequestQueue queue= Volley.newRequestQueue(LoginActivity_php.this);
        queue.add(loginRequest);
    }

    public void Intent_Sign() {
        Intent intent = new Intent(this, RegisterActivity_php.class);
        startActivity(intent);
    }

    class saveSwitchListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                save = 1;
                ToastUtils.set(getApplicationContext(),"자동 로그인 설정",2);
            }
            else
                save=0;
            ToastUtils.set(getApplicationContext(),"자동 로그인 해제",2);
        }

    }


}
