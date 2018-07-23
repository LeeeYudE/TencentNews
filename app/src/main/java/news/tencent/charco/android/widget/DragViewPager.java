package news.tencent.charco.android.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.github.chrisbanes.photoview.PhotoView;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

import news.tencent.charco.android.utils.UIUtils;


/**
 * Created by Johnson on 2018/4/18.
 */

public class DragViewPager extends ViewPager implements View.OnClickListener, Animator.AnimatorListener {
    public static final int STATUS_NORMAL = 0;//正常浏览状态
    public static final int STATUS_MOVING = 1;//滑动状态
    public static final int STATUS_RESETTING = 2;//返回中状态
    public static final String TAG = "DragViewPager";


    public static final float MIN_SCALE_SIZE = 0.3f;//最小缩放比例
    public static final int BACK_DURATION = 300;//ms
    public static final int DRAG_GAP_PX = 50;

    private int currentStatus = STATUS_NORMAL;
    private int currentPageStatus;

    private float mDownX;
    private float mDownY;
    private float screenHeight;

    /**
     * 要缩放的View
     */
    private PhotoView currentShowView;
    /**
     * 滑动速度检测类
     */
    private VelocityTracker mVelocityTracker;
    private OnDragListener mListener;
    private float translationY;

    public void setOnDragListener(OnDragListener listener) {
        this.mListener = listener;
    }

    public DragViewPager(Context context) {
        super(context);
        init(context);
    }

    public DragViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        screenHeight = UIUtils.getScreenHeight();
        setBackgroundColor(Color.BLACK);
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentPageStatus = state;
            }
        });
    }


    public void setCurrentShowView(PhotoView currentShowView) {
        this.currentShowView = currentShowView;
        if (this.currentShowView != null) {
            this.currentShowView.setOnClickListener(this);
        }
    }



    //配合SubsamplingScaleImageView使用，根据需要拦截ACTION_MOVE
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getRawX();
                    mDownY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = ev.getPointerCount();
                    if (pointerCount > 1){
                        return false;
                    }
                    float x = mDownX - ev.getX();
                    if (Math.abs(x) > DRAG_GAP_PX){
                        return super.onInterceptTouchEvent(ev);
                    }
                    if (currentShowView.getScale() == 1) {
                        int deltaY = Math.abs((int) (ev.getRawY() - mDownY));
                        if (deltaY > DRAG_GAP_PX ) {//往下移动超过临界，左右移动不超过临界时，拦截滑动事件
                            return true;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
        }
            return super.onInterceptTouchEvent(ev);
        }catch (Exception e){
                e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (currentStatus == STATUS_RESETTING)
            return false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getRawX();
                mDownY = ev.getRawY();
                addIntoVelocity(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                translationY = ev.getY() - mDownY;
                addIntoVelocity(ev);
                int deltaY = Math.abs((int) (ev.getRawY() - mDownY));
                //手指往上滑动
                if (deltaY <= DRAG_GAP_PX && currentStatus != STATUS_MOVING)
                    return super.onTouchEvent(ev);
                //viewpager不在切换中，并且手指往下滑动，开始缩放
                if (currentPageStatus != SCROLL_STATE_DRAGGING && (deltaY > DRAG_GAP_PX || currentStatus == STATUS_MOVING)) {
                    moveView( ev.getRawY());
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (currentStatus != STATUS_MOVING)
                    return super.onTouchEvent(ev);
                final float mUpX = ev.getRawX();
                final float mUpY = ev.getRawY();

                float vY = computeYVelocity();//松开时必须释放VelocityTracker资源
                if (vY >= 1200 || Math.abs(mUpY - mDownY) > screenHeight / 6) {
                    //下滑速度快，或者下滑距离超过屏幕高度的一半，就关闭

                    if(mUpY > screenHeight/2){
                        downTranslationAmination();
                    }else {
                        upTranslationAmination();
                    }

                } else {
                    resetReviewState(mUpX, mUpY);
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    //缩手后image自动下滑退出
    private void upTranslationAmination(){

        setBackgroundColor(getBlackAlpha(0));
        ValueAnimator valueAnimator = ValueAnimator.ofFloat( translationY, -currentShowView.getHeight());
        valueAnimator.setDuration(BACK_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mY = (float) animation.getAnimatedValue();
//                moveView(mY);
                ViewHelper.setTranslationY(currentShowView, mY);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                if (mListener != null) {
                    mListener.onPictureRelease(currentShowView);
                }
            }
        });
        valueAnimator.start();
    }

    //缩手后image自动下滑退出
    private void downTranslationAmination(){
        setBackgroundColor(getBlackAlpha(0));
        ValueAnimator valueAnimator = ValueAnimator.ofFloat( translationY, currentShowView.getHeight());
        valueAnimator.setDuration(BACK_DURATION);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mY = (float) animation.getAnimatedValue();
//                moveView(mY);
                ViewHelper.setTranslationY(currentShowView, mY);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                if (mListener != null) {
                    mListener.onPictureRelease(currentShowView);
                }
            }
        });
        valueAnimator.start();
    }

    //返回浏览状态
    private void resetReviewState(final float mUpX, final float mUpY) {
        currentStatus = STATUS_RESETTING;
        if (mUpY != mDownY) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(mUpY, mDownY);
            valueAnimator.setDuration(BACK_DURATION);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float mY = (float) animation.getAnimatedValue();
                    moveView(mY);
                    if (mY == mDownY) {
                        mDownY = 0;
                        mDownX = 0;
                        currentStatus = STATUS_NORMAL;
                        if (mListener!=null){
                            mListener.onReset();
                        }
                    }
                }
            });
            valueAnimator.start();
        } else if (mListener != null){
            mListener.onPictureClick();
        }
    }

    @Override
    public float getTranslationY() {
        return translationY;
    }

    @Override
    public void setTranslationY(float translationY) {
        this.translationY = translationY;
        ViewHelper.setTranslationY(currentShowView, translationY);
    }

    //移动View
    private void moveView( float movingY) {
        if (currentShowView == null){
            return;

        }
        if (currentPageStatus == STATUS_NORMAL){
            if (mListener != null){
                mListener.onStartDrag();
            }
        }
        currentStatus = STATUS_MOVING;
        float deltaY = movingY - mDownY;
        float alphaPercent  = 1 - Math.abs(deltaY) / (screenHeight / 2);
        if (alphaPercent<0){
            alphaPercent = 0;
        }

        setBackgroundColor(getBlackAlpha(alphaPercent));
        ViewHelper.setTranslationY(currentShowView, deltaY);
    }

    private int getBlackAlpha(float percent) {
        percent = Math.min(1, Math.max(0, percent));
        int intAlpha = (int) (percent * 255);
        return Color.argb(intAlpha,0,0,0);
    }

    private void addIntoVelocity(MotionEvent event) {
        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(event);
    }

    private float computeYVelocity() {
        float result = 0;
        if (mVelocityTracker != null) {
            mVelocityTracker.computeCurrentVelocity(1000);
            result = mVelocityTracker.getYVelocity();
            releaseVelocity();
        }
        return result;
    }

    private void releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onPictureClick();
        }
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        if (mListener != null) {
            mListener.onPictureRelease(currentShowView);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }


    public interface OnDragListener {
        void onPictureClick();

        void onPictureRelease(View view);

        void onStartDrag();

        void onReset();

    }

}
