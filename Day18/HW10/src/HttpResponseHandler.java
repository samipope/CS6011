import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.security.MessageDigest;


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

    //sendFailResponse requires a socket and sends the fail html file to the client
    public void sendFailResponse(Socket client) throws IOException {
        File failFile = new File ("Resources/ErrorPage.html");
        OutputStream outStream = client.getOutputStream();
        FileInputStream failFileStream = new FileInputStream(failFile);

        outStream.write("HTTP/1.1 200 OK\n".getBytes());
        outStream.write("Content-type: text/html\n".getBytes());
        outStream.write("\n".getBytes("UTF-8"));
        failFileStream.transferTo(outStream);
        outStream.flush();
        outStream.close();

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

