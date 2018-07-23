package news.tencent.charco.android.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import news.tencent.charco.android.R;

/**
 * Created 18/7/21 15:19
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class NewsDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public NewsDetailAdapter( @Nullable List<String> data) {
        super(R.layout.item_news_simple_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

}
