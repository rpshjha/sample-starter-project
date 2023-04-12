package org.example.utilities;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils() {
    }

    public static String aRandomOpenPortOnAllLocalInterfaces() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return String.valueOf(socket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException("no open ports found for bootstrap");
        }
    }

    public static String randomSystemPort() {
        int nextInt = ThreadLocalRandom.current().nextInt(8200, 8299 + 1);
        return String.valueOf(nextInt);
    }
}
