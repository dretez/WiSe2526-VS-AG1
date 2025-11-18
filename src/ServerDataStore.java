import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


public class Server implements DataStore {
    private ServerSocket serverSocket;

    private final Map<Integer,String> store = new HashMap<>();

    @Override
    public void write(int index, String data) {
        store.put(index, data);
    }

    @Override
    public String read(int index) throws NoSuchElementException {
        if(store.containsKey(index))
            return store.get(index);
        throw new NoSuchElementException("No value at index " + index);
    }


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true)
            new ClientHandler(serverSocket.accept()).start();
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(3000);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
            ) {
                for (String received = in.readLine(); received != null; received = in.readLine()) {
                    
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
