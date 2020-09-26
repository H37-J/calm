package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BreathViewModel extends AndroidViewModel {
    public static MutableLiveData<Integer> time=new MutableLiveData<Integer>();
    public static MutableLiveData<Boolean> start=new MutableLiveData<Boolean>();

    public BreathViewModel(Application application){
        super(application);
    }

    public void setStart(Boolean start){
        this.start.setValue(start);
    }

    public static MutableLiveData<Boolean> getStart(){
        return start;
    }

   public void setTime(int time){
        this.time.setValue(time);
   }

   public static MutableLiveData<Integer> getTime(){
        return time;
   }
}
