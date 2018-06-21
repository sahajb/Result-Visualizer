package com.example.android.resultvisualizer.Utilities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public final class AnimationUtils {

    private static ValueAnimator mAnimator;

    private static void clicked(final ConstraintLayout mLinearLayout, final View buttonLayout) {

        if (mLinearLayout.getVisibility() != View.VISIBLE) {
            mLinearLayout.setVisibility(View.VISIBLE);
            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            mLinearLayout.measure(widthSpec, heightSpec);
            mAnimator = slideAnimator(mLinearLayout,0, mLinearLayout.getMeasuredHeight());
            mAnimator.start();
            createRotateAnimator(buttonLayout, 0f, 180f).start();
        } else {
            int finalHeight = mLinearLayout.getHeight();
            ValueAnimator mAnimator = slideAnimator(mLinearLayout,finalHeight, 0);
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    //Height=0, but it set visibility to GONE
                    mLinearLayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            mAnimator.start();
            createRotateAnimator(buttonLayout, 180f, 0f).start();
        }
    }

    private static ValueAnimator slideAnimator(final ConstraintLayout mLinearLayout, int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    private static ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    public static void onClickButton(final ConstraintLayout v, final View buttonLayout) {
        clicked(v, buttonLayout);
    }

}
