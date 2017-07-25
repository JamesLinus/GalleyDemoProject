package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        view = mLayoutInflater.inflate(R.layout.recent_images_grid_item, parent, false);
        return new ImageGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String key = mKeys.get(position);
        ((ImageGridViewHolder) holder).tvDate.setText(key);
        List<String> listImages = mMapDateToImages.get(key);
        RecentImagesGridAdapter recentImagesGridAdapter = new RecentImagesGridAdapter(mContext, listImages, mAllUri);

        ((ImageGridViewHolder)holder).mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        ((ImageGridViewHolder)holder).mRecyclerView.setAdapter(recentImagesGridAdapter);
    }

    @Override
    public int getItemCount() {
        return mMapDateToImages.size();
    }


    private static class ImageGridViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate;
        public RecyclerView mRecyclerView;
        public ImageGridViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView)itemView.findViewById(R.id.tv_top);
            mRecyclerView = (RecyclerView)itemView.findViewById(R.id.rv_recent_images_grid);
        }
    }
}
