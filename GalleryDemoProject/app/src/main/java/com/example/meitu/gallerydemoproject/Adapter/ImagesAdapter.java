package com.example.meitu.gallerydemoproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.View.Activity.ImagePagerActivity;
import com.example.meitu.gallerydemoproject.Adapter.ViewHolder.ImagesViewHolder;
import com.example.meitu.gallerydemoproject.R;

import java.util.List;

/**
 * @author Csm1
 * 相册中图片的RecyclerView的Adapter
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesViewHolder> {

    private Context mContext;
    private List<String> mListURI;

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
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.image_item, parent, false);
        return new ImagesViewHolder(mContext, view);
    }

    /**
     * 绑定视图
     * 加载图片
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ImagesViewHolder holder, int position) {
        final String imageURI;
        imageURI = mListURI.get(position);

        holder.setData(imageURI);

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImagePager = ImagePagerActivity.newInstance(mContext, mListURI, imageURI);
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
    public void onViewRecycled(ImagesViewHolder holder){
        super.onViewRecycled(holder);
        holder.setData(R.mipmap.ic_launcher);
    }

}
