package com.example.meitu.gallerydemoproject.Presenter;

import android.content.ContentResolver;
import android.content.Context;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.Model.AlbumImagesPathModel;
import com.example.meitu.gallerydemoproject.View.View.IAlbumView;

import java.util.List;

/**
 * Created by meitu on 2017/7/26.
 */

public class AlbumPresenter {
    private IAlbumView mAlbumView;
    private AlbumImagesPathModel mAlbumImagesPathModel;

    private List<String> mListURI;

    public AlbumPresenter(IAlbumView view){
        mAlbumView = view;
        mAlbumImagesPathModel = AlbumImagesPathModel.getInstance();
    }

    public void loadData(Context context, String albumName, ContentResolver contentResolver){
        mListURI = mAlbumImagesPathModel.getAlbumImagesPath(contentResolver, albumName);
        ImagesAdapter mAdapterImages = new ImagesAdapter(context, mListURI);
        mAlbumView.setRecyclerViewData(mAdapterImages);
    }

    public void setTitle(String title){
        mAlbumView.setTitle(title);
    }
}
