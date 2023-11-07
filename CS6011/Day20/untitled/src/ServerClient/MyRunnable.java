package ServerClient;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.*;

public class MyRunnable implements Runnable {

    Socket client_;
    private String username_;
    public String roomName_;
    public String message_;
    RoomManager rm_;

    public MyRunnable(Socket client, RoomManager rm) {
        client_ = client;
        username_=null;
        roomName_=null;
        message_=null;
        rm_=rm;
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
            System.out.println("MESSAGE: " + message + "---------------------------------------------");
        } else {
            message = new String(payloadArr, StandardCharsets.UTF_8);
        }
        return message;
    }

    private static ArrayList<Byte> getBytesFromNumber(int number, int length) {
        ArrayList<Byte> results = new ArrayList<>();
        for(int i=0; i<length; i++)
        {
            byte current = (byte) (number & 0xff);
            number = number >> 8;
            results.add(current);
        }
        Collections.reverse(results);
        return results;
    }


    public static byte[] getResponseFrame(String message) {
            //DataOutputStream dataOut = new DataOutputStream(client_.getOutputStream());
            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            int payloadLength = payload.length;
            ArrayList<Byte> responseFrame = new ArrayList<>();
            // FIN / RSV*3 / OPCODE
            byte firstByte = (byte) 0x81;
            responseFrame.add(firstByte);
            if (payloadLength <= 125) {
                byte secondByte = (byte) payloadLength;
                responseFrame.add(secondByte);
            } else if (payloadLength <= 0xffff) {
                byte secondByte = 126;
                responseFrame.add(secondByte);
                ArrayList<Byte> lengthBytes = getBytesFromNumber(payloadLength, 2);
                responseFrame.addAll(lengthBytes);
            } else {
                byte secondByte = 127;
                responseFrame.add(secondByte);
                ArrayList<Byte> lengthBytes = getBytesFromNumber(payloadLength, 8);
                responseFrame.addAll(lengthBytes);
            }
            for (byte p :
                    payload) {
                responseFrame.add(p);
            }
            byte[] results = new byte[responseFrame.size()];
            for (int i = 0; i < responseFrame.size(); i++) {
                results[i] = responseFrame.get(i);
            }
            return results;
    }



    public void encodeMessage(String response) throws IOException {
        System.out.println("send response: " + response);
        OutputStream out = null;
        try {
            out = client_.getOutputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        assert out != null;
        out.write(getResponseFrame(response));
    }






//
//            // Construct a WebSocket text frame
//            byte[] frame = new byte[messageBytes.length + 2];
//            frame[0] = (byte) 0x81;  // Text frame opcode
//            frame[1] = (byte) messageBytes.length;  // Length of the message
//
//            System.arraycopy(messageBytes, 0, frame, 2, messageBytes.length);
//
//            dataOut.write(frame);
//            dataOut.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    private void parseMessType(String message) {
        String cmd_;
        String[] messArr = message.split(":");
        cmd_ = messArr[0];
        username_=messArr[1];
        roomName_=messArr[2];
        if(cmd_.equals("join")){
            rm_.joinRoom(this);
            System.out.println("This is the room they are trying to join"+ rm_);
        }
        if (cmd_.equals("leave")){
            rm_.leaveRoom(this);
            System.out.println("this is the room they are trying to leave:" + rm_);
        }
        if (cmd_.equals("message")){
            message_=messArr[3];
            rm_.sendMess(message_, this);
            System.out.println("this is the message user is sending:" + message_);
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
        HTTPResponse response = new HTTPResponse();
        //Create a request object which requires a ServerSocket to initialize
        HTTPRequest request = null;
        try {
            request = new HTTPRequest(client_);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            //calls the parse method with the request object
            if (!request.parse()) {
                System.out.println("bad req");
            }

            if (request.isWebSocket()) {
                response.sendWebSockHandshake(client_, request.getWebSocketKey());
                String endMessage = new String(new byte[]{3,-23});
                boolean done = false;
                while (!done) {

                    //this decodes the packet after the handshake was completed
                    String msg = decodeMessage();

                    if( msg.equals(endMessage)){
                        System.out.println("closing socket");
                        done = true;

                        //This leave room does the wrong name?
                        rm_.leaveRoom(this);

                        client_.close();
                    }
                    else{
                        parseMessType(msg);
                    }

                }
            }
            else {

                //creates the file that is required to send into the response.sendResponse
                File file = response.createFile(request.getParameter());

                //sends the response back to the client
                response.sendHTTPResponse(client_, file, request.getFileType());
                client_.close();

            }
        } catch (Exception e) {
            //if there are any errors above, the error page is sent to site to prevent crashing
            try {
                response.sendFailResponse(client_);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}





