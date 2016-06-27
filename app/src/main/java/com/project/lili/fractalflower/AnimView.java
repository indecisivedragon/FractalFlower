package com.project.lili.fractalflower;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by Lili on 6/25/2016.
 */
public class AnimView extends SurfaceView implements ValueAnimator.AnimatorUpdateListener {

    private boolean setup = true;

    private Flower testFlower;

    AnimView (Context context) {
        super(context);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);

        if (setup) {
            setTestFlower();
            setAnimation(c);
            setup = false;
        }

        Flower.drawFlower(c, testFlower);
    }

    private void setTestFlower() {
        testFlower = FlowerFactory.createFlower(4, true, FlowerFactory.FlowerColor.PASTEL);
    }

    private void setAnimation(Canvas c) {
        final ValueAnimator anim = ValueAnimator.ofFloat(testFlower.getLocationX(), testFlower.getLocationX() + 500);
        anim.setDuration(1000);
        anim.addUpdateListener(AnimView.this);
        anim.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animator) {

        testFlower.setLocationX((float) animator.getAnimatedValue());
        testFlower.setLocationY((float) animator.getAnimatedValue());

        invalidate();
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

}
