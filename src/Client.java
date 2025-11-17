import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Client implements DataStore{
    private int port=3000;
    private final String DEFAULT_HOST = "localhost";
    private String host=DEFAULT_HOST;
    private int nextId = 1;

    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void write(int index, String data) {
        int id = nextId++;
        StringBuilder sb=new StringBuilder();
        sb.append("{\n");
        sb.append("\"id\":"+id+",\n");
        sb.append("\"method\":\"write\",\n");
        sb.append("\"index\":"+index+",\n");
        sb.append("\"params\": [\n");
        sb.append("\""+data+"\"\n");
        sb.append("]\n}\n");
        String request = sb.toString();
        try {
            sendRequest(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read(int index) throws NoSuchElementException {
        int id = nextId++;
        StringBuilder sb=new StringBuilder();
        sb.append("{\n");
        sb.append("\"id\":"+id+",\n");
        sb.append("\"method\":\"read\",\n");
        sb.append("\"index\":"+index+"\n");
        sb.append("}\n");
        String request = sb.toString();
        try {
            return sendRequest(request);
        } catch (IOException e) {
            return null;
        }
    }

    private String sendRequest(String requestJson) throws IOException {
        try (Socket clientSocket = new Socket(host, port);
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"))
        ) {

            out.write(requestJson);
            out.flush();
            clientSocket.shutdownOutput();

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();

        } catch (IOException e) {
            throw new RuntimeException("Comunication error", e);
        }

    }


}
