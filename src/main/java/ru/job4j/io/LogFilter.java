package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogFilter {
    private final String file;

    public LogFilter(String file) {
        this.file = file;
    }

    public List<String> filter() {
        List<String> res = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(file))) {
            input.lines().filter(line -> {
                String[] lineArr = line.split(" ");
                return lineArr.length > 1 && "404".equals(lineArr[lineArr.length - 2]);
            }).forEach(res::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public void saveTo(String out) {
        var data = filter();
        try (PrintWriter output = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(out)
                ))) {
            data.forEach(output::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LogFilter("data/log.txt").saveTo("data/404.txt");
    }
}
