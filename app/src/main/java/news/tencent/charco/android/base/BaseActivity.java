package news.tencent.charco.android.base;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import news.tencent.charco.android.R;
import news.tencent.charco.android.utils.StatusBarUtil;

/**
 * Created 18/7/5 11:26
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class BaseActivity extends AppCompatActivity {

    //白色状态栏，黑色状态栏图标
    public void setWhiteStatus(){
        StatusBarUtil.setStatusBarDarkFont(this, true);
        StatusBarUtil.setColorNoTranslucent(this,getResources().getColor(R.color.white));
    }

    //状态栏透明，黑色状态栏图标
    public void setTransparentBar(){
        StatusBarUtil.transparentStatusBar(this);
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void showView(View view){
        if (view!=null&&view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    public void showView(@IdRes int id){
        View view = findViewById(id);
        if (view!=null&&view.getVisibility()!=View.VISIBLE){
            view.setVisibility(View.VISIBLE);
        }
    }

    public void hideView(View view){
        if (view!=null&&view.getVisibility()!=View.GONE){
            view.setVisibility(View.GONE);
        }
    }

    public void hideView(@IdRes int id){
        View view = findViewById(id);
        if (view!=null&&view.getVisibility()!=View.GONE){
            view.setVisibility(View.GONE);
        }
    }

}
