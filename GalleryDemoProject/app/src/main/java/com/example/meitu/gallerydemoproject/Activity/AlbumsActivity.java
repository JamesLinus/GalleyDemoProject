package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.MediaStoreUtils;

import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = "GalleryListActivity.activity";

    MediaStoreUtils mMediaStoreUtils;
    Map<String,String> mGalleyNames;

    RecyclerView mRecyclerView;
    AlbumsAdapter mAlbumsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.albums_activity);
//        init();
    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_albums);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AlbumsActivity.this));

        mMediaStoreUtils = new MediaStoreUtils(AlbumsActivity.this);
        mGalleyNames = mMediaStoreUtils.getGalleryNameAndFirst();

        mAlbumsAdapter = new AlbumsAdapter(AlbumsActivity.this, mGalleyNames);

        mRecyclerView.setAdapter(mAlbumsAdapter);



    }


}
