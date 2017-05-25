package bg.uni.sofia.fmi.filehistogram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class CharacterReader {

    private Reader reader;

    public CharacterReader(File f) throws IOException {
        this.reader = new InputStreamReader(new FileInputStream(f), "UTF8");
    }

    public Character getNextChar() throws IOException {
        int i = this.reader.read();
        if (i == -1) {
            this.reader.close();
            return null;
        }
        return (char) i;
    }
}
