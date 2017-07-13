package com.example.meitu.gallerydemoproject.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LongSparseArray;

import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.Beans.ImageMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/10.
 */

public class AlbumsMessageUtils {


    public static Map<String, AlbumMessage> getGalleryNameAndCover(Context context){
        Map<String, AlbumMessage> mapAlbums = new HashMap<>();
        AlbumMessage albumMessage;
        Cursor cursor;
        ContentResolver contentResolver = context.getContentResolver();

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};
        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String albumName = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            String coverURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            if (!mapAlbums.containsKey(albumName)){
                albumMessage = new AlbumMessage();
                albumMessage.setAblumName(albumName);
                albumMessage.setAblumSize(1);
                albumMessage.setCover(coverURI);
                mapAlbums.put(albumName, albumMessage);
            }else {
                albumMessage = mapAlbums.get(albumName);
                int size = albumMessage.getAblumSize();
                albumMessage.setAblumSize(size+1);
                mapAlbums.put(albumName, albumMessage);
            }
        }

        cursor.close();

        cursor = null;

        return mapAlbums;
    }

    public static List<String> getTargetImagePath(Context context, String filePath){
        ContentResolver contentResolver = context.getContentResolver();
        List<String> mImages = new ArrayList<>();
        Cursor cursor;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};

        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME +  "=?";
        String[] selectionArgs = {filePath};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
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

    public static List<String> getRecentImagePath(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        List<String> mRecentImage = new ArrayList<>();
        Cursor cursor;

        String[] projection = { MediaStore.Images.ImageColumns.DATA };
        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

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

    public static ImageMessage getImageMessage(Context context, String uri){
        ContentResolver contentResolver = context.getContentResolver();
        ImageMessage imageMessage = new ImageMessage();
        Cursor cursor;

        String[] projection = {
                        MediaStore.Images.ImageColumns._ID,
                        MediaStore.Images.ImageColumns.DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.ImageColumns.DATE_TAKEN};

        String selection = MediaStore.Images.ImageColumns.DATA +  "=?";
        String[] selectionArgs = {uri};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

            String imageName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

            String mFile = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));

            String mURI = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            imageMessage.setId(id);
            imageMessage.setImageName(imageName);
            imageMessage.setFile(mFile);
            imageMessage.setPath(mURI);
            imageMessage.setDate(date);
        }

        cursor.close();

        cursor = null;


        return imageMessage;
    }




}
