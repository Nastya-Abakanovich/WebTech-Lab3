package by.bsuir.client.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public interface ServerDAO {

    void authorization(DataOutputStream oos, String login, String password) throws IOException;
    void find(DataOutputStream oos, String gatName, String value) throws IOException;
    void change(DataOutputStream oos, String id, String gatName, String value) throws IOException;
    void create(DataOutputStream oos, String name, String surname, String faculty, String speciality, String qroup) throws IOException;
    Socket connect();

}
