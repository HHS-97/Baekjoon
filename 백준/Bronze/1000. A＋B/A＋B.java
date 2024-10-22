import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = bf.readLine();
        StringTokenizer stk = new StringTokenizer(s);
        int a = Integer.parseInt(stk.nextToken());
        int b = Integer.parseInt(stk.nextToken());

        String result = String.valueOf((a+b));
        bw.write(result + "\n");
        bw.flush();
        bw.close();
    }
}
