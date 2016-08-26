package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import java.util.logging.Level;

/**
 * Created by L on 8/18/2016.
 */
public class GameView extends SurfaceView {

    private String GAME_DEBUG = "game view";
    private int mActivePointerId;

    private int numCircles = 4;
    private float rectangleHeight = 100;
    private float rectangleWidth = 100;
    private float circleRadius = 80;

    private boolean startNewGame = false;
    //where do the circle coordinates start
    private float circleCoordinates[][] = new float[numCircles*numCircles][2];

    private Canvas c;

    private ConnectFour connectFour = new ConnectFour();
    //are we waiting on player input
    private boolean waitForHuman = true;
    //can we start showing the board
    private boolean display = false;

    private onRefreshListener onRefreshListener;

    GameView(Context context) {
        super(context);
        if (context instanceof onRefreshListener) {
            onRefreshListener = (onRefreshListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRefreshListener");
        }
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.c = c;

        if (startNewGame) {
            connectFour = new ConnectFour();
            startNewGame = false;
            display = true;
        }

        if (display) {
            setupCircles();
            drawBoard();
        }
    }

    public void setBounds(int h, int w) {
        rectangleHeight = (float) h/numCircles;
        rectangleWidth = (float) w/numCircles;
        //Log.d(GAME_DEBUG, "bounds: " + rectangleHeight + ", " + rectangleWidth);
    }

    //set up initial white circles
    public void setupCircles() {
        float circleSize = 100;
        //find smaller of the two dimensions to set the circle size to
        circleSize = Math.min(rectangleHeight, rectangleWidth);

        //diameter is 3/4 of circle size
        //radius is half that
        circleRadius = circleSize*3/8;
        //Log.d(GAME_DEBUG, "circle radius = " + circleRadius);

        setCircleCoordinates();

        //row
        for (int i=0; i<numCircles; i++) {
            //column
            for (int j=0; j<numCircles; j++) {
                //Log.d(GAME_DEBUG, "draw circle: " + i + ", " + j);
                drawCircleHere(i, j);
            }
        }
    }

    public void drawBoard() {
        for (int i = 0; i < numCircles*numCircles; i++) {
            Paint p = new Paint();
            p.setColor(connectFour.getColor(i));
            float xCenter = circleCoordinates[i][0];
            float yCenter = circleCoordinates[i][1];
            c.drawOval(xCenter - 25, yCenter - 25, xCenter + 25, yCenter + 25, p);
        }
    }

    private void setCircleCoordinates() {
        //row
        for (int i=0; i<numCircles; i++) {
            //column
            for (int j=0; j<numCircles; j++) {
                //find center of each rectangle area
                float xCenter = rectangleWidth*i + rectangleWidth/2;
                float yCenter = rectangleHeight*j + rectangleHeight/2;

                //Log.d(GAME_DEBUG, i + ", " + j + ": " + xCenter + ", " + yCenter);
                //current i, j circle that we are drawing
                int currentPosition = j*4 + i;
                circleCoordinates[currentPosition][0] = xCenter;
                circleCoordinates[currentPosition][1] = yCenter;
            }
        }
    }

    //draw the circle at location row, column with radius
    private void drawCircleHere(int row, int column) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);

        int currentPosition = row*4 + column;

        //draw the circle with the correct dimensions from stored coordinates and radius
        c.drawOval(circleCoordinates[currentPosition][0]-circleRadius, circleCoordinates[currentPosition][1]-circleRadius, circleCoordinates[currentPosition][0]+circleRadius, circleCoordinates[currentPosition][1]+circleRadius, p);
    }

    public void resetScreen(boolean b) {
        startNewGame = b;
        waitForHuman = true;
    }

    /*
    public void checkDrawFlower(float x, float y) {
        int circle = coordinatesValid(x, y);
        //if we can make a move, then proceed
        if (circle != -1) {
            proceed(circle);
            waitForHuman = true;
        }
        //otherwise do nothing
        else {
            waitForHuman = false;
        }
    }

    private void proceed(int circle) {
        connectFour.makeMove(circle);
    }

*/

    private int coordinatesValid(float x, float y) {
        //return the circle number if valid, or -1 if not
        for (int i = 0; i < numCircles*numCircles; i++) {
            float xCenter = circleCoordinates[i][0];
            float yCenter = circleCoordinates[i][1];
            if (isInRadius(x, y, xCenter, yCenter)) {
                Log.d(GAME_DEBUG, "coordinates valid at circle " + i);
                return i;
            }
        }
        Log.d(GAME_DEBUG, "coordinates not valid");
        return -1;
    }

    private boolean isInRadius(float x, float y, float xCenter, float yCenter) {
        //Log.d(GAME_DEBUG, xCenter + ", " + yCenter);
        //Log.d(GAME_DEBUG, Math.abs(x - xCenter) + ", " + Math.abs(y-yCenter) + " with radius " + circleRadius);
        if (Math.abs(x - xCenter)<circleRadius && Math.abs(y-yCenter)<circleRadius) {
            return true;
        }
        else return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        //coordinates of touch point
        float x = event.getX();
        float y = event.getY();

        Log.d(GAME_DEBUG, MotionEvent.actionToString(action) + "(x: " + x + ", y: " + y + "), id " + mActivePointerId);

        if (waitForHuman) {
            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    // Get the pointer ID of the first touch
                    mActivePointerId = event.getPointerId(0);
                    Log.d(GAME_DEBUG, " " + coordinatesValid(x, y));
                    //apparently this always needs to return true or nothing works
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    break;
                case (MotionEvent.ACTION_UP):
                    mActivePointerId = -1;
                    this.invalidate();
                    break;
                case (MotionEvent.ACTION_CANCEL):
                    break;
                case (MotionEvent.ACTION_OUTSIDE):
                    break;
                default:
                    break;
            }

            //if coordinates are indeed valid, we can proceed, otherwise not
            int circle = coordinatesValid(x, y);
            if (circle != -1) {
                connectFour.makeMove(circle);
                waitForHuman = false;
            }
        }
        else {
                connectFour.makeAIMove();
                waitForHuman = true;
        }

        onRefreshListener.onRefresh(getGameStatus());
        this.invalidate();
        return super.onTouchEvent(event);
    }

    public String getGameStatus() {
        return connectFour.getGameMessage();
    }

    public interface onRefreshListener {
        void onRefresh(String s);
    }
}
