package news.tencent.charco.android.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import news.tencent.charco.android.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created 18/7/21 16:58
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class VideoListAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public VideoListAdapter(@Nullable List<String> data) {
        super(R.layout.item_video_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        JZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
        videoPlayer.setAllControlsVisiblity(VISIBLE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE);
        videoPlayer.setState(JZVideoPlayer.CURRENT_STATE_NORMAL);
        videoPlayer.resetProgressAndTime();
        videoPlayer.setUp("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                JZVideoPlayer.SCREEN_WINDOW_LIST,"视频标题");
        videoPlayer.thumbImageView.setImageResource(R.drawable.bg_motherland);
    }
}
