package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
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
        //holder.mImageView.setImageDrawable();
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }
        mImageLoader.displayImage("file://" + mListURI.get(position),holder.mImageView);
        //holder.mTextView.setText(mListURI.get(position));
    }

    @Override
    public int getItemCount() {
        return mListURI.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        //TextView mTextView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.iv_recent_image);
            //mTextView = (TextView)itemView;
        }
    }
}
