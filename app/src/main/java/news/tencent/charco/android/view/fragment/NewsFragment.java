package news.tencent.charco.android.view.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseFragment;
import news.tencent.charco.android.utils.ToastUtil;
import news.tencent.charco.android.utils.UIUtils;
import news.tencent.charco.android.view.activity.MainActivity;
import news.tencent.charco.android.view.adapter.BaseFragmentAdapter;
import news.tencent.charco.android.view.adapter.SearchAdapter;
import news.tencent.charco.android.widget.SearchRecyclerView;
import news.tencent.charco.android.widget.magicindicator.MagicIndicator;
import news.tencent.charco.android.widget.magicindicator.buildins.UIUtil;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.CommonNavigator;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import news.tencent.charco.android.widget.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

/**
 * Created 18/7/5 11:09
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    public static final int offset = 100;

    private ViewPager mViewPager;
    private MainActivity mMainActivity;
    private View mLltNews;
    private DrawerLayout mDrawerLayout;
    private List<BaseFragment> fragments = new ArrayList<>();
    private MagicIndicator mIndicator;
    private String[] channels ;
    private SearchRecyclerView mSearchRecyclerView;
    private SearchAdapter mSearchAdapter;
    private int mDisplayWidth;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initView(View rootView) {
        mViewPager = findViewById(R.id.viewpager);
        mIndicator= findViewById(R.id.magic_indicator);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mLltNews = findViewById(R.id.llt_news);
        //设置搜索界面宽度为屏幕宽度
        mDisplayWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout mLltSearch = rootView.findViewById(R.id.llt_search);
        ViewGroup.LayoutParams layoutParams = mLltSearch.getLayoutParams();
        layoutParams.width = mDisplayWidth;
        mLltSearch.setLayoutParams(layoutParams);
        mSearchRecyclerView = findViewById(R.id.recyclerView);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mSearchAdapter = new SearchAdapter(Arrays.asList(getResources().getStringArray(R.array.hot)));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        channels = getResources().getStringArray(R.array.channel);
        initIndicator();
        for (String channel : channels){
            fragments.add(new NewsListFragment());
        }
        mViewPager.setAdapter(new BaseFragmentAdapter(fragments,getChildFragmentManager()));
    }

    @Override
    public void initListener() {
        findViewById(R.id.tv_seacher).setOnClickListener(this);
        findViewById(R.id.tv_close_search).setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //position为0的时候才能打开搜索界面
                if (position == 0){
                    unlockDrawer();
                }else {
                    lockDrawer();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mIndicator.onPageScrollStateChanged(state);
            }
        });
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                mLltNews.scrollTo((int) (-mDisplayWidth * slideOffset * 0.8),0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mMainActivity.hideBottomBarLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mMainActivity.showBottomBarLayout();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void initIndicator(){
        CommonNavigator mCommonNavigator = new CommonNavigator(UIUtils.getContext());
        mCommonNavigator.setSkimOver(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return channels.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(channels[index]);
                clipPagerTitleView.setTextSize(UIUtils.sp2px(15));
                clipPagerTitleView.setClipColor(getResources().getColor(R.color.colorPrimary));
                clipPagerTitleView.setTextColor(getResources().getColor(R.color.color_BDBDBD));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setRoundRadius(5);
                indicator.setColors(getResources().getColor(R.color.colorPrimary));
                return indicator;
            }
        });
        mIndicator.setNavigator(mCommonNavigator);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivity = (MainActivity) getActivity();
    }

    private void lockDrawer(){
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void unlockDrawer(){
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public boolean isSeacherOpen(){
        return mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    public void closeSeacher(){
        mDrawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_seacher:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.tv_close_search:
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ToastUtil.showToast(mSearchAdapter.getItem(position));
    }
}
