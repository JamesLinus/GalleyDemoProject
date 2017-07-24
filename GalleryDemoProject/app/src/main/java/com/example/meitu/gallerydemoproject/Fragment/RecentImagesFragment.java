package com.example.meitu.gallerydemoproject.Fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Activity.GellayListActivity;
import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.Adapter.RecentImagesAdapter;
import com.example.meitu.gallerydemoproject.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecentImagesFragment extends Fragment {

    private LinearLayout mLlTop;
    private TextView mTvTop;

    private RecyclerView mRvRecentImages;
    private RecentImagesAdapter mAdapterImages;

    private CustomToolBar mCustomToolBar;

    private List<String> mListURI;

    private int lastOffset;
    private int lastPosition;

    private int height;
    private int currentPosition = 0;

    public interface RecentImagesCallBack{
        void showRecentImagesFragment();
    }

    public static RecentImagesFragment newInstance() {
        RecentImagesFragment fragment = new RecentImagesFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_image_fragment, container, false);

        mLlTop = (LinearLayout)view.findViewById(R.id.ll_top) ;
        mTvTop = (TextView)view.findViewById(R.id.tv_top);

        mCustomToolBar = (CustomToolBar) view.findViewById(R.id.ctb_recent);

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GellayListActivity)getActivity()).showAlbumsListFragment();
            }
        });

        mRvRecentImages = (RecyclerView)view.findViewById(R.id.rv_recent_images);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        init();
    }

    private void init(){
        Map<String, List<String>> mapDateToKey = AlbumOperatingUtils.getRecentImageMessage(getActivity());
        final List<String> mListTitle = new ArrayList<String>(mapDateToKey.keySet());
        Collections.reverse(mListTitle);

        mTvTop.setText(mListTitle.get(currentPosition));

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvRecentImages.setLayoutManager(linearLayoutManager);

        mAdapterImages = new RecentImagesAdapter(getActivity(), mapDateToKey);
        mRvRecentImages.setAdapter(mAdapterImages);

        /** 监听RecyclerView滚动状态 */
        mRvRecentImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                height = mLlTop.getHeight();

                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View view = linearLayoutManager.findViewByPosition(currentPosition + 1);
                if (view == null)return;

                if (view.getTop() <= height){
                    mLlTop.setY(-(height-view.getTop()));
                }else {
                    mLlTop.setY(0);
                }

                if (currentPosition!=linearLayoutManager.findFirstVisibleItemPosition()){
                    currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mLlTop.setY(0);

                    mTvTop.setText(mListTitle.get(currentPosition));
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
        /** 获取可视的第一个view */
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
