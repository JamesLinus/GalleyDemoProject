package com.example.meitu.gallerydemoproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Activity.ImagePagerActivity;
import com.example.meitu.gallerydemoproject.Component.CustomThumbnailsImageView;
import com.example.meitu.gallerydemoproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Csm1
 * 相册中图片的RecyclerView的Adapter
 */

public class RecentImagesGridAdapter extends RecyclerView.Adapter<RecentImagesGridAdapter.ImageViewHolder> {

    private Context mContext;
    private Map<String, List<String>> mStringListMap;

    private List<String> mListURI;
    private List<String> mListAllURI;

    /**
     * 构造器
     * @param context 传入Activity的context
     * @param stringListMap
     * @param key
     */
    public RecentImagesGridAdapter(Context context, Map<String, List<String>> stringListMap, String key){
        mContext = context;
        mStringListMap = stringListMap;
        mListURI = mStringListMap.get(key);

        List<String> mKeys = new ArrayList<>(mStringListMap.keySet());
        Collections.reverse(mKeys);
        Iterator iterator = mKeys.iterator();
        mListAllURI = new ArrayList<>();
        while (iterator.hasNext()){
            mListAllURI.addAll(mStringListMap.get(iterator.next()));
        }

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    /**
     * 绑定视图
     * 加载图片
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        final String imageURI;
        imageURI = mListURI.get(position);

        holder.mImageView.setImage(imageURI);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImagePager = ImagePagerActivity.newInstance(mContext, mListAllURI, imageURI);

                mContext.startActivity(intentImagePager);
                ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
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
        CustomThumbnailsImageView mImageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (CustomThumbnailsImageView)itemView.findViewById(R.id.iv_recent_image);
        }
    }
}
