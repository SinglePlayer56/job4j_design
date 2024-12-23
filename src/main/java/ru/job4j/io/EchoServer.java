package ru.job4j.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class EchoServer {
    public static void main(String[] args) throws IOException {
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
                            if (Objects.equals("Bye", msg)) {
                                server.close();
                            }
                            firstLine = false;
                        }
                        System.out.println(string);
                    }
                    output.flush();
                }
            }
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