package com.hjk.music_3.utils;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
public class TimeUtils {



   public static String getDay() {
       SimpleDateFormat format = new SimpleDateFormat("MM월 dd일");

       return format.format(System.currentTimeMillis());
   }

   public static String getDayCom(){
       SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");

       return format.format(System.currentTimeMillis());
   }

}
