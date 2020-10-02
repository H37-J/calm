package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.breath.Constants;
import com.hjk.music_3.breath.SettingsDialog;
import com.hjk.music_3.breath.SettingsUtils;
import com.hjk.music_3.databinding.BrBinding;
import com.hjk.music_3.ui.viewmodel.BreathViewModel;

public class BreathPlayActivity extends AppCompatActivity implements SettingsDialog.SettingsChangeListener{

        BrBinding binding;
        BreathViewModel breathViewModel;
        private static final String TAG = BreathPlayActivity.class.getSimpleName();
        private ConstraintLayout contentLayout;
        private TextView statusText;
        private View outerCircleView, innerCircleView;
        private Animation animationInhaleText, animationExhaleText,
        animationInhaleInnerCircle, animationExhaleInnerCircle;
        private Handler handler = new Handler();
        Thread t;
        String time;
        String time2;
        String time3;
        int temp;
        private int holdDuration = 0;
        Boolean in=true;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.br);
        binding.setActivity(this);
        binding.back.setBlurredImg(getResources().getDrawable(R.drawable.i12));
        binding.back.setBlurredLevel(100);




        breathViewModel= ViewModelProviders.of(this).get(BreathViewModel.class);
        statusText = findViewById(R.id.txt_status);
        statusText.setText(Constants.INHALE);
        outerCircleView = findViewById(R.id.v_circle_outer);
        innerCircleView = findViewById(R.id.v_circle_inner);

                setupBackgroundColor();
                prepareAnimations();
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);


        t=new Thread();
        t.start();
        }

        @Override
        public void onResume(){
                super.onResume();
                binding.play.setVisibility(View.GONE);
                 if(breathViewModel.getStart().getValue()) {
            breathViewModel.getTime().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0) {
                        binding.time.setVisibility(View.VISIBLE);
                        time = integer.toString(integer/60);
                        time2=integer.toString(integer%60);
                        if(integer%60<10) time2="0"+time2;
                        time3=time+":"+time2;

                          binding.time.setText(time3);
                    } else if(integer==0){
                            breathViewModel.setStart(false);
                           Intent_com();
                    }
                }
            });
        }
        }
        public void Intent_com(){
                Intent intent =new Intent(this,BreathComplete.class);
                startActivity(intent);
        }
        public void pause(){
                temp=holdDuration;
                holdDuration=9999999;
                binding.pause.setVisibility(View.GONE);
                binding.play.setVisibility(View.VISIBLE);
                breathViewModel.setStart(false);
        }
        public void play(){
                holdDuration=0;
                binding.pause.setVisibility(View.VISIBLE);
                binding.play.setVisibility(View.GONE);
                breathViewModel.setStart(true);
                setupBackgroundColor();
                prepareAnimations();
                t=new Thread();
                t.start();
        }
        public void back(){
                onBackPressed();
        }

        private void setupBackgroundColor() {
        int backgroundResId = SettingsUtils.getBackgroundByPresetPosition(SettingsUtils
        .getSelectedPreset());
        setOuterCircleBackground(backgroundResId);
        }

        private void setOuterCircleBackground(int backgroundResId) {
        outerCircleView.setBackgroundResource(backgroundResId);
        }

        private void setInhaleDuration(int duration) {
        animationInhaleText.setDuration(duration);
        animationInhaleInnerCircle.setDuration(duration);
        }

        private void setExhaleDuration(int duration) {
        animationExhaleText.setDuration(duration);
        animationExhaleInnerCircle.setDuration(duration);
        }

        private void prepareAnimations() {
        int inhaleDuration = SettingsUtils.getSelectedInhaleDuration();
        int exhaleDuration = SettingsUtils.getSelectedExhaleDuration();
        holdDuration=SettingsUtils.getSelectedHoldDuration();
        // Inhale - make large
        animationInhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_inhale);
        animationInhaleText.setFillAfter(true);
        animationInhaleText.setAnimationListener(inhaleAnimationListener);

        animationInhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_inhale);
        animationInhaleInnerCircle.setFillAfter(true);
        animationInhaleInnerCircle.setAnimationListener(inhaleAnimationListener);

        setInhaleDuration(inhaleDuration);


        animationExhaleText = AnimationUtils.loadAnimation(this, R.anim.anim_text_exhale);
        animationExhaleText.setFillAfter(true);
        animationExhaleText.setAnimationListener(exhaleAnimationListener);

        animationExhaleInnerCircle = AnimationUtils.loadAnimation(this, R.anim.anim_inner_circle_exhale);
        animationExhaleInnerCircle.setFillAfter(true);
        animationExhaleInnerCircle.setAnimationListener(exhaleAnimationListener);

        setExhaleDuration(exhaleDuration);

        if(in==false){
                statusText.setText(Constants.INHALE);
                statusText.startAnimation(animationInhaleText);
                innerCircleView.startAnimation(animationInhaleInnerCircle);
        }else{
                statusText.setText(Constants.EXHALE);
                statusText.startAnimation(animationExhaleText);
                innerCircleView.startAnimation(animationExhaleInnerCircle);
        }
        }

        private Animation.AnimationListener inhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
                in=true;
        }
        @Override
        public void onAnimationEnd(Animation animation) {
        Log.d(TAG, "inhale animation end");
        statusText.setText(Constants.HOLD);
        handler.postDelayed(new Runnable() {

        @Override
        public void run() {
        statusText.setText(Constants.EXHALE);
        statusText.startAnimation(animationExhaleText);
        innerCircleView.startAnimation(animationExhaleInnerCircle);
        }
        }, holdDuration);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
        };

        private Animation.AnimationListener exhaleAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) { in=false;}

        @Override
        public void onAnimationEnd(Animation animation) {
        Log.d(TAG, "exhale animation end");

        statusText.setText(Constants.HOLD);
        handler.postDelayed(new Runnable() {

        @Override
        public void run() {
        statusText.setText(Constants.INHALE);
        statusText.startAnimation(animationInhaleText);
        innerCircleView.startAnimation(animationInhaleInnerCircle);

        }
        }, holdDuration);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}
        };
        private View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        showSettingsDialog();
        }
        };
        private void showSettingsDialog() {
        SettingsDialog settingsDialog = new SettingsDialog(this, this);
        settingsDialog.show();
        }
        @Override
        public void onPresetChanged(int backgroundResId) {
        setOuterCircleBackground(backgroundResId);
        }

        @Override
        public void onInhaleValueChanged(int duration) {
        setInhaleDuration(duration);
        }
        @Override
        public void onExhaleValueChanged(int duration) {
        setExhaleDuration(duration);
        }
        @Override
        public void onHoldValueChanged(int duration) {
        holdDuration = 0;
        }

        final Handler handler2=new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                        switch(msg.what){
                                case 0:
                                        breathViewModel.setTime(breathViewModel.getTime().getValue()-1);
                                        int duration=breathViewModel.getTime().getValue();
                                        System.out.println("time:"+duration);

//                                        String all_music_time=Integer.toString(t);
//                                        if(all_music_time.length()==3) {
//                                                all_music_time = all_music_time.substring(0, 1) + ":" + all_music_time.substring(1);
//                                        }else if(all_music_time.length()==4) {
//                                                all_music_time = all_music_time.substring(0, 2) + ":" + all_music_time.substring(2);
//                                        }else if(all_music_time.length()==5) {
//                                                all_music_time = all_music_time.substring(0, 1) + ":" +all_music_time.substring(1, 3) + ":"  + all_music_time.substring(3);
//                                        }
//                                        else{
//                                                all_music_time="0:"+all_music_time.substring(0);
//                                        }
//                                        int time=Integer.parseInt(all_music_time);
//                                        breathViewModel.setTime(time);

                                        break;
                        }
                }
        };

        class Thread extends java.lang.Thread{
                @Override
                public void run(){
                        super.run();

                        while(breathViewModel.getStart().getValue()){

                                Message message=handler2.obtainMessage();
                                String msg=new String(":");
                                message.obj=msg;

                                message.what=0;

                                try{
                                        sleep(1000);
                                }catch(Exception e){
                                        e.printStackTrace();
                                }
                                handler2.sendMessage(message);
                        }

                }
        }


}
