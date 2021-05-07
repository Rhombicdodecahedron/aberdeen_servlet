package wrk;

import beans.PacketTCP;
import helpers.Utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import static wrk.Constante.PORT;

public class TCPClient extends Thread {

    public static final int RECEIVE_RATE = 10;
    private volatile Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private volatile boolean running;
    private Wrk wrk;

    public TCPClient(Wrk wrk) {
        setName("TCP Client Thread");
        this.wrk = wrk;
    }

    /**
     * Checks if a TCP packet is received every RECEIVE_RATE milliseconds. The
     * packet is then added to the ActionList
     */
    @Override
    public void run() {
        running = true;
        while (running) {

            if (socket != null && in != null) {
                if (socket.isConnected() && !(socket.isClosed())) {
                    //   receiveMessage();
                    //System.out.println(in.readLine());
                }
            }
            sleeep(RECEIVE_RATE);
        }
    }

    /**
     * Connect to server with ip address.
     *
     * @param IP the ip address of the server
     * @return true if connected and false if not
     */
    public boolean connectToServer(String IP) {
        boolean result = false;

        try {
            SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(IP), PORT);
            // Create your socket
            socket = new Socket();
            socket.connect(sockaddr, 1000);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            result = true;
        } catch (SocketTimeoutException ex) {
            System.out.println("Erreur dans la connexion à l'ouvrier");
        } catch (IOException exc) {
            result = false;
        }

        return result;
    }

    /**
     * Disconnect client from the server.
     */
    public void disconnect() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }
        socket = null;
        running = false;
    }

    /**
     * Receive message incoming from the server.The message is then added to the
     * action list to be processed later
     *
     * @return
     */
    public PacketTCP receiveMessage() {
        PacketTCP result = null;

        try {
            String msg = in.readLine();
            if (msg != null && running) {
                PacketTCP pkt = Utils.stringDeserialisation(msg);
                //  wrk.sendMessage(pkt);
                result = pkt;
            }
        } catch (SocketTimeoutException ex) {
        } catch (IOException ex) {
        }

        return result;
    }

    /**
     * Cette méthode permet d'envoyer un paquet au client. Elle demande en
     * paramètre l'objet PacketTCP.
     *
     * @param pkt représente l'objet PacketTCP contenant un ordre et les
     * informations à faire transmettre au client.
     */
    public void envoiePaquet(PacketTCP pkt) {
        if (pkt != null) {
            try {
                String packetString = Utils.packetTCPSerialisation(pkt);
                out.write(packetString + System.getProperty("line.separator"));
                out.flush();
            } catch (IOException ex) {

                //writeMessage(LogLevel.ERROR, ex.getMessage());
            }
        }
    }

    /**
     * This method is used to sleep
     *
     * @param ms number of milliseconds to sleep
     */
    public void sleeep(long ms) {
        try {
            sleep(ms);
        } catch (InterruptedException ex) {
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isConnected() {
        return socket.isConnected();
    }

}
