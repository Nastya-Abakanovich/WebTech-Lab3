package by.bsuir.server.main;

import by.bsuir.server.entity.Student;
import by.bsuir.server.service.ArchiveService;
import by.bsuir.server.service.ServiceFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;
    private boolean isAdmin = false;
    private static final ArchiveService service = ServiceFactory.getInstance().getArchiveService();
    public ClientHandler(Socket client) {
        clientDialog = client;
    }

    @Override
    public void run() {

        try {
            // канал записи в сокет
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            // канал чтения из сокета
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());

            while (!clientDialog.isClosed()) {

                try {
                    boolean result;
                    String entry = in.readUTF();

                    String[] params = entry.split(", ");

                    switch (params[0]) {
                        case "FIND": {
                            List<Student> students = service.findStudents(params[1], params[2]);
                            out.writeUTF(students.toString());
                            break;
                        }
                        case "CHANGE": {
                            result = service.changeStudent(Integer.parseInt(params[1]), params[2], params[3], isAdmin);
                            out.writeUTF("Change result: " + result);
                            break;
                        }
                        case "CREATE": {
                            result = service.createStudent(params[1], params[2], params[3], params[4], params[5], isAdmin);
                            out.writeUTF("Create result:" + result);
                            break;
                        }
                        case "AUTH": {
                            isAdmin = service.authorization(params[1], params[2]);
                            out.writeUTF("Authorization result: " + isAdmin);
                            break;
                        }
                    }

                    out.flush();
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    out.writeUTF("Not enough parameters.");
                }
                catch(EOFException e) {}
                catch(SocketException e) {
                    in.close();
                    out.close();
                    clientDialog.close();
                }
            }

            System.out.println("Client disconnected");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
