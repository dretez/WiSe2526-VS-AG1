import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Client implements DataStore{
    private static final int DEFAULT_PORT = 3000;
    private static final String DEFAULT_HOST = "127.0.0.1";

    private int port;
    private String host;
    private int nextId = 1;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.clientSocket = new Socket(host, port);
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    public void connect() throws IOException {
        connect(DEFAULT_HOST, DEFAULT_PORT);
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

    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
