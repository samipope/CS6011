package DNSResolver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class DNSMessage {

    /**
     * This corresponds to an entire DNS Message. It should contain:
     *
     * the DNS Header
     * an array of questions
     * an array of answers
     * an array of "authority records" which we'll ignore
     * an array of "additional records" which we'll almost ignore
     * You should also store the the byte array containing the complete message in this class. You'll need it to handle the compression technique described above
     */

    static DNSMessage decodeMessage(byte[] bytes){
        return null;
    }

    /**
     * read the pieces of a domain name starting from the current position of the input stream
     * @param inputStream
     * @return
     */
    String[] readDomainName(InputStream inputStream){
        return null;
    }

    /**
     * same, but used when there's compression and we need to find the domain from earlier in the message.
     * This method should make a ByteArrayInputStream that starts at the specified byte and call the other version of this method
     * @param firstByte
     * @return
     */
    String[] readDomainName(int firstByte){
        return null;
    }

    /**
     * build a response based on the request and the answers you intend to send back.
     * byte[] toBytes() -- get the bytes to put in a packet and send back
     * @param request
     * @param answers
     * @return
     */
    static DNSMessage buildResponse(DNSMessage request, DNSRecord[] answers){
        return null;
    }


    /**
     * If this is the first time we've seen this domain name in the packet, write it using the DNS encoding (each segment of the domain prefixed with its length, 0 at the end), and add it to the hash map.
     * Otherwise, write a back pointer to where the domain has been seen previously.
     * @param outputStream
     * @param map
     */
    static void writeDomainName(ByteArrayOutputStream outputStream, HashMap<String,Integer> map){

    }


    /**
     *join the pieces of a domain name with dots ([ "utah", "edu"] -> "utah.edu" )
     * @param pieces
     * @return
     */
    String joinDomainName(String[] pieces){
        return null;
    }



    @Override
    public String toString(){
       return super.toString();
    }

}
