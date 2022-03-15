package rh.company;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;

public class Send_Http_Request_Params__File implements ConfData {

    public static void start() throws IOException {
        long start_app = System.currentTimeMillis();

        //create HttpURLConnection for connect
        URL url = new URL(HTTP_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(METHOD);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        File fileForSending = new File("filepath.png");
        String fileMimeType = Files.probeContentType(fileForSending.toPath());

        try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, CHARSET), true)
        ) {

            //send file
            writer.append("--" + BOUNDARY).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileForSending.getName() + "\"").append(CRLF);
            writer.append("Content-Type: " + fileMimeType).append(CRLF); // Text file itself must be saved in this charset!
            writer.append(CRLF).flush();
            Files.copy(fileForSending.toPath(), output);
            output.flush(); // Important before continuing with writer!
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
            //writer.append("Content-Transfer-Encoding: binary").append(CRLF); //if file binary

            // End of multipart/form-data.
            writer.append("--" + BOUNDARY + "--").append(CRLF).flush();
        }

        // Request is lazily fired whenever you need to obtain information about response.
        connection.connect();
        int responseCode = connection.getResponseCode();
        System.out.println("response code = " + responseCode); // Should be 200

        System.out.println("(Send_Http_Request_Params__File) This method worked in " + (System.currentTimeMillis() - start_app) + "Ms");
    }

}
