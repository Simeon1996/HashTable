package Coding.HashTable;

import java.util.ArrayList;

public class Hasher<K, V> {

    private final ArrayList<LinkedListNode<K, V>> arr;

    private static class LinkedListNode<K, V> {
        public LinkedListNode<K, V> prev;
        public LinkedListNode<K, V> next;
        public K key;
        public V value;

        public LinkedListNode(K k, V v) {
            key = k;
            value = v;
        }
    }

    public Hasher(int capacity) {
        arr = new ArrayList<>();
        arr.ensureCapacity(capacity);

        for (int i = 0; i < capacity; i++) {
            arr.add(null);
        }
    }

    public V get(K key) {
        LinkedListNode<K, V> node = getNodeForKey(key);
        return node == null ? null : node.value;
    }

    public void put(K key, V value) {
        LinkedListNode<K, V> node = getNodeForKey(key);

        if (node != null) {
            node.value = value;
            return;
        }

        node = new LinkedListNode<>(key, value);
        int index = getIndexForKey(key);

        if (arr.get(index) != null) {
            node.next = arr.get(index);
            node.next.prev = node;
        }

        arr.set(index, node);
    }

    public void remove(K key) {
        LinkedListNode<K, V> node = getNodeForKey(key);

        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            int hashKey = getIndexForKey(key);
            arr.set(hashKey, node.next);
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    private LinkedListNode<K, V> getNodeForKey(K key) {
        int index = getIndexForKey(key);
        LinkedListNode<K, V> current = arr.get(index);

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    /**
     * A really naive implementation of hashing function
     */
    public int getIndexForKey(K key) {
        return Math.abs(key.hashCode() % arr.size());
    }

    public int size() {
        return arr.size();
    }
}
