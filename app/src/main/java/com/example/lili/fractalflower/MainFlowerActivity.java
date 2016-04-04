package com.example.lili.fractalflower;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainFlowerActivity extends AppCompatActivity {

    private FlowerView flowerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flower);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //get layout and add canvas to it
        LinearLayout layout = (LinearLayout) getLayout();
        drawCanvas(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_flower, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingsPage();
        }

        return super.onOptionsItemSelected(item);

        //TODO add menu things
    }

    @Override
    public void onPause() {
        super.onPause();
        flowerView.saveBitmap();
    }

    @Override
    public void onResume() {
        super.onResume();
        flowerView.restoreBitmap();
    }

    /**
     * go to the settings page
     */
    private void showSettingsPage() {
        Intent intent = new Intent(this, DisplaySettingsActivity.class);
        startActivity(intent);
        //TODO actually add settings
    }

    public View getLayout() {
        return findViewById(R.id.main_flower_layout);
    }

    /**
     * draws the flower view onto layout
     * @param layout the layout to add view to
     */
    public void drawCanvas(LinearLayout layout) {
        flowerView = new FlowerView(this);
        flowerView.setBackgroundColor(Color.WHITE);
        layout.addView(flowerView);
    }

    /**
     * clears the canvas
     * does not add a new flower
     * @param view
     */
    public void clearCanvas(View view) {
        flowerView.resetBufferCanvas();
        flowerView.invalidate();
    }

    /**
     * makes a new random flower and adds it to canvas
     * @param view
     */
    public void generateCanvas(View view) {
        flowerView.addFlower();
        flowerView.invalidate();
    }

    //testing
    public void addCanvas(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "this button doesn't do anything :(", Toast.LENGTH_SHORT);
        t.show();

        //flowerView.canvas.drawOval(100, 100, 200, 200, new Paint(Color.BLUE));

        /*
        //flowerView.setMakeNewCanvas(false);
        //flowerView.store();
        flowerView.invalidate(); */
    }
}
