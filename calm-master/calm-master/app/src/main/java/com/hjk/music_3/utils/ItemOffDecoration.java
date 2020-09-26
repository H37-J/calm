package com.hjk.music_3.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemOffDecoration extends RecyclerView.ItemDecoration {

    private int mItemOffset;

    public ItemOffDecoration(int itemOffset) {mItemOffset=itemOffset;}

    public ItemOffDecoration(@NonNull Context context, @DimenRes int itemOffsetId){
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state){
            super.getItemOffsets(outRect,view,parent,state);
            outRect.set(mItemOffset,mItemOffset,mItemOffset,mItemOffset);
    }
}
