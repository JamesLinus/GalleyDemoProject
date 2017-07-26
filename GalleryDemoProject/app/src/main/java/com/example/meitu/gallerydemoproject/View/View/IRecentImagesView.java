package com.example.meitu.gallerydemoproject.View.View;

import android.support.v7.widget.RecyclerView;

/**
 * Created by meitu on 2017/7/26.
 */
public interface IRecentImagesView {

    void setRecyclerViewData(RecyclerView.Adapter adapter);

    void setTopData(String topData);
}
