package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsActivity extends AppCompatActivity {

    private static final String TAG = "AlbumsActivity.activity";

    private AlbumsMessageUtils mAlbumsMessageUtils;

    /**键值对保存 相册名和相册信息 */
    private Map<String, AlbumMessage> mMapAlbumsMessage;

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
        mMapAlbumsMessage = mAlbumsMessageUtils.getGalleryNameAndCover();
        List<AlbumMessage> albumMessages = new ArrayList<>(mMapAlbumsMessage.values());

        for (AlbumMessage s: albumMessages){
            Log.d(TAG, s.getAblumName());
        }


        mAdapterAlbums = new AlbumsAdapter(AlbumsActivity.this, albumMessages);
        mRvAlbums.setAdapter(mAdapterAlbums);

    }


}
