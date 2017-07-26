package com.example.meitu.gallerydemoproject.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.View.Activity.GellayListActivity;
import com.example.meitu.gallerydemoproject.Adapter.ViewHolder.AlbumsViewHolder;
import com.example.meitu.gallerydemoproject.Model.bean.AlbumMessage;
import com.example.meitu.gallerydemoproject.R;

import java.util.List;

/**
 * Created by meitu on 2017/7/11.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsViewHolder> {

    private Context mContext;
    private List<AlbumMessage> mMapAlbumsMessage;

    public AlbumsAdapter(Context context, List albumMessages){
        mContext = context;
        mMapAlbumsMessage = albumMessages;
    }

    @Override
    public AlbumsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.album_item, parent, false);

        return new AlbumsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlbumsViewHolder holder, final int position) {

        AlbumMessage albumMessage = mMapAlbumsMessage.get(position);
        final String albumName = albumMessage.getAblumName();

        holder.setData(albumMessage);
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GellayListActivity)mContext).showAlbumFragment(albumName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMapAlbumsMessage.size();
    }


}
