package bg.sofia.uni.fmi.dss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {

    private BlockingQueue<Product> storage;
    private File file;

    public Producer(BlockingQueue<Product> storage, File file) {
        this.storage = storage;
        this.file = file;
    }

    @Override
    public void run() {
        produce();
    }

    private void produce() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                Product currentProduct = new Product(file.getName(), ++lineNumber, line);
                storage.put(currentProduct);

            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("No such file!");
        } catch (IOException e) {
            System.out.println("IO Exception");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted:" + currentThread());
        }

    }

}
