package com.example.lili.fractalflower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lili on 3/25/2016.
 */
public class FlowerView extends SurfaceView {

    private Paint paint = new Paint();
    private Random rand = new Random();

    private Canvas bufferCanvas = null;
    private Bitmap bufferBitmap = null;

    private static Bitmap savedBitmap = null;

    private int height = 200;
    private int width = 200;
    private int heightPadding = 0;
    private int widthPadding = 0;

    private ArrayList<Flower> flowers = new ArrayList<Flower>();


    FlowerView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        //set bitmap and draw to canvas
        initSizeParams(c);

        //draw to canvas the current bitmap stored
        c.drawBitmap(bufferBitmap, 0, 0, paint);
    }

    /**
     * sets width and height
     */
    private void initSizeParams(Canvas canvas) {
        //sets height and width of the screen
        height = canvas.getHeight();
        width = canvas.getWidth();

        //sets padding to 1/5 of dimension
        heightPadding = height/5;
        widthPadding = width/5;

        if (bufferBitmap == null) {
            resetBufferCanvas();
        }
    }

    /**
     * draw every flower in the flower array
     */
    private void drawFlowers() {
        for (int i=0; i<flowers.size(); i++) {
            Flower.drawFlower(bufferCanvas, flowers.get(i));
        }
    }

    //testing oval
    private void drawOval() {
        paint.setColor(Color.argb(200, 100, 0, 100));
        paint.setStrokeWidth(0);
        bufferCanvas.drawOval(100, 100, 300, 200, paint);

        paint.setColor(Color.argb(200, 150, 0, 150));
        bufferCanvas.drawOval(175, 140, 300, 160, paint);
    }

    public void resetBufferCanvas() {
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bufferCanvas = new Canvas(bufferBitmap);
        bufferCanvas.drawColor(Color.WHITE);
    }

    //draw a flower at a random location
    public void addFlower() {
        int dx = widthPadding + rand.nextInt(width - 2 * widthPadding);
        int dy = heightPadding + rand.nextInt(height - 2 * heightPadding);
        addFlower(dx, dy);
    }

    //draw a flower at location dx, dy
    public void addFlower(float dx, float dy) {
        //flowers.add(new Flower(Flower.FlowerTypes.THREE_LAYERED_RANDOM_PASTEL));
        //FlowerFactory factory = new FlowerFactory();
        Flower flower = FlowerFactory.createFlower(3, FlowerFactory.getCenterPetals(), FlowerFactory.getColor());
        flower.setLocationX(dx);
        flower.setLocationY(dy);

        flowers.add(flower);

        //System.out.println("added flowers, now drawing");
        drawFlowers();

        //System.out.println("finished drawing flowers");
        flowers.clear();
    }

    //saves current bitmap for activity resume
    public void saveBitmap() {
        savedBitmap = bufferBitmap.copy(bufferBitmap.getConfig(), true);
    }

    //restores previous bitmap on activity resume
    public void restoreBitmap() {
        if (savedBitmap != null) {
            bufferBitmap = savedBitmap.copy(savedBitmap.getConfig(), true);
            bufferCanvas = new Canvas(bufferBitmap);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        float x, y;

        switch (action) {
            default:
                x = event.getX();
                y = event.getY();

                //Toast t = Toast.makeText(this.getContext(), x + " " + y, Toast.LENGTH_SHORT);
                //t.show();

                addFlower(x, y);

                break;
        }
        this.postInvalidate();
        return super.onTouchEvent(event);
    }
}
