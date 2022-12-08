package by.bsuir.client.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerDAONetwork implements ServerDAO {

    public ServerDAONetwork() {
    }

    public void authorization(DataOutputStream oos, String login, String password) throws IOException {
        String hash = getHashSHA256(password);
        String request = "AUTH, " + login + ", " + hash;
        oos.writeUTF(request);
        oos.flush();
    }

    public void find(DataOutputStream oos, String gatName, String value) throws IOException {
        String request = "FIND, " + gatName + ", " + value;
        oos.writeUTF(request);
        oos.flush();
    }

    public void change(DataOutputStream oos, String id, String gatName, String value) throws IOException {
        String request = "CHANGE, " + id + ", " + gatName + ", " + value;
        oos.writeUTF(request);
        oos.flush();
    }

    public void create(DataOutputStream oos, String name, String surname, String faculty, String speciality, String qroup) throws IOException {
        String request = "CREATE, " + name + ", " + surname + ", " + faculty + ", " + speciality + ", " + qroup;
        oos.writeUTF(request);
        oos.flush();
    }

    public Socket connect() {
        try {
            return new Socket("localhost", 3333);
        } catch (Exception e) {
            System.out.println("Server is not available.");
        }
        return null;
    }

    private String getHashSHA256(String originalString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

            BigInteger bigInt = new BigInteger(1, encodedHash);
            String hashText = bigInt.toString(16);
            hashText = "0".repeat(64 - hashText.length()) + hashText;
            return hashText;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }




}
