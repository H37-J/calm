package com.hjk.music_3.data.remote.api;

import androidx.lifecycle.LiveData;

import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.local.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MusicService {
    //Spring
    @GET("music_list")
    Call<List<Music>> getMusic();

    @GET("music_list_mind/{Id}")
    Call<List<Music>> music_list_mind(@Path("Id") int Id);

    @GET("music_list_music/{Id}")
    Call<List<Music>> music_list_music(@Path("Id") int Id);

    //php
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql&categories=58")
    Call<List<Music>> getMusicSleep();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql&categories=56")
    Call<List<Music>> getMusicDream();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql&categories=57")
    Call<List<Music>> getMusicSCape();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql")
    Call<List<Music>> getMusic11();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql&categories=54")
    Call<List<Music>> getMusic12();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql&categories=55")
    Call<List<Music>> getMusic13();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/posts?access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql")
    Call<List<Music>> getMusic3(@Query("categories") int categories);
}
