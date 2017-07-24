package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/21.
 */

//TODO 按时间分类的图片显示 的一级列表

public class RecentImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TITLE = 1;
    private static final int IMAGES_GRID = 2;

    private Context mContext;
    private Map<String, List<String>> mMapDateToImages;
    private List<String> mKeys;
    private List<String> mAllUri;

    public RecentImagesAdapter(Context context, Map<String, List<String>> mData){
        mContext = context;
        mMapDateToImages = mData;
        mKeys = new ArrayList<>(mMapDateToImages.keySet());
        Collections.reverse(mKeys);
        Iterator iterator = mKeys.iterator();
        mAllUri = new ArrayList<>();
        while (iterator.hasNext()){
            mAllUri.addAll(mMapDateToImages.get(iterator.next()));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        if (TITLE == viewType){
            view = mLayoutInflater.inflate(R.layout.recent_images_title_item, parent, false);
            return new DateViewHolder(view);
        }else if (IMAGES_GRID == viewType){
            view = mLayoutInflater.inflate(R.layout.recent_images_grid_item, parent, false);
            return new ImageGridViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).tvDate.setText(mKeys.get(position/2));
        } else if (holder instanceof ImageGridViewHolder) {
            List<String> listImages = mMapDateToImages.get(mKeys.get(position/2));
            RecentImagesGridAdapter recentImagesGridAdapter = new RecentImagesGridAdapter(mContext, listImages, mAllUri);

            ((ImageGridViewHolder)holder).mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
            ((ImageGridViewHolder)holder).mRecyclerView.setAdapter(recentImagesGridAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mMapDateToImages.size() * 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? TITLE : IMAGES_GRID;
    }



    private static class DateViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate;
        public DateViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date_title);
        }
    }

    private static class ImageGridViewHolder extends RecyclerView.ViewHolder{
        public RecyclerView mRecyclerView;
        public ImageGridViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView)itemView.findViewById(R.id.rv_recent_images_grid);
        }
    }
}
