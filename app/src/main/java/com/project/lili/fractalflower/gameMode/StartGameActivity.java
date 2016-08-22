package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.lili.fractalflower.AnimView;
import com.project.lili.fractalflower.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class StartGameActivity extends AppCompatActivity implements LevelTrackFragment.OnFragmentInteractionListener, LevelScreenFragment.OnFragmentInteractionListener {

    private AnimView animView;
    private String filename = "data_file.txt";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
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
        int width = size.x;
        int height = size.y;

    /*
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        int height = params.height;
        int width = params.width;
*/

        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            width = size.x;
            height = size.y;
        }
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            width = size.x;
            height = size.y;
        }

        LevelScreenFragment screen = LevelScreenFragment.newInstance(height, width, rotation);
        System.out.println("layout: height " + height + ", width " + width);

        //fragmentTransaction.add(R.id.fragment_level_screen, screen, "fragment_level_screen");
        fragmentTransaction.replace(R.id.fragment_level_screen, screen);

        LevelTrackFragment trackFragment = LevelTrackFragment.newInstance(height, width, rotation);
        //fragmentTransaction.add(R.id.fragment_level_track, hello, "fragment_level_track");
        fragmentTransaction.replace(R.id.fragment_level_track, trackFragment);

        /*
        int thing = R.id.fragment_level_screen;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.findViewById(thing).getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.findViewById(thing).setLayoutParams(params);
        */

        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
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

    public void gameReset(View view) {
        Toast t = Toast.makeText(this.getApplicationContext(), "game reset button here", Toast.LENGTH_SHORT);
        t.show();

        LevelScreenFragment levelScreenFragment = (LevelScreenFragment) fragmentManager.findFragmentById(R.id.fragment_level_screen);
        if (levelScreenFragment != null) {
            levelScreenFragment.resetGameScreen();
        }
    }

}

