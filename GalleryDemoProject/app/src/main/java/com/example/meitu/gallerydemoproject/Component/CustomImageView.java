package com.example.meitu.gallerydemoproject.Component;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;
/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView{

    private Context mContext;

    private Bitmap mBitmap;

    private RectF mBitmapRectF;
    private Matrix mBitmapMatrix;

    private PointF mStartPoint;

    private float mViewHeight;
    private float mViewWidth;
    private float mBitmapHeight;
    private float mBitmapWidth;

    /**
     *  1为拖拽
     *  2为缩放
     */
    private int dragOrScale = 1;

    private Paint mDeafultPaint;

    private float mScale = 1;
    private float mInitScale = 1;
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
        setScaleType(ScaleType.MATRIX);

        mDeafultPaint = new Paint();

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mStartPoint = new PointF(0, 0);

        mBitmapRectF = new RectF(getWidth()/2 - mBitmap.getWidth()/2, getHeight()/2 - mBitmap.getHeight()/2,
                getWidth()/2 + mBitmap.getWidth()/2, getHeight()/2 + mBitmap.getHeight()/2);
        mBitmapMatrix = new Matrix();

    }

    @Override
    public void onMeasure(int measureSpecWidth, int measureSpecHeight){
        super.onMeasure(measureSpecWidth, measureSpecHeight);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();

        mBitmapHeight = this.mBitmap.getHeight();
        mBitmapWidth = this.mBitmap.getWidth();

        if (mBitmapHeight/mBitmapWidth > mViewHeight / mViewWidth){
            mScale = mViewHeight/mBitmapHeight;
            mBitmapHeight = mViewHeight;
            mBitmapWidth = mBitmapWidth * mScale;
        }else {
            mScale = mViewWidth/mBitmapWidth;
            mBitmapWidth = mViewWidth;
            mBitmapHeight = mBitmapHeight * mScale;

        }
        mInitScale = mScale;
        mRestoreScale = mScale;

        float[] values = new float[9];

        mBitmapMatrix.getValues(values);
        values[0] = mScale;
        values[4] = mScale;
        mBitmapMatrix.setValues(values);

        setImageMatrix(mBitmapMatrix);

    }


    @Override
    public void setImageBitmap(Bitmap bitmap){
        super.setImageBitmap(bitmap);
        mBitmap = bitmap;
        mBitmapMatrix.mapRect(mBitmapRectF);

   }

   @Override
   public void onDraw(Canvas canvas){
       mDeafultPaint = new Paint();

       canvas.translate(getWidth()/2 - mBitmapWidth/2, getHeight()/2 - mBitmapHeight/2);
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
                    startDistance = oldDistance;
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
                        getParent().requestDisallowInterceptTouchEvent(true);

                        float dx = event.getX(1) - event.getX(0);
                        float dy = event.getY(1) - event.getY(0);

                        float newDistance;

                        float centerX = (event.getX(0) + event.getX(1))/2;
                        float centerY = (event.getY(0) + event.getY(1))/2;



                        newDistance = (float) Math.sqrt(dx * dx + dy * dy);


                        mScale = newDistance / oldDistance;
                        mRestoreScale = newDistance / startDistance;

                        oldDistance = newDistance;

                        /** 在中心点处缩放 */
                      mBitmapMatrix.postScale(mScale, mScale, mBitmap.getWidth()*mInitScale/2, mBitmap.getHeight()*mInitScale/2);
//                        mBitmapMatrix.postScale(mScale, mScale);


                        setImageMatrix(mBitmapMatrix);
                        break;
                    }
                }
            }
            case MotionEvent.ACTION_POINTER_UP :{
                mRestoreScale = mRestoreScale * mInitScale;
                if (mRestoreScale < mInitScale ){
                    startAnimationToInitialStatus();
                    mRestoreScale = 1 ;
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
        float[] values = new float[9];
        mBitmapMatrix.getValues(values);

        float oldScale = values[0];

        float oldX = values[2];
        float oldY = values[5];

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(oldScale, mInitScale);
        final ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(oldX, 0);
        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(oldY, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(valueAnimator).with(valueAnimator1).with(valueAnimator2);
        animatorSet.setDuration(800);
        animatorSet.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float)animation.getAnimatedValue();
                float[] values = new float[9];
                mBitmapMatrix.getValues(values);
                values[0] = scale;
                values[4] = scale;

                mBitmapMatrix.setValues(values);
                setImageMatrix(mBitmapMatrix);
            }
        });

        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translation = (float)animation.getAnimatedValue();
                float[] values = new float[9];
                mBitmapMatrix.getValues(values);
                values[2] = translation;

                mBitmapMatrix.setValues(values);
                setImageMatrix(mBitmapMatrix);
            }
        });

        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translation = (float)animation.getAnimatedValue();
                float[] values = new float[9];
                mBitmapMatrix.getValues(values);
                values[5] = translation;

                mBitmapMatrix.setValues(values);
                setImageMatrix(mBitmapMatrix);
            }
        });
    }

}
