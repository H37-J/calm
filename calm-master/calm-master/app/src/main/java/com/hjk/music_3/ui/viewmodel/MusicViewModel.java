package com.hjk.music_3.ui.viewmodel;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjk.music_3.Service.MusicApplication;
import com.hjk.music_3.data.local.model.Music;
import com.hjk.music_3.data.remote.MusicRepository;

import java.util.List;

public class MusicViewModel extends AndroidViewModel {
    private static MusicRepository musicRepository=MusicRepository.getInstance();

    private LiveData<List<Music>> AllMusic; //로컬
    private static MutableLiveData<List<Music>> music; //레트로핏
    private static MutableLiveData<List<Music>> music_sleep; //슬립
    private static MutableLiveData<List<Music>> music_dream;
    private static MutableLiveData<List<Music>> music_scape;
    private static MutableLiveData<List<Music>> music_11;
    private static MutableLiveData<List<Music>> music_12;
    private static MutableLiveData<List<Music>> music_13;
    private static MutableLiveData<List<Music>> music_3;
    private static MutableLiveData<List<Music>> music_music;
    private static MutableLiveData<List<Music>> like_music=new MutableLiveData<List<Music>>();

    private static MutableLiveData<Music> current_music=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_dream=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_scape=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_sleep=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_11=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_12=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_13=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_all=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_like_music=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music3=new MutableLiveData<Music>();
    private static MutableLiveData<Music> current_music_music=new MutableLiveData<Music>();

    public static int time=0;
    public static int pos=1;
    public static int cate_id;

    public static MutableLiveData<Integer> rem_time=new MutableLiveData<Integer>();
    public static MutableLiveData<Integer> Seek=new MutableLiveData<Integer>();
    public static MutableLiveData<Boolean> isPlaying=new MutableLiveData<Boolean>();

    public static boolean timer=false;
    public static boolean loop=false;
    public static boolean random=false;

    public static int save_time;

    public static MutableLiveData<String> Progress=new MutableLiveData<String>();
    public static MutableLiveData<String> start_progress=new MutableLiveData<String>();
    public static MutableLiveData<String> end_progress=new MutableLiveData<String>();

    public MusicViewModel(Application application){
        super(application);
    }

   public void init() {
       if (music != null)
           return;
       music = musicRepository.getMusic();
       music_sleep = musicRepository.getMusicSleep();
       music_dream = musicRepository.getMusicDream();
       music_scape=musicRepository.getMusicScape();

       music_11=musicRepository.getMusic11();
       music_12=musicRepository.getMusic12();
       music_13=musicRepository.getMusic13();
   }

   public void setSave_time(int save_time){
        this.save_time=save_time;
    }
    public void setLike_music(List<Music> m){ like_music.setValue(m); }
    public void setCurrent_like_music(int pos){ current_like_music.setValue(getLike_music().getValue().get(pos)); }

    public static int getSave_time(){
        return save_time;
    }
    public MutableLiveData<List<Music>> getLike_music(){
        return like_music;
    }
    public MutableLiveData<Music> getCurrent_like_music(){
        return current_like_music;
    }

    public int getSize(){ return music.getValue().size();}
    public int getSizeMusic11(){return music_11.getValue().size();}
    public int getSizeMusic12(){return music_12.getValue().size();}
    public int getSizeMusic13(){return music_13.getValue().size();}
    public int getSizeDream(){return music_dream.getValue().size();}
    public int getSizeSleep(){return music_sleep.getValue().size();}
    public int getSizeScape(){return music_scape.getValue().size();}
    //다음곡 이전곡이 있을 때 사이즈 계산

    public MutableLiveData<List<Music>> getMusic(){
        return music;
    }
    public MutableLiveData<List<Music>> getMusic_11(){
        return music_11;
    }
    public MutableLiveData<List<Music>> getMusic_12(){
        return music_12;
    }
    public MutableLiveData<List<Music>> getMusic_13(){
        return music_13;
    }
    public MutableLiveData<List<Music>> getMusic_sleep(){return music_sleep; }
    public MutableLiveData<List<Music>> getMusic_dream(){return music_dream;}
    public MutableLiveData<List<Music>> getMusic_scape(){return music_scape;}
    public MutableLiveData<List<Music>> getMusic_3(){
        music_3=musicRepository.music_list_mind(getCate_id());
        return music_3;
    }
    public MutableLiveData<List<Music>> getMusic_music(){
        music_music=musicRepository.music_list_music(getCate_id());
        return music_music;
    }

    public MutableLiveData<List<Music>> getMusic_32(){
        return music_3;
    }
    public MutableLiveData<List<Music>> getMusicMusic(){
        return music_music;
    }

