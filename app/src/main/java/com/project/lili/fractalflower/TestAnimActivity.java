package com.project.lili.fractalflower;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class TestAnimActivity extends AppCompatActivity {

    private AnimView animView;
    private Thread animThread;

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
        animThread = new Thread(new AnimView(this.getApplicationContext()));
        animThread.start();
    }

    public void replayAnimation(View view) {
        animView.reset();
    }
}
