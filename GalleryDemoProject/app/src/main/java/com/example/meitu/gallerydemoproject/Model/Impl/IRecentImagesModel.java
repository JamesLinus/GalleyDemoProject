package com.example.meitu.gallerydemoproject.Model.Impl;

import android.content.ContentResolver;

import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/25.
 */
public interface IRecentImagesModel  {
    Map<String, List<String>> getRecentImageMessage(ContentResolver contentResolver);
}
