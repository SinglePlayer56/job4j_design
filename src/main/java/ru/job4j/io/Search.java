package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        validateArgs(args);
        Path start = Path.of(args[0]);
        search(start, path -> path.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    private static void validateArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }
        if (args[0].isBlank() || !Path.of(args[0]).toFile().isDirectory()) {
            throw new IllegalArgumentException("Переданный путь не является директорией");
        }
        if (!args[1].startsWith(".")) {
            throw new IllegalArgumentException("Расширение должно начинаться с \".\"");
        }
    }
}
