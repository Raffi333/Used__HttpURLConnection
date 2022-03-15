package rh.company;

public interface ConfData {

    boolean SEND_Http_Request__GetResponse = true;
    boolean SEND_Http_Request_Params__Text = true;
    boolean SEND_Http_Request_Params__File = true;

    String CHARSET = "UTF-8";
    String BOUNDARY = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
    String CRLF = "\r\n"; // Line separator required by multipart/form-data.
    String HTTP_URL = "http://localhost:8080/";
    String METHOD = "POST";

}
