package ir.ashkanabd.server;

import java.util.*;

public class Main {

    private List<Room> roomsList;

    public static void main(String[] args) throws Exception {
        new Main().start();
    }

    private void start() throws Exception {
        Server server = new Server(1893);
        roomsList = new LinkedList<>();
        server.setOnClientAnswer(this::answered);
        server.setOnClientConnected(this::connected);
        server.start();
    }

    private void connected(Client client) {
        Scanner scn = new Scanner(client.getInStream());
        while (scn.hasNextLine()){
            client.setName(scn.nextLine());
            break;
        }
        if (roomsList.size() != 0 && roomsList.get(roomsList.size() - 1).getClientB() == null) {
            roomsList.get(roomsList.size() - 1).addClient(client);
            client.setRoom(roomsList.get(roomsList.size() - 1));
            client.getRoom().sendA(client.getName() + " connected");
        } else {
            Room r = new Room(client);
            client.setRoom(r);
            roomsList.add(r);
            client.getRoom().sendA("Waiting for another client");
        }

    }

    private void answered(String line, Client client) {
        Room r = client.getRoom();
        if (r.isA(client)) {
            r.sendB(client.getName() + " : " + line);
        } else {
            r.sendA(client.getName() + " : " + line);
        }
    }
}
