package com.saimawzc.freight.weight.utils.foucsurfaceview.animation;
@SuppressWarnings("unused")
public interface SimpleValueAnimator {
    void startAnimation(long duration);
    void cancelAnimation();
    boolean isAnimationStarted();
    void addAnimatorListener(SimpleValueAnimatorListener animatorListener);
}
