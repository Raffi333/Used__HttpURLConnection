package rh.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Send_Http_Request_Params__Text implements ConfData {


    public static void start() throws IOException {
        long start_app = System.currentTimeMillis();

        //create HttpURLConnection for connect
        URL url = new URL(HTTP_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(METHOD);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, CHARSET), true)
        ) {

            //send params
            Map<String, String> params = new HashMap<String, String>() {{
                put("name", "John");
                put("surname", "Smith");
                put("age", "33");
            }};
            params.forEach((name, value) ->
                    writer.append("--")
                            .append(BOUNDARY).append(CRLF)
                            .append("Content-Disposition: form-data; name=").append(name).append(CRLF)
                            .append("Content-Type: text/plain; charset=" + CHARSET).append(CRLF)
                            .append(CRLF)
                            .append(value).append(CRLF).flush());

            // End of multipart/form-data.
            writer.append("--" + BOUNDARY + "--").append(CRLF).flush();
        }

        // Request is lazily fired whenever you need to obtain information about response.
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code = " + responseCode); // Should be 200

        System.out.println("This method worked in " + (System.currentTimeMillis() - start_app) + "Ms");
    }


}
