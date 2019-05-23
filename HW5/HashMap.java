/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzwcompression;

import java.util.LinkedList;

/**
 *
 * @author weiwang_ww5
 */
public class HashMap<K, V> {

    class Element<K, V> {

        private final K key;
        private V value;

        public Element(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public boolean equal(K key) {
            if (this.key == null) {
                return this.key == key;
            } else {
                return this.key.equals(key);
            }
        }
    }

    private final static int DEFAULT_SIZE = 256;
    private final int size;
    private final LinkedList<Element<K, V>>[] elements;

    public HashMap() {
        this(DEFAULT_SIZE);
    }

    public HashMap(int size) {
        this.size = size;
        this.elements = (LinkedList<Element<K, V>>[]) new LinkedList[size];
    }

    public int basicHash(K key) {
        if (key == null || key.equals("")) {
            return 0;
        } else {
            int hash = 7;
            String str = key.toString();
            for (int i = 0; i < str.length(); i++) {
                //avoid over flow
                if(hash>(Integer.MAX_VALUE-str.charAt(i))/31){
                    hash = hash % 31;
                }
                hash = hash * 31 + str.charAt(i);
            }
            return hash;
        }
    }

    public boolean contains(K key) {
        int index = basicHash(key) % size;
        if (elements[index] == null) {
            return false;
        }
        for (Element<K, V> e : elements[index]) {
            if (e.equal(key)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        int index = basicHash(key) % size;
        if (elements[index] == null) {
            return null;
        }
        for (Element<K, V> e : elements[index]) {
            if (e.equal(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    public void put(K key, V value) {
        int index = basicHash(key) % size;
        Element<K, V> newEle = new Element<>(key, value);

        if (elements[index] == null) {
            elements[index] = new LinkedList<>();
        }
        for (Element<K, V> e : elements[index]) {
            if (e.equal(key)) {
                elements[index].remove(e);
            }
        }
        elements[index].add(newEle);
    }

}
