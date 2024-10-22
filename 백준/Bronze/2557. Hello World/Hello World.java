import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter((System.out)));
        String s = "Hello World!";
        bw.write(s+"\n");
        bw.flush();
        bw.close();
    }
}