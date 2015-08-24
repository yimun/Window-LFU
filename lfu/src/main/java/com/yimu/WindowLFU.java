package com.yimu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class WindowLFU<K, V extends Comparable<V>> {
    private MinHeap<K, V> minHeap;
    private Map<K, Node<K, V>> map;
    private Queue<K> window;
    private int windowSize;
    private int total;
    private int hits;


    public WindowLFU(int cacheSize, int windowSize) {
        minHeap = new MinHeap<>(cacheSize);
        window = new LinkedList<>();
        map = new HashMap<>((int) ((float) cacheSize / 0.75F + 1.0F));
        this.windowSize = windowSize;
        total = 0;
        hits = 0;
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            return;
        }

        appendWindow(key);
        Node<K, V> previous;
        if ((previous = map.get(key)) != null) { // exists
            previous.setValue(value);
            minHeap.reVisited(previous.getIndex());
        }else{
            if(minHeap.isFull()){
                map.remove(minHeap.getMin()
                        .getKey());
            }
            int cnt = 0;
            for(K k : window){
                if(k.equals(key)){
                    cnt++;
                }
            }
            Node<K, V> node = new Node<>(key, value, cnt);
            map.put(key, node);
            minHeap.add(node);
        }
    }

    public V get(K key) {
        total++;
        V value = null;
        if (key != null) {
            Node<K, V> node = map.get(key);
            if (node != null) {
                hits ++;
                appendWindow(key);
                value = node.getValue();
                minHeap.reVisited(node.getIndex());
            }
        }
        return value;
    }

    private void appendWindow(K key){
        window.offer(key);
        if (map.containsKey(key)) {
            map.get(key).addCount(1);
        }
        if (window.size() > windowSize) {
            K first = window.poll(); // 移除队首元素
            if (map.containsKey(first)) {
                Node<K, V> item = map.get(first);
                item.addCount(-1);
            }
        }

    }

    public float hitrate(){
        if(total == 0){
            return 0;
        }
        return (float)hits/total;
    }


    public static void main(String args[]){
        WindowLFU<String, Integer> cache = new WindowLFU<>(10,20);
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
