package com.example.meitu.gallerydemoproject.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.Activity.ImageActivity;
import com.example.meitu.gallerydemoproject.Activity.ImageMessageActivity;
import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
import com.example.meitu.gallerydemoproject.R;
import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by meitu on 2017/7/13.
 */

public class ImageFragment extends Fragment {
    private static final String IMAGE_URI = "image_URI";

    private ImageView mIvImage;

    private String imageURI;

    private ImageLoader mImageLoader;

    public static ImageFragment newInstance(String imageURI) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URI, imageURI);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            imageURI = getArguments().getString(IMAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment, container, false);
        mIvImage = (ImageView)view.findViewById(R.id.iv_big_image);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void init(){
        mImageLoader = ImageLoader.getInstance();
        if (!mImageLoader.isInited()) {
            mImageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        }
        mImageLoader.displayImage("file://" + imageURI, mIvImage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.item_image_message:{
                Intent intent = new Intent(getActivity(), ImageMessageActivity.class);
                String imageMessageKey = getString(R.string.image_message_key);
                intent.putExtra(imageMessageKey, imageURI);
                startActivity(intent);
                break;
            }
            default:{
                break;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
