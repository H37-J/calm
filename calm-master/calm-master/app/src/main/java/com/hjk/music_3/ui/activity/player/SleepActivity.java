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
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.databinding.ActivityDreamBinding;
import com.hjk.music_3.ui.adapter.DreamAdapter;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;
import com.hjk.music_3.utils.MethodUtils;

import java.util.List;
public class SleepActivity extends AppCompatActivity implements DreamAdapter.OnItemClickListener{
    private MusicViewModel musicViewModel;
    DreamAdapter dreamAdapter;
    ActivityDreamBinding binding;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_dream);
        binding.title.setText("꿈나라 방송");
        getData();
    }

    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);

        musicViewModel.getMusic_sleep().observe(this,m->{
            if(musicViewModel!=null){
                setMusic(m);
            }
        });
    }

    public void setMusic(List<Music> m){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int pos) {
                return 1;
            }
        });
        RecyclerView recycler_music_list=binding.musicList;

        dreamAdapter=new DreamAdapter(m,this);
        recycler_music_list.setAdapter(dreamAdapter);
        recycler_music_list.setLayoutManager(gridLayoutManager);
        ItemOffDecoration itemOffDecoration=new ItemOffDecoration(this,R.dimen.item_offset);
        recycler_music_list.addItemDecoration(itemOffDecoration);
    }

    @Override
    public void onItemClicked(int pos, ImageView imageView){
        User user=new User();
        user= UserViewModel.getCurrent_user().getValue();
        int history=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history())+1;
        user.setSave_history(Integer.toString(history));
        UserViewModel.user_update(user);


        MusicApplication.getInstance().getServiceInterface().setInit21();
        musicViewModel.setPos(pos);
        musicViewModel.set_current_music_all(pos);
        musicViewModel.set_curreunt_music_sleep(pos);

        MusicApplication.getInstance().getServiceInterface().init_sleep();
        Intent intent=new Intent(this, PlayerActivity2.class);
        startActivity(intent);
    }


}
