package kzn.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import kzn.properties.MyPropertiesHolder;

import java.io.IOException;
import java.util.ArrayList;

//Осуществление всей работы с сетью
public class Connection {

    //Хранение IP-адреса сервера во внешнем файле
    public MyPropertiesHolder ipHolder;

    private Client client;

    //Статус последнего осуществленного запроса
    private ConnectionStatus status;

    @SuppressWarnings("WeakerAccess")
    public Connection() throws IOException {
        //Сохранение IP
        try {
            ipHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (Exception ex) {
            ipHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_CREATE);
            ipHolder.setProperty("ip", "192.168.0.13:8080");
            ipHolder.commit();
        }
        client = new Client();
    }

    public void setServerIP(String newIP) {
        ipHolder.setProperty("ip", newIP);
    }

    //Попытка отправить тестовый запрос на сервер
    public boolean tryConnect() {
        try {
            String serverIP = ipHolder.getProperty("ip");

            WebResource webResource = client.resource("http://" + serverIP + "/rest/rest/wmap/test");

            client.setConnectTimeout(5000);

            ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

            String responsestr = response.getEntity(String.class);
            System.out.println(responsestr);
            // Status 200 is successful.
            if (response.getStatus() != 200) {
                status = ConnectionStatus.IP_ERROR;
                System.out.println("Failed with HTTP Error code: " + response.getStatus());
                String error= response.getEntity(String.class);
                System.out.println("Error: "+error);
                return false;

            }
            status = ConnectionStatus.SUCCESS;

            return true;
        } catch (ClientHandlerException ex) {
            status = ConnectionStatus.IP_ERROR;
            //ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
    }

    //Получение списка доступных систем
    public ArrayList<Mapa> getSystemsList() {
        WebResource webResource = client.resource("http://" + ipHolder.getProperty("ip") + "/rest/rest/wmap/connection");

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        // Status 200 is successful.
        if (response.getStatus() != 200) {
            status = ConnectionStatus.IP_ERROR;
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getEntity(String.class);
            System.out.println("Error: "+error);
            return null;
        }
        status = ConnectionStatus.SUCCESS;
        ArrayList<Mapa> sapDataList = deserialize(response);

        return sapDataList;
    }

    public ArrayList<Mapa> tryLogin(String system, String username, String password, String language) {
        WebResource webResource = client.resource("http://" + ipHolder.getProperty("ip") +
                "/rest/rest/wmap/" + system + "/" + username + "/" + password + "/" + language);

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            status = ConnectionStatus.IP_ERROR;
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getEntity(String.class);
            System.out.println("Error: "+error);
            return null;
        }
        status = ConnectionStatus.SUCCESS;
        ArrayList<Mapa> sapDataList = deserialize(response);

        return sapDataList;
    }

    //Преобразование ответа от сервера в ArrayList<Mapa>
    public static ArrayList<Mapa> deserialize(ClientResponse response) {
        return (new Gson()).fromJson(response.getEntity(String.class),
                new TypeToken<ArrayList<Mapa>>() {}.getType());
    }

    //Получение статуса последнего запроса
    public ConnectionStatus getStatus() { return status; }

    public static enum ConnectionStatus {
        SUCCESS,
        NETWORK_ERROR,
        IP_ERROR
    }
}
