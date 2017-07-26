package com.example.meitu.gallerydemoproject.Model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.meitu.gallerydemoproject.Model.Impl.IImageMessageModel;
import com.example.meitu.gallerydemoproject.Model.bean.ImageMessage;

/**
 * Created by meitu on 2017/7/26.
 */
public class ImageMessageModel implements IImageMessageModel {

    public static ImageMessageModel getInstance(){
        return new ImageMessageModel();
    }

    @Override
    public ImageMessage getImageMessage(ContentResolver contentResolver, String uri) {
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

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        cursor.moveToNext();

        String id = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

        String imageName = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

        String albumName = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));

        String imageUri = cursor.getString(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

        long date = cursor.getLong(
                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

        imageMessage.setId(id);
        imageMessage.setImageName(imageName);
        imageMessage.setAlbum(albumName);
        imageMessage.setPath(imageUri);
        imageMessage.setDate(date);

        cursor.close();
        cursor = null;

        return imageMessage;
    }
}
