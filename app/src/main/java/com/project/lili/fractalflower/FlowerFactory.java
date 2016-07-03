package com.project.lili.fractalflower;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Lili on 4/2/2016.
 * ugh, I gave in and mada a factory...
 */
public class FlowerFactory {

    private static Random rand = new Random();

    //overall + defaults
    //these are the settings modes that should be preserved after action end
    private static int rings = 1;
    private static boolean centerPetals = false;
    private static FlowerColor color = FlowerColor.PASTEL;
    private static int spinnerPosition = 0;

    private static double gradient = 50;

    //each ring
    private static int size = 2;
    private static int petalRangeStart;
    private static int petalRangeEnd;
    private static int offset;
    //don't forget color here even though it's defined above
    private static Flower.PetalShape shape = Flower.PetalShape.MEDIUM;

    //apparently I need this to set the color
    private static int[] colorShades = new int[4];


    FlowerFactory() {

    }

    public static Flower createFlower() {
        Flower flower = new Flower(Flower.FlowerTypes.THREE_LAYERED_RANDOM_PASTEL);
        flower.setCenterPetals(centerPetals);
        return flower;
    }

    public static Flower createFlower(int rings, boolean centerPetals, FlowerColor color) {
        FlowerFactory.rings = rings;
        FlowerFactory.centerPetals = centerPetals;
        FlowerFactory.color = color;

        return makeFlower();
    }

    private static Flower makeFlower() {
        Flower f = new Flower();
        f.setCenterPetals(centerPetals);
        double increase = gradient/rings;
        int[] rgbArray = getColorFromEnum(color);

        int numPetals = rand.nextInt(4)+5;

        for (int i=rings; i>0; i--) {
            int ringColor = Color.argb((int) (rgbArray[0]+increase *i), (int) (rgbArray[1]+increase*i), (int) (rgbArray[2]+increase*i), (int) (rgbArray[3]+increase*i));
            f.addRing(i*(0.5+0.25*(size-1)), numPetals, 0, ringColor, shape);
        }

        return f;
    }

    //from palest shade
    private static int[] getColorFromEnum(FlowerColor color) {
        int[] tempColor;

        switch (color) {
            case PASTEL:
                tempColor = setRandomColorArray(100, 100, 100, 0, 100);
                break;
            case PURPLE:
                tempColor = setRandomColorArray(30, 100, 180, 0, 180);
                break;
            case YELLOW:
                tempColor = setRandomColorArray(30, 100, 180, 180, 10);
                break;
            case PINK:
                tempColor = setRandomColorArray(30, 100, 180, 90, 140);
                break;
            case BLUE:
                tempColor = setRandomColorArray(30, 100, 0, 0, 180);
                break;
            case ORANGE:
                tempColor = setRandomColorArray(30, 100, 180, 115, 0);
                break;
            default:
                tempColor = setRandomColorArray(30, 100, 0, 0, 0);
                break;
        }
        return tempColor;
    }

    /**
     * sets random shades of a color
     * @param range change in shades
     * @param a alpha base which is not randomized
     * @param r r base
     * @param g g base
     * @param b b base
     * @return array that contains color in argb order
     */
    private static int[] setRandomColorArray(int range, int a, int r, int g, int b) {
        colorShades[0] = a;
        colorShades[1] = setRandomColor(range, r);
        colorShades[2] = setRandomColor(range, g);
        colorShades[3] = setRandomColor(range, b);
        return colorShades;
    }

    /**
     * returns random within range with base, bounds checking for color values 0 - 255
     * @param range range of shade
     * @param base a, r, g, b value to be passed in
     * @return color as int as in Color class
     */
    private static int setRandomColor(int range, int base) {
        int color = range/2 - rand.nextInt(range) + base;
        if (color < 0) {
            return 0;
        }
        else if (color > 255) {
            return 255;
        }
        else {
            return color;
        }
    }

    private static int getColorFromArray(int[] color, int level) {
        int a = color[0];
        int r = color[1];
        int g = color[2];
        int b = color[3];

        return Color.argb(a, r, g, b);
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        FlowerFactory.size = size;
    }

    public static int getPetalRangeStart() {
        return petalRangeStart;
    }

    public static void setPetalRangeStart(int petalRangeStart) {
        FlowerFactory.petalRangeStart = petalRangeStart;
    }

    public static int getPetalRangeEnd() {
        return petalRangeEnd;
    }

    public static void setPetalRangeEnd(int petalRangeEnd) {
        FlowerFactory.petalRangeEnd = petalRangeEnd;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        FlowerFactory.offset = offset;
    }

    public static Flower.PetalShape getShape() {
        return shape;
    }

    public static void setShape(Flower.PetalShape shape) {
        FlowerFactory.shape = shape;
    }

    public static int getRings() {
        return rings;
    }

    public static void setRings(int rings) {
        FlowerFactory.rings = rings;
    }

    public static void setCenterPetals(boolean centerPetals) {
        FlowerFactory.centerPetals = centerPetals;
    }

    public static boolean getCenterPetals() {
        return centerPetals;
    }

    public static void setColor(FlowerColor color) {
        FlowerFactory.color = color;
    }

    public static FlowerColor getColor() {
        return FlowerFactory.color;
    }

    public static int getSpinnerPosition() {
        return spinnerPosition;
    }

    public static void setSpinnerPosition(int spinnerPosition) {
        FlowerFactory.spinnerPosition = spinnerPosition;
    }

    public static double getGradient() {
        return gradient;
    }

    public static void setGradient(int gradient) {
        FlowerFactory.gradient = gradient;
    }


    public enum FlowerColor {
        PASTEL, PURPLE, YELLOW, PINK, BLUE, ORANGE
    }
}
