package com.example.meitu.gallerydemoproject.Model.bean;

/**
 * 相册信息
 * @author csm
 * mAlbumName 相册名
 * mAlbumSize 图片数
 * mCover 封面uri;
 */
public class AlbumMessage {
    private String mAblumName;
    private int mAlbumSize;
    private String mCover;

    public AlbumMessage() {
        mAblumName = "";
        mAlbumSize = 0;
        mCover = "";
    }

    public String getAblumName() {
        return mAblumName;
    }

    public void setAblumName(String ablumName) {
        mAblumName = ablumName;
    }

    public int getAlbumSize() {
        return mAlbumSize;
    }

    public void setAlbumSize(int albumSize) {
        mAlbumSize = albumSize;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }
}
