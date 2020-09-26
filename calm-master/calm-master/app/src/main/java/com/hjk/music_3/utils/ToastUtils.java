package com.hjk.music_3.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

   public static void set(Context context, String message, int second){
       Toast.makeText(context,message,second).show();
   }

}
