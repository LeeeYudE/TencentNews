package news.tencent.charco.android.view.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseFragment;
import news.tencent.charco.android.utils.UIUtils;
import news.tencent.charco.android.view.adapter.BaseFragmentAdapter;
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

public class VideoFragment extends BaseFragment {

    private ViewPager mViewPager;
    private MagicIndicator mIndicator;
    private String[] channels ;
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    public void initView(View rootView) {
        mViewPager = findViewById(R.id.viewpager);
        mIndicator= findViewById(R.id.magic_indicator);
    }

    @Override
    protected void loadData() {
        channels = getResources().getStringArray(R.array.channel);
        initIndicator();
        for (String channel : channels){
            fragments.add(new VideoListFragment());
        }
        mViewPager.setAdapter(new BaseFragmentAdapter(fragments,getChildFragmentManager()));
    }

    @Override
    public void initListener() {
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

}
