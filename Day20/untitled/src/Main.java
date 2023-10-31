import ServerClient.MyRunnable;
import ServerClient.RoomManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<MyRunnable> runnArr= new ArrayList<>();
        ServerSocket server = new ServerSocket(8080);
        RoomManager rm = new RoomManager();
        while (true) {
            Socket client = server.accept();
            Thread clientThread = new Thread(new MyRunnable(client,rm));
            clientThread.start();

        }
    }
}

