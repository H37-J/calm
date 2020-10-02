package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.os.Bundle;
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
import com.hjk.music_3.databinding.ActivityMindBinding;
import com.hjk.music_3.ui.adapter.MindContentAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import java.util.List;

public class ActivityMind extends AppCompatActivity implements MindContentAdapter.OnItemClickListener {

    MusicViewModel musicViewModel;
    MindContentAdapter musicContentAdapter;

    ActivityMindBinding binding;
    int bno;

    @Override
    public void onCreate(Bundle save) {
        super.onCreate(save);
    }

    @Override
    public void onResume(){
        super.onResume();
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_mind);
        binding.setActivity(this);
         getData();
    }

    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getMusic_3().observe(this,m->{
            if(m!=null){
                setMusic(m);
            }
        });
    }

    public void setMusic(List<Music> m){
        RecyclerView recycler_music_list=binding.content;
        musicContentAdapter=new MindContentAdapter(m,this,getApplicationContext());
        recycler_music_list.setAdapter(musicContentAdapter);
        recycler_music_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onItemClicked(int pos, LinearLayout linearLayout) throws Exception{
        User user=new User();
        user= UserViewModel.getCurrent_user().getValue();
        int history=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history())+1;
        user.setSave_history(Integer.toString(history));

        UserViewModel.user_update(user);
        MusicApplication.getInstance().getServiceInterface().setInit34();

        musicViewModel.setPos(pos);
        musicViewModel.set_current_music_all(pos);
        musicViewModel.set_current_music_3(pos);

        MusicApplication.getInstance().getServiceInterface().init3();
        Intent intent=new Intent(this, PlayerActivity2.class);
        startActivity(intent);
    }
}
