package com.hjk.music_3.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.phprequest.RegisterRequest;
import com.hjk.music_3.databinding.ActivitySignBinding;
import com.hjk.music_3.utils.ToastUtils;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity_php extends AppCompatActivity {
    ActivitySignBinding binding;
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_sign);
        binding.setActivity2(this);
    }

    public void sign(){
        String userId=binding.id.getText().toString();
        String userPassword=binding.pwd.getText().toString();
        String userName=binding.name.getText().toString();
        if(binding.id.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"이메일을 입력해 주세요.",2);
            return;
        }


        if(!isEmail(userId)) {
            ToastUtils.set(getApplicationContext(),"이메일 형식이 아닙니다.",2);
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

        Response.Listener<String> responseListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    boolean success=jsonObject.getBoolean("success");
                    boolean dupli=jsonObject.getBoolean("dupli");
                    String userId33=jsonObject.getString("userId");



                    System.out.println("userId33"+userId33);
                    System.out.println("dupli"+dupli);




                    if(dupli){
                        ToastUtils.set(getApplicationContext(),"이미 존재하는 아이디입니다.",2);
                        return;
                    }

                    if(success){
                        ToastUtils.set(getApplicationContext(),"회원가입 성공",2);
                        Intent intent=new Intent(RegisterActivity_php.this,LoginActivity_php.class);
                        startActivity(intent);
                    }
                    else{
                        ToastUtils.set(getApplicationContext(),"회원가입 실패",2);
                        return;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest=new RegisterRequest(userId,userPassword,userName,responseListener);
        RequestQueue queue= Volley.newRequestQueue(RegisterActivity_php.this);
        queue.add(registerRequest);


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
