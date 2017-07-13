package com.example.meitu.gallerydemoproject.Activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.Beans.ImageMessage;
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

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);

        String imageMessageKey = getString(R.string.image_message_key);
        imageURI = getIntent().getStringExtra(imageMessageKey);

        init();

    }

    private void init(){
        mImageView = (ImageView)findViewById(R.id.iv_big_image);

        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(ImageActivity.this));
        }
        mImageLoader.displayImage("file://" + imageURI, mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.item_image_message:{
                Intent intent = new Intent(ImageActivity.this, ImageMessageActivity.class);
                String imageMessageKey = getString(R.string.image_message_key);
                intent.putExtra(imageMessageKey, imageURI);
                startActivity(intent);
                break;
            }
            default:{
                break;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
