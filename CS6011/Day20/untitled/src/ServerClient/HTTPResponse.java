package ServerClient;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Base64;

public class HTTPResponse {

    private Socket socket;


    public HTTPResponse(Socket socket) {
        this.socket = socket;

    }

    private String getFinalFilePath() {
        // If the file does not exist, return 404 fallback page
        return "src/ErrorPage.html";
    }

    private String getFallBackPageHTML(String errorMessage) {
        return "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h3>Error Message From the Server:</h3>\n" +
                "    <p>" + errorMessage + "</p>\n" +
                "</body>\n" +
                "</html>";
    }




    private void sendResponseHeader(PrintWriter printWriter, String statusCode, String contentType) {
        printWriter.println("HTTP/1.1 " + statusCode);
        printWriter.println("Content-Type: " + contentType);
        printWriter.println("Connection: close");
        printWriter.println();
    }

    private void sendResponseBody(OutputStream socketOutputStream) {
        try {
            InputStream finalFileStream = new FileInputStream(getFinalFilePath());
            finalFileStream.transferTo(socketOutputStream);
            finalFileStream.close();
        } catch (IOException e) {
            // If the file does not exist, return a fallback page to display a server error message
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public void handleResponse() {
        try {
            OutputStream socketOutputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(socketOutputStream, true);

            // Check if the file is valid and exists
            //Users/samanthapope/6011GitHub/Github/CS6011/Day20/untitled/src/
            File file = new File("/Users/samanthapope/6011GitHub/Github/CS6011/Day20/untitled/src");
            if (file.exists() && !file.isDirectory()) {
                // File exists, serve it
                sendResponseHeader(printWriter, "200 OK", "text/html");
                Files.copy(file.toPath(), socketOutputStream);
            } else {
                // File not found, serve 404 response
                sendResponseHeader(printWriter, "404 Not Found", "text/html");
                printWriter.println(getFallBackPageHTML("File not found."));
            }

            printWriter.close();
            socketOutputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void sendWebSockHandshake(Socket client, String key) throws IOException {
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

    public void write404FallBackPage() {
        try {
            FileWriter fileWriter = new FileWriter(getFinalFilePath());
            fileWriter.write(getFallBackPageHTML("File not found."));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
