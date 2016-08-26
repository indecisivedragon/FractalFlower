package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.lili.fractalflower.AnimView;
import com.project.lili.fractalflower.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.StreamHandler;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class StartGameActivity extends AppCompatActivity implements LevelTrackFragment.OnFragmentInteractionListener, LevelScreenFragment.OnFragmentInteractionListener, GameView.onRefreshListener {

    private String filename = "data_file.txt";

    private FragmentManager fragmentManager;

    private int mActivePointerId;

    private String DEBUG_TAG = "game activity screen";

    private String toDisplay = "...";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.start_game_layout_vertical);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        //this is to get the height and width of the display screen so that it displays properly
        //i can't believe this was so much trouble
        Display display = getWindowManager().getDefaultDisplay();
        int rotation = display.getRotation();

        Point size = new Point();
        display.getSize(size);
        //default sizes, to be changed
        int height = size.y;
        int width = size.x;

    /*
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        int height = params.height;
        int width = params.width;
*/

        LevelScreenFragment screen = LevelScreenFragment.newInstance(height, width, rotation);
        System.out.println("layout: height " + height + ", width " + width);
        fragmentTransaction.replace(R.id.fragment_level_screen, screen);

        LevelTrackFragment trackFragment = LevelTrackFragment.newInstance(height, width, rotation);
        fragmentTransaction.replace(R.id.fragment_level_track, trackFragment);

        /*
        int thing = R.id.fragment_level_screen;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.findViewById(thing).getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.findViewById(thing).setLayoutParams(params);
        */

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        textView = (TextView) findViewById(R.id.text_to_display);
    }

    public void writeFile(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "write file clicked", Toast.LENGTH_SHORT);
        t.show();

        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "read file clicked", Toast.LENGTH_SHORT);
        t.show();

        FileInputStream inputStream;

        try {
            inputStream = openFileInput(filename);
            int content;
            System.out.println("before text");
            while ((content=inputStream.read()) != -1) {
                System.out.print((char) content);
            }
            System.out.println();
            System.out.println("after text");
            inputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearFile(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "clear file", Toast.LENGTH_SHORT);
        t.show();


        String string = "";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("hi");
    }

    @Override
    public void onFragmentStart() {
        System.out.println("fragment start");
    }

    @Override
    public void onLevelScreenChange(String s) {
        Toast t = Toast.makeText(this.getApplicationContext(), s, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();
    }

    public void gameReset(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "game reset button here", Toast.LENGTH_SHORT);
        t.show();

        //game screen is same size as before
        LevelScreenFragment levelScreenFragment = (LevelScreenFragment) fragmentManager.findFragmentById(R.id.fragment_level_screen);
        int levelHeight = levelScreenFragment.getGameView().getHeight();
        int levelWidth = levelScreenFragment.getGameView().getWidth();
        levelScreenFragment.resetGameScreen(levelHeight, levelWidth);
    }

    @Override
    public void onRefresh(String s) {
        System.out.println(s);
        toDisplay = s;
        textView.setText(toDisplay);
    }
}

