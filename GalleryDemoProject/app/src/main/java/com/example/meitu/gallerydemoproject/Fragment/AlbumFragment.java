package com.example.meitu.gallerydemoproject.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;


import java.util.List;

public class AlbumFragment extends Fragment {

    private static final String ALBUM_NAME = "album_name";
    private static final String TAG = "GalleyFragment.fragment";

    private String mAlbumName;

    private RecyclerView mRvImages;
    private ImagesAdapter mAdapterImages;

    private CustomToolBar mCustomToolBar;

    private List<String> mListURI;

    private int lastOffset;
    private int lastPosition;

    public interface AlbumCallBack{
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.album_fragment, container, false);

        mCustomToolBar = (CustomToolBar)view.findViewById(R.id.ctb_album);
        mCustomToolBar.setTitle(mAlbumName);

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });

        mRvImages = (RecyclerView)view.findViewById(R.id.rv_images);
        mRvImages.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        init();
    }


    private void init(){
        mListURI = AlbumOperatingUtils.getAlbumImagesPath(getActivity(), mAlbumName);
        mAdapterImages = new ImagesAdapter(getActivity(), mListURI);
        mRvImages.setAdapter(mAdapterImages);

        /** 监听RecyclerView滚动状态 */
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
        if(mRvImages.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) mRvImages.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
        }
    }
}
