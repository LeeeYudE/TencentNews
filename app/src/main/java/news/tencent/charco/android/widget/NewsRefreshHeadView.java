package news.tencent.charco.android.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import news.tencent.charco.android.R;


/**
 * Created 18/6/15 13:23
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsRefreshHeadView extends FrameLayout  implements RefreshHeader {

    private TextView mTvHint;
    private ImageView mIvLogo;
    private ObjectAnimator mRotateAnimator;
    private float mCurrentRotate = 0f;
    private boolean isAnimation = false;

    public NewsRefreshHeadView(@NonNull Context context) {
        this(context,null);
    }

    public NewsRefreshHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewsRefreshHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_news_refresh_head,this);
        mTvHint = findViewById(R.id.tv_hint);
        mIvLogo = findViewById(R.id.iv_logo);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    private void startAnimation(){
        mRotateAnimator = ObjectAnimator.ofFloat(mIvLogo, "rotation", 0, 360f );
        mRotateAnimator.setDuration(500);
        mRotateAnimator.setInterpolator(new LinearInterpolator());
        mRotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRotateAnimator.setRepeatMode(ValueAnimator.RESTART);
        mRotateAnimator.start();
        isAnimation = true;
    }

    private void stopAnimation(){
        if (mRotateAnimator != null){
            mRotateAnimator.cancel();
        }
        mIvLogo.setRotation(0);
        isAnimation = false;
//        mAnimation.cancel();
    }


    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
//        KLog.d("onPulling percent = "+percent +" offset = "+offset + " height = "+height +" extendHeight = "+extendHeight);
        mCurrentRotate = 360*percent;
        mIvLogo.setRotation(mCurrentRotate);
        mIvLogo.setAlpha(percent);
    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {
//        KLog.d("onReleasing percent = "+percent +" offset = "+offset + " height = "+height +" extendHeight = "+extendHeight);
        mCurrentRotate = 360*percent;
        if (!isAnimation){
            mIvLogo.setRotation(mCurrentRotate);
        }
        if (offset == 0){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopAnimation();
                }
            },500);
        }
    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {
//        KLog.d("onReleased percent = "+refreshLayout +" height = "+height + " extendHeight = "+extendHeight);

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
//        KLog.d("onStartAnimator percent = "+refreshLayout +" height = "+height + " extendHeight = "+extendHeight);
        startAnimation();
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        KLog.d("onFinish");

        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
//        KLog.d("onHorizontalDrag percentX = "+percentX +" offsetX = "+offsetX + " offsetMax = "+offsetMax);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mTvHint.setText("下拉获取新闻");
                break;
            case Refreshing:
                mTvHint.setText("正在发现新闻");
                break;
            case ReleaseToRefresh:
                mTvHint.setText("释放获取新闻");
                break;
        }
    }
}
