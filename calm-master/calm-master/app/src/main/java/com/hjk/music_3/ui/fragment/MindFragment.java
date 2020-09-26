package com.hjk.music_3.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.databinding.FragmentDreamBinding;
import com.hjk.music_3.databinding.FragmentMindBinding;
import com.hjk.music_3.ui.activity.player.ActivityMind;
import com.hjk.music_3.ui.adapter.MindAdapter;
import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;
import com.hjk.music_3.utils.TimeUtils;

import java.util.List;

public class MindFragment extends Fragment implements MindAdapter.OnItemClickListener{

    CateViewModel cateViewModel;
    MusicViewModel musicViewModel;

    FragmentMindBinding binding;
    MindAdapter mindAdapter;

    String day= TimeUtils.getDay();

    public static MindFragment newInstance(){return new MindFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle save){
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_mind,container,false);
        binding.setActivity(this);
        day=day+"- 감사";
        binding.day.setText(day);

        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        View root=binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle save){
        super.onActivityCreated(save);
        binding.mainRefresh.setOnRefreshListener(this::getData);
        getData();
    }

    public void getData(){
        cateViewModel=ViewModelProviders.of(this).get(CateViewModel.class);
        cateViewModel.getCateMind().observe(this,c->{
            if(c!=null){
                setCategory(c);
            }
        });
        binding.mainRefresh.setRefreshing(false);
    }

    public void setCategory(List<Category> c){
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int pos) {
                return 1;
            }
        });
        RecyclerView recyclerView=getActivity().findViewById(R.id.music_list);

        mindAdapter=new MindAdapter(c,this);
        recyclerView.setAdapter(mindAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        ItemOffDecoration itemOffDecoration=new ItemOffDecoration(getActivity(),R.dimen.item_offset);
        recyclerView.addItemDecoration(itemOffDecoration);
    }


    @Override
    public void onItemClicked(int pos, ImageView imageView){
        cateViewModel.setCurrentMind(pos);
        musicViewModel.setCate_id(cateViewModel.getCateMind().getValue().get(pos).getId());
        System.out.println("클릭"+cateViewModel.getCateMind().getValue().get(pos).getId());
        //카테고리 값 받아오기

        Intent intent=new Intent(getActivity(),ActivityMind.class);
        intent.putExtra("bno",pos);
        startActivity(intent);
    }
}
