package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.meitu.gallerydemoproject.Fragment.AlbumFragment;
import com.example.meitu.gallerydemoproject.Fragment.AlbumListFragment;
import com.example.meitu.gallerydemoproject.Fragment.RecentImagesFragment;
import com.example.meitu.gallerydemoproject.R;

import static android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE;

/**
 * Created by meitu on 2017/7/21.
 */

public class GellayListActivity extends AppCompatActivity
        implements AlbumFragment.CallBack,
                    AlbumListFragment.CallBack,
                    RecentImagesFragment.CallBack {

    private static final String ALBUM_TAG = "ALBUM";
    private static final String ALBUMS_LIST_TAG = "ALBUMS_LIST";
    private static final String RECENT_IMAGES_FRAGMENT_TAG = "RECENT_IMAGES";

    private FragmentManager mFramentManager;

    private AlbumFragment mAlbumFragment;
    private AlbumListFragment mAlbumListFragment;
    private RecentImagesFragment mRecentImagesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);

        showRecentImagesFragment();

    }

    @Override
    public void showRecentImagesFragment() {
        mFramentManager = getSupportFragmentManager();

        if (null != mFramentManager.findFragmentByTag(RECENT_IMAGES_FRAGMENT_TAG)){
            mRecentImagesFragment = (RecentImagesFragment) mFramentManager.findFragmentByTag(RECENT_IMAGES_FRAGMENT_TAG);
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .show(mRecentImagesFragment)
                    .commitAllowingStateLoss();
        }else {
            mRecentImagesFragment = RecentImagesFragment.newInstance();
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .add(R.id.fragment_list_container, mRecentImagesFragment, RECENT_IMAGES_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void showAlbumFragment(String albumName) {
        mFramentManager = getSupportFragmentManager();

        if (null != mFramentManager.findFragmentByTag(ALBUM_TAG)){
            mAlbumFragment = (AlbumFragment) mFramentManager.findFragmentByTag(ALBUM_TAG);
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .show(mAlbumFragment)
                    .commitAllowingStateLoss();
        }else {
            mAlbumFragment = AlbumFragment.newInstance(albumName);
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .add(R.id.fragment_list_container, mAlbumFragment, ALBUM_TAG)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }

    }

    @Override
    public void showAlbumsListFragment() {
        mFramentManager = getSupportFragmentManager();

        if (null != mFramentManager.findFragmentByTag(ALBUMS_LIST_TAG)){
            mAlbumListFragment = (AlbumListFragment) mFramentManager.findFragmentByTag(ALBUMS_LIST_TAG);
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .show(mAlbumListFragment)
                    .commitAllowingStateLoss();
        }else {
            mAlbumListFragment = AlbumListFragment.newInstance();
            mFramentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_FADE)
                    .add(R.id.fragment_list_container, mAlbumListFragment, ALBUMS_LIST_TAG)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                if (1 == getSupportFragmentManager().getBackStackEntryCount()){
                    finish();
                }else {
                    getSupportFragmentManager().popBackStack();
                }
                break;
            }default:{
                break;
            }
        }
        return true;
    }
}
