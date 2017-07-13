//package com.example.meitu.gallerydemoproject.Fragment;
//
//import android.app.Fragment;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.meitu.gallerydemoproject.Adapter.ImagesAdapter;
//import com.example.meitu.gallerydemoproject.R;
//import com.example.meitu.gallerydemoproject.Utils.AlbumsMessageUtils;
//
///**
// * Created by meitu on 2017/7/13.
// */
//
//public class ImageFragment extends Fragment {
//
//    public static GalleyFragment newInstance(String albumName) {
//        GalleyFragment fragment = new GalleyFragment();
//        Bundle args = new Bundle();
//        args.putString(ALBUM_NAME, albumName);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mAlbumName = getArguments().getString(ALBUM_NAME);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.galley_fragment, container, false);
//
//        mTvTitle = (TextView)view.findViewById(R.id.tv_title);
//        mTvTitle.setText(mAlbumName);
//        mBtnBack = (View)view.findViewById(R.id.ll_btn);
//        mBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().finish();
//            }
//        });
//
//        mRvImages = (RecyclerView)view.findViewById(R.id.rv_images);
//        return view;
//    }
//
//    @Override
//    public void onStart(){
//        super.onStart();
//        init();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    private void init(){
//        mAlbumsMessageUtils = new AlbumsMessageUtils(getActivity());
//
//        mRvImages.setLayoutManager(new GridLayoutManager(getActivity(), 3));
//
//        mListURI = mAlbumsMessageUtils.getTargetImagePath(mAlbumName);
//
//        mAdapterImages = new ImagesAdapter(getActivity(), mListURI);
//        mRvImages.setAdapter(mAdapterImages);
//
//        //监听RecyclerView滚动状态
//        mRvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(recyclerView.getLayoutManager() != null) {
//                    getPositionAndOffset();
//                }
//            }
//        });
//        scrollToPosition();
//    }
//}
