package helpers;

import beans.PacketTCP;

import java.io.*;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Classe de méthodes statiques permettant la sérialisation et désérialisation
 * d'objet, compression et dépression de tableau de byte.
 *
 * @author Stella Alexis
 * @version 1.0
 * @since 15.01.2021
 */
public class Utils {

    /**
     * Constructeur de la class "Utils"
     */
    public Utils() {
    }

    /**
     * Cette méthode permet de sérialiser l'objet PacketTCP à envoyer au client.
     * Cela permet de faire passer le paquet tcp à travers le tunnel tcp tout en
     * le rendant plus léger.
     *
     * @param packetTCP représente l'objet PacketTCP à envoyer au client.
     * @return un String de la sérialisation de l'objet PacketTCP.
     */
    public static String packetTCPSerialisation(PacketTCP packetTCP) {
        String result = null;
        if (packetTCP != null) {
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(packetTCP);
                result = Base64.getEncoder().encodeToString(baos.toByteArray());
            } catch (IllegalArgumentException | IOException ignored) {
            } finally {
                try {
                    if (oos != null) {
                        oos.close();
                    }
                } catch (IOException e) {
                }
            }

        }
        return result;
    }

    /**
     * Cette méthode permet de désérialiser en objet PacketTCP la sérialisation
     * envoyé par le client tcp.
     *
     * @param serialisation représente la sérialisation envoyé par le client.
     * @return un objet PacketTCP représentant la désérialisation du String.
     */
    public static PacketTCP stringDeserialisation(String serialisation) {
        PacketTCP packetTCP = null;
        if (serialisation != null) {
            ObjectInputStream ois = null;
            try {
                byte[] data = Base64.getDecoder().decode(serialisation);
                ois = new ObjectInputStream(new ByteArrayInputStream(data));
                packetTCP = (PacketTCP) ois.readObject();
            } catch (IllegalArgumentException | IOException | ClassNotFoundException ignored) {

            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return packetTCP;
    }

}
