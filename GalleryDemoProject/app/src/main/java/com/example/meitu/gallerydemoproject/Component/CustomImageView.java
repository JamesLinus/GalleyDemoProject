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
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.meitu.gallerydemoproject.R;

/**
 * Created by meitu on 2017/7/13.
 */

public class CustomImageView extends ImageView{

    private Context mContext;

    private Bitmap mBitmap;

    private Matrix mBitmapMatrix;

    private PointF mLastPoint;

    /**
     * 作为缩放到顶格显示后的图片大小
     * 自行计算得到
     */
    private float mBitmapHeight;
    private float mBitmapWidth;

    /**
     * 初始时的缩放倍数
     */
    private float mInitScale = 1;

    /**
     * 两只手指的上一次触发事件时的距离
     */
    private float mLastDistance = 0;

    private GestureDetector mGestureDetector;

    private ValueAnimator mAnimatorScale;
    private AnimatorSet mAnimatorSetMove;

    public CustomImageView(Context context) {
        this(context, null);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initValues();
    }

    private void initValues(){
        mGestureDetector = new GestureDetector(mContext, new MyGestureListener());

        setScaleType(ScaleType.MATRIX);

        mBitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
        mLastPoint = new PointF(0, 0);

        mBitmapMatrix = new Matrix();

        mAnimatorScale = new ValueAnimator();
        mAnimatorSetMove = new AnimatorSet();
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
            mInitScale = mViewHeight/mBitmapHeight;
            mBitmapHeight = mViewHeight;
            mBitmapWidth = mBitmapWidth * mInitScale;
        }else {
            mInitScale = mViewWidth/mBitmapWidth;
            mBitmapWidth = mViewWidth;
            mBitmapHeight = mBitmapHeight * mInitScale;
        }

        float[] values = new float[9];

        mBitmapMatrix.getValues(values);
        values[0] = mInitScale;
        values[4] = mInitScale;
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

       canvas.translate( (getWidth() - mBitmapWidth)/2f, (getHeight() - mBitmapHeight)/2f);
       canvas.drawBitmap(mBitmap, mBitmapMatrix, mDeafultPaint);

   }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        mGestureDetector.onTouchEvent(event);
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_POINTER_DOWN:{
                if (2 == event.getPointerCount()){
                    mAnimatorScale.cancel();
                    mAnimatorSetMove.cancel();

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

                    float mScale;

                    mScale = newDistance / mLastDistance;
                    mLastDistance = newDistance;

                    //FIXME 放大后双击缩小后，可能会使图片消失；也有可能是移动所产生的BUG
                    float[] values = new float[9];
                    mBitmapMatrix.getValues(values);

                    if ((values[0] >= mInitScale * 3f && mScale > 1f) || (values[0] <= mInitScale / 2.5f && mScale < 1f)) {
                        return false;
                    }
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
                float startX = values[2];
                float startY = values[5];
                if (mFinalScale < mInitScale ){
                    startAnimationScale(mFinalScale, mInitScale, mBitmapMatrix);
                    startAnimationToTargetPoint(startX, startY, 0, 0, mBitmapMatrix);
                }
                break;
            }
            default:{
                break;
            }
        }
        return true;
    }

    private void startAnimationScale(float startScale, float endScale, final Matrix bitmapMatrix){

        mAnimatorScale = ValueAnimator.ofFloat(startScale, endScale);
        mAnimatorScale.setDuration(800);
        mAnimatorScale.start();

        mAnimatorScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float)animation.getAnimatedValue();
                float[] values = new float[9];
                bitmapMatrix.getValues(values);
                values[0] = scale;
                values[4] = scale;

                bitmapMatrix.setValues(values);
                setImageMatrix(bitmapMatrix);
            }
        });
    }

    private void startAnimationToTargetPoint(float startX, float startY, float targetX, float targetY, final Matrix bitmapMatrix){

        final ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(startX, targetX);
        final ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(startY, targetY);

        mAnimatorSetMove.play(valueAnimatorX).with(valueAnimatorY);
        mAnimatorSetMove.setDuration(800);
        mAnimatorSetMove.start();

        valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translation = (float)animation.getAnimatedValue();
                float[] values = new float[9];

                bitmapMatrix.getValues(values);
                values[2] = translation;

                bitmapMatrix.setValues(values);
                setImageMatrix(bitmapMatrix);
            }
        });

        valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float translation = (float)animation.getAnimatedValue();
                float[] values = new float[9];
                Matrix animatorMatrix = mBitmapMatrix;
                animatorMatrix.getValues(values);
                values[5] = translation;

                animatorMatrix.setValues(values);
                setImageMatrix(animatorMatrix);
                animatorMatrix = null;
            }
        });
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent event){
            float[] values = new float[9];

            Matrix doubleTapMatrix = mBitmapMatrix;
            doubleTapMatrix.getValues(values);

            float mScale = values[0];
            float startX = values[2];
            float startY = values[5];

            float dValueX = (mBitmapWidth * 2f - mBitmapWidth) / 2f;
            float dValueY = (mBitmapHeight * 2f - mBitmapHeight) / 2f;

            if (mScale < mInitScale * 2f && startX == 0 && startY == 0){
                startAnimationScale(mScale, mInitScale * 2f, doubleTapMatrix);
                startAnimationToTargetPoint(startX, startY, -dValueX, -dValueY, doubleTapMatrix);
            }else {
                startAnimationScale(mScale, mInitScale, doubleTapMatrix);
                startAnimationToTargetPoint( startX, startY, 0, 0, doubleTapMatrix);
            }

            doubleTapMatrix = null;
            return super.onDoubleTap(event);
        }
    }
}
