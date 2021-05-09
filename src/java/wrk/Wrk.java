package wrk;

import beans.PacketTCP;
import beans.Utilisateur;
import ctrl.Ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;

/**
 * Classe Wrk
 * Cette classe permet de contacter les méthodes des sous-workers du douanier.
 *
 * @author StellaA
 * @version 1.0
 * @project Aberdeen module 133
 * @since 06.05.2021
 */
public class Wrk {

    /**
     * Instance de la classe TCPClient
     */
    private TCPClient tcpClient;
    /**
     * Instance de la classe WrkSession
     */
    private final WrkSession wrkSession;
    /**
     * Instance de la classe WrkREST
     */
    private final WrkREST wrkREST;
    /**
     * Instance de la classe Ctrl
     */
    private final Ctrl refCtrl;

    /**
     * Constructeur de la classe Wrk du douanier. Il défini les instances
     * de wrkSession et wrkREST. Elle défini aussi les paramètres refCtrl et request.
     *
     * @param refCtrl représente la référence de la classe Ctrl qui est le contrôlleur du douanier.
     * @param request représente l'objet de la requête au douanier.
     */
    public Wrk(Ctrl refCtrl, HttpServletRequest request) {
        this.refCtrl = refCtrl;
        wrkSession = new WrkSession(request);
        wrkREST = new WrkREST();
    }


    public void connectToWorker(String ip) {
        if (tcpClient == null) {
            tcpClient = new TCPClient(this);
            tcpClient.connectToServer(Constante.IP_ROBOT);
            tcpClient.start();
        }
    }

    public void disconnectToWorker() throws InterruptedException {
            tcpClient.disconnect();
            tcpClient.join();
            tcpClient = null;
    }

    public Utilisateur connectUser(String username, String password) throws ClientErrorException {
        return wrkREST.getAppendConnectUser(username, password);
    }

    public boolean destroySession() {
        return wrkSession.destroySession();
    }

    public boolean createSession(Utilisateur utilisateur) {
        return wrkSession.createSession(utilisateur);
    }

    public HttpSession getSession(HttpServletRequest request) {
        return wrkSession.getSession(request);
    }

    public boolean isUserConnected() {
        return wrkSession.isUserConnected();
    }

    public PacketTCP getWorkerMessage() {
        return tcpClient.receiveMessage();
    }

    public void envoiePaquet(PacketTCP pkt) {
        tcpClient.envoiePaquet(pkt);
    }

}
