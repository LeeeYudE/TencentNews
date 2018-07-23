package news.tencent.charco.android.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerManager;
import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseFragment;
import news.tencent.charco.android.utils.AnimationUtil;
import news.tencent.charco.android.utils.ToastUtil;
import news.tencent.charco.android.view.activity.AlbumActivity;
import news.tencent.charco.android.view.activity.WebViewActivity;
import news.tencent.charco.android.view.adapter.NewsListAdapter;
import news.tencent.charco.android.widget.popup.LoseInterestPopup;

/**
 * Created 18/7/19 16:39
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsListFragment extends BaseFragment implements RecyclerView.OnChildAttachStateChangeListener, OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, LoseInterestPopup.OnLoseInterestListener {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView mTvTip;
    private NewsListAdapter mAdapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void initView(View rootView) {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setEnableOverScrollBounce(false);//是否启用越界回弹
        mSmartRefreshLayout.setEnableOverScrollDrag(false);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new NewsListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnChildAttachStateChangeListener(this);
        mTvTip = findViewById(R.id.tv_tip);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public void initListener() {
        NewsFragment fragment = (NewsFragment) getParentFragment();
        mRecyclerView.addOnScrollListener(fragment.getScrollListener());
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onChildViewAttachedToWindow(View view) {

    }

    @Override
    public void onChildViewDetachedFromWindow(View view) {
        JZVideoPlayer jzvd = view.findViewById(R.id.video_player);
        if (jzvd!=null){
            Object[] dataSourceObjects = jzvd.dataSourceObjects;
            if (dataSourceObjects!=null&&
                    JZUtils.dataSourceObjectsContainsUri(dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                JZVideoPlayer currentJzvd = JZVideoPlayerManager.getCurrentJzvd();
                if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                    JZVideoPlayer.releaseAllVideos();
                }
            }
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        mSmartRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSmartRefreshLayout.finishRefresh(0);
                AnimationUtil.showTipView(mTvTip,mRecyclerView);
            }
        },2000);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position % 7){
            case 0:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case 1:
//                return NEWS_HOT_LIST;
            case 2:
//                return NEWS_HOT_TEXT;
            case 3:
//                return NEWS_SIMPLE_PHOTO;
            case 4:
//                return NEWS_THREE_PHOTO;
            case 5:
                startActivity(new Intent(getActivity(), AlbumActivity.class));
                break;
            case 6:
//                return NEWS_VIDEO;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.iv_delete:
                new LoseInterestPopup(getActivity())
                        .setSource("腾讯新闻")
                        .setPosition(position)
                        .setOnLoseInterestListener(this)
                .showPopup(view);
                break;
        }
    }


    @Override
    public void onLoseInterestListener(int poor_quality, int repeat, String source, int position) {
        ToastUtil.showToast("position = "+position);
    }
}
