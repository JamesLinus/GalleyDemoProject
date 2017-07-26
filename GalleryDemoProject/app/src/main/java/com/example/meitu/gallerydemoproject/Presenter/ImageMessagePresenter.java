package com.example.meitu.gallerydemoproject.Presenter;

import android.content.ContentResolver;
import android.content.Context;

import com.example.meitu.gallerydemoproject.Model.ImageMessageModel;
import com.example.meitu.gallerydemoproject.Model.bean.ImageMessage;
import com.example.meitu.gallerydemoproject.View.View.IImageMessageView;

/**
 * Created by meitu on 2017/7/26.
 */
public class ImageMessagePresenter {
    private IImageMessageView mIImageMessageView;
    private ImageMessageModel mImageMessageModel;
    private ImageMessage mImageMessage;

    public ImageMessagePresenter(IImageMessageView view){
        mIImageMessageView = view;
        mImageMessageModel = ImageMessageModel.getInstance();
    }

    public void loadData(Context context,String imageUri, ContentResolver contentResolver){
        mImageMessage = mImageMessageModel.getImageMessage(contentResolver, imageUri);

        mIImageMessageView.setImageId(mImageMessage.getId());
        mIImageMessageView.setImageName(mImageMessage.getImageName());
        mIImageMessageView.setImageFile(mImageMessage.getAlbum());
        mIImageMessageView.setImagePath(mImageMessage.getPath());
        mIImageMessageView.setCreateDate(mImageMessage.getDate().toString());
    }


}
