package com.hjk.music_3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.FragmentDreamBinding;
import com.hjk.music_3.ui.activity.player.DreamActivity;
import com.hjk.music_3.ui.activity.player.ScapeActivity;
import com.hjk.music_3.ui.activity.player.SleepActivity;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;

import org.jetbrains.annotations.Nullable;

public class SleepFragment extends Fragment {

    private MusicViewModel musicViewModel;

    FragmentDreamBinding binding;

    public static SleepFragment newInstance(){return new SleepFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        setHasOptionsMenu(true);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_dream,container,false);
        View root=binding.getRoot();
        binding.setActivity(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void Intent_Dream(){
        Intent intent=new Intent(getActivity(), DreamActivity.class);
        startActivity(intent);

    }

    public void Intent_Sleep(){
        Intent intent=new Intent(getActivity(), SleepActivity.class);
        startActivity(intent);

    }

    public void Intent_Scape(){
        Intent intent=new Intent(getActivity(), ScapeActivity.class);
        startActivity(intent);
    }

}
