package com.example.lili.fractalflower;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Lili on 4/2/2016.
 * ugh, I gave in and mada a factory...
 */
public class FlowerFactory {

    private static Random rand = new Random();

    //overall + defaults
    private static int rings = 1;
    private static boolean centerPetals = false;
    private static FlowerColor color = FlowerColor.PASTEL;

    public static int getSpinnerPosition() {
        return spinnerPosition;
    }

    public static void setSpinnerPosition(int spinnerPosition) {
        FlowerFactory.spinnerPosition = spinnerPosition;
    }

    private static int spinnerPosition = 0;
    //alpha, r, g, b
    //private static int[] color = {100, 100, 100, 100};
    private static double gradient = 50;

    //each ring
    private static int size;
    private static int petalRangeStart;
    private static int petalRangeEnd;
    private static int offset;
    //don't forget color here even though it's defined above
    private static Flower.PetalShape shape = Flower.PetalShape.MEDIUM;


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
            f.addRing(i, numPetals, 0, ringColor, Flower.PetalShape.WIDE);
        }
        return f;
    }

    //from palest shade
    private static int[] getColorFromEnum(FlowerColor color) {
        int[] tempColor = new int[4];
        tempColor[0] = 100;

        switch (color) {
            case PASTEL:
                tempColor[1] = rand.nextInt(100) + 100;
                tempColor[2] = rand.nextInt(100) + 1;
                tempColor[3] = rand.nextInt(100) + 100;
                break;
            case PURPLE:
                tempColor[1] = 200;
                tempColor[2] = 0;
                tempColor[3] = 200;
                break;
            case YELLOW:
                tempColor[1] = 200;
                tempColor[2] = 200;
                tempColor[3] = 20;
                break;
            case PINK:
                tempColor[1] = 200;
                tempColor[2] = 100;
                tempColor[3] = 150;
                break;
            case BLUE:
                tempColor[1] = 0;
                tempColor[2] = 0;
                tempColor[3] = 200;
                break;
            case ORANGE:
                tempColor[1] = 200;
                tempColor[2] = 125;
                tempColor[3] = 0;
                break;
            default:
                tempColor[1] = 0;
                tempColor[2] = 0;
                tempColor[3] = 0;
                break;
        }
        return tempColor;
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

    public enum FlowerColor {
        PASTEL, PURPLE, YELLOW, PINK, BLUE, ORANGE;
    }
}
