import java.net.Socket;
import java.util.ArrayList;

public class Room {

    //room class needs array list of rooms
  public static  ArrayList<Room> rooms_= new ArrayList<>();
  String roomName_;
  ArrayList<Socket> clients_ = new ArrayList<>();


//add a client
 public Room(String roomName){
    // getRoom(roomName);
     roomName = this.roomName_;
     rooms_.add(this);
  }

  //TODO this is the only one that has the socket!!!!
  public void addClientToRoom(Socket client){
     this.clients_.add(client);

  }

//send message to all the clients in the room
    public void sendMessage(String message){
     for(int i = 0; i<this.clients_.size(); i++){
         clients_.get(i).getOutputStream();
     }


    }


    //remove a client
public synchronized void removeClient (Socket clientName){
     clients_.remove(clientName);
}



    //get the room
public synchronized static Room getRoom(String roomName){
     if(rooms_!=null){
     //if room exists, return room
        for(int i =0; i<rooms_.size();i++) {
            if (rooms_.get(i).roomName_.equals("roomName")) {
                // rooms_.get(i).clients_.add(clientName);
                return rooms_.get(i);
        }
    }
        //if room doesn't exist --> create it!
    }
    return new Room(roomName);
        }




}





