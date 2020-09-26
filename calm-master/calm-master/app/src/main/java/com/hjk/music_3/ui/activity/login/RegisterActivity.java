package com.hjk.music_3.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.api.RetrofitService;
import com.hjk.music_3.data.remote.api.UserService;
import com.hjk.music_3.databinding.ActivitySignBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    User user=new User();
    ActivitySignBinding binding;
    Gson gson=new Gson();
    UserService userService;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign);
        binding.setActivity(this);
    }

    public void sign(){
        String Email=binding.id.getText().toString();
        String pwd=binding.pwd.getText().toString();
        String name=binding.name.getText().toString();

        if(binding.id.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"아이디를 입력해 주세요.",2);
            return;
        }
        if(binding.pwd.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"비밀번호을 입력해 주세요.",2);
            return;
        }
        if(binding.name.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"이름 입력해 주세요.",2);
            return;
        }


        user=UserViewModel.returnUser(Email,pwd,name,"0","0","0","0");
        UserViewModel.insert_user(user);

        String objJson=gson.toJson(user);
        userService= RetrofitService.getRetro().create(UserService.class);

        Call<ResponseBody> sign=userService.sign(objJson);

        sign.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response){
                try {
                    String result=response.body().string();
                    if(result.equals("1")){
                        Toast.makeText(getApplicationContext(),"회원가입 되었습니다",Toast.LENGTH_SHORT).show();
                        UserViewModel.insert_user(user);
                        Intent intent=getIntent();
                        intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t){
                Toast.makeText(getApplicationContext(),"회원가입 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void intent_login(){
        Intent intent=new Intent(this,LoginActivity_php.class);
        startActivity(intent);
    }

    public static boolean isEmail(String email){
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }

}
