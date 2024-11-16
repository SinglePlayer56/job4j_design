package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public void packFiles(List<Path> sources, File target) {
        if (sources.isEmpty()) {
            throw new IllegalArgumentException("Не найден ни один путь к файлу");
        }
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path source : sources) {
                writeToZip(source.toFile(), zip);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            writeToZip(source, zip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToZip(File source, ZipOutputStream outputStream) throws IOException {
        outputStream.putNextEntry(new ZipEntry(source.getPath()));
        try (BufferedInputStream output = new BufferedInputStream(new FileInputStream(source))) {
            outputStream.write(output.readAllBytes());
        }
    }

    public static void validateArgs(ArgsName argsName) {
        argsName.get("d");
        argsName.get("o");
        String extension = argsName.get("e");
        if (!extension.startsWith(".")) {
            throw new IllegalArgumentException("Расширение должно начинаться с \".\"");
        }
    }

    public static void main(String[] args) throws IOException {
        Zip zip = new Zip();
        zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );

        ArgsName argsName = ArgsName.of(args);
        validateArgs(argsName);
        Path destination = Path.of(argsName.get("d"));
        File output = Path.of(argsName.get("o")).toFile();
        String extension = argsName.get("e");
        List<Path> paths = Search.search(destination, path -> !path.toFile().getName().endsWith(extension));
        zip.packFiles(paths, output);
    }
}