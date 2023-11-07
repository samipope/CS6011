// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.net.ServerSocket;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
       // listen at port 8080
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            Server server = new Server(serverSocket);
            server.runServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}