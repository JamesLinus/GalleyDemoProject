package com.example.meitu.gallerydemoproject.Presenter;

import com.example.meitu.gallerydemoproject.View.View.IImageView;

/**
 * Created by meitu on 2017/7/26.
 */
public class ImagePresenter {
    private IImageView mIImageView;

    public ImagePresenter(IImageView view){
        mIImageView = view;
    }

    public void setImage(String uri){
        mIImageView.setImage(uri);
    }
}
