package com.example.meitu.gallerydemoproject.View.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Presenter.ImageMessagePresenter;
import com.example.meitu.gallerydemoproject.View.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.View.View.IImageMessageView;

/**
 * Created by meitu on 2017/7/12.
 */

public class ImageMessageActivity extends AppCompatActivity implements IImageMessageView{

    private static final String IMAGE_MESSAGE_KEY = "image_message";

    private String imageUri;

    private ContentResolver contentResolver;

    private TextView mTvImageMessageId;
    private TextView mTvImageMessageName;
    private TextView mTvImageMessageFile;
    private TextView mTvImageMessagePath;
    private TextView mTvImageMessageDate;
    private CustomToolBar mCustomToolBar;

    private ImageMessagePresenter mImageMessagePresenter;

    public static Intent newInstance(Context context, String imageMessageKey){
        Intent intentImageMessageActivity = new Intent(context, ImageMessageActivity.class);
        intentImageMessageActivity.putExtra(IMAGE_MESSAGE_KEY, imageMessageKey);
        return intentImageMessageActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_message_activity);

        findWidget();

        imageUri = getIntent().getStringExtra(IMAGE_MESSAGE_KEY);
        contentResolver = getContentResolver();

        mImageMessagePresenter = new ImageMessagePresenter(this);
        mImageMessagePresenter.loadData(this, imageUri, contentResolver);

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    public void setImageId(String id) {
        mTvImageMessageId.setText(id);
    }

    @Override
    public void setImageName(String imageName) {
        mTvImageMessageName.setText(imageName);
    }

    @Override
    public void setImageFile(String file) {
        mTvImageMessageFile.setText(file);
    }

    @Override
    public void setImagePath(String path) {
        mTvImageMessagePath.setText(path);

    }

    @Override
    public void setCreateDate(String date) {
        mTvImageMessageDate.setText(date);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                finishActivity();
                break;
            }
            default:{
                break;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    private void finishActivity(){
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    private void findWidget(){
        mTvImageMessageId = (TextView)findViewById(R.id.tv_image_id);
        mTvImageMessageName = (TextView)findViewById(R.id.tv_image_name);
        mTvImageMessageFile = (TextView)findViewById(R.id.tv_image_file);
        mTvImageMessagePath = (TextView)findViewById(R.id.tv_image_path);
        mTvImageMessageDate = (TextView)findViewById(R.id.tv_image_date);

        mCustomToolBar = (CustomToolBar) findViewById(R.id.ctb_message);
    }
}
