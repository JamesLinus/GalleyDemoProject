package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Adapter.ViewHolder.GridImageItemViewHolder;
import com.example.meitu.gallerydemoproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/21.
 */

public class RecentImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Map<String, List<String>> mMapDateToImages;
    private List<String> mKeys;

    public RecentImagesAdapter(Context context, Map<String, List<String>> mData){
        mContext = context;
        mMapDateToImages = mData;
        mKeys = new ArrayList<>(mMapDateToImages.keySet());
        Collections.reverse(mKeys);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view;
        view = mLayoutInflater.inflate(R.layout.recent_images_grid_item, parent, false);
        return new GridImageItemViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String key = mKeys.get(position);

        RecentImagesGridAdapter recentImagesGridAdapter = new RecentImagesGridAdapter(mContext, mMapDateToImages, key);

        ((GridImageItemViewHolder) holder).setData(key, recentImagesGridAdapter);

    }

    @Override
    public int getItemCount() {
        return mMapDateToImages.size();
    }


}
