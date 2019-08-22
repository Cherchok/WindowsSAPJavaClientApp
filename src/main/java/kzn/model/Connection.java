package kzn.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.ArrayList;

public class Connection {
    private String serverIP = "192.168.0.13:8080";

    private Client client;

    @SuppressWarnings("WeakerAccess")
    public Connection(String serverIP) {
        client = new Client();
        this.serverIP = serverIP;
    }

    public void setServerIP(String newIP) {
        this.serverIP = newIP;
    }

    public boolean tryConnect() {
        try {


            WebResource webResource = client.resource("http://" + serverIP + "/rest/rest/wmap/test");

            ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

            String responsestr = response.getEntity(String.class);
            System.out.println(responsestr);
            // Status 200 is successful.
            if (response.getStatus() != 200) {
                System.out.println("Failed with HTTP Error code: " + response.getStatus());
                String error= response.getEntity(String.class);
                System.out.println("Error: "+error);
                return false;
            }

            return true;
        } catch (ClientHandlerException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<Mapa> getConnections() {
        WebResource webResource = client.resource("http://" + serverIP + "/rest/rest/wmap/connection");

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        // Status 200 is successful.
        if (response.getStatus() != 200) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getEntity(String.class);
            System.out.println("Error: "+error);
        }


//        ArrayList<Mapa> sapDataList = (new Gson()).fromJson(response.getEntity(String.class),
//                new TypeToken<ArrayList<Mapa>>() {}.getType());

        ArrayList<Mapa> sapDataList = deserialize(response);

//        for (Mapa i : sapDataList) {
//            System.out.println(i.getName() + "  " + i.getValues());
//        }

        return sapDataList;
    }

    public static ArrayList<Mapa> deserialize(ClientResponse response) {
        return (new Gson()).fromJson(response.getEntity(String.class),
                new TypeToken<ArrayList<Mapa>>() {}.getType());
    }
}
