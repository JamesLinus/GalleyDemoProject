package com.example.meitu.gallerydemoproject.Model.Impl;

import android.content.ContentResolver;

import java.util.List;

/**
 * Created by meitu on 2017/7/26.
 */

public interface IAlbumImagesPathModel {
    List<String> getAlbumImagesPath(ContentResolver contentResolver, String albumName);
}
