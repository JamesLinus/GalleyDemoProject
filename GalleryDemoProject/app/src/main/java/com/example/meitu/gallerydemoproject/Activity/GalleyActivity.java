package com.example.meitu.gallerydemoproject.Activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.Fragment.GalleyFragment;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class GalleyActivity extends AppCompatActivity {
    private static final String TAG = "GalleyActivity.activity";

    private String mAlbumNameKey;
    private String mAlbumName;

    private FragmentManager mFragmentManager;
    private GalleyFragment mGalleyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        mAlbumNameKey = getString(R.string.album_name);
        mAlbumName = getIntent().getStringExtra(mAlbumNameKey);

        mGalleyFragment = GalleyFragment.newInstance(mAlbumName);


        mFragmentManager = getFragmentManager();
        mFragmentManager.findFragmentById(R.id.frame_container);
        mFragmentManager.popBackStack();
        mFragmentManager.beginTransaction()
                .add(R.id.frame_container, mGalleyFragment)
                .addToBackStack("Images")
                .commit();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }


}
