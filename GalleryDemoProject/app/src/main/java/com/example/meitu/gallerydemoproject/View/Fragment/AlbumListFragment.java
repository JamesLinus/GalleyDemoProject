package com.example.meitu.gallerydemoproject.View.Fragment;


import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Presenter.AlbumListPresenter;
import com.example.meitu.gallerydemoproject.View.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.View.View.IAlbumListView;

/**
 * Created by meitu on 2017/7/21.
 */

public class AlbumListFragment extends Fragment implements IAlbumListView{

    private static final String TAG = "AlbumsActivity.activity";


    private View mView;

    private RecyclerView mRvAlbums;

    private CustomToolBar mCustomToolBar;

    private ContentResolver contentResolver;
    private AlbumListContentObserver mAlbumListContentObserver;

    private AlbumListPresenter mAlbumListPresenter;

    public interface CallBack {
        void showAlbumsListFragment();
    }

    public static AlbumListFragment newInstance(){
        AlbumListFragment albumListFragment = new AlbumListFragment();
        return albumListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        contentResolver = getActivity().getContentResolver();
        mAlbumListContentObserver = new AlbumListContentObserver(new Handler());

        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mAlbumListContentObserver);

        mAlbumListPresenter = new AlbumListPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        mView = layoutInflater.inflate(R.layout.albums_list_fragment, container, false);

        findWidget();
        initRecyclerView();

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });

        mAlbumListPresenter.loadData(getActivity(), contentResolver);
        return mView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        contentResolver.unregisterContentObserver(mAlbumListContentObserver);
    }

    @Override
    public void setRecyclerViewData(RecyclerView.Adapter adapter) {
        mRvAlbums.setAdapter(adapter);
    }

    private void initRecyclerView(){
        mRvAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private class AlbumListContentObserver extends ContentObserver{
        public AlbumListContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            mAlbumListPresenter.loadData(getActivity(), contentResolver);
        }
    }

    private void findWidget(){
        mCustomToolBar = (CustomToolBar)mView.findViewById(R.id.ctb_albums);
        mRvAlbums = (RecyclerView)mView.findViewById(R.id.rv_albums);
    }

}
