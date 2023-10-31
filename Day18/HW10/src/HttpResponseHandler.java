import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;



//put an output stream in the class that replies
//in the return type make a FILE as a variable
//construct the file in it's own constructor and nest that in the original contructor
public class HttpResponseHandler {

    OutputStream output_;
    HttpRequestHandler request_;
    HttpResponseHandler( OutputStream outStream, HttpRequestHandler request ){
        output_ = outStream;
        request_ = request;
    }



    public void sendResponse (File file, String fileType) throws IOException, NoSuchAlgorithmException {
        if (request_.isWebsocketReq()){
            System.out.println("This is a web socket request!");
            sendWebSocketResponse(request_.headers.get("Sec-WebSocket-Key"));
            ClientHandler handler = new ClientHandler(request_.client_);
            handler.handleWebSocketMessages();
        } else {

//        OutputStream outStream = output_;

            FileInputStream fileStream = null;
            fileStream = new FileInputStream(file);

            if (fileType != null)
                fileStream = new FileInputStream(file);
            output_.write("http/1.1 200 Success \n".getBytes());
            output_.write(("Content-Type: " + fileType + "\n").getBytes());
            output_.write("\n".getBytes());
            fileStream.transferTo(output_);

            output_.flush();
            output_.close();
        }
    }



    public static String generateHandshakeString(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        key += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11"; // Magic string
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(key.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(digest);
    }

    public void sendWebSocketResponse(String key) throws IOException, NoSuchAlgorithmException {
      // System.out.println("handshake key:" + key);
      //  System.out.println("handshake generated string:" + generateHandshakeString(key));
        // Send the HTTP 101 response
        output_.write("HTTP/1.1 101 Switching Protocols\r\n".getBytes());
        output_.write("Upgrade: websocket\r\n".getBytes());
        output_.write("Connection: Upgrade\r\n".getBytes());
        output_.write(("Sec-WebSocket-Accept: " + generateHandshakeString(key) + "\r\n").getBytes());
        output_.write("\r\n".getBytes());
        output_.flush();
    }
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


    public void sendWebSocketMessage(String message, OutputStream outputStream) throws IOException {
        boolean fin = true; // We're sending the entire message in one frame

        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        long payloadLen = messageBytes.length;

        byte[] frameHeader = new byte[2];

        // FIN (1 bit), RSV1-3 (3 bits, all 0), Opcode (4 bits)
        frameHeader[0] = (byte) (fin ? 0x80 : 0x00); // Set the FIN bit
        frameHeader[0] |= 0x01; // Opcode for text frame

        frameHeader[1] = (byte) (payloadLen < 126 ? payloadLen : (payloadLen <= 0xFFFF ? 126 : 127));


        outputStream.write(frameHeader);

        // If payload length is greater than 125, you may need to send additional length bytes
        if (payloadLen >= 126) {
            if (payloadLen <= 0xFFFF) {
                byte[] extendedPayloadLen = new byte[2];
                extendedPayloadLen[0] = (byte) ((payloadLen >> 8) & 0xFF);
                extendedPayloadLen[1] = (byte) (payloadLen & 0xFF);
                outputStream.write(extendedPayloadLen);
            } else {
                byte[] extendedPayloadLen = new byte[8];
                extendedPayloadLen[0] = (byte) 0x7F;
                for (int i = 7; i >= 0; i--) {
                    extendedPayloadLen[i] = (byte) (payloadLen & 0xFF);
                    payloadLen >>= 8;
                }
                outputStream.write(extendedPayloadLen);
            }
        }

        // Write the unmasked message
        outputStream.write(messageBytes);
        outputStream.flush();

    }



    public void printErrorMessage(){
        //compiler made me put in a try catch for this statement?
        PrintWriter outStream = new PrintWriter(output_);
        outStream.print("HTTP/1.1 404 Not Found\n");
        outStream.print("Content-Type: text/html\n\n"); // 2nd \n means end of header
        outStream.print("<h1>Error 404: Page not found</h1>");
        outStream.flush();
        outStream.close();
    }



    }

