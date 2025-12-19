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
	static class Edge {
		int to, weight;
		Edge(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}

	static class State {
		int node, dist;
		State(int node, int dist) {
			this.node = node;
			this.dist = dist;
		}
	}

	static int maxDist;
	static int maxNode;

	static void Dfs(List<Edge>[] tree, int start) {
		boolean[] visited = new boolean[tree.length];
		ArrayDeque<State> stack = new ArrayDeque<>();

		visited[start] = true;
		stack.push(new State(start, 0));

		while (!stack.isEmpty()) {
			State cur = stack.pop();
			int node = cur.node;
			int dist = cur.dist;

			if (dist > maxDist) {
				maxDist = dist;
				maxNode = node;
			}

			for (Edge e : tree[node]) {
				if (!visited[e.to]) {
					visited[e.to] = true;
					stack.push(new State(e.to, dist + e.weight));
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		// 기존 1167 트리의 지름 문제의 Dfs를 스택으로 구현했을 경우
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int V = Integer.parseInt(br.readLine().trim());

		List<Edge>[] tree = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) tree[i] = new ArrayList<>();

		for (int i = 0; i < V; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());

			while (true) {
				int to = Integer.parseInt(st.nextToken());
				if (to == -1) break;
				int w = Integer.parseInt(st.nextToken());

				tree[from].add(new Edge(to, w));
				tree[to].add(new Edge(from, w));
			}
		}

		maxDist = 0;
		maxNode = 1;
		Dfs(tree, 1);

		maxDist = 0;
		Dfs(tree, maxNode);

		System.out.println(maxDist);
	}
}