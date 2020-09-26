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
import com.hjk.music_3.data.local.model.Category;
import com.hjk.music_3.databinding.ItemMindBinding;

import java.util.List;

public class MusicAdapter4 extends RecyclerView.Adapter<MusicAdapter4.ItemViewHolder>{



    private List<Category> music;
    private Context context;
    MusicAdapter4.OnItemClickListener mOnItemClickListener;

    public MusicAdapter4(List<Category> music, MusicAdapter4.OnItemClickListener itemClickListener){
        this.music=music;
        this.mOnItemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public MusicAdapter4.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemMindBinding itemMusicBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_mind,parent,false);
        return new MusicAdapter4.ItemViewHolder(itemMusicBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter4.ItemViewHolder holder, int pos){
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
        ItemMindBinding itemMusicBinding;
        ImageView music_image;

        ItemViewHolder(@NonNull ItemMindBinding itemView){
            super(itemView.getRoot());
            itemMusicBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            music_image=itemView.getRoot().findViewById(R.id.image);
        }

        void bind(int pos){
            Category cate_item=music.get(pos);
            itemMusicBinding.setCate(cate_item);
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