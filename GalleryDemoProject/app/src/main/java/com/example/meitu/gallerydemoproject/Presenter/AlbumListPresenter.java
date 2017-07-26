package com.example.meitu.gallerydemoproject.Presenter;


import android.content.ContentResolver;
import android.content.Context;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.Model.AlbumsMessageModel;
import com.example.meitu.gallerydemoproject.Model.Impl.IAlbumsMessageModel;
import com.example.meitu.gallerydemoproject.Model.bean.AlbumMessage;
import com.example.meitu.gallerydemoproject.View.View.IAlbumListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/26.
 */

public class AlbumListPresenter {
    private IAlbumListView mAlbumListView;
    private IAlbumsMessageModel mIAlbumsMessageModel;

    private Map<String, AlbumMessage> mMapAlbumsMessage;


    public AlbumListPresenter(IAlbumListView view){
        mAlbumListView = view;
        mIAlbumsMessageModel = AlbumsMessageModel.getInstance();
    }

    public void loadData(Context context, ContentResolver contentResolver){
        mMapAlbumsMessage = mIAlbumsMessageModel.getAlbumsMessage(contentResolver);
        List<AlbumMessage> albumMessages = new ArrayList<>(mMapAlbumsMessage.values());

        AlbumsAdapter adapterAlbums = new AlbumsAdapter(context, albumMessages);
        mAlbumListView.setRecyclerViewData(adapterAlbums);
    }
}
