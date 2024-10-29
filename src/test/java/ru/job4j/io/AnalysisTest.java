package ru.job4j.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnalysisTest {

    @Test
    void notEmpty(@TempDir Path tempDir) {
        File source = tempDir.resolve("source.log").toFile();
        File target = tempDir.resolve("target.csv").toFile();
        try (PrintWriter output = new PrintWriter(source)) {
            output.println("200 10:56:01");
            output.println("500 10:57:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("400 10:58:01");
            output.println("300 10:59:01");
            output.println("500 11:01:02");
            output.println("200 11:02:02");
            output.println("200 10:56:01");
            output.println("500 10:57:01");
            output.println("400 10:58:01");
            output.println("500 10:59:01");
            output.println("400 11:01:02");
            output.println("300 11:02:02");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder res = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(target))) {
            input.lines().forEach(res::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat("10:57:01;10:56:01;10:58:01;10:59:01;11:01:02;11:02:02;10:57:01;11:02:02;").isEqualTo(res.toString());
    }

    @Test
    void empty(@TempDir Path tempDir) {
        File source = tempDir.resolve("source.log").toFile();
        File target = tempDir.resolve("target.csv").toFile();
        try (PrintWriter output = new PrintWriter(source)) {
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("200 10:56:01");
            output.println("300 10:59:01");
            output.println("200 11:02:02");
            output.println("200 10:56:01");
            output.println("300 11:02:02");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Analysis analysis = new Analysis();
        analysis.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder res = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(target))) {
            input.lines().forEach(res::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(res).isEmpty();
    }
}