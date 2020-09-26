package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ActivityDreamBinding;
import com.hjk.music_3.ui.adapter.DreamAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;
import com.hjk.music_3.utils.MethodUtils;
import com.hjk.music_3.utils.StringUtils.StringUtils;

import java.util.*;

public class ActivityLike extends AppCompatActivity implements DreamAdapter.OnItemClickListener {
    MusicViewModel musicViewModel;
    UserViewModel userViewModel;
    ActivityDreamBinding binding;
    DreamAdapter dreamAdapter;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_dream);
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
        binding.title.setText("나의 노래");
        binding.mainRefresh.setOnRefreshListener(this::getData);
        getData();
    }

    @Override
    public void onResume(){
        super.onResume();
        getData();
        binding.mainRefresh.setOnRefreshListener(this::getData);
    }

    public void getData(){
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getMusic_11().observe(this,m->{
            if(m!=null){
                setMusic(m);
            }
        });
        binding.mainRefresh.setRefreshing(false);
    }

    public void setMusic(List<Music> m){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        String[] arr= StringUtils.str_split(userViewModel.getCurrent_user().getValue().getLike_music());
        //좋아요 한 정보 저장

        ArrayList<Music> m3=new ArrayList<Music>();
        for(int i=0; i<m.size(); i++){
            for(int j=0; j<arr.length; j++){
                if(Integer.parseInt(arr[j])==Integer.parseInt(m.get(i).getBno())){
                    m3.add(m.get(i));
                }
            }
        }
        musicViewModel.setLike_music(m3);
        RecyclerView recyclerView=binding.musicList;

        dreamAdapter=new DreamAdapter(m3,this);
        recyclerView.setAdapter(dreamAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemOffDecoration itemOffDecoration=new ItemOffDecoration(this,R.dimen.item_offset);
        recyclerView.addItemDecoration(itemOffDecoration);
    }

    @Override
    public void onItemClicked(int pos, ImageView imageView){
        MethodUtils.UpdateHistory(getApplication());

        MusicApplication.getInstance().getServiceInterface().setInitLike();

        musicViewModel.setPos(pos);
        musicViewModel.set_current_music_all(pos);
        musicViewModel.setCurrent_like_music(pos);

        MusicApplication.getInstance().getServiceInterface().initLike();
        Intent intent=new Intent(this, PlayerActivityLike.class);
        startActivity(intent);
    }

}
