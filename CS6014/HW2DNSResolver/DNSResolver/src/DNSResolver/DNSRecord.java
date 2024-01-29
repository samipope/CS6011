package DNSResolver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class DNSRecord {
   // Everything after the header and question parts of the DNS message are stored as records. This should have all the fields listed in the spec as well as a Date object storing when this record was created by your program. It should also have the following public methods:

    public static DNSRecord decodeRecord(InputStream inputStream, DNSMessage message){
        return null;
    }

    public void writeBytes(ByteArrayOutputStream outputStream, HashMap<String,Integer> map){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * return whether the creation date + the time to live is after the current time. The Date and Calendar classes will be useful for this.
     * @return
     */
    boolean isExpired(){
        return false;
    }
}
