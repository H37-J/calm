package com.hjk.music_3.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hjk.music_3.data.local.model.User;

@Dao
public interface UserDao {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert_user(User user);

    @Query("update User set save_day=:save_day")
    void save_day(int save_day);

    @Query("update User set save_history=:save_history")
    void save_history(int save_history);

    @Query("update User set save_time=:save_time")
    void save_time(int save_time);

    @Query("update User set save_music=:save_music")
    void save_music(int save_music);

    @Query("update User set save_back=:save_back")
    void save_back(int save_back);

    @Query("update User set save_login=:save_login")
    void save_login(int save_login);

    @Query("select save_back from User")
    int load_save_back();

    @Query("select save_music from User")
    int load_save_music();

    @Query("select id from User")
    String load_id();

    @Query("select pwd from User")
    String load_pwd();

    @Query("update User set pwd=:pwd")
    void save_pwd(String pwd);

    @Query("select save_login from User")
    int load_save_login();

    @Query("SELECT * FROM User")
    LiveData<User> getUser();


}
