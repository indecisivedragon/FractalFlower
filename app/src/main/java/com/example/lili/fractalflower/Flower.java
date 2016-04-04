package com.example.lili.fractalflower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lili on 3/26/2016.
 */
public class Flower {

    private ArrayList<Petal> petals = new ArrayList<Petal>();
    private ArrayList<Ring> rings = new ArrayList<Ring>();

    //centered overlap in petals is default off
    private boolean centerPetals = false;

    private Random rand = new Random();

    public Flower() {
        /*
        Petal petal = new Petal(PetalShape.MEDIUM);
        petals.add(petal);

        rings.add(new Ring(1, 5, 0, Color.BLUE));*/
    }

    public Flower(int type) {
        //lavender shades for all of them
        int color = Color.argb(255,200, 0, 200);
        int scale = 3;

        //medium petal
        Petal med = new Petal(PetalShape.WIDE);
        petals.add(0, med);

        Ring current = new Ring(scale, 5, 0, color);
        rings.add(0, current);
    }

    public Flower(FlowerTypes type) {
        switch (type) {
            case THREE_LAYERED_RANDOM_PASTEL:
                //random number of petals
                int numPetals = rand.nextInt(4) + 5;
                //pastel colors
                int r = rand.nextInt(100) + 150;
                int g = rand.nextInt(100) + 50;
                int b = rand.nextInt(100) + 150;
                //three rings of varying size 1, 2, 3
                for (int i = 0; i<3; i++) {
                    int color = Color.argb(200 - 50*i,r-25*i, g-25*i, b-25*i);
                    int scale = i+1;

                    Petal med = new Petal(PetalShape.MEDIUM);
                    petals.add(i, med);

                    Ring current = new Ring(scale, numPetals, 0, color);
                    rings.add(i, current);
                }
                break;
            case TEST:
                break;
            default:
                break;
        }
    }

    //adds a complete ring to the arraylists, including the petal information
    public void addRing(int size, int petalRangeStart, int petalRangeEnd, int offset, int color, PetalShape shape) {
        int numPetals = rand.nextInt(petalRangeEnd - petalRangeStart + 1) + petalRangeStart;
        Ring r = new Ring(size, numPetals, offset, color);
        rings.add(r);

        Petal p = new Petal(shape);
        petals.add(p);
    }

    public static void drawFlower(Canvas canvas, Flower flower) {
        canvas.save();
        for (int i=0; i<flower.getLevels(); i++) {
            //rotation of each individual petal
            int numPetal = flower.getNumPetals(i);
            double angle = 360.0/numPetal;
            int offset = flower.getOffset(i);

            //create petal shape from bounds (petal) and ring color
            ShapeDrawable oval = new ShapeDrawable(new OvalShape());
            oval.setBounds(flower.getLeft(i), flower.getTop(i), flower.getRight(i), flower.getBottom(i));
            oval.getPaint().setColor(flower.getColor(i));

            canvas.save();
            canvas.rotate(offset);
            //draw the ring
            for (int j=0; j<numPetal; j++) {
                //check centering and align petals if needed
                if (flower.centerPetals) {
                    //TODO make this less redundant
                    float mult = (float) (flower.getOvalHeight(i)*2.0/flower.getOvalWidth(i));
                    canvas.translate((float) (flower.getOvalWidth(i)*mult)/numPetal, (float) (flower.getOvalWidth(i)*mult)/numPetal);
                }
                canvas.rotate((float) (angle));

                oval.draw(canvas);
            }
            canvas.restore();

            //align center if center petals is on
            if (flower.centerPetals) {
                //canvas.translate(numPetal, 0);
                //canvas.translate(0, -(flower.getPetal(i).bottom-flower.getPetal(i).top)/2);
            }
        }
        canvas.restore();
    }

    //single ring
    public static void drawTestFlower(Canvas canvas, Flower flower) {
        //rotation of each individual petal
        int numPetal = flower.getNumPetals(0);
        int angle = 360/numPetal;
        int offset = flower.getOffset(0);

        //create petal shape from bounds (petal) and ring color
        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setBounds(flower.getLeft(0), flower.getTop(0), flower.getRight(0), flower.getBottom(0));
        oval.getPaint().setColor(Color.argb(100, 100, 0, 100));

        canvas.save();
        canvas.translate(300, 300);

        oval.draw(canvas);

        canvas.translate(60, 60);
        //Flower.translateToCenter(canvas, flower, 0, angle);
        canvas.rotate(angle);
        oval.draw(canvas);

        canvas.translate(60, 60);
        //Flower.translateToCenter(canvas, flower, 0, angle);
        canvas.rotate(angle);
        oval.draw(canvas);

        canvas.translate(60, 60);
        canvas.rotate(angle);
        oval.draw(canvas);

        canvas.translate(60, 60);
        canvas.rotate(angle);
        oval.draw(canvas);

        canvas.restore();
    }

