import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

class ClientHandler implements Runnable {
        private Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

    public void printErrorMessage(){
        //compiler made me put in a try catch for this statement?
        try {
            PrintWriter outStream = new PrintWriter(client.getOutputStream());
            outStream.print("HTTP/1.1 404 Not Found\n");
            outStream.print("Content-Type: text/html\n\n"); // 2nd \n means end of header
            outStream.print("<h1>Error 404: Page not found</h1>");
            outStream.flush();
            outStream.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        @Override
        public void run() {
            try {

                Thread.sleep(1000);
                    HttpRequestHandler request = new HttpRequestHandler( client );

                    String resource_dir = "/Users/samanthapope/6011GitHub/CS6011/Day17/HW9/src";

                    String fileName = request.fileName;
                    if (fileName.equals("/")) {
                        fileName = "/index.html";
                    }
                    System.out.println( "Server: the client requested: " + fileName);
                    File file = new File( resource_dir + fileName );
                    HttpResponseHandler responder = new HttpResponseHandler( client.getOutputStream() );


                String[] fileNameParts = fileName.split("\\.");

                String fileType = " ";
                if(Objects.equals(fileNameParts[0], "html")){
                    fileType = "text/html";}
                else if(Objects.equals(fileNameParts[0],"png")){
                    fileType = "image/png";
                }
                else if (Objects.equals(fileNameParts[0],"jpeg") || Objects.equals(fileNameParts[1],"jpg")){
                    fileType = "image/jpg";
                }
                else if (Objects.equals(fileNameParts[1],"css")){
                    fileType = "text/css";
                }

                    responder.printClientFile( file, fileType );

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




