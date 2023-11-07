import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



//put an output stream in the class that replies
//in the return type make a FILE as a variable
//construct the file in it's own constructor and nest that in the original contructor
public class WebServer {

    WebServer(){

    }

    public static void printErrorMessage(Socket client){
        //compiler made me put in a try catch for this statement?
        PrintWriter outStream = null;
        try {
            outStream = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        outStream.print("HTTP/1.1 404 Not Found\n");
        outStream.println("Content-Type: text/html\n");
        outStream.println("<h1>Error 404: Page not found</h1>");
        outStream.flush();
        outStream.close();
    }

    public static void printClientFile(File requestedFile, OutputStream outStream) throws IOException {
        //using the new http page for the client's file
        FileInputStream fileInputStream = new FileInputStream(requestedFile);
        outStream.write("HTTP/1.1 200 OK\n".getBytes());
        outStream.write("Content-Type: text/html\n".getBytes());
        outStream.write("\n".getBytes());
        fileInputStream.transferTo(outStream);
        outStream.flush();
        outStream.close();
    }



    }

