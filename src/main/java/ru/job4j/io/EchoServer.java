package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class.getName());

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream output = socket.getOutputStream();
                     BufferedReader input = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    boolean firstLine = true;
                    output.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                    for (String string = input.readLine(); string != null && !string.isEmpty(); string = input.readLine()) {
                        if (firstLine) {
                            String msg = extractParamValue(string, "msg");
                            if (Objects.nonNull(msg)) {
                                switch (msg) {
                                    case "Hello" -> output.write("Hello".getBytes());
                                    case "Exit" -> server.close();
                                    default -> output.write("What".getBytes());
                                }
                            }
                            firstLine = false;
                        }
                        System.out.println(string);
                    }
                    output.flush();
                }
            }
        } catch (IOException e) {
            LOG.error("Error", e);
        }
    }

    private static String extractParamValue(String string, String paramName) {
        String[] parts = string.split(" ");
        String[] params = parts[1].substring(2).split("&");
        String result = null;
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(paramName)) {
                result = keyValue[1];
                break;
            }
        }
        return result;
    }
}