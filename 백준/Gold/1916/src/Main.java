/*
문제
N개의 도시가 있다. 그리고 한 도시에서 출발하여 다른 도시에 도착하는 M개의 버스가 있다. 우리는 A번째 도시에서 B번째 도시까지 가는데 드는 버스 비용을 최소화 시키려고 한다. A번째 도시에서 B번째 도시까지 가는데 드는 최소비용을 출력하여라. 도시의 번호는 1부터 N까지이다.

입력
첫째 줄에 도시의 개수 N(1 ≤ N ≤ 1,000)이 주어지고 둘째 줄에는 버스의 개수 M(1 ≤ M ≤ 100,000)이 주어진다. 그리고 셋째 줄부터 M+2줄까지 다음과 같은 버스의 정보가 주어진다. 먼저 처음에는 그 버스의 출발 도시의 번호가 주어진다. 그리고 그 다음에는 도착지의 도시 번호가 주어지고 또 그 버스 비용이 주어진다. 버스 비용은 0보다 크거나 같고, 100,000보다 작은 정수이다.

그리고 M+3째 줄에는 우리가 구하고자 하는 구간 출발점의 도시번호와 도착점의 도시번호가 주어진다. 출발점에서 도착점을 갈 수 있는 경우만 입력으로 주어진다.

출력
첫째 줄에 출발 도시에서 도착 도시까지 가는데 드는 최소 비용을 출력한다.
 */

import java.util.*;
import java.io.*;

// 간선 클래스
class Edge {
	// 현재 노드에서 이어진 노드
	int to;
	// 간선의 가중치
	int weight;

	public Edge(int to, int weight) {
		this.to = to;
		this.weight = weight;
	}
}

// 우선순위 큐에 넣을 노드 (노드 + 현재까지의 가중치)
class Node implements Comparable<Node> {
	int node;
	int weight;

	public Node(int node, int weight) {
		this.node = node;
		this.weight = weight;
	}

	@Override
	public int compareTo(Node o) {
		return this.weight - o.weight;
	}
}

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int m = Integer.parseInt(st.nextToken());

		// 간선의 관계와 가중치를 초기화
		List<List<Edge>> graph = new ArrayList<>();
		for (int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}

		// 간선 정보 추가
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			// 노드와 연결된 인접 노드들을 가중치와 함께 간선 연결 상태 추가
			graph.get(start).add(new Edge(end, weight));
		}

		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());

		int[] dist = dijkstra(graph, start, n);
		System.out.println(dist[end]);
	}

	// 다익스트라 알고리즘
	static int[] dijkstra(List<List<Edge>> graph, int start, int n) {
		int[] dist = new int[n+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[start] = 0;
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int curWeight = cur.weight;
			int curNode = cur.node;

			// 이미 가중치가 더 낮으면 넘기기
			if (dist[curNode] < curWeight) continue;

			// 인접 노드들을 확인
			for (Edge e : graph.get(curNode)) {
				int next = e.to;
				int weight = e.weight;

				// 거리가 더 짧으면 갱신
				if (dist[next] > dist[curNode] + weight) {
					dist[next] = dist[curNode] + weight;
					pq.offer(new Node(next, dist[next]));
				}
			}
		}
		return dist;
	}
}