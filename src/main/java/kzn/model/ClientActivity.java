package kzn.model;

import java.util.ArrayList;

public class ClientActivity {
    private static String serverIP = "192.168.0.13:8080";

    @SuppressWarnings("WeakerAccess")
    public static Connection connection;

    public static void main( String[] args )
    {
        connection = new Connection(serverIP);
        if (connection.tryConnect()) {
            System.out.println("Yes");
            ArrayList<Mapa> systems = connection.getConnections();
            for (Mapa sys : systems) {
                System.out.println(sys.getName() + "  " + sys.getValues());
            }
        }
        else {
            System.out.println("No");
        }
    }

}
