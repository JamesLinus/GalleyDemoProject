package com.example.meitu.gallerydemoproject.Adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Model.bean.AlbumMessage;
import com.example.meitu.gallerydemoproject.View.Component.CustomThumbnailsImageView;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/26.
 */

public class AlbumsViewHolder extends BaseViewHolder<AlbumMessage>{
    private CustomThumbnailsImageView ivAblumCover;
    private TextView tvAblumName;
    private View vContainer;
    public AlbumsViewHolder(View itemView) {
        super(itemView);
        ivAblumCover = (CustomThumbnailsImageView) itemView.findViewById(R.id.iv_albums_first_image);
        tvAblumName = (TextView)itemView.findViewById(R.id.tv_album_name);
        vContainer = (View)itemView.findViewById(R.id.ll_album_item);
    }

    @Override
    public void setData(AlbumMessage data) {
        ivAblumCover.setImage(data.getCover());
        tvAblumName.setText(data.getAblumName() + " (" + data.getAlbumSize() + ")");
    }

    public void setOnClickListener(View.OnClickListener listener){
        vContainer.setOnClickListener(listener);
    }
}