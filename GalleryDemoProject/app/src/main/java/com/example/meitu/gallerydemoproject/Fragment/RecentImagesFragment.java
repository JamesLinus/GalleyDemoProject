package com.example.meitu.gallerydemoproject.Fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.meitu.gallerydemoproject.Activity.AlbumsActivity;
import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;

import java.util.List;

public class RecentImagesFragment extends Fragment {

    private AlbumsMessageUtils mAlbumsMessageUtils;

    private RecyclerView mRvRecentImages;
    private ImagesAdapter mAdapterImages;

    private View mBtnOthers;

    private List<String> mListURI;

    private int lastOffset;
    private int lastPosition;

    public static RecentImagesFragment newInstance() {
        RecentImagesFragment fragment = new RecentImagesFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_image_fragment, container, false);

        mBtnOthers = (View)view.findViewById(R.id.ll_btn);
        mBtnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentGalleryList = new Intent(getActivity(), AlbumsActivity.class);
                startActivity(mIntentGalleryList);
            }
        });

        mRvRecentImages = (RecyclerView)view.findViewById(R.id.rv_recent_images);
        mRvRecentImages.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }


    @Override
    public void onStart(){
        super.onStart();
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(){

        mAlbumsMessageUtils = new AlbumsMessageUtils(getActivity());
        mListURI = mAlbumsMessageUtils.getRecentImagePath();

        mAdapterImages = new ImagesAdapter(getActivity(), mListURI);
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
