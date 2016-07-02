package com.project.lili.fractalflower;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.Iterator;

/**
 * Created by Lili on 6/25/2016.
 *
 */
public class AnimView extends SurfaceView implements Runnable {

    private boolean setup = true;
    private Flower testFlower;

    private String DEBUG_TAG = "fractal_flower debug: ";

    //offset of touch point to center, so that flower can be dragged by petals
    private float offsetX = 0, offsetY = 0;
    private boolean move;

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
        //TODO whoops this resets everything on the main page also since I used flowerfactory
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
        animX.setDuration((long) (speed*distance));
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
        animY.setDuration((long) (speed*distance));
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

    private int mActivePointerId;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        //coordinates of touch point
        float x = event.getX();
        float y = event.getY();

        Log.d(DEBUG_TAG, MotionEvent.actionToString(action) +"(x: " + x + ", y: " + y + "), id " + mActivePointerId);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                // Get the pointer ID of the first touch
                mActivePointerId = event.getPointerId(0);

                //check if the touch is on the flower
                if (testFlower.checkBounds(event.getX(), event.getY())) {
                    move = true;
                    this.setOffsets(x, y);
                }
                //apparently this always needs to return true or nothing works
                return true;
            case (MotionEvent.ACTION_MOVE):
                if (event.findPointerIndex(mActivePointerId) == 0) {
                    //pick up if we touch the flower even if we start somewhere else
                    if (testFlower.checkBounds(x, y) && !move) {
                        move = true;
                        this.setOffsets(x, y);
                    }

                    //only move if we're touching the flower
                    if (move) {
                        testFlower.setLocationX(x + offsetX);
                        testFlower.setLocationY(y + offsetY);
                    }
                    Log.d(DEBUG_TAG, "" + move);
                }
                break;
            case (MotionEvent.ACTION_UP):
                move = false;
                break;
            case (MotionEvent.ACTION_CANCEL):
                move = false;
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                move = false;
                break;
            default:
                move = false;
                break;
        }

        this.postInvalidate();
        return super.onTouchEvent(event);
    }

    private void setOffsets(float x, float y) {
        offsetX = testFlower.getLocationX() - x;
        offsetY = testFlower.getLocationY() - y;
    }
}
