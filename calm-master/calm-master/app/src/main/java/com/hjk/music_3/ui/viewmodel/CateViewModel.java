package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.CategoryRepository;

import java.util.List;

public class CateViewModel extends AndroidViewModel {
    private static MutableLiveData<List<Category>> cate_mind;
    private static MutableLiveData<List<Category>> cate_music;
    private static MutableLiveData<List<Category>> day;//3번째 카테고리 상단 이미지

    private  static CategoryRepository categoryRepository;

    public static MutableLiveData<Category> current_mind=new MutableLiveData<Category>();
    public static MutableLiveData<Category> current_music=new MutableLiveData<Category>();
    public static MutableLiveData<Category> current_day=new MutableLiveData<Category>();

    public CateViewModel(Application application){
        super(application);
    }

    public void init(){
        categoryRepository=CategoryRepository.getInstance();
        if(cate_mind!=null)
            return;
        cate_mind= categoryRepository.getMind();
        cate_music=categoryRepository.getMusic();
        day=categoryRepository.getDaily();
    }

    public MutableLiveData<List<Category>> getCateMind(){
        return cate_mind;
    }
    public MutableLiveData<List<Category>> getCateMusic(){return cate_music;}
    public MutableLiveData<List<Category>> getDay(){return day;}

    public void setCurrentDay(int pos){
        current_day.setValue(getDay().getValue().get(pos));
    }
    public void setCurrentMind(int pos){ current_mind.setValue(getCateMind().getValue().get(pos)); }
    public void setCurrentMusic(int pos){ current_music.setValue(getCateMusic().getValue().get(pos)); }

    public static MutableLiveData<Category> getCurrentDay(){return current_day;}
    public static MutableLiveData<Category> getCurrentMind(){
        return current_mind;
    }
    public static MutableLiveData<Category> getCurrentMusic(){
        return current_music;
    }



}
