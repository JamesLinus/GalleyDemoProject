package com.example.meitu.gallerydemoproject.Utils;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

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

    public Map<String, List<String>> getGalleryNameAndCover(){
        Map<String, List<String>> mapAlbums = new HashMap<>();
        List<String> listImages;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};
        cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String mFile = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            String mURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            if (!mapAlbums.containsKey(mFile)){
                listImages = new ArrayList<>();
                listImages.add(mURI);
                mapAlbums.put(mFile, listImages);
            }else {
                listImages = mapAlbums.get(mFile);
                listImages.add(mURI);
                mapAlbums.put(mFile, listImages);
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
