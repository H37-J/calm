package com.hjk.music_3.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.hjk.music_3.data.local.model.Music;

import java.util.List;

@Dao
public interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMusic(Music music);

    @Transaction
    @Query("SELECT * FROM music WHERE music.bno= :bno")
    LiveData<Music> getMusic(long bno);

    @Query("SELECT * FROM music")
    LiveData<List<Music>> getAllMusic();

    @Query("DELETE FROM music")
    void deleteAll();

    @Query("DELETE FROM music where music.title=:title")
    void deleteMusic(String title);
}