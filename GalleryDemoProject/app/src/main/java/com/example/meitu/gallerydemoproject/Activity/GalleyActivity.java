package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.example.meitu.gallerydemoproject.Fragment.GalleyFragment;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/11.
 */

public class GalleyActivity extends AppCompatActivity {
    private static final String TAG = "GalleyActivity.activity";

    private String mAlbumName;

    private FragmentManager mFragmentManager;
    private GalleyFragment mGalleyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        String mAlbumNameKey = getString(R.string.album_name);
        mAlbumName = getIntent().getStringExtra(mAlbumNameKey);

        mGalleyFragment = GalleyFragment.newInstance(mAlbumName);


        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.findFragmentById(R.id.frame_container);
        mFragmentManager.popBackStack();
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mGalleyFragment)
                .addToBackStack("Images")
                .commit();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                finish();
                break;
            }
            default:{
                break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
