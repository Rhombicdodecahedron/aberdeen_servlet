package wrk;

import beans.PacketTCP;
import beans.Utilisateur;
import ctrl.Ctrl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author stellaa
 */
public class Wrk {

    private TCPClient tcpClient;
    private final WrkSession wrkSession;
    private final WrkREST wrkREST;
    private final Ctrl refCtrl;

    private HttpServletRequest request;

    public Wrk(Ctrl refCtrl, HttpServletRequest request) {
        wrkSession = new WrkSession();
        wrkREST = new WrkREST();

        this.refCtrl = refCtrl;
        this.request = request;
        /*
        tcpClient = new TCPClient(this);
        tcpClient.connectToServer(Constante.IP_ROBOT);
        tcpClient.start();*/
    }

    public void connectToWorker(String ip) {
        if (tcpClient == null) {
            tcpClient = new TCPClient(this);
            tcpClient.connectToServer(Constante.IP_ROBOT);
            tcpClient.start();
        }

    }

    public void disconnectToWorker() {
        try {
            tcpClient.disconnect();
            tcpClient.join();
            tcpClient = null;
        } catch (InterruptedException ex) {
        }
    }

    public Utilisateur connectUser(String username, String password) throws ClientErrorException {
        return wrkREST.getAppendConnectUser(username, password);
    }

    public boolean destroySession() {
        return wrkSession.destroySession(request);
    }

    public boolean createSession(Utilisateur utilisateur) {
        return wrkSession.createSession(request, utilisateur);
    }

    public HttpSession getSession() {
        return wrkSession.getSession(request);
    }

    public boolean isUserConnected() {
        return wrkSession.isUserConnected(request);
    }

    public PacketTCP getWorkerMessage() {
        return tcpClient.receiveMessage();
    }

    public void envoiePaquet(PacketTCP pkt) {
        tcpClient.envoiePaquet(pkt);
    }

}
