package com.example.meitu.gallerydemoproject.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.List;

public class RecentImagesActivity extends AppCompatActivity {
    private static final String TAG = "RecentImagesActivity.activity";

    private AlbumsMessageUtils mAlbumsMessageUtils;
    private List<String> mListURI;

    private View mBtnOthers;

    private RecyclerView mRvRecentImages;
    private ImagesAdapter mAdapterImages;

    private int lastOffset;
    private int lastPosition;

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

        mAlbumsMessageUtils = new AlbumsMessageUtils(RecentImagesActivity.this);

        mRvRecentImages = (RecyclerView)findViewById(R.id.rv_recent_images);
        mRvRecentImages.setLayoutManager(new GridLayoutManager(RecentImagesActivity.this, 3));

        mListURI = mAlbumsMessageUtils.getRecentImagePath();

        mAdapterImages = new ImagesAdapter(RecentImagesActivity.this, mListURI);
        mRvRecentImages.setAdapter(mAdapterImages);

        //监听RecyclerView滚动状态
        mRvRecentImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
        scrollToPosition();
    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvRecentImages.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(mRvRecentImages.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRvRecentImages.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }

}
