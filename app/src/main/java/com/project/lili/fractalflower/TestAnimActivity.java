package com.project.lili.fractalflower;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class TestAnimActivity extends AppCompatActivity {

    private AnimView animView;

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
    }

    public void replayAnimation(View view) {
        animView.setSetup(true);
        animView.invalidate();
    }
}
