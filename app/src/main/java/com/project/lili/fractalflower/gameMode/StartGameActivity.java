package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.lili.fractalflower.AnimView;
import com.project.lili.fractalflower.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class StartGameActivity extends AppCompatActivity {

    private AnimView animView;
    private String filename = "data_file.txt";

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

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.start_game_layout);
        animView = new AnimView(this);
        animView.setBackgroundColor(Color.WHITE);

        layout.addView(animView);
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

}
