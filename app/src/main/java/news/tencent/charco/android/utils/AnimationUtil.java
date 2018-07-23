package news.tencent.charco.android.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;

/**
 * Created 18/4/24 10:05
 * Author:charcolee
 * Version:V1.0
 * ----------------------------------------------------
 * 文件描述：
 * ----------------------------------------------------
 */

public class AnimationUtil {



    public static void showTipView(final View view , final View translationView){
        if (view!=null ){

            if (view.getTag()!=null){
                if ((boolean) view.getTag()){
                    return;
                }
            }

            if (view.getVisibility() == View.INVISIBLE){
                view.setTranslationY(-Math.abs(view.getHeight()));
                view.setVisibility(View.VISIBLE);
            }
            view.setTag(true);

                ViewPropertyAnimator viewPropertyAnimator = view.animate()
                        .yBy(Math.abs(view.getHeight()))
                        .setStartDelay(0)
                        .setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                view.animate().setListener(null);
                                 ViewPropertyAnimator viewPropertyAnimator1 = view.animate().yBy(-view.getHeight())
                                        .setStartDelay(1000)
                                        .setListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animator) {
                                                view.setTag(false);
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                    view.animate().setUpdateListener(null);
                                                }
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animator) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animator) {

                                            }
                                        })
                                        .setInterpolator(new LinearInterpolator()).setDuration(150);
                                    viewPropertyAnimator1.setUpdateListener(null);
                                    viewPropertyAnimator1.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            float value = (float) valueAnimator.getAnimatedValue();
                                            int height = view.getHeight();
                                            ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) translationView.getLayoutParams();
                                            layoutParams.topMargin = (int) (height*(1-value));
                                            translationView.requestLayout();
                                        }
                                    });
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        })
                        .setInterpolator(new LinearInterpolator());

                        viewPropertyAnimator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float value = (float) valueAnimator.getAnimatedValue();
                                int height = view.getHeight();
                                ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) translationView.getLayoutParams();
                                layoutParams.topMargin = (int) (height*value);
                                translationView.requestLayout();
                            }
                        });
                viewPropertyAnimator.start();
        }
    }

    public static void showBottomTipView(final View view) {
        if (view != null) {
            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setVisibility(View.GONE);
                    }
                },1000);
            }

        }
    }

}
