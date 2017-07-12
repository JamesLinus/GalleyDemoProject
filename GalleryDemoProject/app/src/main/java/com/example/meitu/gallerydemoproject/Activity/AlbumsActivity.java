package com.example.meitu.gallerydemoproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.MediaStoreUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = "AlbumsActivity.activity";

    private MediaStoreUtils mMediaStoreUtils;
    private Map<String,List<String>> mListAlbums;
    private Map<String, Integer> mListImagesNum;

    private RecyclerView mRvAlbums;
    private AlbumsAdapter mAdapterAlbums;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albums_activity);

    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        mRvAlbums = (RecyclerView)findViewById(R.id.rv_albums);
        mRvAlbums.setLayoutManager(new LinearLayoutManager(AlbumsActivity.this));

        mMediaStoreUtils = new MediaStoreUtils(AlbumsActivity.this);
        mListAlbums = mMediaStoreUtils.getGalleryNameAndCover();

        mListImagesNum = new HashMap<>();

        for (String s : mListAlbums.keySet()){
            mListImagesNum.put(s, mListAlbums.get(s).size());
        }


        mAdapterAlbums = new AlbumsAdapter(AlbumsActivity.this, mListAlbums, mListImagesNum);
        mRvAlbums.setAdapter(mAdapterAlbums);

    }


}
