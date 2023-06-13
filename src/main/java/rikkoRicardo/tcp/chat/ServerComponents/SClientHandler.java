package rikkoRicardo.tcp.chat.ServerComponents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SClientHandler implements Runnable {

    public static ArrayList<SClientHandler> ClientHandlerList = new ArrayList<>();
    private Socket socket;
    private BufferedReader streamReader;
    private BufferedWriter streamWriter;

    private String email;
    private String fnome;
    private String lnome;
    private int ID;

    /**
     * Class that handles all connections client-server
     *
     * @param Socket -> Socket to be used as a reference to interact
     * @throws IOException
     */
    public SClientHandler(Socket Socket) throws IOException {

        try {
            this.socket = Socket;
            this.streamWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.email = streamReader.readLine();

            checkAndStoreIncomingClient();
        } catch (IOException e) {
            closeAll();
        }

        for (SClientHandler client : ClientHandlerList) {
            broadCastMessage("user/" + client.email);
        }
    }

    /**
     * Checks the incoming connections and stores them
     *
     */
    public void checkAndStoreIncomingClient() throws IOException {

        if (!SClientManager.existsClient(email)) {

            sendDataToSender("new@user");

            do {
                fnome = streamReader.readLine();
                lnome = streamReader.readLine();
            } while (fnome == null || fnome.isEmpty() && lnome == null || lnome.isEmpty());

            SClientManager.addClient(email, fnome, lnome);
            SClientManager.saveData();
        } else {
            sendDataToSender("---");
        }

        ClientHandlerList.add(this);

    }

    /**
     * Starts hearing client messages from clients
     */
    @Override
    public void run() {

        try {

            while (socket.isConnected()) {

                String incomingMessage;
                if ((incomingMessage = streamReader.readLine()) == null) {
                    throw new IOException();
                }

                String[] messageStructure = incomingMessage.split("/");
                String userEmail = incomingMessage.split(":")[1].split("/")[0];
                String message = incomingMessage.split("/")[1];

                if (messageStructure.length - 1 != 0) {

                    if (messageStructure[0].equals("message:Global")) {
                        broadCastMessage("Global: " + message);
                    }

                    if (incomingMessage.split(":")[0].equals("message")) {
                        forwardIncomingMessage(userEmail, message);
                    }

                }
            }
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * Forwards messages to one client socket
     *
     * @param userEmail -> User that will recieve the message
     * @param message -> The content of the message
     */
    private void forwardIncomingMessage(String userEmail, String message) {
        for (SClientHandler client : ClientHandlerList) {
            if (client.email.equals(userEmail)) {
                //Successful Message
                sendDataToSender("To: " + client.email + ": " + message);
                //Forwarded message
                sendDataToDest(client, email + ": " + message);
            }
        }
    }

    /**
     * Also forwards messages but sends it to multiple clients
     *
     * @param usersEmailList -> String Array that contains the Senders's Email
     * address
     * @param message -> The content of the message to be sent
     */
    private void forwardIncomingMessageToGroup(String[] usersEmailList, String message) {
        for (String user : usersEmailList) {
            for (SClientHandler client : ClientHandlerList) {
                if (client.email.equals(user)) {
                    //Successful Message
                    sendDataToSender("To: " + client.email + ": " + message.split("/")[1]);
                    //Forwarded message
                    sendDataToDest(client, email + ": " + message.split("/")[1]);
                }
            }
        }
    }

    /**
     * Broadcasts/forwards the message to all active clients
     *
     * @param message -> the content of the message to be forwarded
     */
    private void broadCastMessage(String message) {
        for (SClientHandler client : ClientHandlerList) {
            try {
                client.streamWriter.write(message);
                client.streamWriter.newLine();
                client.streamWriter.flush();
            } catch (IOException e) {
                closeAll();
            }
        }
    }

    /**
     *
     * @param c
     * @param m
     */
    private void sendDataToSender(String m) {
        try {
            this.streamWriter.write(m);
            this.streamWriter.newLine();
            this.streamWriter.flush();
        } catch (IOException e) {
            closeAll();
        }
    }

    private void sendDataToDest(SClientHandler Client, String m) {
        try {
            Client.streamWriter.write(m);
            Client.streamWriter.newLine();
            Client.streamWriter.flush();
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     *
     */
    private void remove() {
        ClientHandlerList.remove(this);
        broadCastMessage("userR/" + email);
    }

    /**
     *
     */
    private void closeAll() {
        remove();
        try {
            if (this.streamReader != null) {
                streamReader.close();
            }
            if (this.streamWriter != null) {
                streamWriter.close();
            }
            if (this.socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
