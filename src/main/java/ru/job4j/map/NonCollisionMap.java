package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class NonCollisionMap<K, V> implements SimpleMap<K, V> {
    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if ((float) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        int index = getIndex(key);
        if (Objects.nonNull(table[index])) {
            return false;
        }
        table[index] = new MapEntry<>(key, value);
        count++;
        modCount++;
        return true;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key);
        MapEntry<K, V> entry = table[index];
        if (Objects.isNull(entry)) {
            return null;
        }
        if (Objects.isNull(key) && Objects.isNull(entry.key)) {
            return entry.value;
        }
        if (isEqualsKey(key, entry.key)) {
            return entry.value;
        }
        return null;
    }

    @Override
    public boolean remove(K key) {
        int index = getIndex(key);
        MapEntry<K, V> entry = table[index];
        if (Objects.isNull(entry)) {
            return false;
        }
        if (Objects.isNull(key) && Objects.isNull(entry.key)) {
            table[index] = null;
            count--;
            modCount++;
            return true;
        }
        if (isEqualsKey(key, entry.key)) {
            table[index] = null;
            count--;
            modCount++;
            return true;
        }
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private final int currentModCount = modCount;
            public int index;
            private MapEntry<K, V>[] data = table;

            @Override
            public boolean hasNext() {
                if (modCount != currentModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < data.length && data[index] == null) {
                    index++;
                }
                return index < data.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return data[index++].key;
            }
        };
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & capacity - 1;
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (int index = 0; index < table.length; index++) {
            MapEntry<K, V> entry = table[index];
            if (Objects.nonNull(entry)) {
                int newIndex = getIndex(entry.key);
                newTable[newIndex] = entry;
                table[index] = null;
            }
        }
        table = newTable;
    }

    private int getIndex(K key) {
        int keyHashCode = Objects.hashCode(key);
        int keyHash = hash(keyHashCode);
        return indexFor(keyHash);
    }

    private boolean isEqualsKey(K firstKey, K secondKey) {
        return Objects.nonNull(firstKey) && Objects.nonNull(secondKey)
                && Objects.equals(firstKey.hashCode(), secondKey.hashCode())
                && Objects.equals(firstKey, secondKey);
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
