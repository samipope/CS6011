import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;
import java.io.*;

class ClientHandler implements Runnable {
    private Socket client_;
    private String username_;
    public String roomName_;
    public String message_;

    public ClientHandler(Socket client) {
        this.client_ = client;
        username_=null;
        roomName_=null;
        message_=null;

    }

    private String decodeMessage() throws IOException {

        Boolean fin_;
        int opcode_;
        Boolean mask_;
        long payloadLen_;
        byte[] maskBytes = new byte[0];
        String message;

        DataInputStream in = new DataInputStream(client_.getInputStream());

        System.out.println("handling incoming message for: " + client_);

        byte[] input = in.readNBytes(2);

        fin_ = (input[0] & 0x80) > 0;

        opcode_ = (input[0] & 0x0F);

        mask_ = (input[1] & 0x80) > 0;

        payloadLen_ = (input[1] & 0x7f);

        if (payloadLen_ == 126) {
            payloadLen_ = in.readShort();
        } else if (payloadLen_ == 127) {
            payloadLen_ = in.readLong();
        }

        System.out.println("Masked: " + mask_ + " Length: " + payloadLen_);

        if (mask_) {
            maskBytes = in.readNBytes(4);
        }

        byte[] payloadArr = in.readNBytes((int) payloadLen_);

        if (mask_) {
            for (int i = 0; i < payloadArr.length; i++) {
                payloadArr[i] = (byte) (payloadArr[i] ^ maskBytes[i % 4]);
            }
            message = new String(payloadArr, StandardCharsets.UTF_8);
            System.out.println("MESSAGE: " + message );
        } else {
            message = new String(payloadArr, StandardCharsets.UTF_8);
        }
        return message;
    }

