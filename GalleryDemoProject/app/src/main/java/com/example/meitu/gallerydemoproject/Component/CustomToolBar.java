package com.example.meitu.gallerydemoproject.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meitu.gallerydemoproject.R;


/**
 * Created by meitu on 2017/7/14.
 */

public class CustomToolBar extends LinearLayout {

    private Context mContext;

    private LayoutInflater mLayoutInflater;
    private LinearLayout mToolBar;

    private String mStrBtn;
    private String mStrTitle;

    private TextView mTvBtn;
    private TextView mTvTitle;

    public CustomToolBar(Context context) {
        this(context, null);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initValue(attrs);
        initView();
    }

    private void initValue(AttributeSet attributeSet){
        TypedArray typedArray = mContext.obtainStyledAttributes(attributeSet, R.styleable.custom_title_bar, 0, 0);
        mStrBtn = typedArray.getString(R.styleable.custom_title_bar_btn_name);
        mStrTitle = typedArray.getString(R.styleable.custom_title_bar_title);
        typedArray.recycle();
    }

    private void initView(){
        if (null == mLayoutInflater){
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        mToolBar = (LinearLayout) mLayoutInflater.inflate(R.layout.meitu_custom_tool_bar, (ViewGroup) getRootView(), false);
        addView(mToolBar);
        mToolBar.setVisibility(VISIBLE);

        mTvBtn = (TextView) mToolBar.findViewById(R.id.tv_tool_bar_btn);
        mTvTitle = (TextView)mToolBar.findViewById(R.id.tv_tool_bar_title);

        setButton(mStrBtn);
        setTitle(mStrTitle);
    }

    public void setButton(String btnMessage){
        if (null != btnMessage) {
            mTvBtn.setText(btnMessage);
        }
    }

    public void setTitle(String title){
        if (null != title){
            mTvTitle.setText(title);
        }
    }

    public void setButtonClickListener(OnClickListener onClickListener){
        mTvBtn.setOnClickListener(onClickListener);
    }
}
