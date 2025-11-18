import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RPC {
    private RPC() {}

    public static void sendRequest(String host, int port, String json) throws IOException {
        try (Socket clientSocket = new Socket(host, port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {
            out.write(json);
            out.flush();
        }
    }

    public static String awaitReply(String host, int port) throws IOException {
        try (Socket clientSocket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            for (var line = in.readLine(); line != null; line = in.readLine())
                sb.append(line);
            return sb.toString();
        }
    }
}
