package com.example.meitu.gallerydemoproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.MediaStoreUtils;

import java.util.List;

public class RecentImagesActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.activity";

    private MediaStoreUtils mMediaStoreUtils;

    private View mBtnOthers;

    private RecyclerView mRecyclerView;
    private ImagesAdapter mImagesAdapter;

    private List<String> mListURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recent_image_activity);



    }

    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recent_images);
        mRecyclerView.setLayoutManager(new GridLayoutManager(RecentImagesActivity.this, 3));

        mMediaStoreUtils = new MediaStoreUtils(RecentImagesActivity.this);



        mBtnOthers = (View)findViewById(R.id.ll_btn);

        mBtnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentGalleryList = new Intent(RecentImagesActivity.this, AlbumsActivity.class);
                startActivity(mIntentGalleryList);
            }
        });

        mListURI = mMediaStoreUtils.getRecentImagePath();

        for (String s : mListURI){
            Log.d(TAG, s);
        }

        mImagesAdapter = new ImagesAdapter(RecentImagesActivity.this, mListURI);
        mRecyclerView.setAdapter(mImagesAdapter);
    }

}
