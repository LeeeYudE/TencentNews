package news.tencent.charco.android.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.Arrays;

import news.tencent.charco.android.R;
import news.tencent.charco.android.base.BaseActivity;
import news.tencent.charco.android.utils.UIUtils;
import news.tencent.charco.android.view.adapter.NewsDetailAdapter;

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private RecyclerView mRecyclerView;
    private NewsDetailAdapter mAdapter;
    private AppBarLayout mAppBarLayout;
    private TextView mTvPublisher;
    private View mLltHead, mBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        initListener();
    }

    private void initView() {
        setWhiteStatus();
        mTvPublisher = findViewById(R.id.tv_publisher);
        mAppBarLayout = findViewById(R.id.app_bar);
        mLltHead = findViewById(R.id.llt_head);
        mBarLayout = findViewById(R.id.rlt_bar);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new NewsDetailAdapter(Arrays.asList(getResources().getStringArray(R.array.hot)));
        mRecyclerView.setAdapter(mAdapter);
        initWebview();
    }

    private void initWebview() {
        View view = View.inflate(this, R.layout.item_webview, null);
        mWebView = view.findViewById(R.id.webview);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
        mWebView.loadUrl("https://m.baidu.com");
        mAdapter.addHeaderView(view);
    }

    private void initListener() {

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float a = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
                mLltHead.setAlpha(1f - a);
                mTvPublisher.setAlpha(a);
                mBarLayout.setBackgroundColor(UIUtils.changeAlpha(getResources().getColor(R.color.white), (int) (a*255)));
            }
        });

    }

}
