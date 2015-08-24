package com.yimu;


import com.sun.istack.internal.NotNull;

class Node<K, V extends Comparable<V>> implements Comparable<Node<K, V>> {
    private K key;
    private V value;
    private int index;
    private int counts;
    private long lastTime;

    Node(K key, V value, int counts) {
        this.key = key;
        this.value = value;
        this.counts = counts;
    }

    @Override
    public int compareTo(@NotNull Node<K, V> node) {
        if(counts == node.counts){
            return (int)(this.lastTime - node.lastTime);
        }
        return counts-node.counts;
    }

    K getKey() {
        return key;
    }

    V getValue() {
        return value;
    }

    void setValue(V value){
        this.value = value;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }

    void addCount(int val){
        this.counts += val;
    }

    void setLastTime(long time){
        this.lastTime = time;
    }

}
