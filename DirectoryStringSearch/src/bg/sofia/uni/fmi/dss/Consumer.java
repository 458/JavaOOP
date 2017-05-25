package bg.sofia.uni.fmi.dss;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {

    private BlockingQueue<Product> storage;
    private String keyword;

    public Consumer(BlockingQueue<Product> storage, String keyword) {
        this.storage = storage;
        this.keyword = keyword;
    }

    @Override
    public void run() {

        consume();
    }

    private void consume() {
        boolean interrupted = false;
        try {

            while (!interrupted) {
                Product currentProduct = storage.take();
                if (currentProduct.getContent().contains(keyword)) {
                    System.out.println(currentProduct.toString());
                }
            }

        } catch (InterruptedException e) {
            interrupted = true;
        }
    }

}
