package DNSResolver;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class DNSRecord {

    private String[] name; //A variable-length field containing a domain name.
    private short type; //A 16-bit integer specifying the type of resource record.
    private short rclass; //A 16-bit integer specifying the class of the resource record.
    private int ttl; //A 32-bit unsigned integer specifying the time interval that the resource record may be cached.
    private short rdLength; //A 16-bit integer specifying the length of the RDATA field.
    private byte[] rdata; //A variable-length field containing data specific to the resource record type.
    private Date creationDate;
    DNSMessage message_;

   // Everything after the header and question parts of the DNS message are stored as records.
    // This should have all the fields listed in the spec as well as a Date object storing when this record was created by your program.
    // It should also have the following public methods:

    //---------------- 4.1.3. Resource Record Format ---------------------------------------
//
//    The answer, authority, and additional sections all share the same
//    format: a variable number of resource records, where the number of
//    records is specified in the corresponding count field in the header.
//    Each resource record has the following format:
//                                          1  1  1  1  1  1
//            0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//            |                                               |
//            /                                               /   unknown size
//            /                      NAME                     /
//            |                                               |
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//            |                      TYPE                     |  2 byte
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//            |                     CLASS                     |  2 byte
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//            |                      TTL                      |  4 byte
//            |                                               |
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//            |                   RDLENGTH                    |  2 byte
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--|
//            /                     RDATA                     /
//            /                                               / unknown size
//            +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
//
//    NAME      a domain name to which this resource record pertains.
//    TYPE      two octets containing one of the RR type codes.  This
//    field specifies the meaning of the data in the RDATA field
//    CLASS           two octets which specify the class of the data in the
//            RDATA field.
//    TTL             a 32 bit unsigned integer that specifies the time
//    interval (in seconds) that the resource record may be
//    cached before it should be discarded.  Zero values are
//    interpreted to mean that the RR can only be used for the
//    transaction in progress, and should not be cached.

    public DNSRecord(){
    }

    public static DNSRecord decodeRecord(InputStream inputStream, DNSMessage message) throws IOException {

        //TODO CHECK FOR COMPRESSION --> if compressed call read domain name with the int location 4.1.4 compression in document
        DNSRecord record = new DNSRecord();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        //check for compression here
        //if compressed, pull out everything else
        //then can pass in compressed thing as an int
        //if it isn't compressed, then we reset and use the other domain name function call (without int)
        //then read in everything else regarding which if/else (with both conditions)

        // Prepare to mark the stream before reading (for potential reset)
        dataInputStream.mark(2); // Marks the current position in the stream
        short firstTwoBytes = dataInputStream.readShort();
        dataInputStream.reset(); // Resets the stream to the most recent mark

        // Check if the first two bytes indicate a pointer (compression)
        if ((firstTwoBytes & 0xC000) == 0xC000) { // Compression flag is set
            int offset = firstTwoBytes & 0x3FFF; // Extract the offset from the pointer
            // Assuming DNSMessage has a method to handle reading a domain name from a specific byte offset
            record.name = message.readDomainName(offset);
        } else {
            // Normal domain name reading without compression
            record.name = message.readDomainName(inputStream);
        }

        record.type = dataInputStream.readShort();
        record.rclass = dataInputStream.readShort();
        record.ttl = dataInputStream.readInt();
        record.rdLength = dataInputStream.readShort(); //tells us the length of RD field
        record.rdata = new byte[record.rdLength];
        dataInputStream.readFully(record.rdata);
        record.creationDate = new Date();
        return record;
    }

    public void writeBytes(ByteArrayOutputStream outputStream, HashMap<String,Integer> domainNameLocations)throws IOException{
        DNSMessage.writeDomainName(outputStream, domainNameLocations , name);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        // Write domain name bytes
        // Write type (2 bytes)
        dataOutputStream.writeShort(type);
        // Write class (2 bytes)
        dataOutputStream.writeShort(rclass);
        // Write time to live (4 bytes)
        dataOutputStream.writeInt( ttl);
        // Write length of the RDATA field (2 bytes)
        dataOutputStream.writeShort(rdLength);
        // Write RDATA field
        dataOutputStream.write(rdata);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * return whether the creation date + the time to live is after the current time.
     * The Date and Calendar classes will be useful for this.
     * @return
     */
    boolean isExpired(){
        //get date right now
        Date now = new Date();
        long currentTime = now.getTime();
        //ttl is time to live, so if the time it was made plus the amount of time it has to live
        long expirationTime = creationDate.getTime() + (ttl*1000);
        //if the current time is greater than the total time to live, then it is expired
        return currentTime > expirationTime;
    }
}
