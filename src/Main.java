public class Main {
    public static void main(String[] args) {
        // local call
        LocalDataStore lds = new LocalDataStore();
        lds.write(1, "Hallo Welt");
        try {
            String val = lds.read(1);
            System.out.println(val);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // remote call
        Client client = new Client();
        try {
            client.connect();

            client.write(1,"Hallo Welt");
            String val = client.read(1);
            System.out.println(val);

            client.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}