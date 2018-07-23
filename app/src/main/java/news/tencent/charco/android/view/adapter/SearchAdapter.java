package news.tencent.charco.android.view.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import news.tencent.charco.android.R;

/**
 * Created 18/7/21 16:34
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class SearchAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public SearchAdapter( @Nullable List<String> data) {
        super(R.layout.item_search_hot, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_hot_name,item);
    }
}
