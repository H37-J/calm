package com.hjk.music_3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.databinding.FragmentMainBinding;
import com.hjk.music_3.phprequest.UpdateRequestMusic;
import com.hjk.music_3.data.local.model.Music;

import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.databinding.FragmentMusicBinding;
import com.hjk.music_3.ui.activity.player.PlayerActivity;
import com.hjk.music_3.ui.adapter.MusicAdapter;

import com.hjk.music_3.ui.adapter.MusicAdapter2;
import com.hjk.music_3.ui.adapter.MusicAdapter3;
import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment implements MusicAdapter.OnItemClickListener , MusicAdapter2.OnItemClickListener, MusicAdapter3.OnItemClickListener  {

    private MusicViewModel musicViewModel;
    CateViewModel cateViewModel;
    private UserViewModel userViewModel;

    MusicAdapter musicAdapter;
    MusicAdapter2 musicAdapter2;
    MusicAdapter3 musicAdapter3;

    FragmentMainBinding binding;
    public static MainFragment newInstance(){return new MainFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false);
        binding.setTest(userViewModel);

        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        cateViewModel=ViewModelProviders.of(this).get(CateViewModel.class);

        View root=binding.getRoot();
        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        userViewModel.getCurrent_user().observe(this,new Observer<User>(){
            @Override
            public void onChanged(User u) {
                binding.textView1.setText(u.getName());
            }
        });
        //로그인한 유저 닉네임 표시
        getData();
    }

    public void getData(){
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        musicViewModel.getMusic_11().observe(this,musicViewModel->{
            if(musicViewModel!=null){
                setMusic(musicViewModel);
                MusicApplication.getInstance().getServiceInterface().setViewModel( ViewModelProviders.of(this).get(MusicViewModel.class));
            }
        });
        musicViewModel.getMusic_12().observe(this,musicViewModel->{
            if(musicViewModel!=null){
                setMusic2(musicViewModel);
            }
        });
        musicViewModel.getMusic_13().observe(this,musicViewModel->{
            if(musicViewModel!=null){
                setMusic3(musicViewModel);
            }
        });
    }

    public void setMusic(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list);
        if(m.size()>=7){
            ArrayList<Music> m3=new ArrayList<Music>();
            m3.add(m.get(0));
            m3.add(m.get(1));
            m3.add(m.get(2));
            m3.add(m.get(3));
            m3.add(m.get(4));
            m3.add(m.get(5));
            m3.add(m.get(6));
            musicAdapter=new MusicAdapter(m3,this);
            recycler_music_lsit.setAdapter(musicAdapter);
            recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }else{
            musicAdapter=new MusicAdapter(m,this);
            recycler_music_lsit.setAdapter(musicAdapter);
            recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public void setMusic2(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list2);
        if(m.size()>=7){
        ArrayList<Music> m2=new ArrayList<Music>();
        m2.add(m.get(0));
        m2.add(m.get(1));
        m2.add(m.get(2));
        m2.add(m.get(3));
        m2.add(m.get(4));
        m2.add(m.get(5));
        m2.add(m.get(6));
        musicAdapter2=new MusicAdapter2(m2,this);
        recycler_music_lsit.setAdapter(musicAdapter2);
        recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }else{
            musicAdapter2=new MusicAdapter2(m,this);
            recycler_music_lsit.setAdapter(musicAdapter2);
            recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public void setMusic3(List<Music> m){
        RecyclerView recycler_music_lsit=getActivity().findViewById(R.id.music_list3);
        if(m.size()>=7){
            ArrayList<Music> m2=new ArrayList<Music>();
            m2.add(m.get(0));
            m2.add(m.get(1));
            m2.add(m.get(2));
            m2.add(m.get(3));
            m2.add(m.get(4));
            m2.add(m.get(5));
            m2.add(m.get(6));
            musicAdapter3=new MusicAdapter3(m2,this);
            recycler_music_lsit.setAdapter(musicAdapter3);
            recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }else{
            musicAdapter3=new MusicAdapter3(m,this);
            recycler_music_lsit.setAdapter(musicAdapter3);
            recycler_music_lsit.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        }
    }

    @Override
    public void onItemClicked(int pos, ImageView imageView) throws Exception{
        final int pos_=pos;
        MusicApplication.getInstance().getServiceInterface().setInit11();


        musicViewModel.setPos(pos_);
        musicViewModel.set_current_music_all(pos_);
        musicViewModel.set_current_music_11(pos_);
        MusicApplication.getInstance().getServiceInterface().init11();
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked2(int pos, ImageView imageView) throws Exception{
        final int pos_=pos;

        MusicApplication.getInstance().getServiceInterface().setInit12();
        musicViewModel.setPos(pos_);
        musicViewModel.set_current_music_all(pos_);
        musicViewModel.set_current_music_12(pos_);
        MusicApplication.getInstance().getServiceInterface().init12();
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked3(int pos, ImageView imageView) throws Exception{
        final int pos_=pos;

        MusicApplication.getInstance().getServiceInterface().setInit13();
        musicViewModel.setPos(pos_);
        musicViewModel.set_current_music_all(pos_);
        musicViewModel.set_current_music_13(pos_);

        MusicApplication.getInstance().getServiceInterface().init13();
        Intent intent=new Intent(getActivity(), PlayerActivity.class);
        startActivity(intent);
    }

    public void history_update(){
        Response.Listener<String> responseListener2= new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
            }
        };

        int count=Integer.parseInt(UserViewModel.getCurrent_user().getValue().getSave_history())+1;
        UserViewModel.getCurrent_user().getValue().setSave_history(Integer.toString(count));

        UpdateRequestMusic updateRequestMusic=new UpdateRequestMusic( UserViewModel.getCurrent_user().getValue().getId(),UserViewModel.getCurrent_user().getValue().getSave_day(),Integer.toString(count),UserViewModel.getCurrent_user().getValue().getSave_time(),UserViewModel.getCurrent_user().getValue().getLast_login(), responseListener2);
        RequestQueue queue2= Volley.newRequestQueue(getActivity().getApplicationContext());
        queue2.add(updateRequestMusic);
    }
}
