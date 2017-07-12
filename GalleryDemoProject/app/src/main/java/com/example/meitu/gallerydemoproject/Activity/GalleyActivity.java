package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.MediaStoreUtils;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class GalleyActivity extends AppCompatActivity {
    private static final String TAG = "GalleyActivity.activity";

    private String mAlbumNameKey;
    private String mAlbumName;


    private MediaStoreUtils mMediaStoreUtils;

    private RecyclerView mRvImages;
    private ImagesAdapter mAdapterImages;

    private View mBtnBack;
    private TextView mTvTitle;

    private List<String> mListURI;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        mAlbumNameKey = getString(R.string.album_name);
        mAlbumName = getIntent().getStringExtra(mAlbumNameKey);
    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        mMediaStoreUtils = new MediaStoreUtils(GalleyActivity.this);

        mTvTitle = (TextView)findViewById(R.id.tv_title);
        mTvTitle.setText(mAlbumName);
        mBtnBack = (View)findViewById(R.id.ll_btn);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRvImages = (RecyclerView)findViewById(R.id.rv_images);
        mRvImages.setLayoutManager(new GridLayoutManager(GalleyActivity.this, 3));

        mListURI = mMediaStoreUtils.getTargetImagePath(mAlbumName);

        mAdapterImages = new ImagesAdapter(GalleyActivity.this, mListURI);
        mRvImages.setAdapter(mAdapterImages);
    }
}
