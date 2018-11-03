package org.academiadecodigo.hashtronauts.server;

import org.academiadecodigo.hashtronauts.server.prompt.ServerPrompt;
import org.academiadecodigo.hashtronauts.server.utils.ServerMessages;
import org.academiadecodigo.hashtronauts.server.utils.ServerUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {

    /**
     * Client Entry Point
     *
     * @param args Arguments passed to PromptToDo
     */
    public static void main(String[] args) {
        System.out.println("PromptToDo Proudly Created by \"Delta Team\" @ Hashtronauts!");

        int serverPort = ServerUtils.getPort();

        Server server = new Server();
        ExecutorService promptThreadPool = Executors.newSingleThreadExecutor();

        if (!server.initServer(serverPort)) {
            return;
        }

        System.out.println(ServerMessages.START_LISTEN);
        promptThreadPool.submit(new ServerPrompt(server));
        server.listenConnections();
    }
}
