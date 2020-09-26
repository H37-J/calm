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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.databinding.FragmentMusicBinding;
import com.hjk.music_3.ui.activity.player.MusicContentActivity;
import com.hjk.music_3.ui.adapter.MindAdapter;
import com.hjk.music_3.ui.viewmodel.CateViewModel;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.ItemOffDecoration;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MusicFragment extends Fragment implements MindAdapter.OnItemClickListener{

    FragmentMusicBinding binding;

    CateViewModel cateViewModel;
    MusicViewModel musicViewModel;

    MindAdapter mindAdapter;

    public static MusicFragment newInstance(){return new MusicFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle saveInstanceState){
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_music,container,false);
        binding.setActivity(this);
        musicViewModel=ViewModelProviders.of(this).get(MusicViewModel.class);
        View root=binding.getRoot();
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        binding.mainRefresh.setOnRefreshListener(this::getData);
        getData();
    }

    public void getData(){
        cateViewModel= ViewModelProviders.of(this).get(CateViewModel.class);


        cateViewModel.getCateMusic().observe(this,c->{
            if(c!=null)
                setCategory(c);
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
        cateViewModel.setCurrentMusic(pos);
        musicViewModel.setCate_id(cateViewModel.getCateMusic().getValue().get(pos).getId());
        Intent intent=new Intent(getActivity(), MusicContentActivity.class);
        intent.putExtra("bno",pos);
        startActivity(intent);
    }
}
