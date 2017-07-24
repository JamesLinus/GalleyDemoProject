package com.example.meitu.gallerydemoproject.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Adapter.TestAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/24.
 */

public class TestActivity extends AppCompatActivity {

    private LinearLayout mLlTop;
    private TextView mTvTop;

    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;

    private int height;
    private int currentPosition = 0;
    private List<String> mListTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recent_images_activity);

        mLlTop = (LinearLayout)findViewById(R.id.ll_top);
        mTvTop = (TextView)findViewById(R.id.tv_top);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_recent_images);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        final Map<String, List<String>> mapDateToKey = AlbumOperatingUtils.getRecentImageMessage(TestActivity.this);
        mListTitle = new ArrayList<String>(mapDateToKey.keySet());
        Collections.reverse(mListTitle);

        mTvTop.setText(mListTitle.get(0));

        mTestAdapter = new TestAdapter(TestActivity.this, mapDateToKey);
        mRecyclerView.setAdapter(mTestAdapter);



        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * RecyclerView的滑动状态被改变的时候调用
             * 状态包括：Fling、Touch、Idle
             * @param recyclerView
             * @param newState
             */

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                height = mLlTop.getHeight();
            }


            /**
             * 监听屏幕滑动
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View view = linearLayoutManager.findViewByPosition(currentPosition + 1);
                if (view == null)return;

                if (view.getTop() <= height){
                    mLlTop.setY(-(height-view.getTop()));
                }else {
                    //mLlTop.setY(0);
                }

                if (currentPosition!=linearLayoutManager.findFirstVisibleItemPosition()){
                    currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mLlTop.setY(0);

                    mTvTop.setText(mListTitle.get(currentPosition));
                }
            }
        });
    }
}
