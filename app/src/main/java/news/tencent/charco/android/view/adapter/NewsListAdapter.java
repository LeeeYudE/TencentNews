package news.tencent.charco.android.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import news.tencent.charco.android.R;
import news.tencent.charco.android.utils.ToastUtil;
import news.tencent.charco.android.utils.UIUtils;
import news.tencent.charco.android.widget.HotRefrechHead;
import news.tencent.charco.android.widget.MyHorizontalRefreshLayout;
import xiao.free.horizontalrefreshlayout.HorizontalRefreshLayout;
import xiao.free.horizontalrefreshlayout.RefreshCallBack;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created 18/7/19 16:49
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity,BaseViewHolder> implements View.OnClickListener {

    /**
     * 专题
     */
    public static final int NEWS_SUBJECT = 100;
    /**
     * 热点列表,可横向滚动
     */
    public static final int NEWS_HOT_LIST = 200;
    /**
     * 热点列表,纯文字滚动
     */
    public static final int NEWS_HOT_TEXT = 201;
    /**
     * 单图右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int  NEWS_SIMPLE_PHOTO = 300;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int NEWS_THREE_PHOTO = 400;
    /**
     * 图集
     */
    public static final int NEWS_ALBUM_PHOTO = 500;
    /**
     * 视频
     */
    public static final int NEWS_VIDEO = 600;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public NewsListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(NEWS_SUBJECT, R.layout.item_news_subject);
        addItemType(NEWS_SIMPLE_PHOTO, R.layout.item_news_simple_photo);
        addItemType(NEWS_THREE_PHOTO, R.layout.item_news_three_photo);
        addItemType(NEWS_ALBUM_PHOTO, R.layout.item_news_album_photo);
        addItemType(NEWS_VIDEO, R.layout.item_news_video);
        addItemType(NEWS_HOT_LIST, R.layout.item_news_hot_list);
        addItemType(NEWS_HOT_TEXT, R.layout.item_hot_text_srcoll);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case NEWS_HOT_LIST:
                setHotList(helper);
                break;
            case NEWS_VIDEO:
                JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
                videoPlayer.setAllControlsVisiblity(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
                videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
                videoPlayer.resetProgressAndTime();
                videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                        JZVideoPlayer.SCREEN_WINDOW_LIST,"视频标题");
                videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
                break;
            case NEWS_HOT_TEXT:
                setScrollText(helper);
                break;
        }
        helper.addOnClickListener(R.id.iv_delete);
    }

    private void setScrollText(BaseViewHolder helper){
        ViewFlipper viewFlipper = helper.getView(R.id.viewFlipper);
        if (viewFlipper != null){

            if (viewFlipper.getChildCount() > 0){
                viewFlipper.removeAllViews();
            }
            String[] array = UIUtils.getContext().getResources().getStringArray(R.array.hot);
                for (String string : array){
                    TextView textView = (TextView) View.inflate(mContext,R.layout.item_hot_flipper,null);
                    textView.setText(string);
                    textView.setTag(string);
                    textView.setOnClickListener(this);
                    viewFlipper.addView(textView);
            }
            viewFlipper.startFlipping();

        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position % 7){
            case 0:
                return NEWS_SUBJECT;
            case 1:
                return NEWS_HOT_LIST;
            case 2:
                return NEWS_HOT_TEXT;
            case 3:
                return NEWS_SIMPLE_PHOTO;
            case 4:
                return NEWS_THREE_PHOTO;
            case 5:
                return NEWS_ALBUM_PHOTO;
            case 6:
                return NEWS_VIDEO;
        }
        return super.getItemViewType(position);
    }

    private void setHotList(BaseViewHolder helper){

            RecyclerView recyclerView = helper.getView(R.id.recyclerView);
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter == null){
                new PagerSnapHelper().attachToRecyclerView(recyclerView);
                final MyHorizontalRefreshLayout refreshLayout = helper.getView(R.id.refreshLayout);
                refreshLayout.setRefreshHeader(new HotRefrechHead(UIUtils.getContext()), HorizontalRefreshLayout.RIGHT);
                refreshLayout.setRefreshCallback(new RefreshCallBack() {
                    @Override
                    public void onLeftRefreshing() {

                    }

                    @Override
                    public void onRightRefreshing() {
                        refreshLayout.onRefreshComplete();
                        ToastUtil.showToast("查看更多");
                    }
                });

                final NewsHotHorizontalListAdapter hotAdapter = new NewsHotHorizontalListAdapter(null);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setAdapter(hotAdapter);
                refreshLayout.setLayoutManager(linearLayoutManager);
                recyclerView.setLayoutManager(linearLayoutManager);
                refreshLayout.setItemSize(4);
                hotAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });
                helper.addOnClickListener(R.id.tv_more_hot);
            }

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_hot_item:
                ToastUtil.showToast((String) view.getTag());
            break;
        }
    }
}
