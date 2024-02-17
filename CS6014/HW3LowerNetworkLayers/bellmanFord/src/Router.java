
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Router {

    private HashMap<Router, Integer> distances;
    private String name;
    public Router(String name) {
        this.distances = new HashMap<>();
        this.name = name;
    }

    public void onInit() throws InterruptedException {
        Set<Router> routers = Network.getRouters();
        for (Router router : routers) {
            if (router == this) {
                distances.put(router, 0); // distance to self is 0
            } else {
                distances.put(router, Integer.MAX_VALUE/2); // Infinity for others
            }
        }

        // update distances to immediate neighbors based on direct links
        HashSet<Neighbor> neighbors = Network.getNeighbors(this);
        for (Neighbor neighbor : neighbors) {
            distances.put(neighbor.getRouter(), neighbor.getCost());
        }

        // broadcast initial distance vector to neighbors
        broadcastDistanceVector();
    }

    //helper method
    private void broadcastDistanceVector() throws InterruptedException {
        HashSet<Neighbor> neighbors = Network.getNeighbors(this);
        for (Neighbor neighbor : neighbors) {
            Network.sendDistanceMessage(new Message(this, neighbor.getRouter(), new HashMap<>(distances)));
        }
    }

    public void onDistanceMessage(Message message) throws InterruptedException {
        boolean updated = false;
        Router sourceRouter = message.getSender();
        HashMap<Router, Integer> receivedDistances = message.getDistances();

        for (Map.Entry<Router, Integer> entry : receivedDistances.entrySet()) {
            Router targetRouter = entry.getKey();
            // Check if source distance is infinity to avoid unnecessary calculations
            if (distances.get(sourceRouter) == Integer.MAX_VALUE) {
                continue;
            }

            int distanceThroughSource = distances.get(sourceRouter) + entry.getValue();
            // Check to ensure we don't exceed Integer.MAX_VALUE
            if (distanceThroughSource < 0) { // Overflow occurred
                distanceThroughSource = Integer.MAX_VALUE;
            }

            if (!distances.containsKey(targetRouter) || distanceThroughSource < distances.get(targetRouter)) {
                distances.put(targetRouter, distanceThroughSource);
                updated = true;
            }
        }

        if (updated) {
            broadcastDistanceVector();
        }
    }


    public void dumpDistanceTable() {
        System.out.println("router: " + this);
        for(Router r : distances.keySet()){
            System.out.println("\t" + r + "\t" + distances.get(r));
        }
    }

    @Override
    public String toString(){
        return "Router: " + name;
    }

}
