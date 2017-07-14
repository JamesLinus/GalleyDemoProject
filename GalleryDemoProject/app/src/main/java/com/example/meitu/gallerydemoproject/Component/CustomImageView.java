package com.example.meitu.gallerydemoproject.Component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView {

    private int widthSize;
    private int widthMode;
    private int heightSize;
    private int heithtMode;

    private Drawable mContentDrawable;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        widthMode = MeasureSpec.getMode(widthMeasureSpec);

        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heithtMode = MeasureSpec.getMode(heightMeasureSpec);
    }

}
