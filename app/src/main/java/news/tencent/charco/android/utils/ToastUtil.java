package news.tencent.charco.android.utils;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by HIAPAD on 2017/8/10.
 */

public class ToastUtil {

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static void showToast( int resId) {
        if (mContext == null)return;
        showToast(mContext.getString(resId));
    }


    public static void showToast(final String s) {
        if (mContext == null|| TextUtils.isEmpty(s))return;
        if (Looper.myLooper() == Looper.getMainLooper()){
            show(s);
        }else {
            UIUtils.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    show(s);
                }
            });
        }

        oneTime = twoTime;
    }

    private static void show(String s){
        if (toast == null) {
            toast = Toast.makeText(mContext, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }

    }




}
