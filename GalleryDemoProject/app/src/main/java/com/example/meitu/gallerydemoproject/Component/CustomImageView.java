package com.example.meitu.gallerydemoproject.Component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView {

    private Context mContext;

    private int mWidthSize;
    private int mWidthMode;
    private int mHeightSize;
    private int mHeithtMode;


    private float scaleCenterX;
    private float scaleCenterY;

    private float mScale;

    private float oldDistance = 1;

    private Drawable mContentDrawable;
    private BitmapDrawable mContentBitmap;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initValue();
    }

    private void initValue(){

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        mWidthMode = MeasureSpec.getMode(widthMeasureSpec);

        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        mHeithtMode = MeasureSpec.getMode(heightMeasureSpec);
    }

   @Override
   public void setImageBitmap(Bitmap bitmap){
       super.setImageBitmap(bitmap);
   }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        float centerX = canvas.getWidth();
        float centerY = canvas.getHeight();


//        Rect rect = new Rect(mWidthSize/2, mHeightSize/2, mWidthSize, mHeightSize);

        mContentDrawable = getDrawable();
        mContentBitmap = (BitmapDrawable)mContentDrawable;


        RectF rect = new RectF(mWidthSize - scaleCenterX, mHeightSize-mContentDrawable.getIntrinsicHeight()/2, mContentDrawable.getIntrinsicWidth(), mContentDrawable.getIntrinsicHeight());
        Log.d("custom image view" , scaleCenterX + " " + scaleCenterY);

        Paint paint = new Paint();
        Matrix mMatrix = getMatrix();
        mMatrix.mapRect(rect);
        mMatrix.postScale(mScale, mScale);

        canvas.drawBitmap(mContentBitmap.getBitmap(), mMatrix, paint);
//        canvas.scale(mScale, mScale);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event){
        super.onTouchEvent(event);
        float x1;
        float y1;
        float x2;
        float y2;

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:{
                if (2 == event.getPointerCount()){
                    float oldX1 = event.getX(0);
                    float oldY1 = event.getY(0);

                    float oldX2 = event.getX(1);
                    float oldY2 = event.getY(1);

                    oldDistance = (float) Math.sqrt((oldX1 - oldX2)*(oldX1 - oldX2) + (oldY1 - oldY2)*(oldY1 - oldY2));

                    scaleCenterX = (oldX1 + oldX2) / 2;
                    scaleCenterY = (oldY1 + oldY2) / 2;

//                    Log.d("CustomImageView", oldDistance + " " + scaleCenterX + " " + scaleCenterY);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (1 == event.getPointerCount()){
//                    invalidate();
                }else if (2 == event.getPointerCount()){
                    x1 = event.getX(0);
                    y1 = event.getY(0);

                    x2 = event.getX(1);
                    y2 = event.getY(1);

                    float newDistance = (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
                    mScale = (newDistance/oldDistance);
                    oldDistance = newDistance;
//                    Log.d("CustomImageView", mScale + " " + oldDistance + " " + scaleCenterX + " " + scaleCenterY);
                    invalidate();
                }
                break;
            }
            default:{
                break;
            }
        }

        return true;
    }

}
