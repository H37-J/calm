package com.hjk.music_3.data.remote;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.MusicDatabases;
import com.hjk.music_3.data.local.dao.MusicDao;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.api.MusicService;
import com.hjk.music_3.data.remote.api.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRepository {

    private MusicService musicService;
    private static MusicRepository musicRepository;

    public static MusicRepository getInstance(){
        if(musicRepository==null)
            musicRepository=new MusicRepository();
        return musicRepository;
    }

    private MusicRepository(){
        musicService= RetrofitService.getRetro().create(MusicService.class);
    }


    //Spring
    public MutableLiveData<List<Music>> getMusic(){
        MutableLiveData<List<Music>> music=new MutableLiveData<>();
        musicService.getMusic().enqueue(new Callback<List<Music>>(){
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response){
                if(response.isSuccessful()){
                    music.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable e){
                music.setValue(null);
            }
        });
        return music;
    }

    public MutableLiveData<List<Music>> music_list_mind(int bno){
        MutableLiveData<List<Music>> music=new MutableLiveData<>();
        musicService.music_list_mind(bno).enqueue(new Callback<List<Music>>(){
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response){
                if(response.isSuccessful()){
                    music.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable e){
                music.setValue(null);
            }
        });
        return music;
    }

    public MutableLiveData<List<Music>> music_list_music(int bno){
        MutableLiveData<List<Music>> music=new MutableLiveData<>();
        musicService.music_list_music(bno).enqueue(new Callback<List<Music>>(){
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response){
                if(response.isSuccessful()){
                    music.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable e){
                music.setValue(null);
            }
        });
        return music;
    }






    //php
    public MutableLiveData<List<Music>> getMusicSleep(){
        MutableLiveData<List<Music>> music=new MutableLiveData<>();
        musicService.getMusicSleep().enqueue(new Callback<List<Music>>(){
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response){
                if(response.isSuccessful()){
                    music.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable e){
                music.setValue(null);
            }
        });
        return music;
    }
    public MutableLiveData<List<Music>> getMusicDream(){
        MutableLiveData<List<Music>> music_dream=new MutableLiveData<>();
        musicService.getMusicDream().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music_dream.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                    music_dream.setValue(null);
            }
        });
        return music_dream;
    }

    public MutableLiveData<List<Music>> getMusicScape(){
        MutableLiveData<List<Music>> music_dream=new MutableLiveData<>();

        musicService.getMusicSCape().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music_dream.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                music_dream.setValue(null);
            }
        });

        return music_dream;
    }

    public MutableLiveData<List<Music>> getMusic11(){
        MutableLiveData<List<Music>> music_dream=new MutableLiveData<>();

        musicService.getMusic11().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music_dream.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                music_dream.setValue(null);
            }
        });
        return music_dream;
    }

    public MutableLiveData<List<Music>> getMusic12(){
        MutableLiveData<List<Music>> music_dream=new MutableLiveData<>();

        musicService.getMusic12().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music_dream.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                music_dream.setValue(null);
            }
        });
        return music_dream;
    }

    public MutableLiveData<List<Music>> getMusic13(){
        MutableLiveData<List<Music>> music_dream=new MutableLiveData<>();

        musicService.getMusic13().enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music_dream.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {
                music_dream.setValue(null);
            }
        });
        return music_dream;
    }

    public MutableLiveData<List<Music>> getMusic3(int categories){
        MutableLiveData<List<Music>> music3=new MutableLiveData<>();

        musicService.getMusic3(categories).enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful())
                    music3.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {

            }
        });
        return music3;
    }
}