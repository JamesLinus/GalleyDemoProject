package com.example.meitu.gallerydemoproject.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/26.
 */

public class GridImageItemViewHolder extends BaseViewHolder<String> {

    private Context mContext;
    private TextView tvDate;
    private RecyclerView mRecyclerView;

    public GridImageItemViewHolder(Context context, View itemView) {
        super(itemView);

        mContext = context;

        tvDate = (TextView)itemView.findViewById(R.id.tv_top);
        mRecyclerView = (RecyclerView)itemView.findViewById(R.id.rv_recent_images_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
    }

    @Override
    public void setData(String data) {
        tvDate.setText(data);
    }

    public void setData(String data, RecyclerView.Adapter adapter){
        this.setData(data);
        mRecyclerView.setAdapter(adapter);

    }
}
