import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.connect();
            client.write(10,"hallo welt");
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}