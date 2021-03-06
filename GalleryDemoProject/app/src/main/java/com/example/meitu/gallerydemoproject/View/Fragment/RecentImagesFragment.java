package com.example.meitu.gallerydemoproject.View.Fragment;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Presenter.RecentImagesPresenter;
import com.example.meitu.gallerydemoproject.View.Activity.GellayListActivity;
import com.example.meitu.gallerydemoproject.View.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.View.View.IRecentImagesView;

public class RecentImagesFragment extends Fragment implements IRecentImagesView{

    private View mView;
    private LinearLayout mLlTop;
    private TextView mTvTop;
    private RecyclerView mRvRecentImages;
    private CustomToolBar mCustomToolBar;

    private ContentResolver contentResolver;
    private RecentChangeContentObserver mRecentChangeContentObserver;
    private LinearLayoutManager linearLayoutManager;

    private RecentImagesPresenter mRecentImagesPresenter;

    private int lastOffset;
    private int lastPosition;

    private int height;
    private int currentPosition = 0;

    public interface CallBack {
        void showRecentImagesFragment();
    }

    public static RecentImagesFragment newInstance() {
        RecentImagesFragment fragment = new RecentImagesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentResolver = getActivity().getContentResolver();
        mRecentChangeContentObserver = new RecentChangeContentObserver(new Handler());
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, mRecentChangeContentObserver);

        mRecentImagesPresenter = new RecentImagesPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.recent_image_fragment, container, false);
        findWidgets();

        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GellayListActivity)getActivity()).showAlbumsListFragment();
            }
        });
        initRecyclerView();
        mRecentImagesPresenter.loadData(getActivity(), contentResolver);
        mRecentImagesPresenter.setTop(currentPosition);
        initView();
        return mView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        contentResolver.unregisterContentObserver(mRecentChangeContentObserver);
    }

    @Override
    public void setRecyclerViewData(RecyclerView.Adapter adapter) {
        mRvRecentImages.setAdapter(adapter);
    }

    @Override
    public void setTopData(String topData) {
        mTvTop.setText(topData);
    }

    private void initRecyclerView(){
        linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvRecentImages.setLayoutManager(linearLayoutManager);
    }

    private void initView(){
        /** 监听RecyclerView滚动状态 */
        mRvRecentImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                height = mLlTop.getHeight();
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 获取下一个显示的View / 即，当前topView所覆盖的item的下一个item
                 * 如过下一个item的顶部坐标小于height（因为屏幕坐标系的原点是左上角；
                 * 此时证明该item已经开始顶掉当前item了；
                 * 那么，此时，滚动过程中设置mLlTop的Y值，即让他向上移动
                 * 下一个item完全顶替当前item后，它就是当前item了；
                 * 于是进行下一轮检测；
                 */
                View view = linearLayoutManager.findViewByPosition(currentPosition + 1);
                if (null == view)return;
                if (view.getTop() <= height){
                    mLlTop.setY(view.getTop()-height);
                }else {
                    mLlTop.setY(0);
                }
                /**
                 * 获取当前第一个可见item的位置；
                 * 获取后设置其文字
                 */
                if (currentPosition != linearLayoutManager.findFirstVisibleItemPosition()){
                    currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mLlTop.setY(0);

                    mRecentImagesPresenter.setTop(currentPosition);
                }
            }
        });
    }


    private class RecentChangeContentObserver extends ContentObserver {
        public RecentChangeContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            mRecentImagesPresenter.loadData(getActivity(), contentResolver);
            initView();
        }
    }

    private void findWidgets(){
        mLlTop = (LinearLayout)mView.findViewById(R.id.ll_top) ;
        mTvTop = (TextView)mView.findViewById(R.id.tv_top);
        mCustomToolBar = (CustomToolBar) mView.findViewById(R.id.ctb_recent);
        mRvRecentImages = (RecyclerView)mView.findViewById(R.id.rv_recent_images);
    }
}
