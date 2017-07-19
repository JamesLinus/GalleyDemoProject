package com.example.meitu.gallerydemoproject.Component;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;
import com.nostra13.universalimageloader.utils.L;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private Context mContext;
    private Canvas mCanvas;

    private Bitmap mBitmap;
    private RectF mBitmapRectF;
    private Matrix mBitmapMatrix;
    private PointF mStartPoint;


    private float mViewHeight;
    private float mViewWidth;
    private float mBitmapHeight;
    private float mBitmapWidth;
    private float mInitBitmapHeight;
    private float mInitBitmapWidth;


    private boolean once = true;


    /**
     *  1为拖拽
     *  2为缩放
     */
    private int dragOrScale = 1;

    private Paint mDeafultPaint;


    private float mScale = 1;
    private float mRestoreScale = 1;

    private float oldDistance = 0;

    private float startDistance = 0;



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
        mDeafultPaint = new Paint();

       mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mStartPoint = new PointF(0, 0);

        mBitmapRectF = new RectF(getWidth()/2 - mBitmap.getWidth()/2, getHeight()/2 - mBitmap.getHeight()/2,
                getWidth()/2 + mBitmap.getWidth()/2, getHeight()/2 + mBitmap.getHeight()/2);
        mBitmapMatrix = new Matrix();

    }

    @Override
    protected void onAttachedToWindow() {
        // 注册 OnGlobalLayoutListener 的监听
        ViewTreeObserver observer = getViewTreeObserver();
        observer.addOnGlobalLayoutListener(this);
        super.onAttachedToWindow();
    }

    @Override
    public void onGlobalLayout() {
        if (once){
            mViewHeight = getMeasuredHeight();
            mViewWidth = getMeasuredWidth();

            mBitmapHeight = this.mBitmap.getHeight();
            mBitmapWidth = this.mBitmap.getWidth();

            mInitBitmapHeight = mBitmapHeight;
            mInitBitmapWidth = mBitmapWidth;

            float mDrawableWidth = this.getDrawable().getIntrinsicWidth();
            float mDraeableHeight = this.getDrawable().getIntrinsicHeight();

            Log.d("mBitmap", mBitmapWidth + " " + mBitmapHeight);
            Log.d("mView", mViewWidth + " " + mViewHeight);
            Log.d("mDrawable", mDrawableWidth + " " + mDraeableHeight);

            if (mBitmapHeight/mBitmapWidth > mViewHeight / mViewWidth){
                mScale = mViewHeight/mBitmapHeight;
            }else {
                mScale = mViewWidth/mBitmapWidth;
            }
            mBitmapMatrix.postScale(mScale, mScale, mBitmapWidth/2, mBitmapHeight/2);

            setImageMatrix(mBitmapMatrix);
            once = false;
        }
    }

    @Override
    public void onMeasure(int measureSpecWidth, int measureSpecHeight){
        super.onMeasure(measureSpecWidth, measureSpecHeight);

    }


    @Override
    public void setImageBitmap(Bitmap bitmap){
        super.setImageBitmap(bitmap);
        mBitmap = bitmap;
        mBitmapMatrix.mapRect(mBitmapRectF);

   }

   @Override
   public void onDraw(Canvas canvas){
       mCanvas = canvas;
       mDeafultPaint = new Paint();

       canvas.translate(getWidth()/2 - mBitmap.getWidth()/2, getHeight()/2 - mBitmap.getHeight()/2);

       Log.d("canvas", mCanvas.getWidth() + " " + mCanvas.getHeight());
       canvas.drawBitmap(mBitmap, mBitmapMatrix, mDeafultPaint);

   }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:{
                if (2 == event.getPointerCount()){
                    dragOrScale = 2;
                    float dx = event.getX(1) - event.getX(0);
                    float dy = event.getY(1) - event.getY(0);
                    oldDistance = (float) Math.sqrt(dx * dx + dy * dy);
                    startDistance = (float) Math.sqrt(dx * dx + dy * dy);
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:{
                dragOrScale = 1;
                mStartPoint.set(event.getX(), event.getY());
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (1 == event.getPointerCount()){
                    if (1 == dragOrScale){
                        mBitmapMatrix.postTranslate(event.getX() - mStartPoint.x, event.getY() - mStartPoint.y);
                        mStartPoint.set(event.getX(), event.getY());
                        setImageMatrix(mBitmapMatrix);
                        break;
                    }
                }else if (2 == event.getPointerCount()){
                    if (2 == dragOrScale){
                        float dx = event.getX(1) - event.getX(0);
                        float dy = event.getY(1) - event.getY(0);

                        float newDistance;
                        newDistance = (float) Math.sqrt(dx * dx + dy * dy);

                        mScale = newDistance / oldDistance;
                        mRestoreScale = newDistance / startDistance;

                        oldDistance = newDistance;

                        /** 在中心点处缩放 */
                        mBitmapMatrix.postScale(mScale, mScale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);

                        setImageMatrix(mBitmapMatrix);

                        Log.d("drawable ", getDrawable().getIntrinsicHeight() + " " + getDrawable().getIntrinsicWidth());

                        break;
                    }
                }
            }
            case MotionEvent.ACTION_POINTER_UP :{
                    if (mRestoreScale<1){
                        startAnimationToInitialStatus();
                        mRestoreScale = 1;
                    }
                    break;
            }
            default:{
                break;
            }
        }
        return true;
    }

    private void startAnimationToInitialStatus(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 1/mRestoreScale);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float)animation.getAnimatedValue();



                mBitmapMatrix.postScale(scale, scale, mBitmap.getWidth()/2, mBitmap.getHeight()/2);

                Log.d("test", scale + " ");
                setImageMatrix(mBitmapMatrix);
            }
        });

        valueAnimator.setDuration(1000);

        valueAnimator.start();

    }


}
