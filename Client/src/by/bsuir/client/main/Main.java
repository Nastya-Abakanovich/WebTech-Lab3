package by.bsuir.client.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newFixedThreadPool(10);

        String[] auth1 = { "admin", "admin"};
        String[] auth2 = { "", ""};
        String[] find = { "group", "051001"};
        String[] change1 = { "2", "surname", "Astapovich"};
        String[] change2 = { "1", "surname", "Astapovich"};
        String[] create = { "Andrey", "Nikitin", "Computer design", "Medical electronics", "911801"};

        exec.execute(new ClientThreads(auth1, find, change1, create, "1"));
        Thread.sleep(500);
        exec.execute(new ClientThreads(auth2, find, change2, create,  "2"));

        exec.shutdown();
    }


}
