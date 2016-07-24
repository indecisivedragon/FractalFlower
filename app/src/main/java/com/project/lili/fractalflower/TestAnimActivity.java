package com.project.lili.fractalflower;

import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class TestAnimActivity extends AppCompatActivity {

    private AnimView animView;
    private Thread animThread;

    private static final String DEBUG_TAG = "Gestures";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_anim);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.action_test_anim);
        animView = new AnimView(this);
        animView.setBackgroundColor(Color.WHITE);

        //RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        //p.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.action_test_anim);

        if (layout != null) {
            layout.addView(animView);
        }

        //TODO does this really need to be a thread? because the run method is empty...
        //animThread = new Thread(new AnimView(this.getApplicationContext()));
        //animThread.start();

        //Thread secondThread = new Thread((new AnimView((this.getApplicationContext()))));
        //secondThread.start();
    }

    public void replayAnimation(View view) {
        animView.reset();
    }

    public void fadeColors(View view) {
        animView.fade();
    }

    public void rotate(View view){
        animView.rotate();
    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString()+e2.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        return true;
    }
    */
}
