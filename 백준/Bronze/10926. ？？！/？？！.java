import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String s = bf.readLine();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(s + "??!" + "\n");
        bw.flush();
        bw.close();
    }
}