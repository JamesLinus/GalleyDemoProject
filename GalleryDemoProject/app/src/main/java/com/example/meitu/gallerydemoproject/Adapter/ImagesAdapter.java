package com.example.meitu.gallerydemoproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.Activity.ImagePagerActivity;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.LoadImageUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Csm1
 * 相册中图片的RecyclerView的Adapter
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

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

        //TODO 视图复用差错的修复;

//        String tag= (String) holder.mImageView.getTag();
//        if (!imageURI.equals(tag)){
//            holder.mImageView.setTag(imageURI);
//            //设置图片
//        }

        LoadImageUtil.loadImage(mContext, holder.mImageView, imageURI);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ImagePagerActivity.class);
                String imageUriKey = mContext.getString(R.string.album_name_key);
                String imagesListKey = mContext.getString(R.string.images_list_key);

                intent.putStringArrayListExtra(imagesListKey, (ArrayList<String>) mListURI);
                intent.putExtra(imageUriKey, imageURI);

                mContext.startActivity(intent);
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
        ImageView mImageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.iv_recent_image);
        }
    }
}
