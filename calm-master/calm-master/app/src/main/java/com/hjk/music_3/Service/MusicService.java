package com.hjk.music_3.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleService;

import com.hjk.music_3.R;
import com.hjk.music_3.Receiver.MusicReceiver;
import com.hjk.music_3.ui.activity.MusicActivity;
import com.hjk.music_3.ui.viewmodel.MusicViewModel;
import com.hjk.music_3.utils.SecondUtils;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class MusicService extends LifecycleService  {

    private final IBinder binder=new MusicServiceBinder();
    public static MediaPlayer mediaPlayer;

    static Uri url;
    static MusicViewModel musicViewModel;

    public static Fragment fragment;

    IntentFilter intentFilter;
    MusicReceiver myNoisyAudioStreamReceiver;
    private PlayerCallHelper mPlayerCallHelper;

    private Boolean init=false;
    private Boolean init11=false;
    private Boolean init12=false;
    private Boolean init13=false;
    private Boolean init2_1=false;
    private Boolean init2_2=false;
    private Boolean init2_3=false;
    private Boolean init3=false;
    private Boolean initMusic=false;
    private Boolean initLike=false;
    private Boolean hide=true;

    private static final String GROUP_ID = "g1";
    private static final String CHANNEL_ID = "c1";
    public static final String NOTIFY_PREVIOUS = "prev";
    public static final String NOTIFY_CLOSE = "close";
    public static final String NOTIFY_PAUSE = "pause";
    public static final String NOTIFY_PLAY = "play";
    public static final String NOTIFY_NEXT = "next";

    Random random=new Random();
    Thread thread;

    public class MusicServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return binder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        myNoisyAudioStreamReceiver = new MusicReceiver();

        if (mPlayerCallHelper == null) {
            mPlayerCallHelper = new PlayerCallHelper(new PlayerCallHelper.PlayerCallHelperListener() {
                @Override
                public void playAudio() {
                    MusicApplication.getInstance().getServiceInterface().start();
                }

                @Override
                public boolean isPlaying() {
                    return MusicApplication.getInstance().getServiceInterface().isPlaying();
                }

                @Override
                public void pauseAudio() {
                    MusicApplication.getInstance().getServiceInterface().pause();
                }
            });
        }
        mPlayerCallHelper.bindCallListener(getApplicationContext());
        mPlayerCallHelper.bindRemoteController(getApplicationContext());

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                try {
                    if(init) {
                        musicViewModel.set_current_music(musicViewModel.getPos());
                        init();
                    }
                    if(init3){
                        musicViewModel.set_current_music_3(musicViewModel.getPos());
                        init_3();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }); //완료시 리스너
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                return false;
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mediaPlayer) {
            }
        });
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mPlayerCallHelper.unbindCallListener(getApplicationContext());
            mPlayerCallHelper.unbindRemoteController();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }

    public void setViewModel(MusicViewModel musicViewModel){
        this.musicViewModel=musicViewModel;
    }//뷰모델겟
    public void getFragment(Fragment fragment){
        this.fragment=fragment;
    }

    public void init(){
        String str=musicViewModel.current_music().getValue().getMp3();
        setData(str);
    }

    //php
    public void init11(){
        String str=musicViewModel.current_music11().getValue().getMp3();
        setData(str);
    }
    public void init12(){
        String str=musicViewModel.current_music12().getValue().getMp3();
        setData(str);
    }
    public void init13(){
        String str=musicViewModel.current_music13().getValue().getMp3();
        setData(str);
    }
    public void init_dream(){
        String str=musicViewModel.getCurrent_music_dream().getValue().getMp3();
        setData(str);
    }
    public void init_sleep(){
        String str=musicViewModel.getCurrent_music_sleep().getValue().getMp3();
        setData(str);
    }
    public void init_scape(){
        String str=musicViewModel.getCurrent_music_scape().getValue().getMp3();
        setData(str);
    }
    public void init_3(){
        String str=musicViewModel.getCurrent_music3().getValue().getMp3();
        setData(str);
    }
    public void init_music(){
        String str=musicViewModel.getCurrent_music_music().getValue().getMp3();
        setData(str);
    }
    public void initLike(){
        String str=musicViewModel.getCurrent_like_music().getValue().getMp3();
        setData(str);
    }
    public void setData(String result) {
        url = Uri.parse(result);
        musicViewModel.setIsPlaying(true);
        play();
    }
    public void play()  {
        if(musicViewModel.getIsPlaying().getValue()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            musicViewModel.setIsPlaying(false);
        }
        try{
            mediaPlayer.setDataSource(this,url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(url!=null) {
            hide=false;
            mediaPlayer.start();
            musicViewModel.setIsPlaying(true);
            createNotification();
            mPlayerCallHelper.requestAudioFocus("Music","App");
            registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
            musicViewModel.setSeek(getDuration());
            thread_start();
        }
    }

    public void thread_start(){
        thread=new MusicService.Thread();
        thread.start();
    }

    public void setPrev(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSize();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSize()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }

    //다음 이전
    public void setNext11(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic11();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        else if(musicViewModel.getSizeMusic11()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }

    public void setPrev11(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic11();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeMusic11()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }

    public void next11()  {
        setNext11();
        musicViewModel.set_current_music_11(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init11();
    }
    public void prev11(){
        setPrev11();
        musicViewModel.set_current_music_11(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init11();
    }
    public void setNext12(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic12();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        else if(musicViewModel.getSizeMusic12()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }
    public void setPrev12(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic12();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeMusic12()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }

    public void next12()  {
        setNext12();
        musicViewModel.set_current_music_12(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init12();
    }
    public void prev12(){
        setPrev12();
        musicViewModel.set_current_music_12(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init12();
    }
    public void setNext13(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic13();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        else if(musicViewModel.getSizeMusic13()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }
    public void setPrev13(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeMusic13();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeMusic13()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }
    public void next13()  {
        setNext13();
        musicViewModel.set_current_music_13(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init13();
    }
    public void prev13(){
        setPrev13();
        musicViewModel.set_current_music_13(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init13();
    }
    public void setNextSleep(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeSleep();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }

        else if(musicViewModel.getSizeSleep()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }
    public void setPrevSleep(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeSleep();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeSleep()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }
    public void nextSleep()  {
        setNextSleep();
        musicViewModel.set_curreunt_music_dream(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init_sleep();
    }
    public void prevSleep(){
        setPrevSleep();
        musicViewModel.set_curreunt_music_dream(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init_sleep();
    }
    public void setNextDream(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeDream();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        else if(musicViewModel.getSizeDream()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }
    public void setPrevDream(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeDream();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeDream()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }
    public void nextDream()  {
        setNextDream();
        musicViewModel.set_curreunt_music_dream(musicViewModel.getPos());
        init_dream();
    }
    public void prevDream(){
        setPrevDream();
        musicViewModel.set_curreunt_music_dream(musicViewModel.getPos());
        init_dream();
    }
    public void setNextScape(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeScape();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        else if(musicViewModel.getSizeScape()-1<musicViewModel.getPos()+1){
            musicViewModel.setPos(0);
            return;
        }else {
            musicViewModel.setPos(musicViewModel.getPos() + 1);
            return;
        }
    }
    public void setPrevScape(){
        if(musicViewModel.getLoop()) {
            musicViewModel.setPos(musicViewModel.getPos());
            return;
        }
        if(musicViewModel.getRandom()){
            int size=musicViewModel.getSizeScape();
            int p=random.nextInt(size);
            musicViewModel.setPos(p);
            return;
        }
        if(musicViewModel.getPos()-1<0){
            musicViewModel.setPos(musicViewModel.getSizeScape()-1);
        }else {
            musicViewModel.setPos(musicViewModel.getPos() - 1);
        }
    }
    public void nextScape()  {
        setNextScape();
        musicViewModel.set_curreunt_music_scape(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init_scape();
    }
    public void prevScape(){
        setPrevScape();
        musicViewModel.set_curreunt_music_scape(musicViewModel.getPos());
        musicViewModel.set_current_music_all(musicViewModel.getPos());
        init_scape();
    }
    public void start(){
        musicViewModel.setIsPlaying(true);
        mPlayerCallHelper.requestAudioFocus("Music","App");
        mediaPlayer.start();
        createNotification();
        thread_start();
    }
    public void pause(){
        musicViewModel.setIsPlaying(false);
        mediaPlayer.pause();
        createNotification();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
    public int getDuration(){
        return mediaPlayer.getDuration();
    }
    public int current_position(){
        return mediaPlayer.getCurrentPosition();
    }
    public void set_seek(int time){
        mediaPlayer.seekTo(time);
    }
    public void setInitLike( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=true;
        initMusic=false;
    }
    public void setInit( ){
        this.init=true;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit11( ){
        this.init=false;
        init11=true;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit12( ){
        this.init=false;
        init11=false;
        init12=true;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit13( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=true;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit21( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=true;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit22( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=true;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit23( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=true;
        init3=false;
        initLike=false;
        initMusic=false;
    }
    public void setInit34( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=true;
        initLike=false;
        initMusic=false;
    }
    public void setInitMusic( ){
        this.init=false;
        init11=false;
        init12=false;
        init13=false;
        init2_1=false;
        init2_2=false;
        init2_3=false;
        init3=false;
        initLike=false;
        initMusic=true;
    }

    public Boolean getInit(){
        return this.init;
    }
    public Boolean getInit11(){
        return this.init11;
    }
    public Boolean getInit12(){
        return this.init12;
    }
    public Boolean getInit13(){
        return this.init13;
    }
    public Boolean getInit2_1(){
        return this.init2_1;
    }
    public Boolean getInit2_2(){
        return this.init2_2;
    }
    public Boolean getInit2_3(){
        return this.init2_3;
    }
    public Boolean getInit3(){return this.init3;}
    public Boolean getInitLike(){return this.initLike;}
    public Boolean getInitMusic(){return this.initMusic;}

    public void setHide(Boolean hide){
        this.hide=hide;
    }
    public Boolean getHide(){
        return hide;
    }

    private void createNotification() {
                try {
                    NotificationManager notificationManager=null;
                    RemoteViews expandedView;
                    expandedView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notify_player);

                    Intent intent = new Intent(getApplicationContext(), MusicActivity.class);
                    intent.setAction("showPlayer");
                    PendingIntent contentIntent = PendingIntent.getActivity(
                            this, 0, intent, 0);

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                         notificationManager = (NotificationManager)
                                getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationChannelGroup playGroup = new NotificationChannelGroup(GROUP_ID, getString(R.string.play));
                        notificationManager.createNotificationChannelGroup(playGroup);

                        NotificationChannel playChannel = new NotificationChannel(CHANNEL_ID,
                                getString(R.string.notify_of_play), NotificationManager.IMPORTANCE_DEFAULT);
                        playChannel.setGroup(GROUP_ID);
                        notificationManager.createNotificationChannel(playChannel);
                    }

                    Notification notification = new NotificationCompat.Builder(
                            getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_player)
                            .setContentIntent(contentIntent)
                            .setOnlyAlertOnce(true)
                            .setContentTitle(musicViewModel.current_music_all().getValue().getTitle()).build();

                    notification.bigContentView = expandedView;
                    setListeners(expandedView);

                    notification.bigContentView.setViewVisibility(R.id.player_next, View.VISIBLE);
                    notification.bigContentView.setViewVisibility(R.id.player_previous, View.VISIBLE);

                    boolean isPaused = MusicApplication.getInstance().getServiceInterface().isPlaying();

                    notification.bigContentView.setViewVisibility(R.id.player_pause, isPaused ? View.VISIBLE : View.GONE);
                    notification.bigContentView.setViewVisibility(R.id.player_play, isPaused ? View.GONE : View.VISIBLE);
                    notification.bigContentView.setTextViewText(R.id.player_song_name, musicViewModel.current_music_all().getValue().getTitle());
                    notification.flags |= Notification.FLAG_ONGOING_EVENT;
                    Picasso.get()
                            .load(musicViewModel.current_music_all().getValue().getImage())
                            .into(notification.bigContentView,R.id.player_album_art,1, notification);

//                    notificationManager.cancelAll();

                } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setListeners(RemoteViews view) {
        try {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent(NOTIFY_PREVIOUS).setPackage(getPackageName()),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.player_previous, pendingIntent);

//            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
//                    0, new Intent(NOTIFY_CLOSE).setPackage(getPackageName()),
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            view.setOnClickPendingIntent(R.id.player_close, pendingIntent);


            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent(NOTIFY_PAUSE).setPackage(getPackageName()),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.player_pause, pendingIntent);

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent(NOTIFY_NEXT).setPackage(getPackageName()),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.player_next, pendingIntent);

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                    0, new Intent(NOTIFY_PLAY).setPackage(getPackageName()),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.player_play, pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    final Handler handler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:

                    String music_time=Integer.toString(msg.arg1);
                    String all_music_time=Integer.toString(msg.arg2);

                    musicViewModel.setSave_time(msg.arg1);

                    if(musicViewModel.getTimer()) {
                        musicViewModel.setRem_Time(musicViewModel.getRem_time().getValue() - 1);

                        if(musicViewModel.getRem_time().getValue()==0) {
                            pause();
                        }
                    }
                    if(music_time.length()==3) {
                        music_time = music_time.substring(0, 1) + ":" + music_time.substring(1);
                    }else  if(music_time.length()==4) {
                        music_time = music_time.substring(0, 2) + ":" + music_time.substring(2);
                    }else  if(music_time.length()==5) {
                        music_time = music_time.substring(0, 1) + ":"+ music_time.substring(1, 3) + ":"  + music_time.substring(3);
                    }
                    else{
                        music_time="0:"+music_time.substring(0);
                    }

                    if(all_music_time.length()==3) {
                        all_music_time = all_music_time.substring(0, 1) + ":" + all_music_time.substring(1);
                    }else if(all_music_time.length()==4) {
                        all_music_time = all_music_time.substring(0, 2) + ":" + all_music_time.substring(2);
                    }else if(all_music_time.length()==5) {
                        all_music_time = all_music_time.substring(0, 1) + ":" +all_music_time.substring(1, 3) + ":"  + all_music_time.substring(3);
                    }
                    else{
                        all_music_time="0:"+all_music_time.substring(0);
                    }
                    String progress=music_time+"/"+all_music_time;
                    musicViewModel.setStart_progress(music_time);
                    musicViewModel.setEnd_progress(all_music_time);
                    musicViewModel.setProgress(progress);
                    break;
            }
        }
    };

    class Thread extends java.lang.Thread{
        @Override
        public void run(){
            super.run();
            while(musicViewModel.getIsPlaying().getValue()){

                int duration=getDuration();
                int All_Time = Integer.parseInt(SecondUtils.formateMilliSeccond(duration));

                int current_pos=current_position();
                int current_progress=Integer.parseInt(SecondUtils.formateMilliSeccond(current_pos));
                Message message=handler.obtainMessage();

                String msg=new String(":");
                message.obj=msg;

                message.arg1=current_progress;
                message.arg2=All_Time;

                message.what=0;

                try{
                    sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
                handler.sendMessage(message);
            }

        }
    }


    // 1. 멈춤하고 다음 재생을 하면 기존곡으로 설정됨
    // 2. 시간표시에 관한 문제
    // 3.
}