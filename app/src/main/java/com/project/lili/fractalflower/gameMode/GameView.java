package com.project.lili.fractalflower.gameMode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

/**
 * Created by L on 8/18/2016.
 */
public class GameView extends SurfaceView {

    private int numCircles = 4;
    private float rectangleHeight = 100;
    private float rectangleWidth = 100;
    private float circleRadius = 80;

    private boolean displaycircles = false;

    private Canvas c;

    GameView(Context context) {
        super(context);
    }

    public void onDraw(Canvas c) {
        super.onDraw(c);
        this.c = c;

        if (displaycircles) {
            setupCircles();
        }
    }

    public void setBounds(int h, int w) {
        rectangleHeight = (float) h/numCircles;
        rectangleWidth = (float) w/numCircles;
        System.out.println(rectangleHeight + ", " + rectangleWidth);
    }

    public void setupCircles() {
        float circleSize = 100;
        //find smaller of the two dimensions to set the circle size to
        if (rectangleHeight <= rectangleWidth) {
            circleSize = rectangleHeight;
        }
        else {
            circleSize = rectangleWidth;
        }

        System.out.println("circle size = " + circleSize);
        //diameter is 2/3 of circle size
        //radius is half that
        circleRadius = circleSize/3;

        //row
        for (int i=0; i<numCircles; i++) {
            //column
            for (int j=0; j<numCircles; j++) {
                System.out.println("circle: " + i + ", " + j);
                drawCircleHere(i, j);
            }
        }
    }

    //draw the circle at location row, column with radius
    private void drawCircleHere(int row, int column) {
        //find center of each rectangle area
        float xCenter = rectangleWidth*row + 10;
        float yCenter = rectangleHeight*column + 10;

        System.out.println(row + ", " + column + ": " + xCenter + ", " + yCenter);

        Paint p = new Paint();
        p.setColor(Color.WHITE);

        c.drawOval(xCenter, yCenter, xCenter+circleRadius*2, yCenter+circleRadius*2, p);
    }

    public void resetScreen(boolean b) {
        displaycircles = b;
    }
}
