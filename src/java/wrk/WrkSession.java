package wrk;

import beans.Utilisateur;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static wrk.Constante.*;

/**
 *
 * @author StellaA
 */
public class WrkSession {

    public WrkSession() {

    }

    public HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
        //return request.getSession(false);
    }

    public boolean createSession(HttpServletRequest request, Utilisateur utilisateur) {
        boolean result = false;
        if (utilisateur != null) {

            String username = utilisateur.getUsername();
            //String date_creation = utilisateur.getDate_creation().toString();
            String password = utilisateur.getPassword();

            if (username != null && !username.isEmpty() && password != null && !password.isEmpty() /*&& date_creation != null && !date_creation.isEmpty()*/) {
                try {
                    getSession(request).setAttribute(SESSION_USERNAME, username);
                     getSession(request).setAttribute("password", password);
                  //  getSession(request).setAttribute("date_creation", date_creation);
                    result = true;
                } catch (Exception e) {

                }
            }
        }

        return result;
    }

    public boolean destroySession(HttpServletRequest request) {
        boolean result = false;
        try {
            HttpSession session = getSession(request);

            if (session != null) {
                session.removeAttribute(SESSION_USERNAME);
                session.removeAttribute("password");
                session.removeAttribute("isUsernamePasswordCorrect");
                result = true;
            }
            //getSession(request).invalidate();
            //request.removeAttribute("tcp_object");
        } catch (Exception e) {
        }
        return result;
    }

    public boolean isUserConnected(HttpServletRequest request) {
        boolean result = false;

        HttpSession session = request.getSession(false);

        if (session != null) {
            if (/*session != null*/session.getAttribute(SESSION_USERNAME) != null) {
                result = true;
            }
        }

        return result;
    }

}
