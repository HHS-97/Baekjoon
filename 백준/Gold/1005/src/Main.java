/*
문제
서기 2012년! 드디어 2년간 수많은 국민들을 기다리게 한 게임 ACM Craft (Association of Construction Manager Craft)가 발매되었다.

이 게임은 지금까지 나온 게임들과는 다르게 ACM크래프트는 다이나믹한 게임 진행을 위해 건물을 짓는 순서가 정해져 있지 않다. 즉, 첫 번째 게임과 두 번째 게임이 건물을 짓는 순서가 다를 수도 있다. 매 게임시작 시 건물을 짓는 순서가 주어진다. 또한 모든 건물은 각각 건설을 시작하여 완성이 될 때까지 Delay가 존재한다.

위의 예시를 보자.

이번 게임에서는 다음과 같이 건설 순서 규칙이 주어졌다. 1번 건물의 건설이 완료된다면 2번과 3번의 건설을 시작할수 있다. (동시에 진행이 가능하다) 그리고 4번 건물을 짓기 위해서는 2번과 3번 건물이 모두 건설 완료되어야지만 4번건물의 건설을 시작할수 있다.

따라서 4번건물의 건설을 완료하기 위해서는 우선 처음 1번 건물을 건설하는데 10초가 소요된다. 그리고 2번 건물과 3번 건물을 동시에 건설하기 시작하면 2번은 1초뒤에 건설이 완료되지만 아직 3번 건물이 완료되지 않았으므로 4번 건물을 건설할 수 없다. 3번 건물이 완성되고 나면 그때 4번 건물을 지을수 있으므로 4번 건물이 완성되기까지는 총 120초가 소요된다.

프로게이머 최백준은 애인과의 데이트 비용을 마련하기 위해 서강대학교배 ACM크래프트 대회에 참가했다! 최백준은 화려한 컨트롤 실력을 가지고 있기 때문에 모든 경기에서 특정 건물만 짓는다면 무조건 게임에서 이길 수 있다. 그러나 매 게임마다 특정건물을 짓기 위한 순서가 달라지므로 최백준은 좌절하고 있었다. 백준이를 위해 특정건물을 가장 빨리 지을 때까지 걸리는 최소시간을 알아내는 프로그램을 작성해주자.

입력
첫째 줄에는 테스트케이스의 개수 T가 주어진다. 각 테스트 케이스는 다음과 같이 주어진다. 첫째 줄에 건물의 개수 N과 건물간의 건설순서 규칙의 총 개수 K이 주어진다. (건물의 번호는 1번부터 N번까지 존재한다)

둘째 줄에는 각 건물당 건설에 걸리는 시간 D1, D2, ..., DN이 공백을 사이로 주어진다. 셋째 줄부터 K+2줄까지 건설순서 X Y가 주어진다. (이는 건물 X를 지은 다음에 건물 Y를 짓는 것이 가능하다는 의미이다)

마지막 줄에는 백준이가 승리하기 위해 건설해야 할 건물의 번호 W가 주어진다.

출력
건물 W를 건설완료 하는데 드는 최소 시간을 출력한다. 편의상 건물을 짓는 명령을 내리는 데는 시간이 소요되지 않는다고 가정한다.

건설순서는 모든 건물이 건설 가능하도록 주어진다.

제한
2 ≤ N ≤ 1000
1 ≤ K ≤ 100,000
1 ≤ X, Y, W ≤ N
0 ≤ Di ≤ 100,000, Di는 정수
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		/*
		위상정렬과 dp를 이용한 문제풀이
		위상정렬을 통해서 노드 순서를 정하고
		dp를 이용해서 건설시간을 빠르게 계산
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//테스트케이스의 개수 T
		int T = Integer.parseInt(st.nextToken());

		for (int t = 1; t <= T; t++) {
			st = new StringTokenizer(br.readLine());
			//건물의 개수 N과 건물간의 건설순서 규칙의 총 개수 K
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			//각 건물당 건설에 걸리는 시간 D1, D2, ..., DN이 공백을 사이로 주어짐
			int[] D = new int[N+1];
			st = new StringTokenizer(br.readLine());
			for (int i = 1; i <= N; i++) {
				D[i] = Integer.parseInt(st.nextToken());
			}

			//셋째 줄부터 K+2줄까지 건설순서 X Y
			//연결을 기록하는 graph
			List<Integer>[] graph = new ArrayList[N+1];
			for (int i = 1; i <= N; i++) {
				graph[i] = new ArrayList<>();
			}

			//진입차수를 기록하는 배열
			int[] entry = new int[N+1];

			for (int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());

				graph[from].add(to);
				//건설을 위해서 건설해야할 건물의 수 = 연결된 노드의 수
				entry[to]++;
			}

			//dp를 이용한 건설 시간
			//간선을 확인할때 다음 노드의 기록된 값과 현재 노드의 기록된 값에 다음 노드의 건설시간을 더한 값 중에 더 큰값을 기록
			long[] dp = new long[N+1];

			ArrayDeque<Integer> q = new ArrayDeque<>();

			// 진입차수 0인 노드부터 시작 (각자 자기 건설시간으로 초기화)
			for (int i = 1; i <= N; i++) {
				if (entry[i] == 0) {
					q.offer(i);
					dp[i] = D[i];
				}
			}

			while(!q.isEmpty()) {
				int cur = q.poll();

				for (int next : graph[cur]) {
					//현재 간선은 확인했으니 entry -1 해주기
					entry[next]--;

					//다음 노드의 기록된 값과 현재 노드의 기록된 값에 다음 노드의 건설시간을 더한 값 중에 더 큰값을 기록
					dp[next] = Math.max(dp[next], dp[cur] + D[next]);

					//entry가 0이면 다음 q에 넣어준다.
					if (entry[next] == 0) q.offer(next);
				}
			}

			st = new StringTokenizer(br.readLine());
			//승리하기 위해 건설해야 할 건물의 번호 W
			int W = Integer.parseInt(st.nextToken());

			bw.write(dp[W] + "\n");
		}
		bw.flush();
		br.close();
		bw.close();
	}
}