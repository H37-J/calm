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
import com.hjk.music_3.databinding.FragmentProfileBinding;
import com.hjk.music_3.ui.activity.profile.BackGroundActivity;
import com.hjk.music_3.ui.activity.profile.BreathActivity;

import com.hjk.music_3.ui.activity.profile.ProfileActivity;
import com.hjk.music_3.ui.activity.profile.SettingActivity;

import org.jetbrains.annotations.Nullable;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    public static ProfileFragment newInstance(){return new ProfileFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        setHasOptionsMenu(true);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        View root=binding.getRoot();
        binding.setActivity(this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

    public void Intent_Profile(){
        Intent intent=new Intent(getActivity(), ProfileActivity.class);
        startActivity(intent);
    }

    public void Intent_BackGround(){
        Intent intent=new Intent(getActivity(), BackGroundActivity.class);
        startActivity(intent);
    }

    public void Intent_Setting(){
        Intent intent=new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    public void Intent_Breath(){
        Intent intent=new Intent(getActivity(), BreathActivity.class);
        startActivity(intent);
    }


}
