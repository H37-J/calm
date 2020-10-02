package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.data.remote.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private static MutableLiveData<User> current_user=new MutableLiveData<User>();
    private static MutableLiveData<User> user=null;
    private static MutableLiveData<List<User>> user_list=null;
    private static LiveData<User> user_room;
    private static UserRepository userRepository_local;
    private static UserRepository userRepository=UserRepository.getInstance();
    private static int save_login=0; //자동로그인 기본 0
    private static MutableLiveData<Integer> alarm=new MutableLiveData<>();

    public void init(){
        if(user!=null)
            return;
        userRepository=UserRepository.getInstance();
        user_room=userRepository.getUser();
        user_list=userRepository.getUserList();

    }

    public UserViewModel(Application application){
        super(application);
        userRepository_local=new UserRepository(application);
        save_login=userRepository_local.load_save_login();
    }

    public static User returnUser(String id,String pwd,String name,String save_back,String save_day,String save_history,String save_time,String last_login,String like_music) {
        User user = new User();
        user.setId(id);
        user.setPwd(pwd);
        user.setName(name);
        user.setSave_back(save_back);
        user.setSave_day(save_day);
        user.setSave_history(save_history);
        user.setSave_time(save_time);
        user.setLast_login(last_login);
        user.setLike_music(like_music);
        return user;
    }

    public static LiveData<User> getUserRoom(){return user_room;}

    public static void insert_user(User user){userRepository_local.insert_user(user);}
    //룸에 유저 저장
    public static int load_save_login(){return save_login;}
    public  void save_login(int save_login){
        this.save_login=save_login;
        userRepository_local.save_login(save_login);
    }

    public void setCurrent_user(User user){ this.current_user.setValue(user); }
    public static MutableLiveData<User> getCurrent_user(){
        return current_user;
    }
    //로그인한 유저

    public static MutableLiveData<List<User>> getUserList(){
        return user_list;
    }


    public MutableLiveData<User> getLogin(String id){
        user=userRepository.getLogin(id);
        return user;
    }

    public static int user_update(User u){
        User user=new User();
        user=u;
        int result=userRepository.user_update(user);
        return result;
    }

    public static int pass_change(User u){
        User user=new User();
        user=u;
        int result=userRepository.pass_change(user);
        return result;
    }

    //레트로핏 로그인
    public static MutableLiveData<User> getUser(){
        return user;
    }
    //로그인한 유저 데이터 리턴

    public static MutableLiveData<Integer> getAlarm(){
        return alarm;
    }

    public void setAlarm(int a){
        alarm.setValue(a);
    }
}
