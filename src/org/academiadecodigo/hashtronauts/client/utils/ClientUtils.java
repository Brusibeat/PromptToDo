package org.academiadecodigo.hashtronauts.client.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientUtils {

    /**
     * Asks the user for a valid port number
     *
     * @return the port number
     */
    public static int getPort() {

        System.out.print(ClientMessages.SELECT_PORT);

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
                System.err.println(ClientMessages.INVALID_PORT);

            }
        }

        System.out.println("Port Set to: " + serverPort);

        return serverPort;
    }

    /**
     * Aks the user for a valid host
     *
     * @return the host INetAddress
     */
    public static InetAddress getHost() {

        InetAddress inetAddress = null;

        while (inetAddress == null) {

            System.out.print(ClientMessages.SELECT_HOST);
            String url = new Scanner(System.in).nextLine();

            try {
                inetAddress = InetAddress.getByName(url);
            } catch (UnknownHostException e) {
                System.out.println(ClientMessages.INVALID_HOST);
            }
        }

        return inetAddress;
    }
}
