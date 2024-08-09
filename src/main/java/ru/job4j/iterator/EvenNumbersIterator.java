package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {
    private int[] data;
    private int index;

    public EvenNumbersIterator(int[] data) {
        this.data = data;
        this.index = findNextEvenIndex(0);
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
        index = findNextEvenIndex(index + 1);
        return res;
    }

    private int findNextEvenIndex(int start) {
        for (int i = start; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                return i;
            }
        }
        return data.length;
    }
}
