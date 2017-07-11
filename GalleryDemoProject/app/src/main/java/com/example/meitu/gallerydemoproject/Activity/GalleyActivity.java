package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private String mGalleyNameKey;

    private String mStrGalleyName;


    private MediaStoreUtils mMediaStoreUtils;

    private RecyclerView mRecyclerView;
    private ImagesAdapter mGalleyAdapter;

    private View mBtnBack;
    private TextView tvTitle;

    private List<String> mListURI;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        mGalleyNameKey = getString(R.string.album_name);

        mStrGalleyName = getIntent().getStringExtra(mGalleyNameKey);

//        init();

    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        mMediaStoreUtils = new MediaStoreUtils(GalleyActivity.this);

        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText(mStrGalleyName);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_images);
        mRecyclerView.setLayoutManager(new GridLayoutManager(GalleyActivity.this, 3));

        mBtnBack = (View)findViewById(R.id.ll_btn);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListURI = mMediaStoreUtils.getTargetImagePath(mStrGalleyName);

        for (String s : mListURI){
            Log.d(TAG, s);
        }

        mGalleyAdapter = new ImagesAdapter(GalleyActivity.this, mListURI);
        mRecyclerView.setAdapter(mGalleyAdapter);
    }
}
