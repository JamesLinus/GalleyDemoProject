package com.example.meitu.gallerydemoproject.Utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by meitu on 2017/7/10.
 */

public class MediaStoreUtils {

    private Context mContext;
    private ContentResolver mContentResolver;

    private Cursor cursor;

    public MediaStoreUtils(Context context) {
        mContext = context;
        mContentResolver = mContext.getContentResolver();
    }

    public Map<String, AlbumMessage> getGalleryNameAndCover(){
        Map<String, AlbumMessage> mapAlbums = new HashMap<>();
        AlbumMessage albumMessage;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};
        cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String mFile = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            String mURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            Log.d("test", mFile + " " + mURI);
            if (!mapAlbums.containsKey(mFile)){
                albumMessage = new AlbumMessage();
                albumMessage.setAblumName(mFile);
                albumMessage.setAblumSize(1);
                albumMessage.setCover(mURI);
                mapAlbums.put(mFile, albumMessage);
            }else {
                albumMessage = mapAlbums.get(mFile);
                int size = albumMessage.getAblumSize();
                albumMessage.setAblumSize(size+1);
                mapAlbums.put(mFile, albumMessage);
            }
        }

        cursor.close();

        cursor = null;

        return mapAlbums;
    }

    public List<String> getTargetImagePath(String filePath){
        List<String> mImages = new ArrayList<>();

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};

        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME +  "=?";
        String[] selectionArgs = {filePath};

        cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String mFile = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));

            String mURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            mImages.add(mURI);

        }

        cursor.close();

        cursor = null;

        return mImages;
    }

    public List<String> getRecentImagePath(){
        List<String> mRecentImage = new ArrayList<>();
        String[] projection = { MediaStore.Images.ImageColumns.DATA };
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        int i = 100;
        while ((cursor.moveToNext())&&((i--)>0)){
            String mPath = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            mRecentImage.add(mPath);
        }

        cursor.close();

        cursor = null;

        return mRecentImage;
    }






}
