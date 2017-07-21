package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsListActivity extends AppCompatActivity {

    private static final String TAG = "AlbumsActivity.activity";

    /**键值对保存 相册名和相册信息 */
    private Map<String, AlbumMessage> mMapAlbumsMessage;

    private RecyclerView mRvAlbums;
    private AlbumsAdapter mAdapterAlbums;

    private CustomToolBar mCustomToolBar;


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


    /**
     * 初始化列表数据
     */
    private void init(){
        mCustomToolBar = (CustomToolBar)findViewById(R.id.ctb_albums);
        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });

        mRvAlbums = (RecyclerView)findViewById(R.id.rv_albums);
        mRvAlbums.setLayoutManager(new LinearLayoutManager(AlbumsListActivity.this));

        mMapAlbumsMessage = AlbumOperatingUtils.getAlbumsMessage(AlbumsListActivity.this);
        List<AlbumMessage> albumMessages = new ArrayList<>(mMapAlbumsMessage.values());

        mAdapterAlbums = new AlbumsAdapter(AlbumsListActivity.this, albumMessages);
        mRvAlbums.setAdapter(mAdapterAlbums);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:{
                finishActivity();
                break;
            }
            default:{
                break;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    private void finishActivity(){
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
