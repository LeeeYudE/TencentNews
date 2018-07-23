package news.tencent.charco.android.view.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseActivity;
import news.tencent.charco.android.base.BaseFragment;
import news.tencent.charco.android.utils.ToastUtil;
import news.tencent.charco.android.view.adapter.BaseFragmentAdapter;
import news.tencent.charco.android.view.fragment.MineFragment;
import news.tencent.charco.android.view.fragment.NewsFragment;
import news.tencent.charco.android.view.fragment.RecommendFragment;
import news.tencent.charco.android.view.fragment.VideoFragment;

public class MainActivity extends BaseActivity implements BottomBarLayout.OnItemSelectedListener {

    private ViewPager mViewPager;
    private List<BaseFragment> mFragments;
    private BaseFragmentAdapter mAdapter;
    private BottomBarLayout mBottomBarLayout;
    private long mLastClick;
    private NewsFragment mNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        setWhiteStatus();
        mViewPager = findViewById(R.id.viewpager);
        mBottomBarLayout = findViewById(R.id.bottom_bar);
        mFragments = new ArrayList<>();
        mNewsFragment = new NewsFragment();
        mFragments.add(mNewsFragment);
        mFragments.add(new VideoFragment());
        mFragments.add(new RecommendFragment());
        mFragments.add(new MineFragment());
        mAdapter = new BaseFragmentAdapter(mFragments,getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mBottomBarLayout.setViewPager(mViewPager);
        mBottomBarLayout.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int i) {

    }

    public void showBottomBarLayout() {
        mBottomBarLayout.setVisibility(View.VISIBLE);
    }

    public void hideBottomBarLayout() {
        mBottomBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
        } else if (mNewsFragment.isSeacherOpen()) {
            mNewsFragment.closeSeacher();
        } else {
            if (System.currentTimeMillis() - mLastClick > 2000) {
                ToastUtil.showToast("再按一次退出腾讯资讯");
                mLastClick = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

}
