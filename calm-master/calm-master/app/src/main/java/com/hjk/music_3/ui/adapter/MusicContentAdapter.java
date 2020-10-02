package com.hjk.music_3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.phprequest.UpdateRequestLike;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ItemMusicContentBinding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.StringUtils.StringUtils;

import java.util.List;

public class MusicContentAdapter extends RecyclerView.Adapter<MusicContentAdapter.ItemViewHolder> {

    private List<Music> music;
    private Context context;
    MusicContentAdapter.OnItemClickListener mOnItemClickListener;
    static int clicked=9999;



    public MusicContentAdapter(List<Music> music, OnItemClickListener itemClickListener,Context context){
        this.music=music;
        this.mOnItemClickListener=itemClickListener;
        this.context=context;
    }

    @NonNull
    @Override
    public MusicContentAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemMusicContentBinding itemMusicContentBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_music_content,parent,false);

        return new MusicContentAdapter.ItemViewHolder(itemMusicContentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicContentAdapter.ItemViewHolder holder, int pos){
        holder.bind(pos);



//        holder.event.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                index=pos;
//                notifyDataSetChanged();
//            }
//        });
//        if(index==pos){
//            holder.play.setImageResource(R.drawable.ic_pause_white_48dp);
//        }
//        else{
//            holder.play.setImageResource(R.drawable.ic_play_arrow_48dp);
//        }
//
    }

    @Override
    public int getItemCount(){
        if(music==null){
            return 0;
        }
        return music.size();
    }



    public interface OnItemClickListener{
        void onItemClicked(int pos, LinearLayout linearLayout,ImageView imageView) throws Exception;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemMusicContentBinding itemMusicContentBinding;
        LinearLayout event;
        ImageView play;
        ImageView heart;
        ImageView heart_on;


        ItemViewHolder(@NonNull ItemMusicContentBinding itemView){
            super(itemView.getRoot());
            itemMusicContentBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            event=itemView.getRoot().findViewById(R.id.event);
            play=itemView.getRoot().findViewById(R.id.playBtn);
            heart=itemView.getRoot().findViewById(R.id.heart);
            heart_on=itemView.getRoot().findViewById(R.id.heart_on);
            heart_on.setVisibility(View.GONE);
            itemMusicContentBinding.playBtn2.setVisibility(View.GONE);
        }

        void bind(int pos){
            Music music_item=music.get(pos);
            itemMusicContentBinding.setMusic(music_item);

            String[] arr= StringUtils.str_split(UserViewModel.getCurrent_user().getValue().getLike_music());
            int bno=Integer.parseInt(music_item.getBno());
            int current_bno=Integer.parseInt(MusicViewModel.getCurrent_music_music().getValue().getBno());

            for(int i=0; i<arr.length; i++){
                if(Integer.parseInt(arr[i])==Integer.parseInt(music_item.getBno())){
                    heart.setVisibility(View.GONE);
                    heart_on.setVisibility(View.VISIBLE);

                }
            }


            heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    heart.setVisibility(View.GONE);
                    heart_on.setVisibility(View.VISIBLE);

                    String str=UserViewModel.getCurrent_user().getValue().getLike_music()+bno+",";
                    UserViewModel.getCurrent_user().getValue().setLike_music(str);

                    User user=new User();
                    user=UserViewModel.getCurrent_user().getValue();
                    UserViewModel.user_update(user);
                }
            });



            heart_on.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    heart.setVisibility(View.VISIBLE);
                    heart_on.setVisibility(View.GONE);

                    String[] arr=StringUtils.str_split(UserViewModel.getCurrent_user().getValue().getLike_music());

                    int index=StringUtils.getIndex(arr,music_item.getBno());

                    String[] array=StringUtils.remove(arr,index);
                    String s=StringUtils.merge(array);
                    UserViewModel.getCurrent_user().getValue().setLike_music(s);
                    User user=new User();
                    user=UserViewModel.getCurrent_user().getValue();
                    UserViewModel.user_update(user);
                }
            });


            if(MusicApplication.getInstance().getServiceInterface().getInitMusic()) {
                if (current_bno == Integer.parseInt(music_item.getBno())) {
                    if (clicked == pos) {
                        itemMusicContentBinding.playBtn.setVisibility(View.GONE);
                        Glide.with(context).load(R.drawable.pulse).into(itemMusicContentBinding.playBtn2);
                        itemMusicContentBinding.playBtn2.setVisibility(View.VISIBLE);

                    } else {
                        itemMusicContentBinding.playBtn2.setVisibility(View.GONE);
                    }
                }
            }

        }

        @Override
        public void onClick(View v){
            try{
                mOnItemClickListener.onItemClicked(getAdapterPosition(),event,play);
                clicked=getAdapterPosition();



            }catch(Exception e){
                e.printStackTrace();
            }
        }

        Response.Listener<String> responseListener= new Response.Listener<String>(){
            @Override
            public void onResponse(String response){

            }
        };
    }
}
