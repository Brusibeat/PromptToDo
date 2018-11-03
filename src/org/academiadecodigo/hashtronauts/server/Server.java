package org.academiadecodigo.hashtronauts.server;

import org.academiadecodigo.hashtronauts.server.clients.Client;
import org.academiadecodigo.hashtronauts.server.clients.ClientConnector;
import org.academiadecodigo.hashtronauts.server.users.User;
import org.academiadecodigo.hashtronauts.server.users.UserStore;
import org.academiadecodigo.hashtronauts.server.utils.ServerMessages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final Set<ClientConnector> clients;
    private final ExecutorService threadPool;
    private ServerSocket socket;

    private final UserStore users;

    public Server() {
        users = new UserStore();
        clients = new HashSet<>();
        threadPool = Executors.newCachedThreadPool();
    }


    /**
     * Starts the server with provided port
     *
     * @param serverPort server port number
     * @return true if successful
     */
    boolean initServer(int serverPort) {
        users.initStore();

        try {
            socket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.err.println(ServerMessages.CANT_START);
            return false;
        }

        return true;
    }


    /**
     * Waits for a client and then starts the client Thread
     */
    void listenConnections() {
        while (!socket.isClosed()) {
            try {
                Socket newClient = socket.accept();
                Client client = new Client(newClient);

                if (!client.clientConnected()) {
                    System.err.println(ServerMessages.ERROR_ACCEPTING_CLIENT);
                    continue;
                }

                ClientConnector connector = new ClientConnector(this, client);

                client.setServerBridge(connector);

                clients.add(connector);

                threadPool.submit(client);

                System.out.println(ServerMessages.CLIENT_CONNECTED);
            } catch (IOException e) {
            }
        }
    }


    /**
     * Shutdown the server and exit
     */
    public void shutdown() {
        try {
            socket.close();
        } catch (IOException e) {
        }

        threadPool.shutdown();

        for (ClientConnector client : clients) {
            client.disconnect();
            clients.remove(client);
        }

        System.out.println(ServerMessages.SERVER_CLOSING);
        System.exit(0);
    }

    public User LoginUser(String user, int password) {
        return users.validateUser(user, password);
    }

    public User registerUser(String username, int password) {
        return users.createUser(username, password);
    }
}
