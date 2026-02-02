/*
문제
외판원 순회 문제는 영어로 Traveling Salesman problem (TSP) 라고 불리는 문제로 computer science 분야에서 가장 중요하게 취급되는 문제 중 하나이다. 여러 가지 변종 문제가 있으나, 여기서는 가장 일반적인 형태의 문제를 살펴보자.

1번부터 N번까지 번호가 매겨져 있는 도시들이 있고, 도시들 사이에는 길이 있다. (길이 없을 수도 있다) 이제 한 외판원이 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 순회 여행 경로를 계획하려고 한다. 단, 한 번 갔던 도시로는 다시 갈 수 없다. (맨 마지막에 여행을 출발했던 도시로 돌아오는 것은 예외) 이런 여행 경로는 여러 가지가 있을 수 있는데, 가장 적은 비용을 들이는 여행 계획을 세우고자 한다.

각 도시간에 이동하는데 드는 비용은 행렬 W[i][j]형태로 주어진다. W[i][j]는 도시 i에서 도시 j로 가기 위한 비용을 나타낸다. 비용은 대칭적이지 않다. 즉, W[i][j] 는 W[j][i]와 다를 수 있다. 모든 도시간의 비용은 양의 정수이다. W[i][i]는 항상 0이다. 경우에 따라서 도시 i에서 도시 j로 갈 수 없는 경우도 있으며 이럴 경우 W[i][j]=0이라고 하자.

N과 비용 행렬이 주어졌을 때, 가장 적은 비용을 들이는 외판원의 순회 여행 경로를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 도시의 수 N이 주어진다. (2 ≤ N ≤ 16) 다음 N개의 줄에는 비용 행렬이 주어진다. 각 행렬의 성분은 1,000,000 이하의 양의 정수이며, 갈 수 없는 경우는 0이 주어진다. W[i][j]는 도시 i에서 j로 가기 위한 비용을 나타낸다.

항상 순회할 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 외판원의 순회에 필요한 최소 비용을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int INF = 1_000_000_000;

	public static void main(String[] args) throws IOException {
		//TSP문제는 dp와 비트마스크를 이용해서 풀이
		//이동하는데 드는 비용은 행렬 W[i][j]과
		//비트마스크를 이용한 dp[mask][last]를 이용해서 문제를 푸는데
		//mask는 현재까지 지나간 모든 도시를 체크한 것이고, last는 현재 도착해있는 도시
		//dp[mask][last]에 last에 도착해있을 때의 최소 누적비용을 저장
		//현재 mask, last일 때 next 도시로 이동
		//next가 mask에 포함돼 있으면 방문불가(이미 방문함)
		//W[last][next] == 0이면 길이 없으므로 방문불가
		//mask를 작은 값 부터 큰 값으로 증가시키며 dp 채우기
		//mask는 최대 2^N-1 까지 존재
		//dp로 모든 도시를 방문 했으면 FULL인 (1<<N)-1로 dp[FULL][last]에 마지막으로 출발 도시로
		//돌아오는 값인 W[last][0]을 더한 값 중 가장 작은 값을 정답으로 한다.
		//(last를 1부터 루프돌리기)
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//도시의 수 N (2 ≤ N ≤ 16)
		int N = Integer.parseInt(st.nextToken());

		//N개의 줄에는 비용 행렬
		//각 행렬의 성분은 1,000,000 이하의 양의 정수이며, 갈 수 없는 경우는 0
		int[][] W = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				W[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		//mask는 최대 2^N-1
		int FULL = (1<<N) - 1;

		//비트마스크를 이용한 dp[mask][last]
		int[][] dp = new int[1<<N][N];
		for (int m = 0; m <= FULL; m++) Arrays.fill(dp[m], INF);

		//시작 도시는 관습적으로 0으로 고정해도 순회는 회전해도 동일하기 때문에 가능
		dp[1<<0][0] = 0;

		//mask는 현재까지 지나간 모든 도시를 체크한 것이고, last는 현재 도착해있는 도시
		for (int mask = 1; mask <= FULL; mask++) {
			// 시작 도시를 포함하지 않는 상태는 고려할 필요 없음
			if ((mask & 1) == 0) continue;

			for (int last = 0; last < N; last++) {
				//last가 mask에 포함돼있지 않으면 방문하지 않은 것이니 넘기기
				if ((mask & (1 << last)) == 0) continue;
				int curCost = dp[mask][last];
				//현재 값이 INF면 방문하지 않은 것이니 넘기기
				if (curCost == INF) continue;

				for (int next = 0; next < N; next++) {
					//mask에서 next가 0이 아니면 이미 방문 했으니 넘기기
					if ((mask & (1 << next)) != 0) continue;
					//W[last][next]가 0이면 길이 없는 것이니 넘기기
					if (W[last][next] == 0) continue;

					int nextMask = mask | (1 << next);
					int cand = curCost + W[last][next];
					if (cand < dp[nextMask][next]) dp[nextMask][next] = cand;
				}
			}
		}

		int result = INF;

		//dp로 모든 도시를 방문 했으면 FULL인 (1<<N)-1로 dp[FULL][last]에 마지막으로 출발 도시로
		//돌아오는 값인 W[last][0]을 더한 값 중 가장 작은 값을 정답으로 한다.
		//(last를 1부터 루프돌리기)
		for (int last = 1; last < N; last++) {
			//INF면 FULL에서 현재 last를 방문하지 않은 것이니 넘기기
			if (dp[FULL][last] == INF) continue;
			//복귀할 길이 없으니 넘기기
			if (W[last][0] == 0) continue;

			result = Math.min(result, dp[FULL][last] + W[last][0]);
		}

		System.out.println(result);
	}
}