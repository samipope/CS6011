import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HttpRequestHandler {

    String fileName;
    Map<String, String> headers = new HashMap();

     Socket client_;

    public HttpRequestHandler(Socket client) throws Exception{
        client_ = client;
        parseRequest();
    }

    private void parseRequest() throws Exception {
        Scanner sc = new Scanner(client_.getInputStream());

        String requestLine = sc.nextLine();
//        System.out.println("The requested line is: " + requestLine);

        String[] requestParts = requestLine.split(" ");
        fileName = requestParts[1]; // GET /index.html http1.1

        boolean done = false;
        while( !done ) {
            requestLine = sc.nextLine();
            System.out.println("Request from Client: " + requestLine);
            if( requestLine.length() != 0 ) {
                String[] pieces = requestLine.split(": ");
                String key = pieces[0];
                String value = pieces[1];
                headers.put(key, value);
            }
            else {
                done = true;
            }
        }
    }

   public boolean isWebsocketReq(){
        return headers.containsKey("Sec-WebSocket-Key");
    }

}
