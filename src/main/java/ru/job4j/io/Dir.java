package ru.job4j.io;

import java.io.File;
import java.util.*;

public class Dir {
    public static void main(String[] args) {
        File file = new File("c:\\projects");
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Директория не существует: %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Это не директория: %s", file.getAbsoluteFile()));
        }
        Deque<File> directories = new ArrayDeque<>();
        directories.add(file);
        while (!directories.isEmpty()) {
            File currentDir = directories.poll();
            for (File subfile : currentDir.listFiles()) {
                if (subfile.isFile()) {
                    System.out.println("%s : %s".formatted(subfile.getName(), subfile.length()));
                } else {
                    directories.add(subfile);
                }
            }
        }
    }
}
