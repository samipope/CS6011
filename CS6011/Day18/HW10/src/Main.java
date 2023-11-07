import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<ClientHandler> runnArr= new ArrayList<>();
        ServerSocket server = new ServerSocket(8080);
            while (true) {
                    Socket client = server.accept();
                    Thread clientThread = new Thread(new ClientHandler(client));
                    clientThread.start();

            }
        }
    }

