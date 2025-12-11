/*
문제
방향그래프가 주어지면 주어진 시작점에서 다른 모든 정점으로의 최단 경로를 구하는 프로그램을 작성하시오. 단, 모든 간선의 가중치는 10 이하의 자연수이다.

입력
첫째 줄에 정점의 개수 V와 간선의 개수 E가 주어진다. (1 ≤ V ≤ 20,000, 1 ≤ E ≤ 300,000) 모든 정점에는 1부터 V까지 번호가 매겨져 있다고 가정한다. 둘째 줄에는 시작 정점의 번호 K(1 ≤ K ≤ V)가 주어진다. 셋째 줄부터 E개의 줄에 걸쳐 각 간선을 나타내는 세 개의 정수 (u, v, w)가 순서대로 주어진다. 이는 u에서 v로 가는 가중치 w인 간선이 존재한다는 뜻이다. u와 v는 서로 다르며 w는 10 이하의 자연수이다. 서로 다른 두 정점 사이에 여러 개의 간선이 존재할 수도 있음에 유의한다.

출력
첫째 줄부터 V개의 줄에 걸쳐, i번째 줄에 i번 정점으로의 최단 경로의 경로값을 출력한다. 시작점 자신은 0으로 출력하고, 경로가 존재하지 않는 경우에는 INF를 출력하면 된다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int INF = Integer.MAX_VALUE;

	static class Edge {
		int to;
		int weight;
		public Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}

	static class Node implements Comparable<Node> {
		int data;
		int weight;

		public Node(int data, int weight) {
			this.data = data;
			this.weight = weight;
		}

		@Override
		public int compareTo(Node o) {
			return this.weight - o.weight;
		}
	}

	public static void main(String[] args) throws IOException {
		// 풀이 방법 : 다익스트라 알고리즘
		// 전형적인 다익스트라 알고리즘을 활용한 최단경로를 찾는 문제
		// 노드 클래스를 만들어서 그래프 형식을 적용한 후에 엣지 클래스로 연결상태와 가중치를 필드로 가진 간선을 표현
		// 다익스트라 알고리즘을 위한 dist배열을 통해 해당하는 번호의 정점을 향한 최저 가중치합을 저장한다.
		// 시작 정점에서 모든 정점으로의 최단 경로를 찾는 문제

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 정점의 개수 v, 간선의 개수 e
		int v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());

		// 시작 정점의 번호
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());

		// 간선정보를 모아두는 배열을 만들자 => 그래프
		List<List<Edge>> graph = new ArrayList<>();

		for (int i = 0; i <= v; i++) {
			graph.add(new ArrayList<>());
		}

		// 간선정보를 graph에 넣어주자
		for (int i = 0; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			// 간선 정보를 저장할 정점
			int v1 = Integer.parseInt(st.nextToken());
			// v1과 연결된 정점의 번호
			int v2 = Integer.parseInt(st.nextToken());
			// 가중치
			int w = Integer.parseInt(st.nextToken());

			graph.get(v1).add(new Edge(v2, w));
		}

		int[] dist = new int[v + 1];
		Arrays.fill(dist, INF);

		// 우선순위 큐를 이용해서 다익스트라 구현
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start] = 0;
		pq.offer(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int curV = cur.data;
			int curW = cur.weight;

			if (dist[curV] < curW) continue;

			for (Edge edge : graph.get(curV)) {
				int nextV = edge.to;
				int weight = edge.weight;

				// 가중치가 더 짧으면 갱신
				if (dist[nextV] > dist[curV] + weight) {
					dist[nextV] = dist[curV] + weight;
					pq.offer(new Node(nextV, dist[nextV]));
				}
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (int i = 1; i <= v; i++) {
			if (dist[i] == INF) {
				bw.write("INF\n");
			} else {
				bw.write(dist[i] + "\n");
			}
		}

		bw.flush();
		br.close();
	}
}