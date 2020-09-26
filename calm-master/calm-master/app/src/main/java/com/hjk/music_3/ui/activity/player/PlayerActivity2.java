package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.BuildConfig;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.phprequest.UpdateRequestLike;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.Player4Binding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.ui.viewmodel.UserViewModel;
import com.hjk.music_3.utils.Binding;
import com.hjk.music_3.utils.OnSwipeTouchListener;
import com.hjk.music_3.utils.StringUtils.StringUtils;
import com.hjk.music_3.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlayerActivity2 extends AppCompatActivity {

    Player4Binding binding;
    MusicViewModel musicViewModel;
    UserViewModel userViewModel;

    String bno;


    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.player_4);
        musicViewModel= ViewModelProviders.of(this).get(MusicViewModel.class);
        userViewModel=ViewModelProviders.of(this).get(UserViewModel.class);
        binding.setActivity(this);
        binding.setMusic(musicViewModel);

    }

    @Override
    public void onResume(){
        super.onResume();
        binding.heartOn.setVisibility(View.GONE);

        binding.background.setOnTouchListener(new OnSwipeTouchListener(PlayerActivity2.this) {

            public void onSwipeBottom() {
                onBackPressed();
            }

        });

        musicViewModel.getSeek().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                binding.SeekBar.setMax(MusicApplication.getInstance().getServiceInterface().getDuration());

                binding.SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int seeked_progess;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        seeked_progess = progress;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        MusicApplication.getInstance().getServiceInterface().set_seek(seeked_progess);
                        seekBar.setProgress(seeked_progess);
                    }
                });

            }
        });

        musicViewModel.getCurrent_music_all().observe(this,new Observer<Music>(){
            @Override
            public void onChanged(Music music){
                binding.maintext.setText(music.getTitle());
                Binding.PicassoImage(binding.background, music.getImage());
                bno=music.getBno();
            }
        });


        String[] arr=StringUtils.str_split(userViewModel.getCurrent_user().getValue().getLike_music());

        for(int i=0; i<arr.length; i++){

            if(Integer.parseInt(arr[i])==Integer.parseInt(bno)){
                binding.heartOn.setVisibility(View.VISIBLE);
                binding.heart.setVisibility(View.GONE);
            }
        }


        System.out.println("음악번호"+ bno);
        musicViewModel.getProgress().observe(this,new Observer<String>(){
            @Override
            public void onChanged(String string){
//                binding.musicTime.setText(string);

                binding.SeekBar.setProgress(MusicApplication.getInstance().getServiceInterface().current_position());
            }
        });

        musicViewModel.getStart_progress().observe(this,new Observer<String>(){
            @Override
            public void onChanged(String string){
                binding.startTime.setText(string);

            }
        });

        musicViewModel.getEnd_progress().observe(this,new Observer<String>(){
            @Override
            public void onChanged(String string){
//                binding.musicTime.setText(string);

                binding.endTime.setText(string);
            }
        });

        musicViewModel.getIsPlaying().observe(this,new Observer<Boolean>(){
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.playBtn.setImageResource(R.drawable.ic_pause_48dp);
                }else{
                    binding.playBtn.setImageResource(R.drawable.ic_play_arrow_48dp);
                }
            }
        });

    }

    public void like(){


        String str=userViewModel.getCurrent_user().getValue().getLike_music()+bno+",";
        userViewModel.getCurrent_user().getValue().setLike_music(str);

        UpdateRequestLike updateRequest=new UpdateRequestLike(userViewModel.getCurrent_user().getValue().getId(),str,responseListener);
        RequestQueue queue= Volley.newRequestQueue(PlayerActivity2.this);
        queue.add(updateRequest);

        ToastUtils.set(getApplicationContext(),"이 음악을 즐겨찾기에 추가 되었습니다",2);

        binding.heart.setVisibility(View.GONE);
        binding.heartOn.setVisibility(View.VISIBLE);
    }

    public void unlike(){
        String[] arr=StringUtils.str_split(userViewModel.getCurrent_user().getValue().getLike_music());

        ToastUtils.set(getApplicationContext(),"이 음악을 즐겨찾기에서 뺐습니다",2);
        binding.heart.setVisibility(View.VISIBLE);
        binding.heartOn.setVisibility(View.GONE);


        System.out.println("지우려고하는 번호"+bno);

        for(int i=0; i<arr.length; i++){
            System.out.println("현재배열:"+arr[i]);
        }

        int index=StringUtils.getIndex(arr,bno);
        System.out.println("지울 인덱스:"+index);

        String[] array=StringUtils.remove(arr,index);
        System.out.println("새로운 값들:"+StringUtils.merge(array));
        String s=StringUtils.merge(array);
        userViewModel.getCurrent_user().getValue().setLike_music(s);
        UpdateRequestLike updateRequest=new UpdateRequestLike(userViewModel.getCurrent_user().getValue().getId(),s,responseListener);
        RequestQueue queue= Volley.newRequestQueue(PlayerActivity2.this);
        queue.add(updateRequest);

    }





    public void exit(){
        Intent intent=new Intent(PlayerActivity2.this,MusicExit.class);
        startActivity(intent);
    }

    public void start_pause(){
        if(MusicApplication.getInstance().getServiceInterface().isPlaying()){
            MusicApplication.getInstance().getServiceInterface().pause();

        }
        else{
            MusicApplication.getInstance().getServiceInterface().start();

        }
    }

    public void prev15(){
        MusicApplication.getInstance().getServiceInterface().set_seek(MusicApplication.getInstance().getServiceInterface().current_position()-15000);
    }


    public void next15(){
        MusicApplication.getInstance().getServiceInterface().set_seek(MusicApplication.getInstance().getServiceInterface().current_position()+15000);
    }

    public void share() {
        Picasso.get().load(
                musicViewModel.getCurrent_music3().getValue().getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));

                Intent chooser = Intent.createChooser(i, "Share File");
                startActivity(chooser);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID+".fileprovider",file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }


    Response.Listener<String> responseListener= new Response.Listener<String>(){
        @Override
        public void onResponse(String response){

        }
    };


}
