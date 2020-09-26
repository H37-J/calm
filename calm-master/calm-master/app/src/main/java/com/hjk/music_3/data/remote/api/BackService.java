package com.hjk.music_3.data.remote.api;

import com.hjk.music_3.data.local.model.BackGround;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackService {
    @GET("back_list")
    Call<List<BackGround>> getBack();
    @GET("back_list/{bno}")
    Call<BackGround> current_back(@Path("bno") int bno);
}
