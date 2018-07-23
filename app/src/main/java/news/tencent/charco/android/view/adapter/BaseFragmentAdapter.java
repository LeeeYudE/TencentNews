package news.tencent.charco.android.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import news.tencent.charco.android.base.BaseFragment;


/**
 * Created 18/5/18 16:02
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragments = new ArrayList<>();

    public BaseFragmentAdapter(List<BaseFragment> fragmentList, FragmentManager fm) {
        super(fm);
        mFragments.addAll(fragmentList);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public long getItemId(int position) {
        return mFragments.get(position).hashCode();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return PagerAdapter.POSITION_NONE;
    }

}
