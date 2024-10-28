package ru.job4j.io;

import java.io.*;
import java.util.Objects;

public class Analysis {
    public void unavailable(String source, String target) {
        try (BufferedReader input = new BufferedReader(new FileReader(source));
             PrintWriter output = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)))) {
            String startLog = null;
            String line;
            while ((line = input.readLine()) != null) {
                boolean isUnavailable = line.startsWith("500") || line.startsWith("400");
                if (isUnavailable && Objects.isNull(startLog)) {
                    startLog = line.split(" ")[1];
                } else if (!isUnavailable && Objects.nonNull(startLog)) {
                    String endLog = line.split(" ")[1];
                    output.println("%s;%s;".formatted(startLog, endLog));
                    startLog = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Analysis analysis = new Analysis();
        analysis.unavailable("data/server.log", "data/target.csv");
    }
}
