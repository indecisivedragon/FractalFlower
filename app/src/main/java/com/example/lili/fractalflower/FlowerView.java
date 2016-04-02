package com.example.lili.fractalflower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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

    private int height = 200;
    private int width = 200;
    private int heightPadding = 0;
    private int widthPadding = 0;

    /*
    //make new canvas if true; reload from bitmap if false
    private boolean makeNewCanvas = true;
    //add a flower or not to whatever canvas we have
    private boolean addFlower = false;
    */

    private ArrayList<Flower> flowers = new ArrayList<Flower>();

    private Bitmap bitmap;

    FlowerView(Context context) {
        super(context);
        resetBufferCanvas();
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);

        height = getHeight();
        width = getWidth();

        //set bitmap and draw to canvas
        //canvas = c;
        //init(c);

        c.drawBitmap(bufferBitmap, 0, 0, paint);

        /*
        if (addFlower) {
            flowers = new ArrayList<Flower>();
            Flower flower = new Flower(Flower.FlowerTypes.THREE_LAYERED_RANDOM_PASTEL);
            //Flower flower = new Flower(10);
            flowers.add(flower);

            //add flowers, if any
            drawFlowers(c);
            //Flower.drawTestFlower(canvas, flower);
        }
        */
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap b) {
        bitmap = b;
    }

    /**
     * initializes the ovals
     */
    private void init(Canvas canvas) {
        //sets height and width of the screen
        height = canvas.getHeight();
        width = canvas.getWidth();

        //sets padding to 1/5 of dimension
        heightPadding = height/5;
        widthPadding = width/5;

        System.out.println("canvas height" + height);

        /*
        if (makeNewCanvas) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
        }
        else {
            canvas = new Canvas(bitmap);
        }
        */
    }

    private void drawFlowers(Canvas canvas) {
        for (int i=0; i<flowers.size(); i++) {
            int dx = widthPadding + rand.nextInt(width - 2*widthPadding);
            int dy = heightPadding + rand.nextInt(height - 2*heightPadding);

            canvas.save();
            canvas.translate(300, 300);
            //canvas.translate(dx, dy);
            Flower.drawFlower(canvas, flowers.get(i));
            canvas.restore();
        }
    }

    //testing oval
    private void drawOval(Canvas canvas) {
        paint.setColor(Color.argb(200, 100, 0, 100));
        paint.setStrokeWidth(0);
        canvas.drawOval(100, 100, 300, 200, paint);

        paint.setColor(Color.argb(200, 150, 0, 150));
        canvas.drawOval(175, 140, 300, 160, paint);
    }

    public void resetBufferCanvas() {new Canvas();
        bufferBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bufferCanvas = new Canvas(bufferBitmap);
        bufferCanvas.drawColor(Color.WHITE);
    }

    public void addFlower() {
        flowers.add(new Flower(10));
        for (int i=0; i<flowers.size(); i++) {
            int dx = widthPadding + rand.nextInt(width - 2 * widthPadding);
            int dy = heightPadding + rand.nextInt(height - 2 * heightPadding);

            bufferCanvas.save();
            bufferCanvas.translate(dx, dy);

            Flower.drawFlower(bufferCanvas, flowers.get(i));

            bufferCanvas.restore();
        }
    }

    /*
    public void setMakeNewCanvas(boolean b) {
        makeNewCanvas = b;
    }

    public void setAddFlower(boolean b) {
        addFlower = b;
    }
    */
}
