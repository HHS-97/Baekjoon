/*
문제
N×N개의 수가 N×N 크기의 표에 채워져 있다. (x1, y1)부터 (x2, y2)까지 합을 구하는 프로그램을 작성하시오. (x, y)는 x행 y열을 의미한다.

예를 들어, N = 4이고, 표가 아래와 같이 채워져 있는 경우를 살펴보자.

1	2	3	4
2	3	4	5
3	4	5	6
4	5	6	7
여기서 (2, 2)부터 (3, 4)까지 합을 구하면 3+4+5+4+5+6 = 27이고, (4, 4)부터 (4, 4)까지 합을 구하면 7이다.

표에 채워져 있는 수와 합을 구하는 연산이 주어졌을 때, 이를 처리하는 프로그램을 작성하시오.

입력
첫째 줄에 표의 크기 N과 합을 구해야 하는 횟수 M이 주어진다. (1 ≤ N ≤ 1024, 1 ≤ M ≤ 100,000) 둘째 줄부터 N개의 줄에는 표에 채워져 있는 수가 1행부터 차례대로 주어진다. 다음 M개의 줄에는 네 개의 정수 x1, y1, x2, y2 가 주어지며, (x1, y1)부터 (x2, y2)의 합을 구해 출력해야 한다. 표에 채워져 있는 수는 1,000보다 작거나 같은 자연수이다. (x1 ≤ x2, y1 ≤ y2)

출력
총 M줄에 걸쳐 (x1, y1)부터 (x2, y2)까지 합을 구해 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		// 2차원 누적합을 이용한 DP로 문제풀이
		// 입력받으면서 (1,1) 부터 (x,y)까지 직사각형의 구간합을 dp[x-1][y-1]에 저장
		int[][] dp = new int[n+1][n+1];

		// 저장하는 방법은 우선 n보다 1더 큰 테이블을 만듦 -> 경계가 0으로 이루어진 테이블을 만들어서 (1,1) 에서도 식을 적용가능
		// (x-1, y)까지의 직사각형의 값과 (x, y-1)까지의 직사각형의 합을 구하고, 중복되는 구간인 (x-1, y-1)의 값을 뺀다.
		// 그러면 (x, y)값을 제외한 부분의 합이 구해지니 여기에 (x, y)의 값을 더해주면 (x, y)까지의 직사각형의 값이 구해진다.

		for (int i = 1; i < n+1; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j < n+1; j++) {
				int now = Integer.parseInt(st.nextToken());
				dp[i][j] = dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1] + now;
			}
		}

		// (x1, y1)부터 (x2, y2)까지의 직사각형 값을 구하는 방법
		// dp[x2][y2]의 값에 dp[x1-1][y2], dp[x2][y1-1]을 빼주고 겹쳐서 두 번 빼진 dp[x1-1][y1-1]을 더해준다.
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			int result = dp[x2][y2] - dp[x1-1][y2] - dp[x2][y1-1] + dp[x1-1][y1-1];
			bw.write(result + "\n");
		}

		bw.flush();
		bw.close();
	}
}