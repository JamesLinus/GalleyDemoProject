package com.example.meitu.gallerydemoproject.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by meitu on 2017/7/12.
 */

public class ImageActivity extends AppCompatActivity {

    private String imageURI;

    private ImageLoader mImageLoader;

    private ImageView mImageView;
    private Drawable mDrawable;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        String imageMessageKey = getString(R.string.image_message_key);
        imageURI = getIntent().getStringExtra(imageMessageKey);
//        mBundle = getIntent().getBundleExtra("image");
//        mDrawable = (Drawable) mBundle.get("image");

        init();

    }

    private void init(){
        mImageView = (ImageView)findViewById(R.id.iv_big_image);

        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(ImageActivity.this));
        }
        mImageLoader.displayImage("file://" + imageURI, mImageView);

//        mImageView.setImageDrawable(mDrawable);
    }
}
