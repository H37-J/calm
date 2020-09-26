package com.hjk.music_3.data.remote;

import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.model.BackGround;
import com.hjk.music_3.data.remote.api.BackService;
import com.hjk.music_3.data.remote.api.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackRepository {

    private BackService backService;
    private static BackRepository backRepository;
    private BackRepository(){
        backService= RetrofitService.getRetro().create(BackService.class);
    }

    public static BackRepository getInstance(){
        if(backRepository==null)
            backRepository=new BackRepository();
        return backRepository;
    }

    public MutableLiveData<List<BackGround>> getBack(){
        MutableLiveData<List<BackGround>> back=new MutableLiveData<>();
        backService.getBack().enqueue(new Callback<List<BackGround>>(){
            @Override
            public void onResponse(Call<List<BackGround>> call, Response<List<BackGround>> response){
                if(response.isSuccessful()){
                    back.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<BackGround>> call, Throwable e){
                back.setValue(null);
            }
        });
        return back;
    }

    public MutableLiveData<BackGround> current_back(int bno){
        MutableLiveData<BackGround> back=new MutableLiveData<>();
        backService.current_back(bno).enqueue(new Callback<BackGround>(){
            @Override
            public void onResponse(Call<BackGround> call, Response<BackGround> response){
                if(response.isSuccessful()){
                    back.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<BackGround> call, Throwable t) {
                back.setValue(null);
            }
        });
        return back;
    }
}
