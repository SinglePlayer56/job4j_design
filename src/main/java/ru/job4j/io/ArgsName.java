package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        String value = values.get(key);
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("This key: '%s' is missing".formatted(key));
        }
        return values.get(key);
    }

    private void parse(String[] args) {
        for (String arg : args) {
            if (!arg.startsWith("-")) {
                throw new IllegalArgumentException(
                        "Error: This argument '%s' does not start with a '-' character".formatted(arg));
            }
            String[] keyValue = arg
                    .replaceFirst("-", "")
                    .split("=", 2);
            if (keyValue.length == 1) {
                throw new IllegalArgumentException(
                        "Error: This argument '%s' does not contain an equal sign".formatted(arg)
                );
            }
            if (keyValue[1].isEmpty()) {
                throw new IllegalArgumentException(
                        "Error: This argument '%s' does not contain a value".formatted(arg)
                );
            }
            if (keyValue[0].isEmpty()) {
                throw new IllegalArgumentException(
                        "Error: This argument '%s' does not contain a key".formatted(arg)
                );
            }
            values.put(keyValue[0], keyValue[1]);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[]{"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[]{"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}