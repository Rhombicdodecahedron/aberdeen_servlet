/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import enumeration.Ordres;

/**
 *
 * @author StellaA
 */
public class JSONStatham {

    private String message;
    private Ordres ordre;

    public JSONStatham(String message, Ordres ordre) {
        this.message = message;
        this.ordre = ordre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLogLevel(Ordres ordre) {
        this.ordre = ordre;
    }

    @Override
    public String toString() {
        return message + "|" + ordre;
    }

}
