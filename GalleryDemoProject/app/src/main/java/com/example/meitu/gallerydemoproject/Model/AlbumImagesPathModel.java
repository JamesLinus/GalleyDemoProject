package com.example.meitu.gallerydemoproject.Model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.meitu.gallerydemoproject.Model.Impl.IAlbumImagesPathModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meitu on 2017/7/26.
 */

public class AlbumImagesPathModel implements IAlbumImagesPathModel {

    public static AlbumImagesPathModel getInstance(){
        return new AlbumImagesPathModel();
    }

    @Override
    public List<String> getAlbumImagesPath(ContentResolver contentResolver, String albumName) {
        List<String> listImages = new ArrayList<>();
        Cursor cursor;

        String[] projection = {
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATA};

        String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME +  "=?";

        String[] selectionArgs = { albumName };

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");

        while (cursor.moveToNext()){
            String imageUrl = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            listImages.add(imageUrl);

        }
        cursor.close();
        cursor = null;

        return listImages;
    }
}
