import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HttpRequestHandler {

    String fileName;
    Map headers = new HashMap<String,String>();

    private Socket client_;

    public HttpRequestHandler(Socket client) throws Exception{
        client_ = client;
        parseRequest();
    }

    private void parseRequest() throws Exception {
        Scanner sc = new Scanner(client_.getInputStream());

        String requestLine = sc.nextLine();

        String[] requestParts = requestLine.split(" ");
        fileName = requestParts[1]; // GET /index.html http1.1

        boolean done = false;
        while( !done ) {
            requestLine = sc.nextLine();
            if( requestLine.length() != 0 ) {
                String[] pieces = requestLine.split(":");
                String key = pieces[0];
                String value = pieces[1];
                headers.put(key, value);
            }
            else {
                done = true;
            }
        }
    }

//    public String getClientPath(String requestLine){
//        String[] requestParts = requestLine.split(" ");
//        //make what the user requests into a string path
//        String path = requestParts[1];
//        return path;
//    }
//    public String getClientMethod(String requestLine) {
//        String[] requestParts = requestLine.split(" ");
//        String method = requestParts[0];
//        return method;
//    }
//

}
