package com.hjk.music_3.ui.activity.profile;

import android.app.Activity;
import android.os.Bundle;

import com.hjk.music_3.R;

public class PopUpActivity extends Activity {


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_popup);
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
