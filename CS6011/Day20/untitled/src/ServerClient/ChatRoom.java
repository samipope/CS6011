package ServerClient;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class ChatRoom {private List<MyRunnable> clientArr = new ArrayList<>();
    String name_;
    ChatRoom(String name){
        name_=name;
    }
    public synchronized void helperSend(String message) throws IOException {
        for (MyRunnable client : clientArr) {
            client.encodeMessage(message);
        }
    }
    public synchronized void addClient(MyRunnable runnable) throws IOException {
        for(MyRunnable client : clientArr){
            //this is returning the incorrect room name and username
            String name =client.getUsername_();
           // MyRunnable.encodeMessage(MyRunnable.makeJoinMsg(client.getRoomName_(), client.getUsername_()));
        }
        clientArr.add(runnable);
        helperSend(MyRunnable.makeJoinMsg(runnable.getRoomName_(), runnable.getUsername_()));
    }
    public synchronized void removeClient(MyRunnable runnable) throws IOException {
        clientArr.remove(runnable);
        helperSend(MyRunnable.makeLeaveMsg(runnable.getRoomName_(), runnable.getUsername_()));
    }
    public synchronized void sendMessage(MyRunnable runnable, String msg) throws IOException {
        helperSend(MyRunnable.makeMsgMsg(runnable.getRoomName_(), runnable.getUsername_(), msg));
    }
}


