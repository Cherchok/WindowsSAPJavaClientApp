package kzn;

//import src.main.java.kzn.Employee;

/*import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;*/


/*//import javax.ws.rs.client.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;*/

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Locale;

public class App
{
    public static void main( String[] args )
    {
        //ClientConfig clientConfig = new DefaultClientConfig();

        // Create Client based on Config
        //Client client = Client.create(clientConfig);

        //Client client = ClientBuilder.newClient();

        /*WebResource webResource = client.resource("http://192.168.0.13:8080/rest/rest/user/1");

        Builder builder = webResource.accept(MediaType.APPLICATION_JSON)
                .header("content-type", MediaType.APPLICATION_JSON);

        ClientResponse response = builder.get(ClientResponse.class);*/

        Client client = Client.create();

        WebResource webResource = client.resource("http://192.168.0.13:8080/rest/rest/wmap/connection");

        //webResource.

        ClientResponse response = webResource.accept("applications/json;charset=utf-8").get(ClientResponse.class);

        // Status 200 is successful.
        if (response.getStatus() != 200) {
            System.out.println("Failed with HTTP Error code: " + response.getStatus());
            String error= response.getEntity(String.class);
            System.out.println("Error: "+error);
            return;
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }
}
