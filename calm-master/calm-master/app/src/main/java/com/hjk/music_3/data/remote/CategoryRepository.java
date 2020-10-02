package com.hjk.music_3.data.remote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.api.CategoryService;
import com.hjk.music_3.data.remote.api.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository   {
    private CategoryService categoryService;
    private static CategoryRepository categoryRepository;
    private CategoryRepository(){ categoryService= RetrofitService.getRetro().create(CategoryService.class); }

    public static CategoryRepository getInstance(){
        if(categoryRepository==null)
            categoryRepository=new CategoryRepository();
        return categoryRepository;
    }

    public MutableLiveData<List<Category>> getMind(){
        MutableLiveData<List<Category>> Mind=new MutableLiveData<>();

        categoryService.getMind().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    Mind.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Mind.setValue(null);
            }
        });
        return Mind;
    }

    public MutableLiveData<List<Category>> getDaily(){
        MutableLiveData<List<Category>> daily=new MutableLiveData<>();

        categoryService.getDaily().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    daily.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                daily.setValue(null);
            }
        });
        return daily;
    }

    public MutableLiveData<List<Category>> getMusic(){
        MutableLiveData<List<Category>> Mind=new MutableLiveData<>();

        categoryService.getMusic().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    Mind.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Mind.setValue(null);
            }
        });
        return Mind;
    }



    //php
    public MutableLiveData<List<Category>> getCategory3(){
        MutableLiveData<List<Category>> cate3=new MutableLiveData<>();

        categoryService.getCategory3().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    cate3.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                    cate3.setValue(null);
            }
        });
        return cate3;
    }

    public MutableLiveData<List<Category>> getCategory4(){
        MutableLiveData<List<Category>> cate4=new MutableLiveData<>();

        categoryService.getCategory4().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    cate4.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                cate4.setValue(null);
            }
        });
        return cate4;
    }
    public MutableLiveData<List<Category>> getImage(){
        MutableLiveData<List<Category>> image=new MutableLiveData<>();
        categoryService.getImage().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    image.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                image.setValue(null);
            }
        });
        return image;
    }
}
