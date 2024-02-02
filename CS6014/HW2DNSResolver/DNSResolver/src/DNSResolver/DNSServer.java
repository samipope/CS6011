package DNSResolver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class DNSServer {
    //Try catch thisssssss.
    //Maybe add some global variables
    //While true
    //Decode message, which is the request
    //check the cache for the question, in that request
    //getQuestions(), store in questions
    //if found and not expired, then answer is the answer we want (the empty array, by making a new one and add the record so that we only have one answer. Which is why you want to set it to new everytime.)
    //Else, ask google (InetAddress googleDNS = InetAddress.getByName("8.8.8.8")
    //Create new and send the forward packet to Google (socket.send)
    //Declare new datagram packet, called response packet.
    //Decode that
    //Add the answer to the cache.
    //Outside of the IF Else, you send it back to the user. Create responseMessage by calling DNSMessage.buildResponse(), and a line for
    //byte[] responseData = responseMessage.toBytes();
    //Create new responsePacket
    //socket.send(responsePacket)
    //You may not need a socket close.
    //Empty response message
    //Build response is at the bottom
    private static final int SERVER_PORT = 8053;
    private static final String GOOGLE_DNS_ADDRESS = "8.8.8.8";
    private static final int BUFFER_SIZE = 1024;
    private static final DNSCache cache = new DNSCache();

    public static void main(String[] args) {
        System.out.println("Listening on port: " + SERVER_PORT);

        try  {

            DatagramSocket socket = new DatagramSocket(SERVER_PORT);
            DatagramSocket googleSocket = new DatagramSocket();
            while (true) {
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Receive request from client

                DNSMessage requestMessage = DNSMessage.decodeMessage(packet.getData());
                DNSMessage responseMessage = new DNSMessage(); // Initialize an empty response message

                for (DNSQuestion question : requestMessage.getQuestions()) {
                    DNSRecord record = DNSCache.query(question);
                    if (record != null && !record.isExpired()) {
                        // If cached answer is found and not expired, use it
                        responseMessage = DNSMessage.buildResponse(requestMessage, new DNSRecord[]{record});
                    } else {
                        // Else, forward the request to Google's DNS
                        InetAddress googleDNS = InetAddress.getByName(GOOGLE_DNS_ADDRESS);
                        DatagramPacket forwardPacket = new DatagramPacket(buffer, packet.getLength(), googleDNS, 53);
                        googleSocket.send(forwardPacket); // Send request to Google

                        byte[] responseBuffer = new byte[BUFFER_SIZE];
                        DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
                        googleSocket.receive(responsePacket); // Receive response from Google

                        DNSMessage googleResponse = DNSMessage.decodeMessage(responsePacket.getData());
                        if(googleResponse.getAnswers().length!=0) {
                            for (DNSRecord answer : googleResponse.getAnswers()) {
                                cache.insert(question, answer); // cache the new answer
                            }
                        }
                        responseMessage = googleResponse; // use Google's response as the response message
                    }
                }
                System.out.println("here is the cache: "+ cache.getCache());


                // Send the response back to the client
                byte[] responseData = responseMessage.toBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, packet.getAddress(), packet.getPort());
                socket.send(responsePacket); // Send response to the client
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



