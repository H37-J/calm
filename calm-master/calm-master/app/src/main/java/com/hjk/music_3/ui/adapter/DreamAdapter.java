package com.hjk.music_3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ItemDreamBinding;
import com.hjk.music_3.databinding.ItemDreamBinding;

import java.util.List;

public class DreamAdapter extends RecyclerView.Adapter<DreamAdapter.ItemViewHolder>{


    private List<Music> music;
    private Context context;
    DreamAdapter.OnItemClickListener mOnItemClickListener;

    public DreamAdapter(List<Music> music, OnItemClickListener itemClickListener){
        this.music=music;
        this.mOnItemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public DreamAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemDreamBinding itemMusicBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_dream,parent,false);
        return new DreamAdapter.ItemViewHolder(itemMusicBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DreamAdapter.ItemViewHolder holder, int pos){
        holder.bind(pos);
    }

    @Override
    public int getItemCount(){
        if(music==null)
            return 0;
        return music.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(int pos, ImageView imageView) throws Exception;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemDreamBinding itemMusicBinding;
        ImageView music_image;

        ItemViewHolder(@NonNull ItemDreamBinding itemView){
            super(itemView.getRoot());
            itemMusicBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            music_image=itemView.getRoot().findViewById(R.id.image);
        }

        void bind(int pos){
            Music music_item=music.get(pos);
            itemMusicBinding.setMusic(music_item);
        }

        @Override
        public void onClick(View v)  {
            try {
                mOnItemClickListener.onItemClicked(getAdapterPosition(),music_image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



}
