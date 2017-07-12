package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = "AlbumsActivity.activity";

    private AlbumsMessageUtils mAlbumsMessageUtils;
    private Map<String, AlbumMessage> mMapAlbumsWithImages;

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

        mAlbumsMessageUtils = new AlbumsMessageUtils(AlbumsActivity.this);
        mMapAlbumsWithImages = mAlbumsMessageUtils.getGalleryNameAndCover();


        mAdapterAlbums = new AlbumsAdapter(AlbumsActivity.this, mMapAlbumsWithImages);
        mRvAlbums.setAdapter(mAdapterAlbums);

    }


}
