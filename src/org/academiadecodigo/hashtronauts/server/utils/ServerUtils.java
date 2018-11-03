package org.academiadecodigo.hashtronauts.server.utils;

import java.util.Scanner;

public class ServerUtils {

    /**
     * Asks the user for a valid port number
     *
     * @return the port number
     */
    public static int getPort() {

        System.out.print(ServerMessages.SELECT_PORT);

        String input = null;

        int serverPort = 0;

        while (input == null) {
            input = new Scanner(System.in).nextLine().replaceAll("\\D", "");

            if (input.equals("")) {
                serverPort = 8080;
                break;
            }

            serverPort = input.equals("") ? 8080 : Integer.parseInt(input);

            if (serverPort > 65535 || serverPort <= 0) {
                input = null;
                System.err.println(ServerMessages.INVALID_PORT);

            }
        }

        System.out.println("Port Set to: " + serverPort);

        return serverPort;
    }
}
