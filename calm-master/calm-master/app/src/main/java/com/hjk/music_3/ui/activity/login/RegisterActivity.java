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
import com.hjk.music_3.utils.EncryptionUtils;
import com.hjk.music_3.utils.ToastUtils;

import java.util.List;
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
    String pwd;
    int check=1;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign);
        binding.setActivity(this);
    }

    public void sign(){
        check=1;
        String Email=binding.id.getText().toString();
        pwd=binding.pwd.getText().toString();
        String name=binding.name.getText().toString();

        if(binding.id.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"아이디를 입력해 주세요.",2);
            return;
        }

        if(!isEmail(Email)){
            ToastUtils.set(getApplicationContext(),"이메일 형식이 아닙니다",2);
            return;
        }



        if(binding.pwd.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"비밀번호을 입력해 주세요.",2);
            return;
        }

        if(binding.pwd.getText().length()<6){
            ToastUtils.set(getApplicationContext(),"비밀번호을 6자 이상으로 입력해 주세요.",2);
            return;
        }
        if(binding.name.getText().length()==0){
            ToastUtils.set(getApplicationContext(),"이름을 입력해 주세요.",2);
            return;
        }


        UserViewModel.getUserList().observe(this,u->{
            for(int i=0; i<u.size(); i++){
                System.out.println(u.get(i).getId());
                if(Email.equals(u.get(i).getId())){
                    System.out.println(u.get(i));
                    ToastUtils.set(getApplicationContext(),"이미 존재하는 아이디입니다.",2);
                    check=0;
                }
            }

        });

        if(check==1) {
            pwd = EncryptionUtils.encryptSHA256(pwd);
            //암호화

            user = UserViewModel.returnUser(Email, pwd, name, "0", "0", "0", "0", "0", "0,");
            user.setSave_music("0,");
            user.setSubscription_start_date("null");
            user.setSubscription_status("null");
            UserViewModel.insert_user(user);

            String objJson = gson.toJson(user);
            userService = RetrofitService.getRetro().create(UserService.class);
            System.out.println();

            Call<ResponseBody> sign = userService.sign(objJson);

            sign.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String result = response.body().string();
                        System.out.println("결과" + result);
                        if (result.equals("1")) {
                            Toast.makeText(getApplicationContext(), "회원가입 되었습니다", Toast.LENGTH_SHORT).show();
                            UserViewModel.insert_user(user);
                            Intent intent = getIntent();
                            intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }


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
