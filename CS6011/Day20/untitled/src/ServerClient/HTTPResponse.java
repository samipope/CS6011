package ServerClient;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public class HTTPResponse {

    private final String fallback404PageFileName = "/resources/index.html";



    public HTTPResponse() {

    }

    private String getFinalFilePath() {
        // if the file does not exist, return 404 fall back page
        return "src/ErrorPage.html";
    }

    private String getFallBackPageHTML(String errorMessage) {
        return """
                <!doctype html>
                 <html lang="en">
                 <head>
                     <meta charset="utf-8"/>
                     <meta name="viewport" content="width=device-width,initial-scale=1"/>
                     <meta name="theme-color" content="#000000"/>
                     <meta name="description" content="Lydia Yuan's Profile"/>
                     <title>404 NOT FOUND</title>
                 </head>
                 <body>
                         <h3> Error Message From the Server: </h3>
                         <p>
                          """ +
                errorMessage
                +
                """
                        </p>
                         
                        </body>
                        </html>
                        """;
    }

    private void write404fallbackPage() {
        try {
            new FileInputStream(request.getFilePath(fileName));
        } catch (FileNotFoundException e) {
            try {
                FileWriter fileWriter = new FileWriter(request.getFilePath(fallback404PageFileName));
                fileWriter.write(getFallBackPageHTML(e.getMessage()));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private String getStatusCodeInfo() {
        String statusCodeInfo = isFileValid ? "200 OK" : "404 NOT FOUND";
        return httpVersion + " " + statusCodeInfo;
    }

    private String getContentType() {
        File file = new File(getFinalFilePath());
        String fileContentType = "";
        try {
            fileContentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            System.out.println("Failed: unknown content of " + fileName);
            e.printStackTrace();
        }
        return "content-type: " + fileContentType;
    }

    private void sendResponseHeader(PrintWriter printWriter) {
        if (!isFileValid) write404fallbackPage();
        printWriter.println(getStatusCodeInfo());
        printWriter.println(getContentType());
        // add blank line to indicate the start of the requested file content
        printWriter.println("");
    }

    private void sendResponseBody(OutputStream socketOutputStream) {
        try {
            InputStream finalFileStream = new FileInputStream(getFinalFilePath());
            finalFileStream.transferTo(socketOutputStream);
            finalFileStream.close();
        } catch (IOException e) {
            // if the file does not exist then return a fallback page to display server error message
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendResponse(Socket socket) {
        try {
            OutputStream socketOutputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(socketOutputStream, true);
            sendResponseHeader(printWriter);
            //Pause for 1 second
            Thread.sleep(1000);
            sendResponseBody(socketOutputStream);
            printWriter.close();
            socketOutputStream.close();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleResponse() {
        sendResponse(socket);
    }









    //createFile takes in a string as the name of the file and then returns a file
    public File createFile(String filename){

        return new File ("src/" + filename);
    }
//
//    /*sendResponse creates an OutputStream from the socket, passes a file into FileInputStream,
//    and sends a header based on the filetype.*/
//    public void sendHTTPResponse (Socket client, File file, String fileType) throws IOException {
//        OutputStream outStream = client.getOutputStream();
//
//        FileInputStream fileStream = null;
//        if( fileType != null )
//            fileStream = new FileInputStream(file);
//        outStream.write("http/1.1 200 Success \n".getBytes());
//        if (fileType.equals("jpeg")) {
//            outStream.write(("Content-type: image/" + fileType + "\n").getBytes());
//        }
//        else if (fileType.equals("pdf")) {
//            outStream.write(("Content-type: application/" + fileType + "\n").getBytes());
//        }
//        else {
//            outStream.write(("Content-type: text/" + fileType + "\n").getBytes());
//        }
//        outStream.write("\n".getBytes());
//        fileStream.transferTo(outStream);
//
//        outStream.flush();
//        outStream.close();
//    }
//
//
//
//
    public void sendWebSockHandshake(Socket client, String key) throws IOException {
        OutputStream outStream = client.getOutputStream();

        // Compute the Sec-WebSocket-Accept response for the key
        key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"; // Magic string
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(key.getBytes("UTF-8"));
            key = Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Send the HTTP 101 response
        outStream.write("HTTP/1.1 101 Switching Protocols\r\n".getBytes());
        outStream.write("Upgrade: websocket\r\n".getBytes());
        outStream.write("Connection: Upgrade\r\n".getBytes());
        outStream.write(("Sec-WebSocket-Accept: " + key + "\r\n").getBytes());
        outStream.write("\r\n".getBytes());
        outStream.flush();
    }

    //sendFailResponse requires a socket and sends the fail html file to the client
    public void sendFailResponse(Socket client) throws IOException {
        File failFile = new File ("src/ErrorPage.html");
        OutputStream outStream = client.getOutputStream();
        FileInputStream failFileStream = new FileInputStream(failFile);

        outStream.write("HTTP/1.1 200 OK\n".getBytes());
        outStream.write("Content-type: text/html\n".getBytes());
        outStream.write("\n".getBytes("UTF-8"));
        failFileStream.transferTo(outStream);
        outStream.flush();
        outStream.close();

    }



}