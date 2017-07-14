package com.example.meitu.gallerydemoproject.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by meitu on 2017/7/13.
 */

public class LoadImageUtil {
    private static ImageLoader mImageLoader;
    private static DisplayImageOptions options;

    static {
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
    }

    public static void loadImage(Context context, ImageView imageView, String uri){
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(context));
        }
        mImageLoader.displayImage("file://" + uri, imageView, options);
    }
}
