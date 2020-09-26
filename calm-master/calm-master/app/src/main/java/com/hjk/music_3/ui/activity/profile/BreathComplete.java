package com.hjk.music_3.ui.activity.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.hjk.music_3.BuildConfig;
import com.hjk.music_3.R;
import com.hjk.music_3.databinding.ActivityBrcomBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.squareup.picasso.Target;
import com.squareup.picasso.Picasso;
public class BreathComplete extends AppCompatActivity {

    ActivityBrcomBinding binding;
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_brcom);
        binding.setActivity(this);
        binding.back.setBlurredImg(getResources().getDrawable(R.drawable.i12));
        binding.back.setBlurredLevel(100);
        requestReadExternalStoragePermission();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(this,BreathActivity.class);
        startActivity(intent);
    }

    public void share() {
          Picasso.get().load("https://taegon.kim/wp-content/uploads/2018/05/image-5.png").into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                Intent chooser = Intent.createChooser(i, "Share File");
                startActivity(chooser);
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID+".fileprovider",file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
            }
        }
    }
}
