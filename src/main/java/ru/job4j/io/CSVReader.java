package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
        String delimiter = argsName.get("delimiter");
        String[] filters = argsName.get("filter").split(",");
        File source = Path.of(argsName.get("path")).toFile();
        String output = argsName.get("out");
        List<String> resList = new ArrayList<>();
        boolean isHeaderLine = true;
        try (Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(source)))) {
            StringJoiner res = new StringJoiner(delimiter);
            List<Integer> includeIndexes = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String value = scanner.nextLine();
                String[] valueArr = value.split(delimiter);
                if (isHeaderLine) {
                    for (String filter : filters) {
                        for (int i = 0; i < valueArr.length; i++) {
                            if (filter.equals(valueArr[i])) {
                                includeIndexes.add(i);
                            }
                        }
                    }
                    isHeaderLine = false;
                }
                for (Integer includeIndex : includeIndexes) {
                    for (int j = 0; j < valueArr.length; j++) {
                        if (includeIndex == j) {
                            res.add(valueArr[j]);
                        }
                    }
                }
                resList.add(res.toString());
                res = new StringJoiner(delimiter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!"stdout".equals(output)) {
            File target = Path.of(output).toFile();
            try (PrintWriter writer = new PrintWriter(new FileWriter(target))) {
                resList.forEach(writer::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            resList.forEach(System.out::println);
        }
    }

    private static void validateArgs(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неверное количество аргументов");
        }
        for (String arg : args) {
            if (arg.startsWith("-path=") && !arg.endsWith(".csv")) {
                throw new IllegalArgumentException("Файл в параметре \"%s\" должен иметь формат \".csv\"".formatted(arg));
            }
            if (arg.startsWith("-delimiter=") && (!arg.contains(";") && !arg.contains(","))) {
                throw new IllegalArgumentException("Указан неверный формат разделителя в параметре %s".formatted(arg));
            }
            if (arg.startsWith("-out=") && (!arg.endsWith("=stdout") && !arg.endsWith(".csv"))) {
                throw new IllegalArgumentException("""
                        Значение параметра -out должно быть "stdout" или содержать путь к файлу формата .csv
                        """);
            }
            if (!arg.startsWith("-path=")
                    && !arg.startsWith("-delimiter=")
                    && !arg.startsWith("-out=")
                    && !arg.startsWith("-filter=")) {
                throw new IllegalArgumentException("Неизвестный параметр %s".formatted(arg));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        validateArgs(args);
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}