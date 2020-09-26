package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.phprequest.UpdateRequest;
import com.hjk.music_3.databinding.ActivityBackselectBinding;
import com.hjk.music_3.ui.viewmodel.BackViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.Binding;
import com.hjk.music_3.utils.ToastUtils;

public class BackSelectActivity extends AppCompatActivity {

    ActivityBackselectBinding binding;
    BackViewModel backViewModel;
    UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_backselect);
        backViewModel= ViewModelProviders.of(this).get(BackViewModel.class);
        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setActivity(this);
        Intent intent=getIntent();
        final int pos_=intent.getIntExtra("pos",0);

        backViewModel.getBack().observe(this,b->{
            Binding.PicassoImage(binding.selectedImage,b.get(pos_).getImage());
        });
    }

    public void set_back(){
        Intent intent=getIntent();
        final int pos_=intent.getIntExtra("pos",0);

        backViewModel.getBack().observe(this,b->{
            backViewModel.set_current_back(b.get(pos_));
        });

        //php
        UpdateRequest updateRequest=new UpdateRequest(userViewModel.getCurrent_user().getValue().getId(),pos_,responseListener);
        RequestQueue queue= Volley.newRequestQueue(BackSelectActivity.this);
        queue.add(updateRequest);

        ToastUtils.set(getApplication(),"배경화면이 바뀌었습니다",2);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    Response.Listener<String> responseListener= new Response.Listener<String>(){
        @Override
        public void onResponse(String response){

        }
    };


}
