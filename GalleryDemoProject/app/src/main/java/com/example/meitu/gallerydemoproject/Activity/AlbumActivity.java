package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.meitu.gallerydemoproject.Fragment.AlbumFragment;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/11.
 */

public class AlbumActivity extends AppCompatActivity {
    private static final String TAG = "GalleyActivity.activity";
    private static final String FRAGMENT_TAG = "ALBUM";

    private String mAlbumName;

    private FragmentManager mFragmentManager;
    private AlbumFragment mGalleyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);

        String albumNameKey = getString(R.string.album_name_key);
        mAlbumName = getIntent().getStringExtra(albumNameKey);
        mGalleyFragment = AlbumFragment.newInstance(mAlbumName);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.findFragmentById(R.id.frame_container);

        if (null != mFragmentManager.findFragmentByTag(FRAGMENT_TAG)){
            mFragmentManager.beginTransaction()
                    .show(mGalleyFragment)
                    .commitAllowingStateLoss();

        }else {
            mFragmentManager.beginTransaction()
                    .add(R.id.frame_container, mGalleyFragment)
                    .addToBackStack(FRAGMENT_TAG)
                    .commitAllowingStateLoss();
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            }
            default:{
                break;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
