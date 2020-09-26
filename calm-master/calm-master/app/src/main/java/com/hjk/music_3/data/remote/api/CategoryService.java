package com.hjk.music_3.data.remote.api;

import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.data.local.model.Music;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/categories?parent=52&access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql")
    Call<List<Category>> getCategory3();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/categories?parent=55&access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql")
    Call<List<Category>> getCategory4();
    @GET("https://nowglobalhealing.com/wp-json/wp/v2/categories?parent=88&access_token=loqemfjewmcf0iojjcz2a6qkgmlmngpodximreql")
    Call<List<Category>> getImage();

    @GET("http://these9909.cafe24.com/cate_list_mind")
    Call<List<Category>> getMind();


    @GET("http://these9909.cafe24.com/cate_list_music")
    Call<List<Category>> getMusic();
}
