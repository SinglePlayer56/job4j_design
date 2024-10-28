package ru.job4j.io;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenFileWithCommentsAndEmptyLines() {
        String path = "./data/file_with_comments.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("John Doe");
        assertThat(config.value("city")).isEqualTo("New York");
        assertThat(config.value("# This is a comment")).isNull();
        assertThat(config.value("")).isNull();
    }

    @Test
    void whenEmptyKeyFileWithMalformedLinesThenThrowException() {
        String path = "./data/empty_key.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenEmptyValueFileWithMalformedLinesThenThrowException() {
        String path = "./data/empty_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenNotEqualFileWithMalformedLinesThenThrowException() {
        String path = "./data/not_equal.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenEmptyKeyAndEmptyValueFileWithMalformedLinesThenThrowException() {
        String path = "./data/empty_key_and_empty_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenValueContainsEqualsSign() {
        String path = "./data/value_with_equals_sign.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key")).isEqualTo("value=1");
    }
}
