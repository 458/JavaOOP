package bg.uni.sofia.fmi.filehistogram;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        try {
            Map<Character, Long> histogram = new TreeMap<>();
            File txtDir = new File("knigi");
            File files[] = txtDir.listFiles();
            List<FileHistogramThread> threads = new ArrayList<>();

            for (File file : files) {
                if (file.isFile() && file.getAbsolutePath().endsWith(".txt")) {
                    CharacterReader cr = new CharacterReader(file);
                    FileHistogramThread thread = new FileHistogramThread(histogram, cr);
                    threads.add(thread);
                }
            }

            long t0 = System.currentTimeMillis();
            for (FileHistogramThread thread : threads) {
                thread.start();
            }
            for (FileHistogramThread thread : threads) {
                thread.join();
            }
            long t1 = System.currentTimeMillis();

            System.out.println(t1 - t0 + " ms");

            Iterator<Map.Entry<Character, Long>> it = histogram.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Character, Long> current = it.next();
                System.out.println(current.getKey() + " = " + current.getValue());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
