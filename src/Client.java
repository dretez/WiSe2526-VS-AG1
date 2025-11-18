import java.io.*;
import java.util.NoSuchElementException;

public class Client implements DataStore{
    private static final int DEFAULT_PORT = 3000;
    private static final String DEFAULT_HOST = "127.0.0.1";

    private final int port;
    private final String host;
    private int nextId = 1;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public Client() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    @Override
    public void write(int index, String data) {
        int id = nextId++;
        String request = "{\n" +
                "\"id\":" + id + ",\n" +
                "\"method\":\"write\",\n" +
                "\"index\":" + index + ",\n" +
                "\"data\":" + data + ",\n" +
                "}\n";
        try {
            RPC.sendRequest(this.host, this.port, request);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String read(int index) throws NoSuchElementException {
        int id = nextId++;
        String request = "{\n" +
                "\"id\":" + id + ",\n" +
                "\"method\":\"read\",\n" +
                "\"index\":" + index + "\n" +
                "}\n";
        try {
            RPC.sendRequest(this.host, this.port, request);
            String response = RPC.awaitReply(this.host, this.port);
            var reader = JSONReader.fromString(response);
            if (!(boolean)reader.get("success"))
                if ("NoSuchElementException".equals(reader.get("exception.type")))
                    throw new NoSuchElementException((String) reader.get("exception.message"));
                else {
                    System.err.println("Unexpected exception received: " + reader.get("exception.type"));
                    System.err.println((String) reader.get("exception.message"));
                }
            return (String) reader.get("return");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
