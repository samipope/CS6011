package DNSResolver;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class DNSQuestion {

/*
+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
|                                               |
|                      QNAME                    |
+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
|                      QTYPE                    |
+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
|                      QCLASS                   |
 +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 */

    private String[] QName;
    private int QType;
    private int QClass;
    DNSMessage message;

    public DNSQuestion(){
        //empty constructor
    }

    /**
     *read a question from the input stream. Due to compression, you may have to ask the DNSMessage containing this question to read some of the fields.
     */
    static DNSQuestion decodeQuestion(InputStream inputStream, DNSMessage message) throws IOException {
        DNSQuestion question = new DNSQuestion();
        question.QName = message.readDomainName(inputStream);

        DataInputStream dataInputStream = new DataInputStream(inputStream);

      //  question.QName = new String[]{String.join(".",message.readDomainName(inputStream))};
        question.QType = dataInputStream.readShort();
        question.QClass = dataInputStream.readShort();

        return question;
    }

    /**
     * Write the question bytes which will be sent to the client.
     * The hash map is used for us to compress the message, see the DNSMessage class below.
     * @param outStream
     * @param domainNameLocations
     */
    void writeBytes(ByteArrayOutputStream outStream, HashMap<String,Integer> domainNameLocations)throws IOException{
        DNSMessage.writeDomainName(outStream, domainNameLocations, QName);

        DataOutputStream dataOutputStream = new DataOutputStream(outStream);
        //both QType and QClass are a short (2bytes)
        dataOutputStream.writeShort(QType);
        dataOutputStream.writeShort(QClass);
    }


//let following functions be taken care of by IDE
    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (this.getClass() != o.getClass()){
            return false;
        }
        DNSQuestion question = (DNSQuestion) o;
        if (this.QClass != question.QClass){
            return false;
        }
        if (this.QType != question.QType){
            return false;
        }
        return Arrays.equals(this.QName, question.QName);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
