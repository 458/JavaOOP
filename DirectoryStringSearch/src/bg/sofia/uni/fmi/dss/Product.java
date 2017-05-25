package bg.sofia.uni.fmi.dss;

public class Product {

    private String fileName;
    private int line;
    private String content;

    public Product(String fileName, int line, String content) {
        super();
        this.fileName = fileName;
        this.line = line;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {

        return String.format("File:%s,Line:%d,Content:%s", fileName, line, content);
    }

}
