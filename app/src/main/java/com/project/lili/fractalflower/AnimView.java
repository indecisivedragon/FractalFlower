package com.project.lili.fractalflower;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.OverScroller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Lili on 6/25/2016.
 *
 */
//public class AnimView extends SurfaceView implements Runnable {
public class AnimView extends SurfaceView {

    private static final int FADE_STOPPED = 0;
    private static final int FADE_TO_WHITE = 1;
    private static final int FADE_REVERSE_STOPPED = 2;
    private static final int FADE_REVERSE = 3;
    private boolean setup = true;
    private Flower testFlower;
    private String DEBUG_TAG = "fractal_flower debug: ";
    //offset of touch point to center, so that flower can be dragged by petals
    private float offsetX = 0, offsetY = 0;
    private boolean move;
    //fade animation variables
    //are we fading or not
    private int fadeState = 0;
    private int[] ringColorAlphaArray;
    private int mActivePointerId;

    AnimView(Context context) {
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

    /*
    @Override
    public void run() {
        System.out.println("I'm running!" + Thread.currentThread().getId());
    }
    */

    public void reset() {
        this.setSetup(true);
        this.postInvalidate();
    }

    private void setTestFlower() {
        testFlower = FlowerFactory.createFlower(3, FlowerFactory.getCenterPetals(), FlowerFactory.getColor());
        testFlower.setLocationX(this.getWidth() / 2);
        testFlower.setLocationY(this.getHeight() / 2);
        setup = false;

        //original colors preserved
        ringColorAlphaArray = new int[testFlower.getLevels()];
        for (int i = 0; i < testFlower.getLevels(); i++) {
            ringColorAlphaArray[i] = Color.alpha(testFlower.getColor(i));
        }

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
                } else {
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
        animX.setDuration((long) (speed * distance));
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup) {
                    testFlower.setLocationX((float) animator.getAnimatedValue());
                    invalidate();
                } else {
                    animator.cancel();
                }
            }
        });

        ValueAnimator animY = ValueAnimator.ofFloat(testFlower.getLocationY(), dy);
        animY.setDuration((long) (speed * distance));
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup) {
                    testFlower.setLocationY((float) animator.getAnimatedValue());
                    invalidate();
                } else {
                    animator.cancel();
                }
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.start();
    }

    public void fade() {
        fadeState = (fadeState + 1) % 4;
        System.out.println(fadeState);
        if (fadeState == FADE_STOPPED || fadeState == FADE_REVERSE_STOPPED) {
            return;
        }

        Collection<Animator> petalFadeAnimators = new ArrayList<>();
        for (int i = 0; i < testFlower.getLevels(); i++) {
            //set current color, not original
            final int j = i;
            final int ringColorAlpha = Color.alpha(testFlower.getColor(j));

            //declare animator
            ValueAnimator animColor = null;
            //animColor.setDuration((long) (10*ringColorAlpha));

            if (fadeState == FADE_TO_WHITE) {
                System.out.println("fade to white");
                //we want to go from the current alpha to 0
                animColor = ValueAnimator.ofInt(ringColorAlpha, 0);
                animColor.setDuration(2000);
                animColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        if (!setup && fadeState == FADE_TO_WHITE) {
                            testFlower.setColorAlpha(j, (int) animator.getAnimatedValue());
                        } else {
                            animator.cancel();
                        }
                        invalidate();
                    }
                });
            } else if (fadeState == FADE_REVERSE) {
                System.out.println("reverse fade");
                //go from current shade to original
                animColor = ValueAnimator.ofInt(ringColorAlpha, ringColorAlphaArray[j]);
                System.out.println(ringColorAlpha + " " + ringColorAlphaArray[j]);
                animColor.setDuration(2000);
                animColor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        if (!setup && fadeState == FADE_REVERSE) {
                            testFlower.setColorAlpha(j, (int) animator.getAnimatedValue());
                        } else {
                            animator.cancel();
                        }
                        invalidate();
                    }
                });
            }
            petalFadeAnimators.add(animColor);
        }

        //make an animation set to play all of them at once
        AnimatorSet set = new AnimatorSet();
        set.playTogether(petalFadeAnimators);
        set.start();
    }

    private ArrayList<Float> velocityTrackerX;
    private ArrayList<Float> velocityTrackerY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        //coordinates of touch point
        float x = event.getX();
        float y = event.getY();

        //Log.d(DEBUG_TAG, MotionEvent.actionToString(action) +"(x: " + x + ", y: " + y + "), id " + mActivePointerId);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                // Get the pointer ID of the first touch
                mActivePointerId = event.getPointerId(0);

                //check if the touch is on the flower
                if (testFlower.checkBounds(event.getX(), event.getY())) {
                    move = true;
                    this.setOffsets(x, y);

                    velocityTrackerX = new ArrayList<>();
                    velocityTrackerY = new ArrayList<>();
                }
                //apparently this always needs to return true or nothing works
                return true;
            case (MotionEvent.ACTION_MOVE):
                //only move if we started out touching the flower
                if (event.findPointerIndex(mActivePointerId) == 0) {
                    //pick up if we touch the flower even if we start somewhere else
                    if (testFlower.checkBounds(x, y) && !move) {
                        move = true;
                        this.setOffsets(x, y);
                    }
                    //only move if we're continuing touching the flower
                    if (move) {
                        testFlower.setLocationX(x + offsetX);
                        testFlower.setLocationY(y + offsetY);

                        velocityTrackerX.add(testFlower.getLocationX());
                        velocityTrackerY.add(testFlower.getLocationY());
                        if (velocityTrackerX.size() > 5){
                            velocityTrackerX.remove(0);
                            velocityTrackerY.remove(0);
                        }
                    }
                    //Log.d(DEBUG_TAG, "" + move);
                }
                break;
            case (MotionEvent.ACTION_UP):
                mActivePointerId = -1;
                if (move && velocityTrackerX.size() != 0) {
                    continueMoving(x, y);
                }
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

    private void continueMoving(float x, float y) {
        //calculate velocity
        float velocityX = 0, velocityY = 0;
        for (int i=0; i<velocityTrackerX.size()-1; i++) {
            velocityX += velocityTrackerX.get(i) - velocityTrackerX.get(i+1);
            velocityY += velocityTrackerY.get(i) - velocityTrackerY.get(i+1);
        }

        float distance = (float) (Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2)));

        //0.002 s for 1 pix
        final ValueAnimator animX = ValueAnimator.ofFloat(testFlower.getLocationX(), testFlower.getLocationX() - 5*velocityX);
        animX.setInterpolator(new DecelerateInterpolator());
        animX.setDuration((long) (Math.abs(distance*5)));
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup && mActivePointerId<0) {
                    testFlower.setLocationX((float) animator.getAnimatedValue());
                    invalidate();
                } else {
                    animator.cancel();
                }
            }
        });
        animX.start();

        final ValueAnimator animY = ValueAnimator.ofFloat(testFlower.getLocationY(), testFlower.getLocationY() - 5*velocityY);
        animY.setInterpolator(new DecelerateInterpolator());
        animY.setDuration((long) Math.abs((distance*5)));
        animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!setup && mActivePointerId<0) {
                    testFlower.setLocationY((float) animator.getAnimatedValue());
                    invalidate();
                } else {
                    animator.cancel();
                }
            }
        });
        animY.start();
    }

    private void setOffsets(float x, float y) {
        offsetX = testFlower.getLocationX() - x;
        offsetY = testFlower.getLocationY() - y;
    }

    //turn flower once
    public void rotate() {
        //otherwise the rings move away from each other
        final float startX = testFlower.getLocationX();
        final float startY = testFlower.getLocationY();

        //animator set to be played
        Collection<Animator> rotateAnimators = new ArrayList<>();

        //add animator set for each ring level
        for (int i = 0; i < testFlower.getLevels(); i++) {
            //set current color, not original
            final int j = i;
            final int offset = testFlower.getOffset(j);

            //declare animator
            ValueAnimator rotateAnim = ValueAnimator.ofInt(offset, offset + 360);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setDuration(2000);
            rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    if (!setup) {
                        testFlower.setOffset(j, (int) animator.getAnimatedValue());

                        //maintain center
                        testFlower.setLocationX(startX);
                        testFlower.setLocationY(startY);
                    } else {
                        animator.cancel();
                    }
                    invalidate();
                }
            });
            rotateAnimators.add(rotateAnim);
        }

        //make an animation set to play all of them at once
        AnimatorSet set = new AnimatorSet();
        set.playTogether(rotateAnimators);
        set.start();
    }
}
