package ru.job4j.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ForwardLinked<T> implements Iterable<T> {
    private int size;
    private int modCount;
    private Node<T> head;

    public void add(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> node = head;
            while (node.next != null) {
                node = node.next;
            }
            node.next = newNode;
        }
        size++;
        modCount++;
    }

    public T deleteFirst() {
        if (head == null) {
            throw new NoSuchElementException("Queue is empty");
        }
        T deletedItem = head.item;
        head = head.next;
        size--;
        modCount++;
        return deletedItem;
    }

    public void addFirst(T value) {
        Node<T> newNode = new Node<>(value, null);
        if (head != null) {
            newNode.next = head;
        }
        head = newNode;
        size++;
        modCount++;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        Node<T> res = head;
        for (int i = 0; i < index; i++) {
            res = res.next;
        }
        return res.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private Node<T> currentNode = head;

            @Override
            public boolean hasNext() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return currentNode != null;
            }

            @Override
            public T next() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                T value = currentNode.item;
                currentNode = currentNode.next;
                return value;
            }
        };
    }

    private static class Node<T> {
        private T item;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.item = element;
            this.next = next;
        }
    }
}
