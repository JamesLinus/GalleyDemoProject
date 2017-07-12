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
 * @author Csm1
 * 相册中图片的RecyclerView的Adapter
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private Context mContext;
    private List<String> mListURI;

    private ImageLoader mImageLoader;


    /**
     * 构造器
     * @param context 传入Activity的context
     * @param listURI 传入该相册图片的URI集合
     */
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


    /**
     * 加载图片
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String URI = new String();
        URI = mListURI.get(position);
//        if (null == holder.mImageView.getTag())holder.mImageView.setTag(URI);
//        else {
//            if (!holder.mImageView.getTag().equals(URI)){
//                holder.mImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher));
//            }
//        }


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
