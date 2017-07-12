package com.example.meitu.gallerydemoproject.Beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by meitu on 2017/7/12.
 */

public class ImageMessage implements Serializable{

    private String id;
    private String imageName;
    private String path;
    private String file;
    private Date date;

    public ImageMessage() {
        this.id = id = "";
        this.imageName = "";
        this.path = "";
        this.file = "";
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        if (null == file)return;
        this.file = file;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Integer date) {
        if (null == date)return;
        Date date1 = new Date(date);
        this.date = date1;
    }
}
