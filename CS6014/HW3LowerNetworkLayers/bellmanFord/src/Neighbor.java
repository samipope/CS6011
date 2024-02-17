public class Neighbor {
    Router router;
    int cost;

    public Neighbor(Router router, int cost) {
        this.router = router;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public Router getRouter() {
        return router;
    }

    @Override
    public String toString(){
        return "to " + router + " cost: " + cost;
    }



}
