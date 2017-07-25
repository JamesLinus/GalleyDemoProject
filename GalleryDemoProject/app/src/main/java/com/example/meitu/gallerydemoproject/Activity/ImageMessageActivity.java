package com.example.meitu.gallerydemoproject.Activity;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Beans.ImageMessage;
import com.example.meitu.gallerydemoproject.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumMessageUtils;

/**
 * Created by meitu on 2017/7/12.
 */

public class ImageMessageActivity extends AppCompatActivity {

    private String imageUri;

    private ContentResolver contentResolver;

    private ImageMessage mImageMessage;

    private TextView mTvImageMessageId;
    private TextView mTvImageMessageName;
    private TextView mTvImageMessageFile;
    private TextView mTvImageMessagePath;
    private TextView mTvImageMessageDate;

    private CustomToolBar mCustomToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_message_activity);

        String imageMessageKey = getString(R.string.image_message_key);
        imageUri = getIntent().getStringExtra(imageMessageKey);
        contentResolver = getContentResolver();

        init();
    }

    private void init(){

        mImageMessage = AlbumMessageUtils.getImageMessage(contentResolver, imageUri);

        mTvImageMessageId = (TextView)findViewById(R.id.tv_image_id);
        mTvImageMessageName = (TextView)findViewById(R.id.tv_image_name);
        mTvImageMessageFile = (TextView)findViewById(R.id.tv_image_file);
        mTvImageMessagePath = (TextView)findViewById(R.id.tv_image_path);
        mTvImageMessageDate = (TextView)findViewById(R.id.tv_image_date);

        mTvImageMessageId.setText(mImageMessage.getId());
        mTvImageMessageName.setText(mImageMessage.getImageName());
        mTvImageMessageFile.setText(mImageMessage.getAlbum());
        mTvImageMessagePath.setText(mImageMessage.getPath());
        mTvImageMessageDate.setText(mImageMessage.getDate().toString());

        mCustomToolBar = (CustomToolBar) findViewById(R.id.ctb_message);
        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
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
}
