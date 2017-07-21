package com.example.meitu.gallerydemoproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Activity.AlbumActivity;
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.Fragment.AlbumFragment;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.LoadImageUtil;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.GalleryListViewHolder> {

    private Context mContext;
    private List<AlbumMessage> mMapAlbumsMessage;

    public AlbumsAdapter(Context context, List albumMessages){
        mContext = context;
        mMapAlbumsMessage = albumMessages;
    }

    @Override
    public GalleryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);

        return new GalleryListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(GalleryListViewHolder holder, final int position) {

        AlbumMessage albumMessage = mMapAlbumsMessage.get(position);
        final String albumName = albumMessage.getAblumName();
        String coverImage = albumMessage.getCover();
        int albumSize = albumMessage.getAlbumSize();

        holder.tvAblumName.setText(albumName + " (" + albumSize + ")");

        LoadImageUtil.loadImage(mContext, holder.ivAblumCover, coverImage);

        holder.vContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlbumFragment.AlbumCallBack)mContext).showAlbumFragment(albumName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMapAlbumsMessage.size();
    }

    class GalleryListViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAblumCover;
        TextView tvAblumName;
        View vContainer;
        public GalleryListViewHolder(View itemView) {
            super(itemView);
            ivAblumCover = (ImageView) itemView.findViewById(R.id.iv_albums_first_image);
            tvAblumName = (TextView)itemView.findViewById(R.id.tv_album_name);
            vContainer = (View)itemView.findViewById(R.id.ll_album_item);
        }
    }
}
