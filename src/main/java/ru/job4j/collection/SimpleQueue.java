package ru.job4j.collection;

public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int inputSize;
    private int outputSize;

    public T poll() {
        if (outputSize == 0) {
            while (inputSize != 0) {
                output.push(input.pop());
                outputSize++;
                inputSize--;
            }
        }
        outputSize--;
        return output.pop();
    }

    public void push(T value) {
        input.push(value);
        inputSize++;
    }
}
