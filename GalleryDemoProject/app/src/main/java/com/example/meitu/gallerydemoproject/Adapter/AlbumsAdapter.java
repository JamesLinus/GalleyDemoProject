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
    private Map<String, List<String>> mMapDatas;
    private Map<String, Integer> mMapImagesNum;
    private List<String> mListImageName;

    private ImageLoader mImageLoader;
    private DisplayImageOptions mOptions;

    public AlbumsAdapter(Context context, Map map, Map map2){
        mContext = context;
        mMapDatas = map;
        mMapImagesNum = map2;
    }

    @Override
    public GalleryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);
        return new GalleryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryListViewHolder holder, final int position) {
        mListImageName = new ArrayList<>(mMapDatas.keySet());
        String albumName = mListImageName.get(position);
        List<String> mListImages = new ArrayList<>();
        int images = mMapImagesNum.get(albumName);

        holder.mTextView.setText(albumName + " (" + images + ")");

        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }
        mListImages = mMapDatas.get(albumName);

        mImageLoader.displayImage("file://" + mListImages.get(0), holder.mImageView);

        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGalley= new Intent(mContext, GalleyActivity.class);
                String keyAlbumName = mContext.getString(R.string.album_name);

                intentGalley.putExtra(keyAlbumName, mListImageName.get(position));
                mContext.startActivity(intentGalley);
            }
        });
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGalley= new Intent(mContext, GalleyActivity.class);
                String albumName = mContext.getString(R.string.album_name);

                intentGalley.putExtra(albumName, mListImageName.get(position));
                mContext.startActivity(intentGalley);
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
