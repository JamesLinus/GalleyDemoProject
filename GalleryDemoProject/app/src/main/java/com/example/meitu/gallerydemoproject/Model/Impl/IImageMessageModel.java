package com.example.meitu.gallerydemoproject.Model.Impl;

import android.content.ContentResolver;

import com.example.meitu.gallerydemoproject.Model.bean.ImageMessage;

/**
 * Created by meitu on 2017/7/26.
 */

public interface IImageMessageModel {
    ImageMessage getImageMessage(ContentResolver contentResolver, String uri);
}
