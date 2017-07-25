package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.meitu.gallerydemoproject.Fragment.ImageFragment;
import com.example.meitu.gallerydemoproject.R;

import java.util.List;

/**
 * Created by meitu on 2017/7/13.
 */

public class ImagePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<String> mImageUris;

    /**外部选择的图片的uri 用来与ImageUris中一一对比，找出当前的位置 */
    private String currentImageUri;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_pager_activity);

        String imagesListKey = getString(R.string.images_list_key);
        String mAlbumName = getString(R.string.image_uri_key);

        mImageUris = getIntent().getStringArrayListExtra(imagesListKey);
        currentImageUri = getIntent().getStringExtra(mAlbumName);

        init();
        setCurrentItem();
    }


    /**
     * 初始化ViewPager
     */
    private void init(){
        mViewPager = (ViewPager)findViewById(R.id.images_pager_view);

        mFragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                String uri = mImageUris.get(position);
                return ImageFragment.newInstance(uri);
            }

            @Override
            public int getCount() {
                return mImageUris.size();
            }
        });
    }

    private void setCurrentItem(){
        /**找出当前位置 */
        for (int i = 0 ; i < mImageUris.size() ; i++){
            if (mImageUris.get(i).equals(currentImageUri)){
                mViewPager.setCurrentItem(i);
                break;
            }
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
