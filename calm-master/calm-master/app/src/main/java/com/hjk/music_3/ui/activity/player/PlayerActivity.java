package com.hjk.music_3.ui.activity.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.databinding.Player1Binding;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.Binding;
import com.hjk.music_3.utils.OnSwipeTouchListener;
import com.hjk.music_3.utils.ToastUtils;

public class PlayerActivity extends AppCompatActivity {

    Player1Binding binding;
    MusicViewModel musicViewModel;
    String time;
    Thread t;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicViewModel = ViewModelProviders.of(this).get(MusicViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.player_1);
        binding.setMusic(musicViewModel);
        binding.setActivity(this);

        Intent intent = getIntent();
        int bno = intent.getIntExtra("bno", 999999);

        if (bno == musicViewModel.getPos()) {

        } else {


        }
        //하단바 클릭시 재생중인 노래면 다시 재생x


        if(!musicViewModel.getTimer()){
            binding.time.setVisibility(View.GONE);
            musicViewModel.setTimer(false);
            binding.alarm.setImageResource(R.drawable.m_c);

        }
    }



    @Override
    public void onResume(){
        super.onResume();

        binding.background.setOnTouchListener(new OnSwipeTouchListener(PlayerActivity.this) {

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
                binding.titleText.setText(music.getTitle());
                Binding.PicassoImage(binding.background, music.getImage());
            }
        });




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

        if(musicViewModel.getTimer()) {
            musicViewModel.getRem_time().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0) {
                        binding.time.setVisibility(View.VISIBLE);
                        if (integer > 60) {
                            if (integer % 60 > 0) {
                                integer = (integer / 60) + 1;
                            } else {
                                integer = integer / 60;
                            }
                            time = integer.toString();
                        }
//                        else if (integer < 60) {
//                            time = integer.toString();
//                            time = "0:" + integer.toString();
//
//                        }

                        binding.alarm.setImageResource(R.drawable.m_c_on);
//                        binding.time.setText(time);
                    } else {
                        binding.time.setVisibility(View.GONE);
                        musicViewModel.setTimer(false);
                        binding.alarm.setImageResource(R.drawable.m_c);
                    }

                }
            });
        }

        if(!musicViewModel.getTimer()){
            binding.time.setVisibility(View.GONE);
            musicViewModel.setTimer(false);
            binding.alarm.setImageResource(R.drawable.m_c);

        }



    }

    public void next()   {
        MusicApplication.getInstance().getServiceInterface().next11();

    }

    public void prev(){
        MusicApplication.getInstance().getServiceInterface().prev11();

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

    public void set_loop(){
        if(musicViewModel.getLoop()==false) {
            binding.loop.setImageResource(R.drawable.icon_reverse_only_one);

            musicViewModel.setLoop(true);
            ToastUtils.set(getApplicationContext(),"반복재생 실행",2);

        }
        else{
            binding.loop.setImageResource(R.drawable.icon_reverse);
            musicViewModel.setLoop(false);
            ToastUtils.set(getApplicationContext(),"반복재생 종료",2);

        }
    }

    public void set_random(){
        if(musicViewModel.getRandom()==false) {
            binding.random.setImageResource(R.drawable.icon_random_on);
            musicViewModel.setRandom(true);
            ToastUtils.set(getApplicationContext(),"랜덤재생 실행",2);
        }
        else{
            binding.random.setImageResource(R.drawable.icon_random);
            musicViewModel.setRandom(false);
            ToastUtils.set(getApplicationContext(),"랜덤재생 종료",2);
        }
    }

    public void Intent_Time(){
        Intent intent=new Intent(this,MusicTimeActivity.class);
        startActivity(intent);
    }


}