package news.tencent.charco.android.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import xiao.free.horizontalrefreshlayout.HorizontalRefreshLayout;

/**
 * Created 18/5/29 23:53
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class MyHorizontalRefreshLayout extends HorizontalRefreshLayout {

    private float downX;
    private LinearLayoutManager mLayoutManager;
    private int size;

    public MyHorizontalRefreshLayout(Context context) {
        super(context);
    }

    public MyHorizontalRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayoutManager(LinearLayoutManager manager){
        this.mLayoutManager = manager;
    }

    public void setItemSize(int size){
        this.size = size;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mLayoutManager!=null){
            int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition == size -1){
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }

        //请求所有父控件及祖宗控件不要拦截事件
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - downX) > 10){//判断是否横向滑动，如果横向滑动则请求父容器不要拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }


}
