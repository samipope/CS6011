package DNSResolver;
import java.io.*;

/*                                            1  1  1  1  1  1
                0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |                      ID                       |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |QR|   OpCode  |AA|TC|RD|RA| Z|AD|CD|   RCODE   |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |                QDCOUNT/ZOCOUNT                |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |                ANCOUNT/PRCOUNT                |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |                NSCOUNT/UPCOUNT                |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
               |                    ARCOUNT                    |
               +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+

 */

//This class should store all the data provided by the 12 byte DNS header. See the spec for all the fields needed.
//include getters, but NO setters
//test that you can read/decode the header before starting other classes
public class DNSHeader {

    //DNS header is 12 bytes
    // 8 bits make a BYTE
    // 2 bytes are a short (16 bits)

    //Header Parts:
    private short ID; // ID: 16 bits make up the ID (USE READ SHORT)
    private int QR;     // QR: one bit --> whether a query or response
    private int OPCODE;  // OPCODE: 4 bits --> specifies type of query
    private int AA;  // AA (Authoritative Answer) --> one bit is valid in responses
    private int TC;  //TC (TrunCation) --> specifies that this message was truncated due to being too long to send in just 1 packet
    private int RD; //RD (Recursion Desired --> one bit
    private int RA;  // RA Recursion available --> one bit
    private int Z;  // Z --> must be 0 in all one bit
    private int AD;
    private int CD;
    private short flags;
    private int RCODE;  // RCODE --> 4 bit field -- tells us the result of the query ( error or not )
    private short qdCount; //QDCOUNT --> unsigned specifying # of entries in the QUESTION section- 16 bits (read short)
    private short anCount; //ANCOUNT --> unsigned specifying # of resource records in the ANSWER section - 16 bits (read short)
    private short nsCount; //NSCOUNT --> unsigned specifying # of name of server resource records in AUTHORITY RECORDS section - 16 bits (read short)
    private short arCount; //ARCOUNT --> unsigned specifying # of resource records in the ADDITIONAL records section - 16 bits (read short)


    public DNSHeader(){
        ID=0; // ID: 16 bits make up the ID (USE READ SHORT)
        QR=0;     // QR: one bit --> whether a query or response
        OPCODE=0;  // OPCODE: 4 bits --> specifies type of query
        AA=0;  // AA (Authoritative Answer) --> one bit is valid in responses
        TC=0;  //TC (TrunCation) --> specifies that this message was truncated due to being too long to send in just 1 packet
        RD=0; //RD (Recursion Desired --> one bit
        RA=0;  // RA Recursion available --> one bit
        Z=0;  // Z --> must be 0 in all one bit
        AD=0;
        CD=0;
        RCODE=0;  // RCODE --> 4 bit field -- tells us the result of the query ( error or not )
        qdCount=0; //QDCOUNT --> unsigned specifying # of entries in the QUESTION section- 16 bits (read short)
        anCount=0; //ANCOUNT --> unsigned specifying # of resource records in the ANSWER section - 16 bits (read short)
        nsCount=0; //NSCOUNT --> unsigned specifying # of name of server resource records in AUTHORITY RECORDS section - 16 bits (read short)
        arCount=0; //ARCOUNT --> unsigned specifying # of resource records in the ADDITIONAL records section - 16 bits (read short)

        //constructor is empty it will initialize them to 0 ?? maybe initialize to null to avoid issues?
    }

    //all my getters needed for other classes/methods
public short getQdCount(){return qdCount;}
    public short getAnCount() {return anCount;}
    public short getNsCount(){
      return nsCount;
    }
    public short getArCount() {
        return arCount;
    }
    public int getQR() {
        return QR;
    }

    //read the header from an input stream (we'll use a ByteArrayInputStream but we will only use the
// basic read methods of input stream to read 1 byte, or to fill in a byte array, so we'll be generic).

    /**
     * Read the header from an input stream (we'll use a ByteArrayInputStream but we will only use the basic read methods of input stream to read 1 byte, or to fill in a byte array, so we'll be generic).
     * @return
     */
    static DNSHeader decodeHeader(ByteArrayInputStream inStream) throws IOException {
        DNSHeader header = new DNSHeader();
        DataInputStream dataInputStream = new DataInputStream(inStream);

        // ID: 16 bits make up the ID
        header.ID = dataInputStream.readShort();
        System.out.println(header.ID);

        // Read next two bytes for flags
        int flags = dataInputStream.readShort();
        // QR: one bit --> whether a query or response
        header.QR = ((flags >> 15) & 0x1);
        // OPCODE: 4 bits --> specifies type of query
        header.OPCODE = (flags >> 11) & 0xF;
        // AA (Authoritative Answer) --> one bit is valid in responses
        header.AA = ((flags >> 10) & 0x1);
        // TC (TrunCation) --> specifies that this message was truncated due to being too long
        header.TC = ((flags >> 9) & 0x1);
        // RD (Recursion Desired --> one bit
        header.RD = ((flags >> 8) & 0x1);
        // RA Recursion available --> one bit
        header.RA = ((flags >> 7) & 0x1);
        // Z --> must be 0 in all one bit
        header.Z = (flags >> 6) & 0x1;
        //AD --> one bit
        header.AD = (flags >> 5) & 0x1;
        //CD--> one bit
        header.CD = (flags >> 4) & 0x1;
        // RCODE --> 4 bit field
        header.RCODE = flags & 0xF;

        // QDCOUNT, ANCOUNT, NSCOUNT, ARCOUNT --> unsigned 16 bits
        header.qdCount = dataInputStream.readShort();
        header.anCount = dataInputStream.readShort();
        header.nsCount = dataInputStream.readShort();
        header.arCount = dataInputStream.readShort();

        return header;
    }

    /**
     * This will create the header for the response. It will copy some fields from the request
     * @param request
     * @param response
     * @return
     */
    static DNSHeader buildHeaderForResponse(DNSMessage request, DNSMessage response){
        DNSHeader builtHeader = request.getHeader();


        builtHeader.anCount=1;
        builtHeader.QR=1;
        return builtHeader;
    }

    /**
     *
     * WE WILL CALL THIS IN MAIN
     * encode the header to bytes to be sent back to the client.
     * The OutputStream interface has methods to write a single byte or an array of bytes.
     * @param outStream
     */
    void writeBytes(OutputStream outStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outStream);
        dataOutputStream.writeShort(ID);
        int flags = 0;
        flags |= (QR << 15);
        flags |= (OPCODE << 11);
        flags |= (AA << 10);
        flags |= (TC << 9);
        flags |= (RD << 8);
        flags |= (RA << 7);
        flags |= (Z<<6);
        flags |= (AD <<5);
        flags |= (CD <<4);
        flags |= (RCODE);
        dataOutputStream.writeShort(flags);
        dataOutputStream.writeShort(qdCount);
        dataOutputStream.writeShort(anCount);
        dataOutputStream.writeShort(nsCount);
        dataOutputStream.writeShort(arCount);
        dataOutputStream.flush();
    }


    /**
     *
     * @return humanly readable string version of a header object
     * reasonable implementation can be autogenerated from this IDE!
     */
    @Override
    public String toString(){
        return super.toString();
    }

}
