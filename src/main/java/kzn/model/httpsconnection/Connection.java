package kzn.model.httpsconnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import kzn.model.Mapa;
import kzn.properties.MyPropertiesHolder;

//Осуществление всей работы с сетью
public class Connection {

    //Хранение IP-адреса сервера во внешнем файле
    public MyPropertiesHolder ipHolder;

    private Client client;

    //Статус последнего осуществленного запроса
    private ConnectionStatus status;

    @SuppressWarnings("WeakerAccess")
    public Connection() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        //Сохранение IP
        try {
            ipHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_UPDATE);
        } catch (Exception ex) {
            ipHolder = new MyPropertiesHolder("settings.properties", MyPropertiesHolder.MODE_CREATE);
            ipHolder.setProperty("ip", "192.168.0.13:8443");
            ipHolder.commit();
        }

        final ClientConfig config = new DefaultClientConfig();
        config.getProperties()
                .put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES,
                        new HTTPSProperties(
                                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER,
                                SSLUtil.getInsecureSSLContext()));
        client = Client.create(config);

    }

    public void setServerIP(String newIP) throws IOException {
        ipHolder.setProperty("ip", newIP);
        ipHolder.commit();
    }

    //Попытка отправить тестовый запрос на сервер
    public boolean tryConnect() {
        try {
            String serverIP = ipHolder.getProperty("ip");

            WebResource webResource = client.resource("https://" + serverIP + "/rest/rest/wmap/test");

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
    @SuppressWarnings("Duplicates")
    public LinkedHashMap<String, LinkedList<String>> getSystemsList() {
        WebResource webResource = client.resource("https://" + ipHolder.getProperty("ip") + "/rest/rest/wmap/connection");

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        // Status 200 is successful.
        if (response.getStatus() != 200) {
            status = ConnectionStatus.IP_ERROR;
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            //String error= response.getEntity(String.class);
            //System.out.println("Error: "+error);
            return null;
        }
        status = ConnectionStatus.SUCCESS;
        LinkedHashMap<String, LinkedList<String>> sapData = deserialize(response);

        return sapData;
    }

    //Попытка войти в систему
    @SuppressWarnings("Duplicates")
    public LinkedHashMap<String, LinkedList<String>> tryLogin(String system, String username, String password, String language) {

        WebResource webResource = client.resource("https://" + ipHolder.getProperty("ip") +
                "/rest/rest/wmap/" + system + "/" + username + "/" + password + "/" + language);

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            status = ConnectionStatus.IP_ERROR;
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error = response.getEntity(String.class);
            System.out.println("Error: "+error);
            return null;
        }
        status = ConnectionStatus.SUCCESS;

        LinkedHashMap<String, LinkedList<String>> sapData = deserialize(response);

        return sapData;
    }

    @SuppressWarnings("Duplicates")
    public LinkedHashMap<String, LinkedList<String>> getDataSet(String system, String username, String password, String clientID,
                                                                String table, String fieldsQuan, String language,
                                                                String where, String order, String group, String fieldNames) {
        WebResource webResource = client.resource("https://" + ipHolder.getProperty("ip") +
                "/rest/rest/wmap/" + system + "/" + username + "/" + password + "/" +
                clientID + "/" + table + "/" + fieldsQuan + "/" + language + "/"
                + where + "/" + order + "/" + group + "/" + fieldNames);

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            status = ConnectionStatus.IP_ERROR;
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error = response.getEntity(String.class);
            System.out.println("Error: "+error);
            return null;
        }
        status = ConnectionStatus.SUCCESS;

        LinkedHashMap<String, LinkedList<String>> sapData = deserialize(response);

        return sapData;
    }

    //Преобразование ответа от сервера в LinkedHashMap
    public static LinkedHashMap<String, LinkedList<String>> deserialize(ClientResponse response) {
        ArrayList<Mapa> sapDataList = (new Gson()).fromJson(response.getEntity(String.class),
                new TypeToken<ArrayList<Mapa>>() {}.getType());
        LinkedHashMap<String, LinkedList<String>> sapData = new LinkedHashMap<>();
        for (Mapa mapa : sapDataList) {
            sapData.put(mapa.getName(), mapa.getValues());
        }
        return sapData;
    }

    //Получение статуса последнего запроса
    public ConnectionStatus getStatus() { return status; }

    public static enum ConnectionStatus {
        SUCCESS,
        NETWORK_ERROR,
        IP_ERROR
    }

    private static class SSLUtil {
        protected static SSLContext getInsecureSSLContext()
                throws KeyManagementException, NoSuchAlgorithmException {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the certificate
                        }

                        public void checkServerTrusted(
                                final java.security.cert.X509Certificate[] arg0, final String arg1)
                                throws CertificateException {
                            // do nothing and blindly accept the server
                        }

                    }
            };

            final SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            return sslcontext;
        }
    }
}
