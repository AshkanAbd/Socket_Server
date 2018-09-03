package ir.ashkanabd.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private OnClientAnswer onClientAnswer;
    private OnClientConnected onClientConnected;

    public Server(int port) throws Exception {
        serverSocket = new ServerSocket(port);
    }

    private void accept() throws Exception {
        Socket socket = serverSocket.accept();
        Client client = new Client(socket, socket.getInputStream(), socket.getOutputStream());
        onClientConnected.onConnected(client);
        Thread thread = new Thread(() -> {
            try {
                Scanner scn = new Scanner(socket.getInputStream());
                while (scn.hasNextLine()) {
                    onClientAnswer.messageReceive(scn.nextLine(), client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                accept();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnClientAnswer(OnClientAnswer onClientAnswer) {
        this.onClientAnswer = onClientAnswer;
    }

    public void setOnClientConnected(OnClientConnected onClientConnected) {
        this.onClientConnected = onClientConnected;
    }
}
