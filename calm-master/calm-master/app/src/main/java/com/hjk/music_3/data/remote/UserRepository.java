package com.hjk.music_3.data.remote;

import android.app.Application;
import android.content.Intent;
import android.service.autofill.UserData;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.hjk.music_3.data.local.UserDatabases;
import com.hjk.music_3.data.local.dao.UserDao;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.api.RetrofitService;
import com.hjk.music_3.data.remote.api.UserService;
import com.hjk.music_3.ui.activity.login.LoginActivity;
import com.hjk.music_3.ui.activity.login.RegisterActivity;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

    private UserService userService;
    private static UserRepository userRepository;

    private UserRepository(){ userService= RetrofitService.getRetro().create(UserService.class); }

    public static UserDao userDao;
    public static LiveData<User> user;
    static int save_login;
    int i;


    public static UserRepository getInstance(){
        if(userRepository==null)
            userRepository=new UserRepository();
        return userRepository;
    }

    public UserRepository(Application application){
        UserDatabases db= UserDatabases.getInstance(application);
        userDao=db.userDao();
        if(userDao.getUser()!=null) {
            user = userDao.getUser();
        }
        save_login=userDao.load_save_login();
        //Room 자동로그인
    }

    public static void insert_user(User user){userDao.insert_user(user);}

    public LiveData<User> getUser(){ return user;}

    public static int load_save_login(){
        return save_login;
    }

    public static void save_login(int save_login){
        UserDatabases.databaseWriteExecutor.execute(()->{
            userDao.save_login(save_login);
        });
    }

    //Spring
    public MutableLiveData<List<User>> getUserList(){
        MutableLiveData<List<User>> user=new MutableLiveData<>();

        userService.getUser().enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable e){
                user.setValue(null);
            }
        });
        return user;
    }


    public MutableLiveData<User> getLogin(String id){
        MutableLiveData<User> user=new MutableLiveData<>();

        userService.getLogin(id).enqueue(new Callback<User>(){
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable e){
                user.setValue(null);
            }
        });
        return user;
    }

    public int user_update(User user) {
        Gson gson=new Gson();
        String objJson = gson.toJson(user);
        userService = RetrofitService.getRetro().create(UserService.class);

        Call<ResponseBody> user_update = userService.user_update(objJson);

        user_update.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (result.equals("1")) {
                        i=1;
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                i=0;
            }
        });
        return i;
    }

    public int pass_change(User user) {
        Gson gson=new Gson();
        String objJson = gson.toJson(user);
        userService = RetrofitService.getRetro().create(UserService.class);

        Call<ResponseBody> user_update = userService.pass_change(objJson);

        user_update.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String result = response.body().string();
                    if (result.equals("1")) {
                        i=1;
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                i=0;
            }
        });
        return i;
    }
}
