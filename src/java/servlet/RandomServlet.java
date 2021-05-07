/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import ctrl.Ctrl;
import enumeration.Ordres;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author stellaa
 */
@WebServlet("/api")
public class RandomServlet extends HttpServlet {

    private Ctrl refCtrl;


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {

            HttpSession session = request.getSession();

            if (session.getAttribute("ctrl") == null) {
                refCtrl = new Ctrl(out, request, response);
                session.setAttribute("ctrl", refCtrl);
            } else {
                refCtrl = (Ctrl) session.getAttribute("ctrl");
            }

            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");

            String action = request.getParameter("action");
            if (action != null && !action.isEmpty()) {
                try {
                    switch (Ordres.valueOf(action)) {
                        case CONNEXION_UTILISATEUR:
                            String username = request.getParameter("username");
                            String password = request.getParameter("password");
                            if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
                                refCtrl.connectUser(username, password);
                            } else {
                                out.println("Des paramètres manquent à l'appelle !");
                            }
                            break;
                        case DEMARRAGE_ROBOT:
                            refCtrl.demarrerRobot();
                            break;
                        case DECONNEXION:
                            refCtrl.deconnexionUtilisateur();
                            break;
                        case CHECK_SESSION:
                            refCtrl.getSession();
                            break;
                        case ARRET_ROBOT:
                            refCtrl.arretRobot();
                            break;
                        case DOCK:
                            refCtrl.dockRobot();
                            break;
                        case UNDOCK:
                            refCtrl.undockRobot();
                            break;
                        case BOUGER_ROBOT:
                            refCtrl.bougerRobot();
                            break;
                    }
                } catch (IllegalArgumentException e) {
                    out.println("Aucune action trouvée !");
                }
            } else {
                out.println("Veuillez spécifier une action !");
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
