package rikkoRicardo.tcp.chat.ServerComponents;

import java.net.*;
import java.io.*;

public class Server {

    private final ServerSocket serverSocket;

    /**
     *
     * @param serverSocket
     */
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Starts Server and Client Manager
     */
    public void startServer() {

        SClientManager cliM = new SClientManager();

        try {
            while (!serverSocket.isClosed()) {

                Socket cliSock = serverSocket.accept();
                System.out.println("New Client Joined! ");

                new Thread(new SClientHandler(cliSock)).start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    /**
     * Closes the ServerScoket and saves clients Data
     */
    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }

            SClientManager.saveData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
