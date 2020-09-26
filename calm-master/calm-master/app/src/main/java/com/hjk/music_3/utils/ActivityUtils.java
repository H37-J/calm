package com.hjk.music_3.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ActivityUtils {

    public static void replaceFragmentInActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment);
        transaction.commit();
    }


}
