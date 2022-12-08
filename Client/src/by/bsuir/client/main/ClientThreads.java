package by.bsuir.client.main;

import by.bsuir.client.dao.DAOFactory;
import by.bsuir.client.dao.ServerDAO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientThreads implements Runnable {

    static Socket socket;
    private final String threadName;
    private final String[] auth, find, change, create;
    private final ServerDAO serverDAO;

    public ClientThreads(String[] auth, String[] find, String[] change, String[] create, String _threadName) {
        this.auth = auth;
        this.find = find;
        this.change = change;
        this.create = create;
        threadName = _threadName;
        serverDAO = DAOFactory.getInstance().getServerDAO();

        socket = serverDAO.connect();
    }

    @Override
    public void run() {

        if (socket != null)
            try (   DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream ois = new DataInputStream(socket.getInputStream())) {

                serverDAO.authorization(oos, auth[0], auth[1]);
                Thread.sleep(10);
                System.out.println("Thread " + threadName + ". Request 1 (AUTH " + auth[0]
                        + " " + auth[1] + "):\n" + ois.readUTF() + "\n");
                Thread.sleep(1000);

                serverDAO.find(oos, find[0], find[1]);
                Thread.sleep(10);
                System.out.println("Thread " + threadName + ". Request 2 (FIND " + find[0]
                        + " " + find[1] + "):\n" + ois.readUTF() + "\n");
                Thread.sleep(1000);

                serverDAO.change(oos, change[0], change[1], change[2]);
                Thread.sleep(10);
                System.out.println("Thread " + threadName + ". Request 3 (CHANGE " + change[0]
                        + " " + change[1] + " " + change[2] + "):\n" + ois.readUTF() + "\n");
                Thread.sleep(1000);

                serverDAO.create(oos, create[0], create[1], create[2], create[3], create[4]);
                Thread.sleep(10);
                System.out.println("Thread " + threadName + ". Request 4 (CREATE " + create[0] + " "
                        + create[1] + " " + create[2] + " " + create[3] + " " + create[4] + "):\n" + ois.readUTF() + "\n");

            }catch (SocketException e) {
                System.out.println("Server disconnected.");

            }catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}