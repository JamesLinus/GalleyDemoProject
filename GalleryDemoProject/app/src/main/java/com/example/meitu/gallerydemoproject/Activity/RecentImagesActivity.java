package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import com.example.meitu.gallerydemoproject.Fragment.RecentImagesFragment;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;

public class RecentImagesActivity extends AppCompatActivity {


    private FragmentManager mFragmentManager;
    private RecentImagesFragment mRecentImagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recent_image_activity);

        mRecentImagesFragment = RecentImagesFragment.newInstance();

        AlbumOperatingUtils.getRecentImageMessage(RecentImagesActivity.this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.findFragmentById(R.id.frame_container);
        mFragmentManager.popBackStack();
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mRecentImagesFragment)
                .addToBackStack("RecnetImages")
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
