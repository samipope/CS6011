import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        //get rid of the throws IOException and then throw exceptions as you go through the code that are specific to the errors that occur (server full, server down, etc)


        //ServerSocket server = null
       // try {
            ServerSocket server = new ServerSocket(8080);
        //} catch(IOException e) {throw new RuntimeException();}
        //have another socket - put another try catch in the try catch


        try{
            while (true) {

                // Set up a socket as a client
                Socket client = server.accept();
                Scanner sc = new Scanner(client.getInputStream());
                WebServer Server = new WebServer();
                if (sc.hasNext()) {
                    String requestLine = sc.nextLine();
                    System.out.println(requestLine);

                    //making a client object
                    Client client1 = new Client();
                    String path = client1.getClientPath(requestLine);
                    String method = client1.getClientMethod(requestLine);

                    if (method.equals("GET")) {
                        //if the client sends "/", we should send back an index.html
                        if (path.equals("/") || path.equals("/samipope.html")) {
                            File file = new File("/Users/samanthapope/6011GitHub/CS6011/Day4/MyHttpServer/src/samipope.html");
                            if (file.exists()) {
                                File requestedFile = new File(path);
                                OutputStream outputStream = client.getOutputStream();
//                                PrintWriter outStream = new PrintWriter(outputStream,true);
                                Server.printClientFile(file,outputStream);

                                //see if flushing after the header works

                                //new file input stream for the client
                                FileInputStream fileInputStream = new FileInputStream(file);
                                fileInputStream.transferTo(client.getOutputStream());
                                fileInputStream.close();
//move this down to the catch
                            }
                            else {
                                System.out.println("Path is wrong");
                                // if the file isn't found then print a 404 error
                               Server.printErrorMessage(client);
                            }
                        }
                    }
                }

                sc.close();
                client.close();
            }

        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
    }

}
