package com.example.meitu.gallerydemoproject.Beans;

/**
 * Created by meitu on 2017/7/12.
 */

public class AlbumMessage {
    private String mAblumName;
    private int mAblumSize;
    private String mCover;

    public AlbumMessage() {
        mAblumName = "";
        mAblumSize = 0;
        mCover = "";
    }

    public String getAblumName() {
        return mAblumName;
    }

    public void setAblumName(String ablumName) {
        mAblumName = ablumName;
    }

    public int getAblumSize() {
        return mAblumSize;
    }

    public void setAblumSize(int ablumSize) {
        mAblumSize = ablumSize;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }
}
