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
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.meitu.gallerydemoproject.R;
/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView{

    private Bitmap mBitmap;

    private Matrix mBitmapMatrix;

    private PointF mLastPoint;

    /**
     * 作为缩放到顶格显示后的图片大小
     * 自行计算得到
     */
    private float mBitmapHeight;
    private float mBitmapWidth;


    private float mScale = 1;
    /**
     * 初始时的缩放倍数
     */
    private float mInitScale = 1;

    /**
     * 两只手指的上一次触发事件时的距离
     */
    private float mLastDistance = 0;


    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues();
    }

    private void initValues(){
        setScaleType(ScaleType.MATRIX);

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mLastPoint = new PointF(0, 0);

        mBitmapMatrix = new Matrix();

    }

    /**
     * 测量时，测量图片的长宽比与控件的长宽比
     * 若mBitmapHeight/mBitmapWidth > mViewHeight / mViewWidth，则将图片长度调整为与View相同
     * mBitmapHeight/mBitmapWidth < mViewHeight / mViewWidth， 则将图片的宽度调整为与View相同
     * 缩放完后，记录缩放比以及图片长宽；
     * @param measureSpecWidth
     * @param measureSpecHeight
     */
    @Override
    public void onMeasure(int measureSpecWidth, int measureSpecHeight){
        super.onMeasure(measureSpecWidth, measureSpecHeight);

        float mViewHeight = getMeasuredHeight();
        float mViewWidth = getMeasuredWidth();

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

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
   }


    /** 将画布的原点移动到  getWidth()/2 - mBitmapWidth/2, getHeight()/2 - mBitmapHeight/2)；使得图片中心在View中心*/
   @Override
   public void onDraw(Canvas canvas){
       Paint mDeafultPaint = new Paint();

       canvas.translate(getWidth()/2f - mBitmapWidth/2f, getHeight()/2f - mBitmapHeight/2f);
       canvas.drawBitmap(mBitmap, mBitmapMatrix, mDeafultPaint);

   }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:{
                if (2 == event.getPointerCount()){
                    float dx = event.getX(1) - event.getX(0);
                    float dy = event.getY(1) - event.getY(0);
                    mLastDistance = (float) Math.sqrt(dx * dx + dy * dy);

                    float centerX = (event.getX(0) + event.getX(1))/2;
                    float centerY = (event.getY(0) + event.getY(1))/2;

                    mLastPoint.set(centerX, centerY);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (2 == event.getPointerCount()){
                    getParent().requestDisallowInterceptTouchEvent(true);
                    float dx = event.getX(1) - event.getX(0);
                    float dy = event.getY(1) - event.getY(0);

                    float centerX = (event.getX(0) + event.getX(1))/2;
                    float centerY = (event.getY(0) + event.getY(1))/2;

                    float newDistance = (float) Math.sqrt(dx * dx + dy * dy);

                    mScale = newDistance / mLastDistance;
                    mLastDistance = newDistance;

                    /** 在中心点处缩放 */
                    mBitmapMatrix.postScale(mScale, mScale, mBitmapWidth/2f, mBitmapHeight/2f);
                    mBitmapMatrix.postTranslate(centerX - mLastPoint.x, centerY - mLastPoint.y);
                    mLastPoint.set(centerX, centerY);

                    setImageMatrix(mBitmapMatrix);
                    break;
                }
            }
            case MotionEvent.ACTION_POINTER_UP :{
                float[] values = new float[9];
                mBitmapMatrix.getValues(values);

                float mFinalScale = values[0];
                if (mFinalScale < mInitScale ){
                    startAnimationToInitialStatus();
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
