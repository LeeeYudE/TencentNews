package news.tencent.charco.android.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created 2018/12/15 10:44
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */
public class NewsDetailBehavior extends CoordinatorLayout.Behavior<View> {

    public NewsDetailBehavior() {
    }

    public NewsDetailBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        Log.d("charco","child "+ child.toString());


        return true;
    }

}
