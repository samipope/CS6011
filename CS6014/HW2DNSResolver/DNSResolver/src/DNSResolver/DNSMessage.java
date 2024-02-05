package DNSResolver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DNSMessage {
    //---------------- 4.1. Format ---------------------------------------
//    All communications inside the domain protocol are carried in a single
//    format called a message.  The top level format of message is divided
//    into 5 sections (some of which are empty in certain cases) shown below:
//
//            +---------------------+
//            |        Header       |
//            +---------------------+
//            |       Question      | the question for the name server
//            +---------------------+
//            |        Answer       | RRs answering the question
//            +---------------------+
//            |      Authority      | RRs pointing toward an authority
//            +---------------------+
//            |      Additional     | RRs holding additional information
//             +---------------------+
//
//    The header section is always present.  The header includes fields that
//    specify which of the remaining sections are present, and also specify
//    whether the message is a query or a response, a standard query or some
//    other opcode, etc.


    DNSHeader header;
    DNSQuestion[] questions;
    DNSRecord[] answers;
    DNSRecord[] authorityRecords; //we will ignore this
    DNSRecord[] additionalRecords; //we'll "almost" ignore ??
    byte[] completeMessage; //You'll need it to handle the compression technique described above

    static HashMap<String,Integer> domainLocations;

    public DNSMessage(){
        //empty constructor
    }


    static DNSMessage decodeMessage(byte[] bytes) throws IOException {
        DNSMessage dnsMessage = new DNSMessage();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        // store the byte array containing the complete message
        dnsMessage.completeMessage = bytes;

        dnsMessage.header = DNSHeader.decodeHeader(byteArrayInputStream);

        // read questions
        dnsMessage.questions = new DNSQuestion[dnsMessage.header.getQdCount()];
        readQuestions(byteArrayInputStream, dnsMessage);

        //if answers is empty - it could be a bad response
        // Read answers
        dnsMessage.answers = new DNSRecord[dnsMessage.header.getAnCount()];
        readRecords(byteArrayInputStream, dnsMessage.answers, dnsMessage.header.getAnCount(), dnsMessage);

        // read authority records
        dnsMessage.authorityRecords = new DNSRecord[dnsMessage.header.getNsCount()];
        readRecords(byteArrayInputStream, dnsMessage.authorityRecords, dnsMessage.header.getNsCount(), dnsMessage);

        // read additional records
        dnsMessage.additionalRecords = new DNSRecord[dnsMessage.header.getArCount()];
        readRecords(byteArrayInputStream, dnsMessage.additionalRecords, dnsMessage.header.getArCount(), dnsMessage);

        return dnsMessage;
    }

    /**
     * helper method to read in the questions section
     * @param byteArrayInputStream
     * @param dnsMessage
     * @throws IOException
     */
    private static void readQuestions(ByteArrayInputStream byteArrayInputStream, DNSMessage dnsMessage) throws IOException {
        int questionCount = dnsMessage.header.getQdCount();
        for (int i = 0; i < questionCount; i++) {
            DNSQuestion question = DNSQuestion.decodeQuestion(byteArrayInputStream, dnsMessage);
            dnsMessage.questions[i]=question;
        }
    }

    /**
     * helper method for answer, authority, additional read in
     * @param byteArrayInputStream
     * @param records
     * @param count
     * @param dnsMessage
     * @throws IOException
     */
    private static void readRecords(ByteArrayInputStream byteArrayInputStream, DNSRecord[] records, int count, DNSMessage dnsMessage) throws IOException {
        for (byte i = 0; i < count; i++) {
            DNSRecord record = DNSRecord.decodeRecord(byteArrayInputStream, dnsMessage);
            records[i]=record;
        }
    }


    /**
     * read the pieces of a domain name starting from the current position of the input stream
     * "Each question contains a domain name encoded in a special format, where each segment of the domain name is preceded by a length byte.
     * For example, "www.example.com" would be encoded as "3www7example3com0"."
     * @param inputStream
     * @return
     */
    String[] readDomainName(InputStream inputStream) throws IOException{
        //DO NOT TOUCH
        List<String> labels = new ArrayList<>();

        DataInputStream stream = new DataInputStream(inputStream);

        byte length = stream.readByte();

        if(length==0){
            return new String[0];
        }

        while(length!=0){
            byte[] buffer;
            buffer = stream.readNBytes(length);
            //int read = inputStream.read(buffer, 0,length);
            String str = new String(buffer, StandardCharsets.UTF_8);
            labels.add(str);
            length = stream.readByte();
        }
        return labels.toArray(new String[0]);

    }

    /**
     * same, but used when there's compression and we need to find the domain from earlier in the message.
     * This method should make a ByteArrayInputStream that starts at the specified byte and call the other version of this method
     * @param firstByte
     * @return
     */
    String[] readDomainName(int firstByte) throws IOException{ //called when compression !! TODO -- call this when compression happening
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(completeMessage,firstByte,completeMessage.length-firstByte);
        return readDomainName(byteArrayInputStream);
    }


    /**
     * build a response based on the request and the answers you intend to send back.
     * byte[] toBytes() -- get the bytes to put in a packet and send back
     * @param request
     * @param answers
     * @return
     */
    static DNSMessage buildResponse(DNSMessage request, DNSRecord[] answers){
        DNSMessage response = new DNSMessage();
        response.header = DNSHeader.buildHeaderForResponse(request,response);
        response.questions = request.questions;
        response.answers = answers;
        response.authorityRecords = request.getAuthorityRecords();
        response.additionalRecords = request.getAdditionalRecords();
        return response;
    }


    /**
     * If this is the first time we've seen this domain name in the packet, write it using the DNS encoding
     * (each segment of the domain prefixed with its length, 0 at the end), and add it to the hash map.
     * Otherwise, write a back pointer to where the domain has been seen previously.
     * @param outputStream
     * @param domainLocations
     */
    static void writeDomainName(ByteArrayOutputStream outputStream, HashMap<String, Integer> domainLocations, String[] domainPieces) throws IOException{

        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String domainName = String.join(".", domainPieces); // Ensure domain name is reconstructed correctly

        if (domainLocations.containsKey(domainName)) {
            // Domain name already written, write a pointer to its location
            int location = domainLocations.get(domainName);
            // Set the two highest bits to 11 to indicate a pointer
            dataOutputStream.writeShort(0xC000 | location);
        } else {
            // Domain name not seen before, write it and update location map
            domainLocations.put(domainName, outputStream.size());
            for (String label : domainPieces) {
                byte[] labelBytes = label.getBytes(StandardCharsets.UTF_8);
                dataOutputStream.writeByte(labelBytes.length);
                dataOutputStream.write(labelBytes);
            }
            // Terminate domain name with a 0-length label
            dataOutputStream.writeByte(0);
        }





    }

    /**
     *join the pieces of a domain name with dots ([ "utah", "edu"] -> "utah.edu" )
     * @param pieces "utah" "edu"
     * @return utah.edu
     */
    static String joinDomainName(String[] pieces){
        return String.join(".",pieces);
    }



    @Override
    public String toString(){
       return super.toString();
    }

    public byte[] toBytes() throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        header.writeBytes(byteArrayOutputStream);

        //initialize domainLocations hashmap
        domainLocations = new HashMap<>();

        for(DNSQuestion question: questions){
            question.writeBytes(byteArrayOutputStream, domainLocations);
        }

        for(DNSRecord answer: answers) {
            answer.writeBytes(byteArrayOutputStream,domainLocations);
        }

        for(DNSRecord ar: authorityRecords){
            ar.writeBytes(byteArrayOutputStream, domainLocations);
        }
        for(DNSRecord ad: additionalRecords){
            ad.writeBytes(byteArrayOutputStream, domainLocations);
        }
        // Implement logic to convert DNSMessage to byte array
        return byteArrayOutputStream.toByteArray();

    }
    public DNSHeader getHeader() {
        return header;
    }

    public DNSQuestion[] getQuestions() {
        return questions;
    }

    public DNSRecord[] getAnswers() {
        return answers;
    }

    public DNSRecord[] getAuthorityRecords() {
        return authorityRecords;
    }

    public DNSRecord[] getAdditionalRecords() {
        return additionalRecords;
    }

    public byte[] getCompleteMessage() {
        return completeMessage;
    }




}
