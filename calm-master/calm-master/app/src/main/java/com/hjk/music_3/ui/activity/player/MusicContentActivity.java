package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.databinding.ActivityCategoryContentBinding;
import com.hjk.music_3.ui.adapter.MusicContentAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.MethodUtils;

import java.util.List;

public class MusicContentActivity extends AppCompatActivity implements MusicContentAdapter.OnItemClickListener{

    MusicViewModel musicViewModel;
    ActivityCategoryContentBinding binding;
    MusicContentAdapter musicContentAdapter;


    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_category_content);
        binding.setActivity(this);
        getData();
    }

    @Override
    public void onResume(){
        super.onResume();
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_category_content);
        binding.setActivity(this);
        getData();

    }


    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getMusic_music().observe(this,m->{
            if(m!=null){
                setMusic(m);
            }
        });
    }

    public void setMusic(List<Music> m){
        RecyclerView recycler_music_list=binding.content;
        musicContentAdapter=new MusicContentAdapter(m,this,getApplicationContext());
        recycler_music_list.setAdapter(musicContentAdapter);
        recycler_music_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void onItemClicked(int pos, LinearLayout linearLayout,ImageView imageView) throws Exception{
        User user=new User();
        user= UserViewModel.getCurrent_user().getValue();
        int history=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history())+1;
        user.setSave_history(Integer.toString(history));
        UserViewModel.user_update(user);

        MusicApplication.getInstance().getServiceInterface().setInitMusic();

        musicViewModel.setPos(pos);
        musicViewModel.set_current_music_all(pos);
        musicViewModel.set_current_music_music(pos);

        MusicApplication.getInstance().getServiceInterface().initMusic();
        Intent intent=new Intent(this, PlayerActivity2.class);
        startActivity(intent);
    }
}

