package com.hjk.music_3.ui.activity.profile;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityBreathBinding;
import com.hjk.music_3.ui.viewmodel.BreathViewModel;

public class BreathActivity extends AppCompatActivity {

    ActivityBreathBinding binding;
    BreathViewModel breathViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        breathViewModel=ViewModelProviders.of(this).get(BreathViewModel.class);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_breath);
        binding.setActivity(this);
        binding.Minute.setMinValue(1);
        binding.Minute.setMaxValue(60);
        binding.Minute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setDividerColor(binding.Minute,android.R.color.white);
        binding.Minute.setWrapSelectorWheel(false);
        binding.Minute.setValue(1);
        breathViewModel.setTime(1*60);
        binding.Minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                    breathViewModel.setTime(newVal*60);
                    System.out.println("new:"+newVal);
            }
        });
    }

    private void setDividerColor(NumberPicker picker,int color){
        java.lang.reflect.Field[] pickerFields=NumberPicker.class.getDeclaredFields();
        for(java.lang.reflect.Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivider")){
                pf.setAccessible(true);
                try{
                    ColorDrawable colorDrawable=new ColorDrawable(color);
                    pf.set(picker,colorDrawable);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void Intent_start(){
        breathViewModel.setStart(true);
        Intent intent=new Intent(this,BreathPlayActivity.class);
        startActivity(intent);

    }

}
