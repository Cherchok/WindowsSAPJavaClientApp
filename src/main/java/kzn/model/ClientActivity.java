package kzn.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import java.util.ArrayList;

public class ClientActivity {
    private static String serverIP = "192.168.0.13:8080";

    public static Connection connection;

    public static void main( String[] args )
    {
        connection = new Connection(serverIP);
        if (connection.tryConnect()) {
            System.out.println("Yes");
        }
        else {
            System.out.println("No");
        }
    }

    public static ArrayList<Mapa> deserialize(JSONArray response) {
        return (new Gson()).fromJson(response.toString(),
                new TypeToken<ArrayList<Mapa>>() {
                }.getType());
    }
}
