package com.example.meitu.gallerydemoproject.View.Fragment;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Presenter.AlbumPresenter;
import com.example.meitu.gallerydemoproject.View.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.View.View.IAlbumView;

public class AlbumFragment extends Fragment implements IAlbumView{

    private static final String ALBUM_NAME = "album_name";
    private static final String TAG = "GalleyFragment.fragment";

    private View mView;

    private String mAlbumName;

    private RecyclerView mRvImages;
    private CustomToolBar mCustomToolBar;

    private int lastOffset;
    private int lastPosition;

    private ContentResolver contentResolver;
    private AlbumContentObserver mAlbumContentObserver;

    private AlbumPresenter mAlbumPresenter;

    public interface CallBack {
        void showAlbumFragment(String albumName);
    }

    public static AlbumFragment newInstance(String albumName) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ALBUM_NAME, albumName);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlbumName = getArguments().getString(ALBUM_NAME);
        }

        contentResolver = getActivity().getContentResolver();
        mAlbumContentObserver = new AlbumContentObserver(new Handler());

        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mAlbumContentObserver);

        mAlbumPresenter = new AlbumPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.album_fragment, container, false);

        findWidget();


        mAlbumPresenter.loadData(getActivity(), mAlbumName, contentResolver);
        mAlbumPresenter.setTitle(mAlbumName);

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });

        return mView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        contentResolver.unregisterContentObserver(mAlbumContentObserver);
    }

    @Override
    public void setRecyclerViewData(RecyclerView.Adapter adapter) {
        mRvImages.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRvImages.setAdapter(adapter);
    }

    @Override
    public void setTitle(String title) {
        mCustomToolBar.setTitle(title);
    }


    private class AlbumContentObserver extends ContentObserver{
        public AlbumContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            mAlbumPresenter.loadData(getActivity(), mAlbumName, contentResolver);
        }
    }

    private void findWidget(){
        mCustomToolBar = (CustomToolBar)mView.findViewById(R.id.ctb_album);
        mRvImages = (RecyclerView)mView.findViewById(R.id.rv_images);
    }
}
