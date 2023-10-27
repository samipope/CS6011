import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Scanner;

class ClientHandler implements Runnable {
    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }




    @Override
    public void run() {
        try {
            HttpRequestHandler request = new HttpRequestHandler(client);

            String resource_dir = "src";

            String fileName = request.fileName;
            if (fileName.equals("/")) {
                fileName = "/chat.html";
            }
            System.out.println("Server: the client requested: " + fileName);
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

            HttpResponseHandler responder = new HttpResponseHandler(client.getOutputStream(), request);
            responder.sendResponse(file, fileType);
//            responder.printClientFile( file, fileType );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void handleWebSocketMessages() throws IOException {

        boolean fin_;
        boolean mask_;
        long payloadLen_;
        byte[] maskArr = new byte[0];

        DataInputStream dataStream = new DataInputStream(client.getInputStream());

        while(true){
            byte[] input = dataStream.readNBytes(2);

            fin_=(input[0]&128)>0;

            mask_=(input[0]&0x80)>0;

            payloadLen_=(input[1]&0x7f);

            if (payloadLen_==126){
                payloadLen_ = dataStream.readShort();
            }
            else if (payloadLen_==127) {
                payloadLen_=dataStream.readLong();
            }

            System.out.println("Masked: "+ mask_+" Length: "+payloadLen_);

            if(mask_){
                maskArr=dataStream.readNBytes(4);
            }

            byte[] payloadArr=dataStream.readNBytes((int) payloadLen_);

            if (mask_) {
                for (int i = 0; i < payloadArr.length; i++) {
                    payloadArr[i] = (byte) (payloadArr[i] ^ maskArr[i % 4]);
                }
            }

            String message = new String(payloadArr, StandardCharsets.UTF_8);
            System.out.println(message);

            //this is the message that the server is sending with the format " join user room" and "message contentofMessage"

            String request[] = message.split(" ");
            System.out.println("request [0]"+ request[0]);
            System.out.println("request [1]"+ request[1]);
            System.out.println("request[2]"+ request[2]);

            if (request[0].equals("join")){
                String roomName = request[1];
                //call function that adds the user to the room
               Room thisRoom = Room.getRoom(roomName);
               thisRoom.addClientToRoom(client);

            }
//
//            if (request[0].equals("message"){
//                //call function that broadcasts message to the world
//            }

        }
        }

    }




