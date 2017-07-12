package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class GalleyActivity extends AppCompatActivity {
    private static final String TAG = "GalleyActivity.activity";

    private String mAlbumNameKey;
    private String mAlbumName;


    private AlbumsMessageUtils mAlbumsMessageUtils;

    private RecyclerView mRvImages;
    private ImagesAdapter mAdapterImages;

    private View mBtnBack;
    private TextView mTvTitle;

    private List<String> mListURI;

    private int lastOffset;
    private int lastPosition;


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
        mAlbumsMessageUtils = new AlbumsMessageUtils(GalleyActivity.this);

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

        mListURI = mAlbumsMessageUtils.getTargetImagePath(mAlbumName);

        mAdapterImages = new ImagesAdapter(GalleyActivity.this, mListURI);
        mRvImages.setAdapter(mAdapterImages);

        //监听RecyclerView滚动状态
        mRvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvImages.getLayoutManager();
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
        if(mRvImages.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRvImages.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }
}
