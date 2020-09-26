package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.BackGround;
import com.hjk.music_3.databinding.ActivityBackgroundBinding;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.adapter.BackGroundAdapter;
import com.hjk.music_3.ui.viewmodel.BackViewModel;

import java.util.List;

public class BackGroundActivity extends AppCompatActivity implements BackGroundAdapter.OnItemClickListener {

    BackViewModel backViewModel;
    ActivityBackgroundBinding binding;
    BackGroundAdapter backGroundAdapter;
    Intent intent =getIntent();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_background);
        binding.setActivity(this);
        backViewModel=ViewModelProviders.of(this).get(BackViewModel.class);
        backViewModel.init();
        getData();
    }

    public void getData(){
        backViewModel.getBack().observe(this,b->{
           if(b!=null){
               set_back_list(b);
           }
        });
    }

    public void set_back_list(List<BackGround> b){
        RecyclerView recyclerView_back_list=binding.backList;
        backGroundAdapter=new BackGroundAdapter(b,this);
        recyclerView_back_list.setAdapter(backGroundAdapter);
        recyclerView_back_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
    }

    @Override
    public void onItemClicked(int pos, ImageView imageView){
        intent =new Intent(BackGroundActivity.this, BackSelectActivity.class);
        intent.putExtra("pos",pos);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        intent=new Intent(BackGroundActivity.this, MusicActivity.class);
        startActivity(intent);
    }
}
