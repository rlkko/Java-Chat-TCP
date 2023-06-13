package rikkoRicardo.tcp.chat.ServerComponents;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClientManager {

    static private ArrayList<SClientData> list;

    static String clientDatabaseDirectory;

    /**
     * Creates the clients.dat file that will backup the users data
     */
    public SClientManager() {
        try {
            // gets current file directory
            clientDatabaseDirectory = new File(".").getCanonicalPath() + "/clients.dat";
            list = new ArrayList<>();

            loadData();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Adds client
     *
     * @param email -> Current user's email address
     * @param name -> current user's name
     * @param lastName -> current user's last name
     */
    public static void addClient(String email, String name, String lastName) {
        int newId = !list.isEmpty() ? list.get(list.size() - 1).getID() + 1 : 1;
        list.add(new SClientData(newId, email, name, lastName));
    }

    /**
     *
     * Returns Client Data Object given the email
     *
     * @param email -> email of the chosen user
     * @return
     */
    public static SClientData getClient(String email) {
        for (SClientData cliente : list) {
            if (cliente.getEmail().equals(email)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Searches for the existence of client ID by email
     *
     * @param email -> client email to be found
     * @return
     */
    public static int getClientID(String email) {
        for (SClientData cliente : list) {
            if (cliente.getFirstName().equals(email)) {
                return cliente.getID();
            }
        }
        return -1;
    }

    /**
     *
     * @param email
     * @return true if a client exists with the provided email
     */
    public static boolean existsClient(String email) {
        for (SClientData cliente : list) {
            if (cliente.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public static boolean saveData() {

        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(SClientManager.clientDatabaseDirectory));
            obj.writeObject((ArrayList<SClientData>) list);
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     *
     * @return @throws IOException
     * @throws ClassNotFoundException
     */
    public static boolean loadData() throws IOException, ClassNotFoundException {
        if (new File(clientDatabaseDirectory).exists()) {
            ObjectInputStream obj = new ObjectInputStream(new FileInputStream(clientDatabaseDirectory));
            list = (ArrayList<SClientData>) obj.readObject();

            for (SClientData c : list) {
                System.out.println("Email: " + c.email);
            }
            return true;
        }
        return false;
    }
}
