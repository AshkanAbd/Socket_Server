package ir.ashkanabd.client;

import java.io.*;
import java.util.*;
import java.net.*;

public class Start {

    public static void main(String[] args) throws Exception {
        new Start().start();
    }

    private void start() throws Exception {
        Socket socket, socket1, socket2;
        Scanner scanner = new Scanner(System.in);
        socket = new Socket("127.0.0.1", 1893);
        System.out.print("Connected\nEnter name : ");
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        pw.println(scanner.nextLine());
        Thread thread = new Thread(() -> {
            try {
                Scanner scn = new Scanner(socket.getInputStream());
                while (scn.hasNextLine()){
                    System.out.println(scn.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        while (true) {
            pw.println(scanner.nextLine());
        }
    }
}
