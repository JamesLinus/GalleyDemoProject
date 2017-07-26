package com.example.meitu.gallerydemoproject.Model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.meitu.gallerydemoproject.Model.Impl.IRecentImagesModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by meitu on 2017/7/25.
 */

public class RecentImagesModel implements IRecentImagesModel {

    public static RecentImagesModel getInstance(){
        return new RecentImagesModel();
    }


    @Override
    public Map<String, List<String>> getRecentImageMessage(ContentResolver contentResolver) {
        Map<String, List<String>> mapDateToUri = new TreeMap<>();
        List<String> listUri;
        Cursor cursor;

        String[] projection = {
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc" + " limit 100");

        while ((cursor.moveToNext())){
            String imageUri = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));

            long date = cursor.getLong(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));

            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY MM dd");
            String dateStr = dateFormat.format(date);

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
}
