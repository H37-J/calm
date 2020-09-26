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
import com.hjk.music_3.data.local.model.BackGround;
import com.hjk.music_3.databinding.ItemBackBinding;

import java.util.List;

public class BackGroundAdapter extends RecyclerView.Adapter<BackGroundAdapter.ItemViewHolder> {

    private List<BackGround> back;
    private Context context;
    BackGroundAdapter.OnItemClickListener onItemClickListener;

    public BackGroundAdapter(List<BackGround> back, OnItemClickListener itemClickListener){
        this.back=back;
        this.onItemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public BackGroundAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
       ItemBackBinding itemBackBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
               R.layout.item_back,parent,false);
       return new BackGroundAdapter.ItemViewHolder(itemBackBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BackGroundAdapter.ItemViewHolder holder, int pos){
        holder.bind(pos);
    }

    @Override
    public int getItemCount(){
        if(back==null)
            return 0;
        return back.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(int pos, ImageView imageView);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemBackBinding itemBackBinding;
        ImageView back_image;

        ItemViewHolder(@NonNull ItemBackBinding itemView){
            super(itemView.getRoot());
            itemBackBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            back_image=itemView.getRoot().findViewById(R.id.image);
        }

        void bind(int pos){
            BackGround back_item=back.get(pos);
            itemBackBinding.setBack(back_item);
        }

        @Override
        public void onClick(View v){
            onItemClickListener.onItemClicked(getAdapterPosition(), back_image);
        }
    }
}
