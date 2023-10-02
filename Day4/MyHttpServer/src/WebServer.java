import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebServer {
    public static void main(String[] args) throws IOException {
        // Set up server with port 8080
        ServerSocket server = new ServerSocket(8080);

        while (true) {
            // Set up a socket as a client
            Socket client = server.accept();
            Scanner sc = new Scanner(client.getInputStream());

            if (sc.hasNext()) {
                String requestLine = sc.nextLine();
                System.out.println(requestLine);

                String[] requestParts = requestLine.split(" ");
                String method = requestParts[0];
                //make what the user requests into a string path
                String path = requestParts[1];

                if (method.equals("GET")) {
                    //if the client sends "/", we should send back an index.html
                    if (path.equals("/") || path.equals("/index.html")) {
                        // serve the index.html file
                        File file = new File("resources/index.html");
                        if (file.exists()) {
                            File requestedFile = new File(path);
                            PrintWriter outStream = new PrintWriter(client.getOutputStream());
                            //using the new http page for the client's file
                            outStream.println("HTTP/1.1 200 OK");
                            outStream.println("Content-Type: text/html");
                            outStream.println("Content-Length: "+ requestedFile.length());
                            outStream.println();
                            outStream.flush();
                            //new file input stream for the client
                            FileInputStream fileInputStream = new FileInputStream(file);
                            fileInputStream.transferTo(client.getOutputStream());
                            fileInputStream.close();

                            outStream.flush();
                            outStream.close();
                        } else {
                            // if the file isn't found then print a 404 error
                            PrintWriter outStream = new PrintWriter(client.getOutputStream());

                            outStream.println("HTTP/1.1 404 Not Found");
                            outStream.println();
                            //always have to flush and close
                            outStream.flush();
                            outStream.close();
                        }
                    }
                }
            }

            sc.close();
            client.close();
        }
    }
}
