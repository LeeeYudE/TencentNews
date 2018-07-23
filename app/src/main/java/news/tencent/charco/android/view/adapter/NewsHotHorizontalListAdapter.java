package news.tencent.charco.android.view.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import news.tencent.charco.android.R;
import news.tencent.charco.android.utils.UIUtils;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created 18/5/26 15:43
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsHotHorizontalListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>  {

    private int width;

    public NewsHotHorizontalListAdapter(@Nullable List<MultiItemEntity> data) {
        super(data);
        width = UIUtils.getResource().getDisplayMetrics().widthPixels - UIUtils.dip2Px(30);
        addItemType(1, R.layout.item_news_hot_image);
        addItemType(2,R.layout.item_news_hot_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity news) {
        helper.getConvertView().getLayoutParams().width = width;
        if (helper.getItemViewType() == 2){
            JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
            videoPlayer.setAllControlsVisiblity(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
            videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
            videoPlayer.resetProgressAndTime();
            videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                    JZVideoPlayer.SCREEN_WINDOW_LIST,"视频标题");
            videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1){
            return 2;
        }else {
            return 1;
        }
    }
}
