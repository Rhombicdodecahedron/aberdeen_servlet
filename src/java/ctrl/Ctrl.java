package ctrl;

import beans.JSONStatham;
import beans.PacketTCP;
import beans.Utilisateur;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enumeration.Ordres;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;
import static wrk.Constante.*;
import wrk.Wrk;

/**
 *
 * @author StellaA
 */
public class Ctrl {

    private final Wrk refWrk;
    private final HttpServletResponse response;
    private final PrintWriter out;

    private PacketTCP pktClient;

    private final Gson builder;

    public Ctrl(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        this.out = out;
        this.response = response;
        refWrk = new Wrk(this, request);
        builder = new GsonBuilder().create();
        pktClient = new PacketTCP();
    }

    public void connectUser(String username, String password) {
        if (!refWrk.isUserConnected()) {
            try {
                Utilisateur utilisateur = refWrk.connectUser(username, password);
                if (utilisateur != null && utilisateur.isIsUsernamePasswordCorrect()) {
                    if (refWrk.createSession(utilisateur)) {
                        refWrk.connectToWorker(IP_ROBOT);
                        out.println(new JSONStatham(LOGIN_SUCCESS, Ordres.SUCCESS));
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        out.println(new JSONStatham(ERROR_ACTION, Ordres.ERROR));
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    out.println(new JSONStatham(BAD_PASSWORD, Ordres.ERROR));
                }
            } catch (ClientErrorException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(new JSONStatham(ERROR_ACTION + " : " + e.getMessage(), Ordres.ERROR));
            }
        } else {
            out.println(new JSONStatham(ALREADY_CONNECTED, Ordres.ERROR));
        }

    }

    public void deconnexionUtilisateur() {
        if (refWrk.isUserConnected()) {
            refWrk.disconnectToWorker();
            if (refWrk.destroySession()) {
                out.println(new JSONStatham(LOGOUT_SUCCESS, Ordres.SUCCESS));
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println(new JSONStatham(ERROR_ACTION, Ordres.ERROR));
            }
        } else {
            out.println(new JSONStatham(NEED_LOGIN, Ordres.ERROR));
        }
    }

    public void getSession() {
        Utilisateur utilisateur = null;
        if (refWrk.isUserConnected()) {
            HttpSession session = refWrk.getSession();
            utilisateur = new Utilisateur((String) session.getAttribute(SESSION_USERNAME), (String) session.getAttribute("password"));
            utilisateur.setIsUsernamePasswordCorrect(true);
            
            //utilisateur.setDate_creation((session.getAttribute("date_creation")));    
        }

        out.println(new JSONStatham(builder.toJson(utilisateur), Ordres.SUCCESS));
    }

    public void demarrerRobot() {
        sendPacket(Ordres.DEMARRAGE_ROBOT);
    }

    public void arretRobot() {
        sendPacket(Ordres.ARRET_ROBOT);
    }

    public void dockRobot() {
        sendPacket(Ordres.DOCK);
    }

    public void undockRobot() {
        sendPacket(Ordres.UNDOCK);
    }

    public void bougerRobot() {
        sendPacket(Ordres.BOUGER_ROBOT);
    }

    private void sendPacket(Ordres ordre) {
        if (refWrk.isUserConnected()) {
            refWrk.connectToWorker(IP_ROBOT);
            PacketTCP pkt = new PacketTCP();
            pkt.setOrdre(ordre);
            refWrk.envoiePaquet(pkt);
            pktClient = refWrk.getWorkerMessage();
            out.println(new JSONStatham(pktClient.getTexte(), pktClient.getOrdre()));
        } else {
            out.println(new JSONStatham(NEED_LOGIN, Ordres.ERROR));
        }
    }

}