    /*
    //translate by x, y amount to move all petals in the ring to a common center
    public static void translateToCenter(Canvas canvas, Flower flower, int level, int angle) {
        //hypotenuse distance between old ycenter and rotated ycenter
        double ovalHeight = flower.getYCenter(level);
        double alpha = (180.0 - angle)/2;
        double hypotenuse = 2*ovalHeight*Math.cos(alpha);

        //translate left x amount to get to rotated x coordinate
        float dx = (float) (Math.cos(90-alpha)*hypotenuse);

        //translate up y amount to get to rotated y coordinate
        float dy = (float) (Math.sin(90-alpha)*hypotenuse);

        canvas.translate(dx, dy);
    } */

    /*
    //TODO finish this method
    public static void drawRing(Canvas canvas, Flower flower, int level) {
        //rotation of each individual petal
        int numPetal = flower.getNumPetals(level);
        int angle = 360/numPetal;
        int offset = flower.getOffset(level);

        //create petal shape from bounds (petal) and ring color
        ShapeDrawable oval = new ShapeDrawable(new OvalShape());
        oval.setBounds(flower.getLeft(level), flower.getTop(level), flower.getRight(level), flower.getBottom(level));
        oval.getPaint().setColor(flower.getColor(level));

        //check centering
        int xTranslate = 0;
        int yTranslate = 0;
        if (flower.centerPetals) {
            xTranslate = flower.getXCenter(level);
            yTranslate = flower.getYCenter(level);
        }

        //draw the ring
        for (int j=0; j<numPetal; j++) {
            //start canvas transform
            canvas.save();
            canvas.rotate(angle * j + offset);

            oval.draw(canvas);

            //end canvas transform
            canvas.restore();
        }
    } */

    public void setCenterPetals(boolean b) {
        centerPetals = b;
    }

    public boolean getCenterPetals() {
        return centerPetals;
    }

    public int getLevels() {
        return rings.size();
    }

    public Petal getPetal(int level) {
        return petals.get(level);
    }

    private int getNumPetals(int level) {
        return rings.get(level).numPetal;
    }

    private int getOffset(int level) {
        return rings.get(level).offset;
    }

    private int getColor (int level) {
        return rings.get(level).color;
    }

    private int getLeft(int level) {
        int scale = rings.get(level).scale;
        return (petals.get(level).left)*scale;
    }

    private int getTop(int level) {
        int scale = rings.get(level).scale;
        return (petals.get(level).top)*scale;
    }

    private int getRight(int level) {
        int scale = rings.get(level).scale;
        return (petals.get(level).right)*scale;
    }

    private int getBottom(int level) {
        int scale = rings.get(level).scale;
        return (petals.get(level).bottom)*scale;
    }

    private int getOvalHeight(int i) {
        int height = Math.abs(this.getBottom(i) - this.getTop(i));
        return height;
    }

    private int getOvalWidth(int i) {
        int width = Math.abs(this.getRight(i) - this.getLeft(i));
        return width;
    }

    private class Ring{
        protected int scale = 0;
        protected int numPetal = 0;
        protected int offset = 0;
        protected int color = Color.argb(100, 100, 0, 100);

        Ring(int scale, int numPetal, int offset, int color) {
            this.scale = scale;
            this.numPetal = numPetal;
            this.offset = offset;
            this.color = color;
        }
    }

    private class Petal{
        //default medium shape
        protected PetalShape shape = PetalShape.MEDIUM;

        protected int left;
        protected int top;
        protected int right;
        protected int bottom;

        Petal(int l, int t, int r, int b) {
            left = l;
            top = t;
            right = r;
            bottom = b;
            this.setDimensions(PetalShape.CUSTOM);
        }

        Petal(PetalShape p) {
            this.setDimensions(p);
        }

        Petal() {
            this(PetalShape.MEDIUM);
        }

        //changing shape requires recalculating bounds
        protected void setDimensions(PetalShape p) {
            shape = p;

            switch (p) {
                case NARROW:
                    left = 0;
                    top = 0;
                    right = 100;
                    bottom = 30;
                    break;
                case MEDIUM:
                    left = 0;
                    top = 0;
                    right = 100;
                    bottom = 40;
                    break;
                case WIDE:
                    left = 0;
                    top = 0;
                    right = 100;
                    bottom = 50;
                    break;
                case CUSTOM:
                    //this should be handled elsewhere
                    break;
                default:
                    break;
            }
        }
    }

    public enum PetalShape {
        NARROW, MEDIUM, WIDE, CUSTOM;
    }

    public enum FlowerTypes {
        THREE_LAYERED_RANDOM_PASTEL, TEST;
    }
}
