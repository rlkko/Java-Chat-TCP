package rikkoRicardo.tcp.chat.Interfaces;

import rikkoRicardo.tcp.chat.ServerComponents.Server;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("Click enter to Quit!");
            sc.nextLine();
            System.exit(0);
        }).start();

        try {
            Server server = new Server(new ServerSocket(6000));
            server.startServer();
        } catch (BindException e) {
            System.out.println("Theres a server opened already, Quiting!");
        } catch (IOException ex) {

        }

    }

}
