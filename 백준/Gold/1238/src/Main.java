/*
문제
N개의 숫자로 구분된 각각의 마을에 한 명의 학생이 살고 있다.

어느 날 이 N명의 학생이 X (1 ≤ X ≤ N)번 마을에 모여서 파티를 벌이기로 했다. 이 마을 사이에는 총 M개의 단방향 도로들이 있고 i번째 길을 지나는데 Ti(1 ≤ Ti ≤ 100)의 시간을 소비한다.

각각의 학생들은 파티에 참석하기 위해 걸어가서 다시 그들의 마을로 돌아와야 한다. 하지만 이 학생들은 워낙 게을러서 최단 시간에 오고 가기를 원한다.

이 도로들은 단방향이기 때문에 아마 그들이 오고 가는 길이 다를지도 모른다. N명의 학생들 중 오고 가는데 가장 많은 시간을 소비하는 학생은 누구일지 구하여라.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 10,000), X가 공백으로 구분되어 입력된다. 두 번째 줄부터 M+1번째 줄까지 i번째 도로의 시작점, 끝점, 그리고 이 도로를 지나는데 필요한 소요시간 Ti가 들어온다. 시작점과 끝점이 같은 도로는 없으며, 시작점과 한 도시 A에서 다른 도시 B로 가는 도로의 개수는 최대 1개이다.

모든 학생들은 집에서 X에 갈수 있고, X에서 집으로 돌아올 수 있는 데이터만 입력으로 주어진다.

출력
첫 번째 줄에 N명의 학생들 중 오고 가는데 가장 오래 걸리는 학생의 소요시간을 출력한다.
 */
import java.util.*;
import java.io.*;

public class Main {
	static final int INF = Integer.MAX_VALUE;
	static int n;
	static int m;
	static int x;

	static class Edge{
		int to;
		int time;
		public Edge(int to, int time) {
			this.to = to;
			this.time = time;
		}
	}

	static class Node implements Comparable<Node>{
		int v;
		int totalTime;
		public Node(int v, int totalTime) {
			this.v = v;
			this.totalTime = totalTime;
		}

		@Override
		public int compareTo(Node o) {
			return this.totalTime - o.totalTime;
		}
	}

	static int[] dijkstra(List<Edge>[] g, int start) {
		int[] dist = new int[n+1];
		Arrays.fill(dist, INF);

		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.add(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int v = cur.v;
			int totalTime = cur.totalTime;

			if (totalTime != dist[v]) continue;

			for (Edge edge : g[v]) {
				int nt = totalTime + edge.time;
				if (dist[edge.to] > nt) {
					dist[edge.to] = nt;
					pq.add(new Node(edge.to, nt));
				}
			}
		}

		return dist;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 노드의 갯수 n, 간선의 갯수 m, 시작점 및 도착점 x
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());

		List<Edge>[] graph = new ArrayList[n+1];
		List<Edge>[] rev = new ArrayList[n+1];

		for (int i = 1; i <= n; i++) {
			graph[i] = new ArrayList<>();
			rev[i] = new ArrayList<>();
		}

		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int time = Integer.parseInt(st.nextToken());

			graph[from].add(new Edge(to, time));
			rev[to].add(new Edge(from, time));
		}

		int[] fromX = dijkstra(graph, x);
		int[] toX = dijkstra(rev, x);

		int result = 0;
		for (int i = 1; i <= n; i++) {
			result = Math.max(result, fromX[i] + toX[i]);
		}

		System.out.println(result);
	}
}