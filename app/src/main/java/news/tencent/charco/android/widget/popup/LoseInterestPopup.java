package news.tencent.charco.android.widget.popup;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import news.tencent.charco.android.R;
import news.tencent.charco.android.utils.UIUtils;

/**
 * Created 18/6/25 23:03
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：不感兴趣popup
 * ----------------------------------------------------
 */

public class LoseInterestPopup implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox mCbContent , mCbTooOld , mCbSource;
    private int position;
    private String source;
    private OnLoseInterestListener onLoseInterestListener;
    private FitPopupWindow mPopupWindow;
    private Activity mContext;
    private View contentView;
    private TextView mTvHint;
    private static final String HINT = "已选择<font color='#3F51B5'>%s</font>理由";

    public LoseInterestPopup(Activity context) {
        mContext = context;
        contentView = View.inflate(context, R.layout.popup_lost_interest,null);
        mTvHint = contentView.findViewById(R.id.tv_hint);
        mCbContent = contentView.findViewById(R.id.cb_content);
        mCbTooOld = contentView.findViewById(R.id.cb_too_old);
        mCbSource = contentView.findViewById(R.id.cb_source);
        mCbContent.setOnCheckedChangeListener(this);
        mCbTooOld.setOnCheckedChangeListener(this);
        mCbSource.setOnCheckedChangeListener(this);
        contentView.findViewById(R.id.tv_confirm).setOnClickListener(this);
    }

    /**
     * 弹出自适应位置的popupwindow
     *
     * @param anchorView 目标view
     */
    public View showPopup(View anchorView) {
        if (mPopupWindow == null) {
            mPopupWindow = new FitPopupWindow(mContext,
                    UIUtils.getScreenWidth() - UIUtils.dip2Px(20),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        mPopupWindow.setView(contentView, anchorView);
        mPopupWindow.show();
        return contentView;
    }

    public boolean isShow(){
        return mPopupWindow.isShowing();
    }

    public void dismiss(){
        mPopupWindow.dismiss();
    }

    public LoseInterestPopup setSource(String source){
        mCbSource.setText("来源:"+source);
        this.source = source;
        return this;
    }

    public LoseInterestPopup setPosition(int position){
        this.position = position;
        return this;
    }

    public LoseInterestPopup setOnLoseInterestListener(OnLoseInterestListener onLoseInterestListener){
        this.onLoseInterestListener = onLoseInterestListener;
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                returnLoseIntereset();
                mPopupWindow.dismiss();
                break;
        }
    }

    private void returnLoseIntereset(){
        if (onLoseInterestListener !=null){
            int poor_quality;
            if (mCbContent.isChecked()){
                poor_quality = 1;
            }else {
                poor_quality = 0;
            }
            int repeat;
            if (mCbTooOld.isChecked()){
                repeat = 1;
            }else {
                repeat = 0;
            }
            String source;
            if (mCbSource.isChecked()){
                source = this.source;
            }else {
                source = null;
            }
            onLoseInterestListener.onLoseInterestListener(poor_quality,repeat,source,position);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        changeHint();
    }

    private void changeHint(){
        int count = 0;
        if (mCbContent.isChecked()){
            count ++ ;
        }

        if (mCbTooOld.isChecked()){
            count ++ ;
        }
        if (mCbSource.isChecked()){
            count ++ ;
        }
        switch (count){
            case 0:
                mTvHint.setText(R.string.choose_reasons_to_optimize_for_you);
                break;
            case 1:
                mTvHint.setText(Html.fromHtml(String.format(HINT,"1个")));
                break;
            case 2:
                mTvHint.setText(Html.fromHtml(String.format(HINT,"2个")));
                break;
            case 3:
                mTvHint.setText(Html.fromHtml(String.format(HINT,"3个")));
                break;
        }
    }

    public interface OnLoseInterestListener{
        void onLoseInterestListener(int poor_quality, int repeat, String source, int position);
    }

}
