package com.hjk.music_3.utils;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.phprequest.UpdateRequestMusic;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

public class MethodUtils {

    public static void UpdateHistory(Application application) {
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };

        int count = Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history()) + 1;
        UserViewModel.getCurrent_user().getValue().setSave_history(Integer.toString(count));

        UpdateRequestMusic updateRequestMusic = new UpdateRequestMusic(UserViewModel.getCurrent_user().getValue().getId(), UserViewModel.getCurrent_user().getValue().getSave_day(), Integer.toString(count), UserViewModel.getCurrent_user().getValue().getSave_time(), UserViewModel.getCurrent_user().getValue().getLast_login(), responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(application);
        queue2.add(updateRequestMusic);
    }

    public static void UpdateSaveTime(Application application){

        Response.Listener<String> responseListener2= new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
            }
        };

        int time=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_time());
        time= (MusicViewModel.getSave_time()/60)+time;
        UserViewModel.getCurrent_user().getValue().setSave_time(Integer.toString(time));

        UpdateRequestMusic updateRequestMusic=new UpdateRequestMusic( UserViewModel.getCurrent_user().getValue().getId(),UserViewModel.getCurrent_user().getValue().getSave_day(),UserViewModel.getCurrent_user().getValue().getSave_history(),Integer.toString(time),UserViewModel.getCurrent_user().getValue().getLast_login(), responseListener2);
        RequestQueue queue2= Volley.newRequestQueue(application);
        queue2.add(updateRequestMusic);
    }


}
