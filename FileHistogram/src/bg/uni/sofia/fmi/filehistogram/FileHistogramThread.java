package bg.uni.sofia.fmi.filehistogram;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileHistogramThread extends Thread {

    private Map<Character, Long> histogram;
    private Map<Character, Long> localHistogram;
    private CharacterReader cr;

    public FileHistogramThread(Map<Character, Long> histogram, CharacterReader cr) {
        this.histogram = histogram;
        this.localHistogram = new HashMap<>();
        this.cr = cr;
    }

    @Override
    public void run() {
        try {
            Character c;
            while ((c = cr.getNextChar()) != null) {
                if (!Character.isLetter(c)) {
                    continue;
                }
                c = Character.toLowerCase(c);
                Long count = this.localHistogram.get(c);
                if (count == null) {
                    this.localHistogram.put(c, 1l);
                } else {
                    this.localHistogram.put(c, count + 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Iterator <Map.Entry<Character, Long>> it = this.localHistogram.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Character, Long> current = it.next();
            Character c = current.getKey();
            Long localCount = current.getValue();
            synchronized (this.histogram) {
                Long count = this.histogram.get(c);
                if (count == null) {
                    this.histogram.put(c, localCount);
                } else {
                    this.histogram.put(c, count + localCount);
                }
            }
        }
    }
}
