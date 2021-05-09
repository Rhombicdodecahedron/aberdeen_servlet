package wrk;

import beans.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:DBConnection [methods]<br>
 * USAGE:
 * <pre>
 *        RandomREST client = new RandomREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author stellaa
 */
public class WrkREST {

    /**
     *
     */
    private final Client client;
    /**
     *
     */
    private static final String BASE_URI = "http://stellaa.emf-informatique.ch/java_AberdeenEtatMajor/webresources/ABERDEEN";
    //private static final String BASE_URI = "http://localhost:8084/EtatMajorAberdeen/webresources/ABERDEEN";

    /**
     *
     */
    public WrkREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
    }

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws ClientErrorException
     */
    public Utilisateur getAppendConnectUser(String username, String password) throws ClientErrorException {
        WebTarget resource = client.target(BASE_URI).path("connectUser/" + username + "/" + password);
        String test = resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        Gson builder = new GsonBuilder().create();
        Utilisateur utilisateur = builder.fromJson(test, Utilisateur.class);
        return utilisateur;
    }

}
