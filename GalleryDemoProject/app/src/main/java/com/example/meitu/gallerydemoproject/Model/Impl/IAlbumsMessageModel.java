package com.example.meitu.gallerydemoproject.Model.Impl;

import android.content.ContentResolver;

import com.example.meitu.gallerydemoproject.Model.bean.AlbumMessage;

import java.util.Map;

/**
 * Created by meitu on 2017/7/26.
 */

public interface IAlbumsMessageModel {
    Map<String, AlbumMessage> getAlbumsMessage(ContentResolver contentResolver);
}
