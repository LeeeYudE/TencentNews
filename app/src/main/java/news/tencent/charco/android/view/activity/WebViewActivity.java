package news.tencent.charco.android.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.Arrays;

import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseActivity;
import news.tencent.charco.android.utils.UIUtils;
import news.tencent.charco.android.view.adapter.NewsDetailAdapter;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private RecyclerView mRecyclerView;
    private NewsDetailAdapter mAdapter;
    private View mLltHead , mLltInfo , mBarLayout;
    private int mScrollY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initListener();
    }

    private void initView() {
        setWhiteStatus();
        mLltHead = findViewById(R.id.llt_head);
        mLltInfo = findViewById(R.id.llt_info);
        mBarLayout = findViewById(R.id.rlt_bar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new NewsDetailAdapter(Arrays.asList(getResources().getStringArray(R.array.hot)));
        mRecyclerView.setAdapter(mAdapter);
        initWebview();
    }

    private void initWebview(){
        View view = View.inflate(this,R.layout.item_webview,null);
        mWebView = view.findViewById(R.id.webview) ;
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        mWebView.loadUrl("https://m.baidu.com");
        mAdapter.addHeaderView(view);

    }

    private void initListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int h = DensityUtil.dp2px(140);
            private int color = ContextCompat.getColor(UIUtils.getContext(), R.color.white);

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mScrollY += dy;
                if (mScrollY < h){

                    mLltHead.setAlpha(1- (mScrollY *1f / h));
                    mLltHead.scrollTo(0, (int) (mScrollY * 0.2));

                    if (mLltInfo.isShown()){
                        //解决无法控件隐藏的问题
                        if (mLltInfo.getAnimation()!=null){
                            mLltInfo.clearAnimation();
                        }
                        mLltInfo.setVisibility(View.GONE);
                        mLltHead.setVisibility(View.VISIBLE);

                        mBarLayout.setBackgroundColor(0);
                    }
                }else {
                    if (!mLltInfo.isShown()){

                        if (mLltInfo.getAnimation()!=null){
                            mLltInfo.clearAnimation();
                        }
                        mLltInfo.setVisibility(View.VISIBLE);
                        mLltHead.setVisibility(View.GONE);
                        mBarLayout.setBackgroundColor(color);
                    }
                }
            }
        });
    }

}
