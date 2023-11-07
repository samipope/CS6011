import java.io.*;


//put an output stream in the class that replies
//in the return type make a FILE as a variable
//construct the file in it's own constructor and nest that in the original contructor
public class HttpResponseHandler {

    OutputStream output_;
    HttpResponseHandler( OutputStream outStream ){
        output_ = outStream;
    }


    public void printClientFile(File requestedFile, String fileType) throws IOException {
        //using the new http page for the client's file
        FileInputStream fileInputStream = new FileInputStream(requestedFile);
        output_.write("HTTP/1.1 200 OK\n".getBytes());
        output_.write(("Content-Type: " + fileType + "\n").getBytes());
        output_.write("\n".getBytes());  // blank line means end of header
        fileInputStream.transferTo(output_);
        output_.flush();
        output_.close();
    }



}

