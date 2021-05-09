package beans;

import enumeration.Ordres;

/**
 * Bean JSONStatham
 * Ce bean contient contient un message et un ordre. Il va permet via la méthode toString, d'afficher les informations sous forme de JSON.
 *
 * @author StellaA
 * @version 1.0
 * @project Aberdeen module 133
 * @since 06.05.2021
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


/*
    @Override
    public String toString() {
        return "{" +
                "message:'" + message +
                ", ordre:" + ordre +
                '}';
    } */
}
