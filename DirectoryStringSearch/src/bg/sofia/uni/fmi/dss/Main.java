package bg.sofia.uni.fmi.dss;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    private BlockingQueue<Product> storage = new ArrayBlockingQueue<>(Constants.STORAGE_CAPACITY);
    private ArrayList<Thread> threads = new ArrayList<>();
    private String keyword;

    public BlockingQueue<Product> getStorage() {
        return storage;
    }

    public void setStorage(BlockingQueue<Product> storage) {
        this.storage = storage;
    }

    public ArrayList<Thread> getThreads() {
        return threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static void main(String[] args) {

        Main main = new Main();

        Scanner input = new Scanner(System.in);
        System.out.print("Enter path to directory:");
        String directoryName = input.nextLine();
        System.out.print("Enter the keyword:");
        main.setKeyword(input.nextLine());
        input.close();
        main.searchDirectory(directoryName);
        long t0 = System.currentTimeMillis();
        for (Thread t : main.getThreads()) {
            t.start();
        }
        for (Thread t : main.getThreads()) {
            try {
                if (t instanceof Producer) {
                    t.join();
                } else if (t instanceof Consumer) {
                    t.interrupt();
                    t.join();
                }

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0 + " ms");

    }

    private void searchDirectory(String directoryName) {

        File file = new File(directoryName);

        if (file.isDirectory()) {

            File[] files = file.listFiles();

            if (files != null) {

                for (File f : files) {

                    if (f.isDirectory()) {
                        searchDirectory(directoryName + File.separator + f.getName());
                    } else {
                        if (f.getAbsolutePath().endsWith(".txt")) {
                            processFile(f.getPath());
                        }
                    }
                }
            }
        }
    }

    private void processFile(String fileName) {
        threads.add(new Producer(storage, new File(fileName)));
        threads.add(new Consumer(storage, keyword));
    }

}
