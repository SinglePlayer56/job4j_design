package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkArgumentsEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("array is empty");
    }

    @Test
    void checkMissingEqualsSymbol() {
        NameLoad nameLoad = new NameLoad();
        String invalidName = "invalidNameWithoutEquals";
        assertThatThrownBy(() -> nameLoad.parse(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("%s does not contain the symbol '='".formatted(invalidName));
    }

    @Test
    void checkStartsWithEqualsSymbol() {
        NameLoad nameLoad = new NameLoad();
        String invalidName = "=valueWithoutKey";
        assertThatThrownBy(() -> nameLoad.parse(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("%s does not contain a key".formatted(invalidName));
    }

    @Test
    void checkMissingValueAfterEquals() {
        NameLoad nameLoad = new NameLoad();
        String invalidName = "keyWithoutValue=";
        assertThatThrownBy(() -> nameLoad.parse(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("%s does not contain a value".formatted(invalidName));
    }

}