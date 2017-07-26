package com.example.meitu.gallerydemoproject.Presenter;

import android.content.ContentResolver;
import android.content.Context;

import com.example.meitu.gallerydemoproject.Adapter.RecentImagesAdapter;
import com.example.meitu.gallerydemoproject.Model.Impl.IRecentImagesModel;
import com.example.meitu.gallerydemoproject.Model.RecentImagesModel;
import com.example.meitu.gallerydemoproject.View.View.IRecentImagesView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/26.
 */
public class RecentImagesPresenter {
    private IRecentImagesView mRecentImagesView;
    private IRecentImagesModel mRecentImagesModel;

    private Map<String, List<String>> mapDateToKey;
    private List<String> mListTitle;

    public RecentImagesPresenter(IRecentImagesView view){
        mRecentImagesView = view;
        mRecentImagesModel = RecentImagesModel.getInstance();
    }

    public void loadData(Context context, ContentResolver contentResolver){
        mapDateToKey = mRecentImagesModel.getRecentImageMessage(contentResolver);
        mListTitle = new ArrayList<>(mapDateToKey.keySet());
        Collections.reverse(mListTitle);
        RecentImagesAdapter mAdapterImages = new RecentImagesAdapter(context, mapDateToKey);
        mRecentImagesView.setRecyclerViewData(mAdapterImages);
    }

    public void setTop(int position){
        mRecentImagesView.setTopData(mListTitle.get(position));
    }


}
