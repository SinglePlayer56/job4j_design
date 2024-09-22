package ru.job4j.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveIf() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListUtils.removeIf(input, value -> value % 2 == 0);
        assertThat(input).containsExactly(1, 3, 5);
    }

    @Test
    void whenRemoveIfNoElementsRemoved() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.removeIf(input, value -> value > 5);
        assertThat(input).containsExactly(1, 2, 3);
    }

    @Test
    void whenReplaceIf() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ListUtils.replaceIf(input, value -> value % 2 == 0, 0);
        assertThat(input).containsExactly(1, 0, 3, 0, 5);
    }

    @Test
    void whenReplaceIfNoElementsReplaced() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3));
        ListUtils.replaceIf(input, value -> value > 5, 0);
        assertThat(input).containsExactly(1, 2, 3);
    }

    @Test
    void whenRemoveAll() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> elementsToRemove = new ArrayList<>(Arrays.asList(2, 4));
        ListUtils.removeAll(input, elementsToRemove);
        assertThat(input).containsExactly(1, 3, 5);
    }

    @Test
    void whenRemoveAllNoElementsRemoved() {
        input = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> elementsToRemove = new ArrayList<>(Arrays.asList(4, 5));
        ListUtils.removeAll(input, elementsToRemove);
        assertThat(input).containsExactly(1, 2, 3);
    }
}