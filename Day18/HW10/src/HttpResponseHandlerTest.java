import org.junit.jupiter.api.Assertions;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseHandlerTest {

    @org.junit.jupiter.api.Test
    void generateHandshakeString() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String test = "dGhlIHNhbXBsZSBub25jZQ==";
        String result = "s3pPLMBiTxaQ9kYGzzhZRbK+xOo=";
        Assertions.assertEquals(result, HttpResponseHandler.generateHandshakeString(test));

    }
}