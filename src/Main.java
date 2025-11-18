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
        client.write(1,"Hallo Welt");
        try {
            String val = client.read(1);
            System.out.println(val);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}