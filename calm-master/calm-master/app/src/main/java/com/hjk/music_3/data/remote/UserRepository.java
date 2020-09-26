package com.hjk.music_3.data.remote;

import android.app.Application;
import android.service.autofill.UserData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.UserDatabases;
import com.hjk.music_3.data.local.dao.UserDao;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.api.RetrofitService;
import com.hjk.music_3.data.remote.api.UserService;

import java.util.List;

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
}
