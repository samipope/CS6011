import java.util.Scanner;

public class Client {


    public String getClientPath(String requestLine){
        String[] requestParts = requestLine.split(" ");
        //make what the user requests into a string path
        String path = requestParts[1];
        return path;
    }
    public String getClientMethod(String requestLine) {
        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0];
        return method;
    }

}
