package by.bsuir.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {

    static ExecutorService executeIt = Executors.newCachedThreadPool();

    public static void main(String[] args) {


        try (ServerSocket server = new ServerSocket(3333)) {

            System.out.println("Server socket created.");

            while (!server.isClosed()) {
                server.setSoTimeout(60000 * 5);
                Socket client = server.accept();

                executeIt.execute(new ClientHandler(client));
                System.out.print("Connection accepted.\n");
            }

            executeIt.shutdown();

        }catch (SocketTimeoutException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
