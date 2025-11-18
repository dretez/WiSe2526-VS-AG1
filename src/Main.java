public class Main {
    public static void main(String[] args) {
        long starttime, stoptime;

        // local call
        LocalDataStore lds = new LocalDataStore();
        starttime = System.nanoTime();
        lds.write(1, "Hallo Welt");
        stoptime = System.nanoTime();
        System.out.println("Local write call took " + ((stoptime - starttime) / 10e6) + " ms");
        try {
            starttime = System.nanoTime();
            String val = lds.read(1);
            stoptime = System.nanoTime();
            System.out.println(val);
            System.out.println("Local read call took " + ((stoptime - starttime) / 10e6) + " ms");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        // remote call
        try {
            Client client = new Client("localhost");
            starttime = System.nanoTime();
            client.write(1,"Hallo Welt");
            stoptime = System.nanoTime();
            System.out.println("Remote write call took " + ((stoptime - starttime) / 10e6) + " ms");
            starttime = System.nanoTime();
            String val = client.read(1);
            stoptime = System.nanoTime();
            System.out.println(val);
            System.out.println("Remote read call took " + ((stoptime - starttime) / 10e6) + " ms");
            client.stop();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}