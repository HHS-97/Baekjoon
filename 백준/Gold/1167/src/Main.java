/*
문제
트리의 지름이란, 트리에서 임의의 두 점 사이의 거리 중 가장 긴 것을 말한다. 트리의 지름을 구하는 프로그램을 작성하시오.

입력
트리가 입력으로 주어진다. 먼저 첫 번째 줄에서는 트리의 정점의 개수 V가 주어지고 (2 ≤ V ≤ 100,000)둘째 줄부터 V개의 줄에 걸쳐 간선의 정보가 다음과 같이 주어진다. 정점 번호는 1부터 V까지 매겨져 있다.

먼저 정점 번호가 주어지고, 이어서 연결된 간선의 정보를 의미하는 정수가 두 개씩 주어지는데, 하나는 정점번호, 다른 하나는 그 정점까지의 거리이다. 예를 들어 네 번째 줄의 경우 정점 3은 정점 1과 거리가 2인 간선으로 연결되어 있고, 정점 4와는 거리가 3인 간선으로 연결되어 있는 것을 보여준다. 각 줄의 마지막에는 -1이 입력으로 주어진다. 주어지는 거리는 모두 10,000 이하의 자연수이다.

출력
첫째 줄에 트리의 지름을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static boolean[] visited;
	static int maxDist = 0;
	static int maxNode;

	static class Edge {
		int to;
		int weight;
		public Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}

	static void Dfs (List<Edge>[] tree, int dist, int node) {
		visited[node] = true;

		for (Edge e : tree[node]) {
			if (visited[e.to]) continue;
			visited[e.to] = true;
			Dfs(tree, dist+e.weight, e.to);
		}

		if (dist > maxDist) {
			maxDist = dist;
			maxNode = node;
		}
	}

	public static void main(String[] args) throws IOException {
		// 임의의 한 정점에서 가장 먼 정점을 dfs로 구한 후 그 정점(가장 먼)에서 다시 dfs를 이용해서 가장 먼 정점을 구하면 그 사이의 거리가 트리의 지름이 된다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		List<Edge>[] tree = new ArrayList[V+1];

		for (int i = 1; i <= V; i++) {
			tree[i] = new ArrayList<>();
		}

		// 간선의 정보를 받을때 첫 숫자는 정점 번호 두번째부터는 -1이 나올때까지 두개가 세트로 들어가는데
		// 세트 첫 숫자는 연결될 정점 번호, 두번째 숫자는 가중치
		for (int i = 0; i < V; i++) {
			st = new StringTokenizer(br.readLine());
			int f = Integer.parseInt(st.nextToken());

			while (true) {
				int t = Integer.parseInt(st.nextToken());
				if (t == -1) break;
				int w = Integer.parseInt(st.nextToken());

				tree[f].add(new Edge(t, w));
				tree[t].add(new Edge(f, w));
			}
		}

		visited = new boolean[tree.length];
		Arrays.fill(visited, false);
		Dfs(tree, 0, 1);
		visited = new boolean[tree.length];
		Arrays.fill(visited, false);
		Dfs(tree, 0, maxNode);

		System.out.println(maxDist);
	}
}