package com.hjk.music_3.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.data.local.model.User;
import com.hjk.music_3.phprequest.UpdateRequestLike;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.ItemMindContentBinding;
import com.hjk.music_3.databinding.ItemMusicContentBinding;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.StringUtils.StringUtils;

import java.util.List;

public class MindContentAdapter extends RecyclerView.Adapter<MindContentAdapter.ItemViewHolder> {

    private List<Music> music;
    private Context context;
    MindContentAdapter.OnItemClickListener mOnItemClickListener;
    int i=1;
    static int clicked=9999;

    public MindContentAdapter(List<Music> music, OnItemClickListener itemClickListener,Context context){
        this.music=music;
        this.mOnItemClickListener=itemClickListener;
        this.context=context;
    }

    @NonNull
    @Override
    public MindContentAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        ItemMindContentBinding itemMusicContentBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_mind_content,parent,false);
        return new MindContentAdapter.ItemViewHolder(itemMusicContentBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MindContentAdapter.ItemViewHolder holder, int pos){
        holder.bind(pos);

    }

    @Override
    public int getItemCount(){
        if(music==null){
            return 0;
        }
        return music.size();
    }


    public interface OnItemClickListener{
        void onItemClicked(int pos, LinearLayout linearLayout) throws Exception;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemMindContentBinding itemMusicContentBinding;
        LinearLayout event;
        Button text;
        ImageView heart;
        ImageView heart_on;


        ItemViewHolder(@NonNull ItemMindContentBinding itemView){
            super(itemView.getRoot());
            itemMusicContentBinding=itemView;
            itemView.getRoot().setOnClickListener(this);
            event=itemView.getRoot().findViewById(R.id.event);
            text=itemView.getRoot().findViewById(R.id.text);
            heart=itemView.getRoot().findViewById(R.id.heart);
            heart_on=itemView.getRoot().findViewById(R.id.heart_on);
            heart_on.setVisibility(View.GONE);

        }

        void bind(int pos){
            Music music_item=music.get(pos);
            itemMusicContentBinding.setMusic(music_item);
            text.setText(Integer.toString(i));
            i++;
            String[] arr= StringUtils.str_split(UserViewModel.getCurrent_user().getValue().getLike_music());

            int bno=Integer.parseInt(music_item.getBno());


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


        }

        @Override
        public void onClick(View v){
            try{
                mOnItemClickListener.onItemClicked(getAdapterPosition(),event);
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
