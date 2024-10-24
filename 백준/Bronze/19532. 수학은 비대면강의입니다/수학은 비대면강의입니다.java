import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stk = new StringTokenizer(bf.readLine());
        int a = Integer.parseInt(stk.nextToken());
        int b = Integer.parseInt(stk.nextToken());
        int c = Integer.parseInt(stk.nextToken());
        int d = Integer.parseInt(stk.nextToken());
        int e = Integer.parseInt(stk.nextToken());
        int f = Integer.parseInt(stk.nextToken());
        String answer_x = "x";
        String answer_y = "y";

        Loop1:
        for (int x = -999; x <= 999; x++) {
            Loop2:
            for (int y = -999; y <= 999; y++) {
                if (((a*x) + (b*y) == c) && ((d*x) + (e*y) == f)) {
                    answer_x = String.valueOf(x);
                    answer_y = String.valueOf(y);
                    break Loop1;
                }
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(answer_x + ' ' + answer_y + '\n');
        bw.flush();
        bw.close();
    }
}
