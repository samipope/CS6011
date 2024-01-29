package DNSResolver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class DNSQuestion {

    /**
     *read a question from the input stream. Due to compression, you may have to ask the DNSMessage containing this question to read some of the fields.
     */
    static DNSQuestion decodeQuestion(InputStream inputStream, DNSMessage message){
        return null;
    }

    /**
     * Write the question bytes which will be sent to the client. The hash map is used for us to compress the message, see the DNSMessage class below.
     * @param outStream
     * @param map
     */
    void writeBytes(ByteArrayOutputStream outStream, HashMap<String,Integer> map){

    }




//let following functions be taken care of by IDE
    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
