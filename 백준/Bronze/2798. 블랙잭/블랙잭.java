import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static int max_sum = 0;

    public static void Brute(List<Integer> list, int[] visited, int nsum, int N, int M, int callStack) {
        if (nsum > M && callStack <= 3) {
            return;
        }

        if (callStack == 3) {
            if (nsum > Main.max_sum) {
                Main.max_sum = nsum;
            }
            return;
        }

        for (int i = 0; i < N; i++) {
            if (visited[i] == 0) {
                visited[i] = 1;
                Brute(list, visited, nsum+list.get(i), N, M, callStack+1);
                visited[i] = 0;
            }
        }
    }

    public static void main(String[] args) throws  IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String NM = bf.readLine();
        StringTokenizer stk = new StringTokenizer(NM);
        int N = Integer.parseInt(stk.nextToken());
        int M = Integer.parseInt(stk.nextToken());

        String cardInput = bf.readLine();
        StringTokenizer cardStk = new StringTokenizer(cardInput);
        // 리스트를 만들자
        List<Integer> cardList = new ArrayList<>();
        int[] visited = new int[N];
        for (int i = N; i > 0; i--) {
            cardList.add(Integer.parseInt(cardStk.nextToken()));
        }
        Brute(cardList, visited, 0, N, M, 0);

        String answer = String.valueOf(Main.max_sum);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
    }
}