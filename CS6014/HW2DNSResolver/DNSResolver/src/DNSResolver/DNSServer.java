package DNSResolver;

public class DNSServer {

    /**
     * This class should open up a UDP socket (DatagramSocket class in Java), and listen for requests.
     * When it gets one, it should look at all the questions in the request. If there is a valid answer in cache, add that the response, otherwise create another UDP socket to forward the request Google (8.8.8.8) and then await their response.
     * Once you've dealt with all the questions, send the response back to the client.
     *
     * Note: dig sends an additional record in the "additionalRecord" fields with a type of 41.
     * You're supposed to send this record back in the additional record part of your response as well.
     * Note, that in a real server, the UDP packets you receive could be client requests or google responses at any time.
     * For our basic testing you can assume that the next UDP packet you get after forwarding your request to Google will be the response from Google.
     * To be more robust, you can look at the ID in the request, and keep track of your "in-flight" requests to Google, but you don't need to do that for the assignment.
     */




}
