package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Activity.GalleyActivity;
import com.example.meitu.gallerydemoproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/11.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.GalleryListViewHolder> {

    private Context mContext;
    private Map<String, String> mMapDatas;
    private List<String> mListDatas;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    public AlbumsAdapter(Context context, Map map){
        mContext = context;
        mMapDatas = map;
    }

    @Override
    public GalleryListViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);
        final GalleryListViewHolder holder = new GalleryListViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryListViewHolder holder, final int position) {
        mListDatas = new ArrayList<>(mMapDatas.keySet());
        String key = mListDatas.get(position);
        holder.mTextView.setText(key);

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        mImageLoader.displayImage("file://" + mMapDatas.get(key), holder.mImageView);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentGalley = new Intent(mContext, GalleyActivity.class);
                String mStrGalleyName = mContext.getString(R.string.album_name);

                mIntentGalley.putExtra(mStrGalleyName, mListDatas.get(position));
                mContext.startActivity(mIntentGalley);
            }
        });
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntentGalley = new Intent(mContext, GalleyActivity.class);
                String mStrGalleyName = mContext.getString(R.string.album_name);

                mIntentGalley.putExtra(mStrGalleyName, mListDatas.get(position));
                mContext.startActivity(mIntentGalley);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMapDatas.size();
    }

    class GalleryListViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public GalleryListViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_albums_first_image);
            mTextView = (TextView)itemView.findViewById(R.id.tv_album_name);
        }
    }
}
