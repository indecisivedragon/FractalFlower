package com.project.lili.fractalflower;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by Lili on 6/25/2016.
 *
 */
public class AnimView extends SurfaceView implements Runnable {

    private boolean setup = true;
    private Flower testFlower;

    AnimView (Context context) {
        super(context);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        //canvas = c;

        if (setup) {
            setTestFlower();
        }
        Flower.drawFlower(c, testFlower);
    }

    @Override
    public void run() {
    }

    public void reset() {
        this.setSetup(true);
        this.postInvalidate();
    }

    private void setTestFlower() {
        testFlower = FlowerFactory.createFlower(4, true, FlowerFactory.FlowerColor.PASTEL);
        testFlower.setLocationX(this.getWidth()/2);
        testFlower.setLocationY(this.getHeight()/2);
        setup = false;
    }

    private void setAnimation(Canvas c) {
        //0.002 s for 1 pix
        final ValueAnimator anim = ValueAnimator.ofFloat(testFlower.getLocationX(), testFlower.getLocationX() + 500);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup) {
                    testFlower.setLocationX((float) animator.getAnimatedValue());
                    invalidate();
                }
                else {
                    animator.cancel();
                }
            }
        });
        anim.start();
    }


    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public void setMoveCoordinates(float dx, float dy) {
        //get total distance to travel
        float distanceX = dx - testFlower.getLocationX();
        //System.out.println(distanceX);
        float distanceY = dy - testFlower.getLocationY();
        //System.out.println(distanceY);
        float distance = (float) (Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2)));

        //0.002 sec for 1 pix
        float speed = (float) 2;

        ValueAnimator animX = ValueAnimator.ofFloat(testFlower.getLocationX(), dx);
        animX.setDuration(2000);
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup) {
                    testFlower.setLocationX((float) animator.getAnimatedValue());
                    invalidate();
                }
                else {
                    animator.cancel();
                }
            }
        });

        ValueAnimator animY = ValueAnimator.ofFloat(testFlower.getLocationY(), dy);
        animY.setDuration((long) (2000));
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup) {
                    testFlower.setLocationY((float) animator.getAnimatedValue());
                    invalidate();
                }
                else {
                    animator.cancel();
                }
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        float x, y;

        switch (action) {
            default:
                x = event.getX();
                y = event.getY();

                //Toast t = Toast.makeText(this.getContext(), x + " " + y, Toast.LENGTH_SHORT);
                //t.show();

                this.setMoveCoordinates(x, y);
                break;
        }
        this.postInvalidate();
        return super.onTouchEvent(event);
    }
}