    //레트로핏 데이터 리스트들
    //method
    public static MutableLiveData<Music> current_music(){
        return current_music;
    }
    public static MutableLiveData<Music> current_music11(){
        return current_music_11;
    }
    public static MutableLiveData<Music> current_music12(){
        return current_music_12;
    }
    public static MutableLiveData<Music> current_music13(){
        return current_music_13;
    }
    public static MutableLiveData<Music> current_music_all(){
        return current_music_all;
    }
    public static MutableLiveData<Music> getCurrent_music_dream(){return current_music_dream;}
    public static MutableLiveData<Music> getCurrent_music_sleep(){return current_music_sleep;}
    public static MutableLiveData<Music> getCurrent_music_scape(){return current_music_scape;}
    public static MutableLiveData<Music> getCurrent_music3(){return current_music3;}
    public static MutableLiveData<Music> getCurrent_music_music(){return current_music_music;}
    public static MutableLiveData<Music> getCurrent_music_all(){return current_music_all;}


    public void set_current_music_3(int pos){

        current_music3.setValue(getMusic_32().getValue().get(pos));
    }

    public void set_current_music_music(int pos){
        if(pos==9999){
            Music music=new Music();
            music.setTitle("");
            music.setImage("https://nowglobalhealing.com/content/uploads/2020/09/photo-1557456170-0cf4f4d0d362.jpeg");
            current_music_music.setValue(music);
        }
        else {
            current_music_music.setValue(getMusicMusic().getValue().get(pos));
        }
    }

    public void set_current_music(int pos){
        if(pos==9999){
            Music music=new Music();
            music.setTitle("");
            music.setImage("https://nowglobalhealing.com/content/uploads/2020/09/photo-1557456170-0cf4f4d0d362.jpeg");
            current_music().setValue(music);
        }
        else {
            current_music_music.setValue(getMusic().getValue().get(pos));
        }
    }
    public void set_current_music_11(int pos){
        current_music_11.setValue(getMusic_11().getValue().get(pos));
    }
    public void set_current_music_12(int pos){
        current_music_12.setValue(getMusic_12().getValue().get(pos));
    }
    public void set_current_music_13(int pos){
        current_music_13.setValue(getMusic_13().getValue().get(pos));
    }
    public void set_curreunt_music_dream(int pos){
        current_music_dream.setValue(getMusic_dream().getValue().get(pos));
    }
    public void set_curreunt_music_sleep(int pos){
        current_music_sleep.setValue(getMusic_sleep().getValue().get(pos));
    }
    public void set_curreunt_music_scape(int pos){
        current_music_scape.setValue(getMusic_scape().getValue().get(pos));
    }

    public void set_current_music_all(int pos){

        if(pos==9999){
            Music music=new Music();
            music.setTitle("");
            music.setImage("https://nowglobalhealing.com/content/uploads/2020/09/photo-1557456170-0cf4f4d0d362.jpeg");
            current_music_all.setValue(music);
            return;
        }

        if(MusicApplication.getInstance().getServiceInterface().getInit11())
            current_music_all.setValue(getMusic_11().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit12())
            current_music_all.setValue(getMusic_12().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit13())
            current_music_all.setValue(getMusic_13().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit2_1())
            current_music_all.setValue(getMusic_sleep().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit2_2())
            current_music_all.setValue(getMusic_dream().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit2_3())
            current_music_all.setValue(getMusic_scape().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInit3())
            current_music_all.setValue(getMusic_32().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInitMusic())
            current_music_all.setValue(getMusicMusic().getValue().get(pos));

        if(MusicApplication.getInstance().getServiceInterface().getInitLike())
            current_music_all.setValue(getLike_music().getValue().get(pos));
    }

    public void setPos(int pos){ this.pos= pos; }
    public void setIsPlaying(boolean isPlaying){this.isPlaying.setValue(isPlaying);}
    public void setProgress(String progress){ this.Progress.setValue(progress); }
    public void setStart_progress(String start_progress){ this.start_progress.setValue(start_progress); }
    public void setEnd_progress(String end_progress){ this.end_progress.setValue(end_progress); }
    public void setTime(int time){ this.time=time; }
    public void setRem_Time(int rem_time){ this.rem_time.setValue(rem_time); }
    public void setSeek(int seek){ this.Seek.setValue(seek); }
    public void setLoop(Boolean loop){ this.loop=loop; }
    public void setRandom(Boolean random){ this.random=random; }
    public void setTimer(Boolean timer){ this.timer=timer; }
    public void setCate_id(int cate_id){
        this.cate_id=cate_id;
    }

    public int getCate_id(){
        return cate_id;
    }
    public int getPos(){ return this.pos; }
    public static MutableLiveData<Boolean> getIsPlaying(){return isPlaying;}
    public MutableLiveData<String> getProgress(){ return this.Progress; }
    public MutableLiveData<String> getStart_progress(){ return this.start_progress; }
    public MutableLiveData<String> getEnd_progress(){ return this.end_progress; }
    public int getTime(){ return time; }
    public MutableLiveData<Integer> getRem_time(){ return this.rem_time; }
    public MutableLiveData<Integer> getSeek(){ return this.Seek; }
    public Boolean getLoop(){ return loop; }
    public Boolean getRandom(){ return random; }
    public Boolean getTimer(){ return timer; }



}