package beans;

import java.sql.Date;

/**
 * Bean Utilisateur
 *
 * Ce bean contient les informations de l'utilisateur (Nom d'utilisateur et mot de passe). Un paramètre
 * isUsernamePasswordCorrect est présent pour indiquer si le mot de passe et le username de cet utilisateur sont
 * corrects ensemble.
 *
 * @author StellaA
 * @version 1.0
 * @project Aberdeen module 133
 * @since 06.05.2021
 */

public class Utilisateur {

    private String username;
    private String password;
   // private Date date_creation;
    private boolean isUsernamePasswordCorrect;

    public Utilisateur(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
/*
    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }
*/
    public void setIsUsernamePasswordCorrect(boolean isUsernamePasswordCorrect) {
        this.isUsernamePasswordCorrect = isUsernamePasswordCorrect;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
/*
    public Date getDate_creation() {
        return date_creation;
    }
*/
    public boolean isIsUsernamePasswordCorrect() {
        return isUsernamePasswordCorrect;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "username=" + username + ", password=" + password +  "isUsernamePasswordCorrect=" + isUsernamePasswordCorrect + '}';
    }
    

}
