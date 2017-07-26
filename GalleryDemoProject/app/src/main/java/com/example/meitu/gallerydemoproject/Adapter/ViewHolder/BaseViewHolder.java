package com.example.meitu.gallerydemoproject.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by meitu on 2017/7/26.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void setData(T data);
}
