package com.yimu;

import java.lang.System;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class Main {

    public static void main(String args[]) {
        putTest();
        hitrateTest();
    }

    static void putTest() {
        WindowLFU<String, Integer> cache = new WindowLFU<>(3, 9);
        cache.put("green", 4);
        cache.put("blue", 1);
        cache.put("blue", 3);
        cache.put("red", 5);
        cache.put("yellow", 2);
        cache.put("orange", 1);
        cache.put("orange", 1);
        cache.put("yellow", 2);
        cache.put("yellow", 2);

        /*cache.put("green", 4);
        cache.put("green", 1);
        cache.put("blue", 3);
        cache.put("red", 5);
        cache.put("green", 2);
        cache.put("blue", 1);
        cache.put("red", 1);
        cache.put("orange", 2);
        cache.put("yellow", 2);
        cache.put("blue", 2);*/

        Map<String, WindowLFU<String, Integer>.CacheNode<Integer>> data = cache.getCache();
        for (Map.Entry<String, WindowLFU<String, Integer>.CacheNode<Integer>> entry : data.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().counts);
        }
        Queue<String> windows = cache.getWindow();
        for (String str : windows) {
            System.out.print(str + ",");
        }
        System.out.println();
    }

    static void hitrateTest() {
        WindowLFU<String, Integer> cache = new WindowLFU<>(100, 200);
        Random random = new Random();
        // cal func(x,y) = 3*x+y
        for (int i = 0; i < 1000; i++) {
            int x = random.nextInt() % 5;
            int y = random.nextInt() % 5;
            String key = String.format("x=%d,y=%d", x, y);
            if (cache.get(key) == null) {
                cache.put(key, 3 * x + y);
            }
        }
        System.out.println("HitRate=" + cache.hitrate());
    }
}