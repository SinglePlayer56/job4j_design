package ru.job4j.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        String command = "";
        boolean isStopped = false;
        List<String> logs = new ArrayList<>();
        List<String> botAnswers = readPhrases();
        while (!OUT.equals(command)) {
            command = input.nextLine();
            logs.add(command);
            if (STOP.equals(command)) {
                isStopped = true;
            }
            if (CONTINUE.equals(command)) {
                isStopped = false;
            }
            if (!isStopped && !command.isBlank()) {
                String randomAnswer = getRandomAnswer(botAnswers);
                logs.add(randomAnswer);
                System.out.println(randomAnswer);
            }
        }
        saveLog(logs);
    }

    private String getRandomAnswer(List<String> answers) {
        if (answers.isEmpty()) {
            throw new IllegalArgumentException("Список ответов не должен быть пустым");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(answers.size());
        return answers.get(randomIndex);
    }

    private List<String> readPhrases() {
        List<String> result = new ArrayList<>();
        try (BufferedReader input = new BufferedReader(new FileReader(botAnswers, StandardCharsets.UTF_8))) {
            input.lines().forEach(result::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter output = new PrintWriter(new FileWriter(path, StandardCharsets.UTF_8, true))) {
            log.forEach(output::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConsoleChat consoleChat = new ConsoleChat("data/bot_logs.txt", "data/bot_answers.txt");
        consoleChat.run();
    }
}
