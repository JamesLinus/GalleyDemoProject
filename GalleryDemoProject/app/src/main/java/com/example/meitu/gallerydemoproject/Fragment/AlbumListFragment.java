package com.example.meitu.gallerydemoproject.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Adapter.AlbumsAdapter;
import com.example.meitu.gallerydemoproject.Beans.AlbumMessage;
import com.example.meitu.gallerydemoproject.Component.CustomToolBar;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumOperatingUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meitu on 2017/7/21.
 */

public class AlbumListFragment extends Fragment {

    private static final String TAG = "AlbumsActivity.activity";

    /**键值对保存 相册名和相册信息 */
    private Map<String, AlbumMessage> mMapAlbumsMessage;

    private RecyclerView mRvAlbums;
    private AlbumsAdapter mAdapterAlbums;

    private CustomToolBar mCustomToolBar;

    public interface AlbumListCallBack{
        void showAlbumsListFragment();
    }

    public static AlbumListFragment newInstance(){
        AlbumListFragment albumListFragment = new AlbumListFragment();
        return albumListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (null != getArguments()){

        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view = layoutInflater.inflate(R.layout.albums_list_fragment, container, false);

        mCustomToolBar = (CustomToolBar)view.findViewById(R.id.ctb_albums);
        mCustomToolBar.setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }
        });

        mRvAlbums = (RecyclerView)view.findViewById(R.id.rv_albums);
        mRvAlbums.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMapAlbumsMessage = AlbumOperatingUtils.getAlbumsMessage(getActivity());
        List<AlbumMessage> albumMessages = new ArrayList<>(mMapAlbumsMessage.values());

        mAdapterAlbums = new AlbumsAdapter(getActivity(), albumMessages);
        mRvAlbums.setAdapter(mAdapterAlbums);
        return view;
    }
}
