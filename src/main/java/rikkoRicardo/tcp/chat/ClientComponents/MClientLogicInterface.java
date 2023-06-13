package rikkoRicardo.tcp.chat.ClientComponents;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * Structure for the data being used
 *
 * @author gonc,ricar
 */
public class MClientLogicInterface {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;

    public String getUsername() {
        return username;
    }

    /**
     * Logic for the Client Interface
     *
     * @param Socket
     * @param username
     */
    public MClientLogicInterface(Socket Socket, String username) {
        try {
            this.socket = Socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

            sendTCPMessageToClient(username);

            String check = reader.readLine();

            if (check.equals("new@user")) {
                sendTCPMessageToClient(JOptionPane.showInputDialog("First Name:"));
                sendTCPMessageToClient(JOptionPane.showInputDialog("Last Name:"));
            }

        } catch (IOException e) {
            closeNetworkHandles();
        }
    }

    /**
     * sends a tcp message using BufferWriter
     *
     * @param message -> Message to be sent
     */
    public void sendTCPMessageToClient(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            closeNetworkHandles();
        }
    }

    /**
     * Function to initiate each user's thread
     *
     * @param modelMessages -> Messages box
     * @param modelUsers -> List of users
     */
    public void listen(DefaultListModel modelMessages, DefaultListModel modelUsers) {
        new Thread(() -> {
            String recivedMessage;

            ArrayList<String> unique = new ArrayList<>();
            while (socket.isConnected()) {
                try {
                    recivedMessage = (String) reader.readLine();

                    if (recivedMessage.split("/")[0].equals("user")) {

                        String data = recivedMessage.split("/")[1];
                        if (!unique.contains(data)) {
                            modelUsers.addElement(data);
                            unique.add(data);
                        }

                    } else if (recivedMessage.split("/")[0].equals("userR")) {
                        String data = recivedMessage.split("/")[1];

                        if (unique.contains(data)) {
                            unique.remove(data);
                            modelUsers.removeElement(data);
                        }
                    } else if (!recivedMessage.equals("##$$")) {

                        String tmp = recivedMessage.split(":")[0];

                        if (!tmp.equals("To")) {
                            JsonLoader.LoadFile();
                            if (tmp.equals("Global")) {
                                JsonLoader.addMessage(tmp, tmp, recivedMessage);
                            } else {
                                JsonLoader.addMessage(tmp, this.username, recivedMessage);
                            }
                        }

                        modelMessages.addElement(recivedMessage);
                    }

                } catch (IOException e) {
                    closeNetworkHandles();
                }
            }
        }).start();
    }

    /**
     * Closes all network handles
     */
    private void closeNetworkHandles() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
