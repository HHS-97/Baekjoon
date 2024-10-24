import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(bf.readLine());
        int num = 0;
        int answer = 0;

        while (answer == 0 && num < N) {
            String s_num = String.valueOf(num);
            int numL = s_num.length()-1;
            // 현재까지 구한 각 자리의 수를 자리 수에 맞게 합한 값
            int now = 0;
            // 현재까지 구한 각 자리의 수를 그냥 모두 더한 값
            int n_sum = 0;
            for (int i = numL; i >= 0; i--) {
                int a = (int) Math.pow(10, i);
                int b = (num-now) / a;
                now += b * a;
                n_sum += b;
            }
            if (num + n_sum == N) {
                answer = num;
            } else {
                num++;
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(String.valueOf(answer) + '\n');
        bw.flush();
        bw.close();
    }
}