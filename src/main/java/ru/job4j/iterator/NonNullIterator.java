package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class NonNullIterator implements Iterator<Integer> {
    private Integer[] data;
    private int index;

    public NonNullIterator(Integer[] data) {
        this.data = data;
        index = findNextNotNullIndex(index);
    }

    @Override
    public boolean hasNext() {
        return index < data.length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int res = data[index];
        index = findNextNotNullIndex(index + 1);
        return res;
    }

    private int findNextNotNullIndex(int start) {
        for (int i = start; i < data.length; i++) {
            if (data[i] != null) {
                return i;
            }
        }
        return data.length;
    }
}
