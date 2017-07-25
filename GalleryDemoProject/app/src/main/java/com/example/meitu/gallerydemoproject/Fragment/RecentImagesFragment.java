package com.example.meitu.gallerydemoproject.Fragment;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RecentImagesFragment extends Fragment {

    private LinearLayout mLlTop;
    private TextView mTvTop;
    private RecyclerView mRvRecentImages;
    private RecentImagesAdapter mAdapterImages;
    private CustomToolBar mCustomToolBar;

    private ContentResolver contentResolver;

    Map<String, List<String>> mapDateToKey;

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

        contentResolver = getActivity().getContentResolver();
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, new RecentChangeContentObserver(new Handler()));
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

        init();

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    private void init(){
        mapDateToKey = getRecentImageMessage();
        final List<String> mListTitle = new ArrayList<>(mapDateToKey.keySet());
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

    private Map<String, List<String>> getRecentImageMessage(){

        Map<String, List<String>> mapDateToUri = new TreeMap<>();
        List<String> listUri;
        Cursor cursor;

        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc" + " limit 100");

        while ((cursor.moveToNext())){
            String imageUri = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            SimpleDateFormat dateformat1 = new SimpleDateFormat("YYYY MM dd");

            String dateStr = dateformat1.format(date);

            if (mapDateToUri.containsKey(dateStr)){
                listUri = mapDateToUri.get(dateStr);
                listUri.add(imageUri);
                mapDateToUri.put(dateStr, listUri);
                listUri = null;
            }else {
                listUri = new ArrayList<>();
                listUri.add(imageUri);
                mapDateToUri.put(dateStr, listUri);
                listUri = null;
            }
        }
        cursor.close();
        cursor = null;

        return mapDateToUri;
    }

    private class RecentChangeContentObserver extends ContentObserver {
        public RecentChangeContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            init();
        }

    }
}
