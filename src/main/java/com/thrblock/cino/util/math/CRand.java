package com.thrblock.cino.util.math;

import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * 随机数产生器
 * 
 * @author thrblock
 *
 */
public class CRand {
    static Random random = new Random();
    static long seed = -1L;

    private CRand() {
    }

    public static void setSeed(long seed) {
        random.setSeed(seed);
        CRand.seed = seed;
    }

    public static long getSeed() {
        return seed;
    }

    public static int getRandomSgn() {
        return CRand.getRandomBoolean() ? 1 : -1;
    }

    public static double getGaussian() {
        return random.nextGaussian();
    }

    /**
     * 获得一个给定范围的随机整数
     * 
     * @param smallistNum
     *            最小值
     * @param biggestNum
     *            最大值
     * @return
     */
    public static int getRandomNum(int smallistNum, int biggestNum) {
        int r = random.nextInt() & Integer.MAX_VALUE;
        return (r % (biggestNum - smallistNum + 1)) + smallistNum;
    }

    /**
     * 获得一个随机的布尔值
     * 
     * @return
     */
    public static boolean getRandomBoolean() {
        return getRandomNum(0, 1) == 1;
    }

    /**
     * 获得一个随机在0~1的浮点数
     * 
     * @return
     */
    public static float getRandomFloatInOne() {
        return (float) getRandomNum(0, 1000) / 1000;
    }

    /**
     * 获得一个随机的颜色
     * 
     * @return
     */
    public static Color getRandomColor() {
        float r = (float) getRandomNum(0, 255) / 255;
        float g = (float) getRandomNum(0, 255) / 255;
        float b = (float) getRandomNum(0, 255) / 255;

        return new Color(r, g, b);
    }

    /**
     * 获得一个随机的暖色调（不包括全部的暖色调）
     * 
     * @return
     */
    public static Color getRandomWarmColor() {
        int ri = getRandomNum(0, 255);
        int gi = getRandomNum(0, ri);
        int bi = getRandomNum(0, gi);

        float r = (float) ri / 255;
        float g = (float) gi / 255;
        float b = (float) bi / 255;

        return new Color(r, g, b);
    }

    /**
     * 获得一个随机的冷色调（不包括全部的冷色调）
     * 
     * @return
     */
    public static Color getRandomColdColor() {
        int ri = getRandomNum(0, 255);
        int gi = getRandomNum(ri, 255);
        int bi = getRandomNum(gi, 255);

        float r = (float) ri / 255;
        float g = (float) gi / 255;
        float b = (float) bi / 255;

        return new Color(r, g, b);
    }

    /**
     * 以一定概率返回一个布尔值 精度为%1
     * 
     * @param rate
     *            0 ～ 100 整数
     * @return
     */
    public static boolean getRate(int rate) {
        if (rate < 0 || rate > 100) {
            return false;
        } else {
            return getRandomNum(0, 100) < rate;
        } 
    }

    /**
     * 返回给定数组中的一个随机元素
     * @param t
     * @return
     */
    public static <T> T getElement(T[] t) {
        int index = getRandomNum(0, t.length - 1);
        return t[index];
    }

    /**
     * 返回给定Collection中的一个随机元素
     * @param c
     * @return
     */
    public static <T> T getElement(Collection<? extends T> c) {
        int atmp = getRandomNum(0, c.size() - 1);
        Iterator<? extends T> iter = c.iterator();
        while (atmp > 0) {
            atmp--;
            iter.next();
        }
        return iter.next();
    }
}
