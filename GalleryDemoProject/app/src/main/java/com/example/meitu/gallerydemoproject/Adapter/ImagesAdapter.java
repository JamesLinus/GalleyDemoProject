package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private Context mContext;
    private List<String> mListURI;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;


    public ImagesAdapter(Context context, List listURI){
        mContext = context;
        mListURI = listURI;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.recent_image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }
        mImageLoader.displayImage("file://" + mListURI.get(position),holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mListURI.size();
    }

    /**
     * 若视图被复用 调用该函数时
     * 将ImageView设置为默认图片，避免旧视图中的图片被加载导致视图混乱
     * @param holder
     */
    @Override
    public void onViewRecycled(ImageViewHolder holder){
        super.onViewRecycled(holder);
        holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.iv_recent_image);
        }
    }
}
