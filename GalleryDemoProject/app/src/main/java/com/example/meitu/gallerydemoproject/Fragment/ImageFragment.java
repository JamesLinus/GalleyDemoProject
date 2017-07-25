package com.example.meitu.gallerydemoproject.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.meitu.gallerydemoproject.Activity.ImageMessageActivity;
import com.example.meitu.gallerydemoproject.Component.CustomBigImageView;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/13.
 */

public class ImageFragment extends Fragment {
    private static final String IMAGE_URI = "image_URI";

    private CustomBigImageView mIvImage;

    private String imageURI;

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
        mIvImage = (CustomBigImageView)view.findViewById(R.id.iv_big_image);
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        init();
    }

    private void init(){
        mIvImage.setImage(imageURI);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.item_image_message:{
                Intent intent = ImageMessageActivity.newInstance(getActivity(), imageURI);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            }
            default:{
                break;
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
