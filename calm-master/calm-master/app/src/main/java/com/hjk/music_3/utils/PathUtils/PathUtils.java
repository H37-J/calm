package com.hjk.music_3.utils.PathUtils;

import android.os.Environment;

import java.io.File;

public class PathUtils {

    private static final char SEP = File.separatorChar;

    private PathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static String getAbsolutePath(final File file){
        if(file==null) return "";
        return file.getAbsolutePath();
    }

    public static String getExternalStoragePath(){
        if(!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) return "";
        return getAbsolutePath(Environment.getExternalStorageDirectory());
    }
}
