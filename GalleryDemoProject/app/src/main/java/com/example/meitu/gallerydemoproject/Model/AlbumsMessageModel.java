package com.example.meitu.gallerydemoproject.Model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.meitu.gallerydemoproject.Model.Impl.IAlbumsMessageModel;
import com.example.meitu.gallerydemoproject.Model.bean.AlbumMessage;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by meitu on 2017/7/26.
 */

public class AlbumsMessageModel implements IAlbumsMessageModel{

    public static AlbumsMessageModel getInstance(){
        return new AlbumsMessageModel();
    }

    @Override
    public Map<String, AlbumMessage> getAlbumsMessage(ContentResolver contentResolver){
        Map<String, AlbumMessage> mapAlbums = new TreeMap<>();
        AlbumMessage albumMessage;
        Cursor cursor;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA,
                "COUNT(*)"};

        String selection = "0=0)GROUP BY (" + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                null,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + "  desc");

        while (cursor.moveToNext()){
            String albumName = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            String coverURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            int sum = cursor.getInt(cursor.getColumnIndex("COUNT(*)"));
            Log.d(albumName, sum + " ");

            albumMessage = new AlbumMessage();
            albumMessage.setAblumName(albumName);
            albumMessage.setAlbumSize(sum);
            albumMessage.setCover(coverURI);
            mapAlbums.put(albumName, albumMessage);

        }
        cursor.close();
        cursor = null;

        return mapAlbums;
    }
}
