/*
문제
n(2 ≤ n ≤ 100)개의 도시가 있다. 그리고 한 도시에서 출발하여 다른 도시에 도착하는 m(1 ≤ m ≤ 100,000)개의 버스가 있다. 각 버스는 한 번 사용할 때 필요한 비용이 있다.

모든 도시의 쌍 (A, B)에 대해서 도시 A에서 B로 가는데 필요한 비용의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 도시의 개수 n이 주어지고 둘째 줄에는 버스의 개수 m이 주어진다. 그리고 셋째 줄부터 m+2줄까지 다음과 같은 버스의 정보가 주어진다. 먼저 처음에는 그 버스의 출발 도시의 번호가 주어진다. 버스의 정보는 버스의 시작 도시 a, 도착 도시 b, 한 번 타는데 필요한 비용 c로 이루어져 있다. 시작 도시와 도착 도시가 같은 경우는 없다. 비용은 100,000보다 작거나 같은 자연수이다.

시작 도시와 도착 도시를 연결하는 노선은 하나가 아닐 수 있다.

출력
n개의 줄을 출력해야 한다. i번째 줄에 출력하는 j번째 숫자는 도시 i에서 j로 가는데 필요한 최소 비용이다. 만약, i에서 j로 갈 수 없는 경우에는 그 자리에 0을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		// 플로이드 워셜 알고리즘을 이용해 풀어야하는 문제
		// 플로이드 워셜 알고리즘은 2차원 테이블을 만들어서 최단거리를 저장한다.
		// 2차원 테이블을 만들어서 라운드 마다 각 경로에서 새로운 노드를 경유지로 선택하고,
		// 기존 경로와 새로운 경로중 더 짧은 경로를 선택해서 저장한다.
		// O(n^3)의 시간이 걸리기에 경로의 갯수가 작은 문제에 적합하다.
		// 점화식 => Dab = min (Dab, Dak + Dkb)

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());
		int[][] graph = new int[n+1][n+1];

		// 테이블을 INF로 채워주면서 자기 자신으로 가는 비용은 0으로 설정
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == j) {
					graph[i][j] = 0;
					continue;
				}
				graph[i][j] = INF;
			}
		}

		// 입력받은 a -> b로 가는 비용을 저장 (출발지점 a, 도착지점 b, 비용 c가 m번의 입력을 통해 주어진다.)
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			graph[a][b] = Math.min(graph[a][b], c);
		}

		// 점화식에 따라 라운드별로 중간 경로를 집어넣어 주자.
		for (int k = 1; k <= n; k++) {
			for (int i = 1; i <= n; i++) {
				if (graph[i][k] == INF) continue;
				for (int j = 1; j <= n; j++) {
					if (graph[k][j] == INF) continue;
					// i -> j 와 i -> k, k -> j 중 비용이 더 적은 것을 고름
					graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
				}
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				sb.append(graph[i][j] == INF ? 0 : graph[i][j]);
				if (j < n) sb.append(' ');
			}
			sb.append("\n");
		}

		bw.write(sb.toString());
		bw.flush();
		br.close();
	}
}