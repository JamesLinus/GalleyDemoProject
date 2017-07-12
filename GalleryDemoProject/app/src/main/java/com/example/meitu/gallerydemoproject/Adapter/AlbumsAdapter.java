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
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
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
    private Map<String, AlbumMessage> mMapAlbums;
    private List<String> mListAblumName;

    private ImageLoader mImageLoader;

    public AlbumsAdapter(Context context, Map map){
        mContext = context;
        mMapAlbums = map;
        mListAblumName = new ArrayList<>(mMapAlbums.keySet());
    }

    @Override
    public GalleryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);

        return new GalleryListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryListViewHolder holder, final int position) {

        final String albumName = mListAblumName.get(position);
        String coverImage = new String();
        AlbumMessage album = mMapAlbums.get(albumName);

        holder.tvAblumName.setText(albumName + " (" + album.getAblumSize() + ")");

        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }
        coverImage = album.getCover();
        mImageLoader.displayImage("file://" + coverImage, holder.ivAblumCover);

        holder.vParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyAlbumName = mContext.getString(R.string.album_name);

                Intent intentGalley= new Intent(mContext, GalleyActivity.class);
                intentGalley.putExtra(keyAlbumName, albumName);
                mContext.startActivity(intentGalley);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMapAlbums.size();
    }

    class GalleryListViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAblumCover;
        TextView tvAblumName;
        View vParent;
        public GalleryListViewHolder(View itemView) {
            super(itemView);
            ivAblumCover = (ImageView) itemView.findViewById(R.id.iv_albums_first_image);
            tvAblumName = (TextView)itemView.findViewById(R.id.tv_album_name);
            vParent = (View)itemView.findViewById(R.id.ll_album_item);
        }
    }
}