    public void encodeMessage(String message) {
        try {
            DataOutputStream dataOut = new DataOutputStream(client_.getOutputStream());
            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

            // Construct a WebSocket text frame
            byte[] frame = new byte[messageBytes.length + 2];
            frame[0] = (byte) 0x81;  // Text frame opcode
            frame[1] = (byte) messageBytes.length;  // Length of the message

            System.arraycopy(messageBytes, 0, frame, 2, messageBytes.length);

            dataOut.write(frame);
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseMessType(String message) {
        String cmd_;
        String[] messArr = message.split(":");
        cmd_ = messArr[0];
        username_=messArr[1];
        roomName_=messArr[2];
        if(cmd_.equals("join")){
            Room thisRoom = Room.getRoom(roomName_);
            thisRoom.addClientToRoom(client_);
        }
        if (cmd_.equals("leave")){
           // rm_.leaveRoom(this);
        }
        if (cmd_.equals("message")){
            message_=messArr[3];
           // thisRoom.sendMess(message_, this);
        }
    }

    public String getUsername_(){
        return username_;
    }
    public String getRoomName_(){
        return roomName_;
    }
    public String getMessage_(){
        return message_;
    }

    public static String makeJoinMsg(String room, String name){
        return "{ \"type\": \"join\", \"room\": \"" + room + "\", \"user\": \"" + name + "\" }";
    }
    public static String makeMsgMsg(String room, String name, String message) {
        return "{ \"type\": \"message\", \"user\": \"" + name + "\", \"room\": \"" + room + "\", \"message\": \"" + message + "\" }";
    }
    public static String makeLeaveMsg(String room, String name) {
        return "{ \"type\": \"leave\", \"room\": \"" + room + "\", \"user\": \"" + name + "\" }";
    }


    @Override
    public void run() {
        //Create a response object
       // HttpResponseHandler response = new HttpResponseHandler(client_,);
        //Create a request object which requires a ServerSocket to initialize
        HttpRequestHandler request = null;
        try {
            request = new HttpRequestHandler(client_);
            HttpResponseHandler response = new HttpResponseHandler(client_.getOutputStream(),request);
            //calls the parse method with the request object
            if (request.isWebsocketReq()) {
                response.sendWebSockHandshake(client_, request.headers.get("Sec-WebSocket-Key"));
                String endMessage = new String(new byte[]{3,-23});
                boolean done = false;
                while (!done) {

                    //this decodes the packet after the handshake was completed
                    String msg = decodeMessage();

                    if( msg.equals(endMessage)){
                        System.out.println("closing socket");
                        done = true;

                        //This leave room does the wrong name?
                        //rm_.leaveRoom(this);

                        client_.close();
                    }
                    else{
                        parseMessType(msg);
                    }
                }
            }
            else {
                String resource_dir = "src";
                String fileName = request.fileName;
                if (fileName.equals("/")) {
                    fileName = "/chat.html";
                }

                File file = new File(resource_dir + fileName);

                String[] fileNameParts = fileName.split("\\.");
//            System.out.println(fileNameParts);

                String fileType = " ";
                if (Objects.equals(fileNameParts[1], "html")) {
                    fileType = "text/html";
                } else if (Objects.equals(fileNameParts[1], "png")) {
                    fileType = "image/png";
                } else if (Objects.equals(fileNameParts[1], "jpeg") || Objects.equals(fileNameParts[1], "jpg")) {
                    fileType = "image/jpg";
                } else if (Objects.equals(fileNameParts[1], "css")) {
                    fileType = "text/css";
                } else if (Objects.equals(fileNameParts[1], "js")) {
                    fileType = "text/javascript";

                }
                System.out.println("filetype: " + fileType);
                //sends the response back to the client
                response.sendResponse(file, fileType);
                client_.close();

            }
        } catch (Exception e) {
            //if there are any errors above, the error page is sent to site to prevent crashing
            try {
                HttpResponseHandler response = new HttpResponseHandler(client_.getOutputStream(),request);
                response.sendFailResponse(client_);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }







    public void handleWebSocketMessages() throws IOException {
        DataInputStream dataStream = new DataInputStream(client_.getInputStream());

        while (true) {
            try {
                byte[] input = dataStream.readNBytes(2);
                boolean fin = (input[0] & 0x80) > 0;
                boolean mask = (input[1] & 0x80) > 0;
                long payloadLen = (input[1] & 0x7F);

                if (payloadLen == 126) {
                    payloadLen = dataStream.readShort();
                } else if (payloadLen == 127) {
                    payloadLen = dataStream.readLong();
                }

                System.out.println("Masked: " + mask + " Length: " + payloadLen);

                byte[] maskArr = new byte[0];
                if (mask) {
                    maskArr = dataStream.readNBytes(4);
                }

                byte[] payloadArr = dataStream.readNBytes((int) payloadLen);

                if (mask) {
                    for (int i = 0; i < payloadArr.length; i++) {
                        payloadArr[i] = (byte) (payloadArr[i] ^ maskArr[i % 4]);
                    }
                }

                String message = new String(payloadArr, StandardCharsets.UTF_8);
                System.out.println("Message received: " + message);

                String[] parts = message.split(" ", 2);

                if (parts.length >= 2) {
                    String command = parts[0];
                    String content = parts[1];

                    if (command.equals("join")) {
                        // Handle join command
                        String[] joinParams = content.split(" ");
                        if (joinParams.length == 2) {
                            String username = joinParams[0];
                            String roomName = joinParams[1];
                            // Call a function to add the user to the room
                            Room thisRoom = Room.getRoom(roomName);
                            thisRoom.addClientToRoom(client_);
                            System.out.println("User " + username + " joined room " + roomName);
                        }
                    } else if (command.equals("message")) {
                        // Handle message command
                        message_ = content;
                        // The content variable already contains the entire message content
                        System.out.println("Message: " + content);
                        //HttpResponseHandler response = new HttpResponseHandler(client.getOutputStream());
                    } else if (command.equals("leave")) {
                        String[] leaveParams = content.split(" ");
                        if (leaveParams.length == 2) {
                            String roomName = leaveParams[1];
                            // Call a function to add the user to the room
                            Room thisRoom = Room.getRoom(roomName);
                            thisRoom.removeClient(client_);
                        }
                    }
                }
                break; // Exit the loop in case of an error
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}




