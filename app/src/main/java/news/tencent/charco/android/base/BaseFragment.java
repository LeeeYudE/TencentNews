package news.tencent.charco.android.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nukc.stateview.StateView;

import org.greenrobot.eventbus.EventBus;

import news.tencent.charco.android.R;

/**
 * @author ChayChan
 * @description: Fragment的基类
 * @date 2017/6/10  17:09
 */

public abstract class BaseFragment extends LazyLoadFragment implements StateView.OnRetryClickListener{


    protected View rootView;
    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局
    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(),container,false);

            mStateView = StateView.inject(getStateViewRoot());
            if (mStateView != null){
                mStateView.setLoadingResource(R.layout.view_loading);
                mStateView.setOnRetryClickListener(this);
            }

            initView(rootView);
            initData();
            initListener();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    /**StateView的根布局，默认是整个界面，如果需要变换可以重写此方法*/
    public View getStateViewRoot() {
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public final <T extends View> T findViewById(int id) {
       return rootView.findViewById(id);
    }

    public void showView(View view){
        if (view!=null&&view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showView(@IdRes int id){
        View view = rootView.findViewById(id);
        if (view!=null&&view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hideView(View view){
        if (view!=null&&view.getVisibility()!=View.GONE){
            view.setVisibility(View.GONE);
        }
    }

    public void hideView(@IdRes int id) {
        View view = rootView.findViewById(id);
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化一些view
     * @param rootView
     */
    public void initView(View rootView) {
    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    /**
     * 设置listener的操作
     */
    public void initListener() {

    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
        loadData();
    }

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //加载数据
    protected abstract void loadData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        rootView = null;
    }

    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    @Override
    public void onRetryClick() {

    }

}
