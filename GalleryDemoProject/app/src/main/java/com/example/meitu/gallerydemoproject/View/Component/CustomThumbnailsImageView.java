package com.example.meitu.gallerydemoproject.View.Component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by meitu on 2017/7/25.
 */

public class CustomThumbnailsImageView extends ImageView {
    private Context mContext;

    public CustomThumbnailsImageView(Context context) {
        this(context, null);
    }

    public CustomThumbnailsImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomThumbnailsImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void setImage(String uri){
        ImageLoader mImageLoader;
        DisplayImageOptions options;
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }
        mImageLoader.displayImage("file://" + uri, this, options);
    }
}
