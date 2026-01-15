/*
문제
크기가 N×M인 행렬 A와 M×K인 B를 곱할 때 필요한 곱셈 연산의 수는 총 N×M×K번이다. 행렬 N개를 곱하는데 필요한 곱셈 연산의 수는 행렬을 곱하는 순서에 따라 달라지게 된다.

예를 들어, A의 크기가 5×3이고, B의 크기가 3×2, C의 크기가 2×6인 경우에 행렬의 곱 ABC를 구하는 경우를 생각해보자.

AB를 먼저 곱하고 C를 곱하는 경우 (AB)C에 필요한 곱셈 연산의 수는 5×3×2 + 5×2×6 = 30 + 60 = 90번이다.
BC를 먼저 곱하고 A를 곱하는 경우 A(BC)에 필요한 곱셈 연산의 수는 3×2×6 + 5×3×6 = 36 + 90 = 126번이다.
같은 곱셈이지만, 곱셈을 하는 순서에 따라서 곱셈 연산의 수가 달라진다.

행렬 N개의 크기가 주어졌을 때, 모든 행렬을 곱하는데 필요한 곱셈 연산 횟수의 최솟값을 구하는 프로그램을 작성하시오. 입력으로 주어진 행렬의 순서를 바꾸면 안 된다.

입력
첫째 줄에 행렬의 개수 N(1 ≤ N ≤ 500)이 주어진다.

둘째 줄부터 N개 줄에는 행렬의 크기 r과 c가 주어진다. (1 ≤ r, c ≤ 500)

항상 순서대로 곱셈을 할 수 있는 크기만 입력으로 주어진다.

출력
첫째 줄에 입력으로 주어진 행렬을 곱하는데 필요한 곱셈 연산의 최솟값을 출력한다. 정답은 231-1 보다 작거나 같은 자연수이다. 또한, 최악의 순서로 연산해도 연산 횟수가 231-1보다 작거나 같다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//행렬의 개수 N(1 ≤ N ≤ 500)
		int N = Integer.parseInt(st.nextToken());
		//N개 줄에는 행렬의 크기 r과 c가 주어진다. (1 ≤ r, c ≤ 500)
		int[] p = new int[N+1];

		//항상 순서대로 곱셈을 할 수 있는 크기만 입력으로 주어지기 때문에 i가 1일때를 제외한 나머지 경우
		//중복되는 앞에 값은 버리고 뒤에 값만 저장
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			if (i == 1) p[0] = r;
			p[i] = c;
		}

		//DP를 이용해서 문제를 품
		//dp[i][j] = i번부터 j번 행렬까지 곱하는 최소 연산 횟수
		long[][] dp = new long[N+1][N+1];

		//굳이 안해도 되지만 dp[i][i]의 경우 행렬이 1개라서 연산이 0이라는 것을 표시하기 위해 적음
		for (int i = 1; i <= N; i++) dp[i][i] = 0;

		//행렬이 2개부터 N개까지의 최저 연산을 구하기 위해 루프
		for (int len = 2; len <= N; len++) {
			//i는 구간의 시작 인덱스이고 i + len - 1 <= N인 이유는 j가 N을 넘으면 안되기 때문
			for (int i = 1; i + len - 1 <= N; i++) {
				//len은 현재 루프에서 행렬의 갯수를 뜻하기 때문에 시작 인덱스에 len을 더한 다음 -1을 하면 끝 인덱스를 알 수 있음
				int j = i + len - 1;
				//현재 구하려는 최저 연산의 값이 들어갈 dp[i][j]는 최저연산을 구하기 때문에 MAX_VALUE로 초기화 해놓는다.
				dp[i][j] = Long.MAX_VALUE;

				//시작인덱스 부터 저장된 값들을 비교해 가면서 최저 값을 찾기
				for (int k = i; k < j; k++) {
					//점화식 dp[i][k] + dp[k+1][j] + p[i-1] * p[k] * p[j]
					// 왼쪽 덩어리 dp[i][k] 오른쪽 덩어리 dp[k+1][j] 마지막 행렬 곱 p[i-1] * p[k] * p[j]
					long cast = dp[i][k] + dp[k+1][j] + (long) p[i - 1] * p[k] * p[j];

					dp[i][j] = Math.min(dp[i][j], cast);
				}
			}
		}

		System.out.println(dp[1][N]);
	}
}