package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final Map<FileProperty, List<Path>> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        FileProperty fileProperty = new FileProperty(Files.size(file), file.toFile().getName());
        files.putIfAbsent(fileProperty, new ArrayList<>());
        files.get(fileProperty).add(file);
        return super.visitFile(file, attributes);
    }

    public void showDuplicates() {
        files.values().stream()
                .filter(paths -> paths.size() > 1)
                .flatMap(Collection::stream)
                .forEach(System.out::println);
    }
}
