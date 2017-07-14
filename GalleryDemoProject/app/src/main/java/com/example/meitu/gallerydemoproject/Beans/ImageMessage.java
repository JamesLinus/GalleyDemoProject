package com.example.meitu.gallerydemoproject.Beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片信息
 * id 图片id
 * imageName 图片名称
 * path 图片的地址
 * album 图片所在的相册名
 * date 图片创建的日期
 */

public class ImageMessage implements Serializable{

    private String id;
    private String imageName;
    private String path;
    private String album;
    private Date date;

    public ImageMessage() {
        this.id = "";
        this.imageName = "";
        this.path = "";
        this.album = "";
        this.date = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (null == id)return;
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        if (null == imageName)return;
        this.imageName = imageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (null == path)return;
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        if (null == album)return;
        this.album = album;
    }

    public Date getDate() {
        return (Date)date.clone();
    }

    public void setDate(long date) {
        if (0 == date)return;
        Date date1 = new Date(date);
        this.date = date1;
    }
}
