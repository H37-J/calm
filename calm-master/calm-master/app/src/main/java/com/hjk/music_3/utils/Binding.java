package com.hjk.music_3.utils;

import android.app.DownloadManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hjk.music_3.R;
import com.squareup.picasso.Picasso;


public class Binding {

    @androidx.databinding.BindingAdapter({"avatar"})
    public static void GlideImage(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions())
                .load(url)
                .placeholder(R.drawable.main_bg2)
                .into(imageView);
    }

    @BindingAdapter({"post"})
    public static void PicassoImage(ImageView imageView,String url){
        Picasso.get()
                .load(url)
                .into(imageView);
    }



}
