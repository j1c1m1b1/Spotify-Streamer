package com.udacity.jcmb.spotifystreamer.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import io.codetail.animation.SupportAnimator;

/**
 * @author Julio Mendoza on 6/29/15.
 */
public class AnimationUtils {

    public static void appear(final View view, Context context)
    {
        Point size = Utils.getScreenDimensions(context);

        int centerX = size.x/2;
        int centerY = size.y/2;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0,
                    view.getWidth());
            animator.setDuration(2000);
            view.setVisibility(View.VISIBLE);
            animator.start();
        }
        else
        {
            SupportAnimator animator = io.codetail.animation.ViewAnimationUtils.
                    createCircularReveal(view, centerX, centerY, 0, view.getWidth());
            animator.setDuration(2000);
            view.setVisibility(View.VISIBLE);
            animator.start();
        }
    }

    public static void bounce(View v, Context context)
    {
        float margin = -Utils.convertDpToPixel(16f, context);
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationY", 0f, margin);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.setRepeatCount(20);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    public static void grow(View v)
    {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f);
        scaleX.setDuration(500);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 0f, 1f);
        scaleY.setDuration(500);
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);

        v.setVisibility(View.VISIBLE);

        set.start();


    }


    public static void shrink(final View v)
    {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0f);
        scaleX.setDuration(500);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 1f, 0f);
        scaleY.setDuration(500);
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);

        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        set.start();


    }


}
