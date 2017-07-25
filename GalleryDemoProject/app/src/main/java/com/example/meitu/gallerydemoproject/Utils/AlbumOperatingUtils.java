package com.example.meitu.gallerydemoproject.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.Beans.ImageMessage;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author csm
 * 用于获取相册以及图片信息
 */

public class AlbumOperatingUtils {

    public static Map<String, List<String>> getRecentImageMessage(ContentResolver contentResolver){

        Map<String, List<String>> mapDateToUri = new TreeMap<>();
        List<String> listUri;
        Cursor cursor;

        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc" + " limit 100");

        while ((cursor.moveToNext())){
            String imageUri = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            SimpleDateFormat dateformat1 = new SimpleDateFormat("YYYY MM dd");

            String dateStr = dateformat1.format(date);

            if (mapDateToUri.containsKey(dateStr)){
                listUri = mapDateToUri.get(dateStr);
                listUri.add(imageUri);
                mapDateToUri.put(dateStr, listUri);
                listUri = null;
            }else {
                listUri = new ArrayList<>();
                listUri.add(imageUri);
                mapDateToUri.put(dateStr, listUri);
                listUri = null;
            }
        }
        cursor.close();
        cursor = null;

        return mapDateToUri;
    }

    /**
     * @param contentResolver
     * @return mapAlbums 存储键值对：相册名 : 相册信息
     * AlbumMessage: 包括相册名、图片数、封面uri;
     */
    public static Map<String, AlbumMessage> getAlbumsMessage(ContentResolver contentResolver){
        Map<String, AlbumMessage> mapAlbums = new TreeMap<>();
        AlbumMessage albumMessage;
        Cursor cursor;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA
                    , "COUNT(*)"};
        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                "0=0)GROUP BY (" + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, null, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + "  desc");

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

    /**
     * 获取相册中所有图片的地址
     * @param contentResolver
     * @param  albumName 相册名
     * @return listImages 该相册下 所有图片的地址
     */
    public static List<String> getAlbumImagesPath(ContentResolver contentResolver, String albumName){
        List<String> listImages = new ArrayList<>();
        Cursor cursor;

        String[] projection = {MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, MediaStore.Images.ImageColumns.DATA};

        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME +  "=?";
        String[] selectionArgs = {albumName};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                selection, selectionArgs, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String imageUrl = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            listImages.add(imageUrl);

        }
        cursor.close();
        cursor = null;

        return listImages;
    }
    /**
     * 获取图片信息
     * @param contentResolver
     * @param uri 图片的地址
     * @return iamgeMessage 图片信息
     */

    public static ImageMessage getImageMessage(ContentResolver contentResolver, String uri){
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

            String albumName = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));

            String imageUri = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            imageMessage.setId(id);
            imageMessage.setImageName(imageName);
            imageMessage.setAlbum(albumName);
            imageMessage.setPath(imageUri);
            imageMessage.setDate(date);
        }
        cursor.close();
        cursor = null;

        return imageMessage;
    }

}
