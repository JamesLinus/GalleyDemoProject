package com.example.meitu.gallerydemoproject.Adapter.ViewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.meitu.gallerydemoproject.View.Component.CustomThumbnailsImageView;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/26.
 */

public class ImagesViewHolder extends BaseViewHolder<String> {
    private Context mContext;

    private CustomThumbnailsImageView mImageView;

    public ImagesViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;

        mImageView = (CustomThumbnailsImageView)itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void setData(String data) {
        mImageView.setImage(data);
    }

    public void setData(int imageId){
        mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
    }

    public void setOnClickListener(View.OnClickListener listener){
        mImageView.setOnClickListener(listener);
    }
}
