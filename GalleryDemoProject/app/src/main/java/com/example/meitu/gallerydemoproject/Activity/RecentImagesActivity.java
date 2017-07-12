package com.example.meitu.gallerydemoproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.MediaStoreUtils;

import java.util.List;

public class RecentImagesActivity extends AppCompatActivity {
    private static final String TAG = "RecentImagesActivity.activity";

    private MediaStoreUtils mMediaStoreUtils;
    private List<String> mListURI;

    private View mBtnOthers;

    private RecyclerView mRvRecentImages;
    private ImagesAdapter mAdapterImages;

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
        mBtnOthers = (View)findViewById(R.id.ll_btn);
        mBtnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentGalleryList = new Intent(RecentImagesActivity.this, AlbumsActivity.class);
                startActivity(mIntentGalleryList);
            }
        });

        mMediaStoreUtils = new MediaStoreUtils(RecentImagesActivity.this);

        mRvRecentImages = (RecyclerView)findViewById(R.id.rv_recent_images);
        mRvRecentImages.setLayoutManager(new GridLayoutManager(RecentImagesActivity.this, 3));

        mListURI = mMediaStoreUtils.getRecentImagePath();

        mAdapterImages = new ImagesAdapter(RecentImagesActivity.this, mListURI);
        mRvRecentImages.setAdapter(mAdapterImages);
    }

}
