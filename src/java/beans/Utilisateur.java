package beans;

import java.sql.Date;

/**
 *
 * @author Alexis
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
