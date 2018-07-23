package news.tencent.charco.android.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import news.tencent.charco.android.R;
import xiao.free.horizontalrefreshlayout.RefreshHeader;

/**
 * Created 18/6/4 21:33
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class HotRefrechHead implements RefreshHeader {

    private final Context mContext;
    private TextView mTvTip;

    public HotRefrechHead(Context context) {
        this.mContext = context;
    }


    @Override
    public void onStart(int dragPosition, View refreshHead) {
        mTvTip.setText("查看更多");
    }

    @Override
    public void onDragging(float distance, float percent, View refreshHead) {

    }

    @Override
    public void onReadyToRelease(View refreshHead) {
        mTvTip.setText("松开查看");
    }

    @NonNull
    @Override
    public View getView(ViewGroup container) {
        mTvTip = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_hot_refresh_head,container,false);
        return mTvTip;
    }

    @Override
    public void onRefreshing(View refreshHead) {

    }
}
