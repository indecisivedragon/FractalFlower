package com.project.lili.fractalflower;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Lili on 3/26/2016.
 * this class describes a flower
 */
public class Flower {

    private ArrayList<Petal> petals = new ArrayList<>();
    private ArrayList<Ring> rings = new ArrayList<>();

    private float locationX = 0, locationY = 0;

    //centered overlap in petals is default off
    private boolean centerPetals = false;

    private static int width = 100;

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

        Random rand = new Random();

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
    public void addRing(double size, int numPetals, int offset, int color, PetalShape shape) {
        //int numPetals = rand.nextInt(petalRangeEnd - petalRangeStart + 1) + petalRangeStart;
        Ring r = new Ring(size, numPetals, offset, color);
        rings.add(r);

        Petal p = new Petal(shape);
        petals.add(p);
    }

    //draws the flower on the canvas
    //meant to be called from view thread
    public static void drawFlower(Canvas canvas, Flower flower) {
        canvas.save();
        canvas.translate(flower.getLocationX(), flower.getLocationY());
        for (int i=0; i<flower.getLevels(); i++) {
            //rotation of each individual petal
            int numPetal = flower.getNumPetals(i);
            double angle = 360.0/numPetal;
            int offset = flower.getOffset(i);

            //create petal shape from bounds (petal) and ring color
            ShapeDrawable oval = new ShapeDrawable(new OvalShape());
            oval.setBounds(flower.getLeft(i), flower.getTop(i), flower.getRight(i), flower.getBottom(i));
            oval.getPaint().setColor(flower.getColor(i));

            //difference between center of flower and center of where oval will be drawn
            float yShift = flower.getOvalHeight(i)/2;

            canvas.save();
            canvas.rotate(offset);
            //draw the ring
            for (int j=0; j<numPetal; j++) {
                oval.draw(canvas);
                //check centering and align petals if needed
                //move oval up to center
                if (flower.centerPetals) {
                    canvas.translate(0, yShift);
                }
                //turn around center
                canvas.rotate((float) (angle));
                //move oval back down for next petal
                if (flower.centerPetals) {
                    canvas.translate(0, -yShift);
                }
            }
            canvas.restore();

            //center ring centers if centerpetals is on
            //shift up so that next yShift will be correct (but don't do this for last level
            if (flower.centerPetals && i<flower.getLevels()-1) {
                float diff = yShift - flower.getOvalHeight(i+1)/2;
                canvas.translate(0, diff);
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
        return ((int) ((petals.get(level).left)*getScaled(level)));
    }

    private int getTop(int level) {
        return ((int) ((petals.get(level).top)*getScaled(level)));
    }

    private int getRight(int level) {
        return ((int) ((petals.get(level).right)*getScaled(level)));
    }

    private int getBottom(int level) {
        return ((int) ((petals.get(level).bottom)*getScaled(level)));
    }

    private double getScaled(int level) {
        return rings.get(level).scale;
    }

    private int getOvalHeight(int i) {
        return Math.abs(this.getBottom(i) - this.getTop(i));
    }

    private int getOvalWidth(int i) {
        return Math.abs(this.getRight(i) - this.getLeft(i));
    }

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float x) {
        locationX = x;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float y) {
        locationY = y;
    }

    //sizes
    //i, 2, 3
    //0.75, 1.5, 2.25
    //0.5, 1, 1.5
    private class Ring{
        protected double scale = 0;
        protected int numPetal = 0;
        protected int offset = 0;
        protected int color = Color.argb(100, 100, 0, 100);

        Ring(double scale, int numPetal, int offset, int color) {
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
            left = 0;
            top = 0;
            right = width;

            switch (p) {
                case NARROW:
                    bottom = width/3;
                    break;
                case MEDIUM:
                    bottom = (int) (width/2.5);
                    break;
                case WIDE:
                    bottom = width/2;
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
        NARROW, MEDIUM, WIDE, CUSTOM
    }

    public enum FlowerTypes {
        THREE_LAYERED_RANDOM_PASTEL, TEST
    }
}
