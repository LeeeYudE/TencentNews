package news.tencent.charco.android.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created 18/6/5 14:20
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：适用于搜索界面的RecyclerView
 * 当用户是横向滑动的时候才 不 请求所有父控件及祖宗控件不要拦截事件
 * 其他情况都请求父容器不要拦截事件
 * 以免drawabLayout关闭
 * ----------------------------------------------------
 */

public class SearchRecyclerView extends RecyclerView {

    private float downX;

    public SearchRecyclerView(Context context) {
        super(context);
    }

    public SearchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getX() - downX) > 30){
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
//        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
