package com.hjk.music_3.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.data.local.model.BackGround;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.BackRepository;

import java.util.List;

public class BackViewModel extends ViewModel {

    public static MutableLiveData<List<BackGround>> back;
    public static MutableLiveData<BackGround> current_back=new MutableLiveData<BackGround>();
    public static int pos=2;
    public static boolean change=true;
    private static BackRepository backRepository=BackRepository.getInstance();

    public void init(){
        if(back!=null){
            return;
        }
        back=backRepository.getBack();

    }


    public void setPos(int pos){
        this.pos=pos;
    }

    public int getPos(){
        return this.pos;
    }

    public static MutableLiveData<List<BackGround>> getBack(){
        return back;
    }

    public static MutableLiveData<BackGround> current_back(){
         return current_back;
   }

   public static void set_current_back(BackGround backgroud){
        current_back.setValue(backgroud);
   }




}



//여기서는 getValue로 값을 받을 수 없는 듯 하다
