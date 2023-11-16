package ServerClient;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private final Map<String, String> requestHeader = new HashMap<>();
    private String fileName = "";
    private Boolean isFileValid;
    private String httpVersion = "";

    private boolean isWebSocket = false;

    private String websocketRequestKey = "";

    public Request() {
    }


    public String getFilePath(String fn) {
        return "/Users/samanthapope/6011Github/Github/CS6011/Day20/ChatClient/resources" + fn;
    }


    public Boolean getIsFileValid() {
        return isFileValid;
    }


}
