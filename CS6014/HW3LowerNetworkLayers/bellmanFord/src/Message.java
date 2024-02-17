import java.util.HashMap;

public class Message {
    Router sender, receiver;
    HashMap<Router, Integer> distances;

    public Message(Router sender, Router receiver, HashMap<Router, Integer> distances) {
        this.sender = sender;
        this.receiver = receiver;
        this.distances = distances;
    }

    public HashMap<Router, Integer> getDistances() {
        return distances;
    }

    public Router getReceiver() {
        return receiver;
    }

    public Router getSender(){
        return sender;
    }

    public void dump() {
        System.out.println("sender: " + sender + " receiver " + receiver);
        for(Router r : distances.keySet()){
            System.out.println("\t" + r + "\t" + distances.get(r));
        }
    }

}
