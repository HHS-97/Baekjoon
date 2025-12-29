/*
문제
그래프가 주어졌을 때, 그 그래프의 최소 스패닝 트리를 구하는 프로그램을 작성하시오.

최소 스패닝 트리는, 주어진 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 그 가중치의 합이 최소인 트리를 말한다.

입력
첫째 줄에 정점의 개수 V(1 ≤ V ≤ 10,000)와 간선의 개수 E(1 ≤ E ≤ 100,000)가 주어진다. 다음 E개의 줄에는 각 간선에 대한 정보를 나타내는 세 정수 A, B, C가 주어진다. 이는 A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있다는 의미이다. C는 음수일 수도 있으며, 절댓값이 1,000,000을 넘지 않는다.

그래프의 정점은 1번부터 V번까지 번호가 매겨져 있고, 임의의 두 정점 사이에 경로가 있다. 최소 스패닝 트리의 가중치가 -2,147,483,648보다 크거나 같고, 2,147,483,647보다 작거나 같은 데이터만 입력으로 주어진다.

출력
첫째 줄에 최소 스패닝 트리의 가중치를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static int V;
	//union-find용 부모 기록용 배열
	static int[] parent;
	//간선 기록 배열
	static List<Edge> edges;
	//union-find 최적화를 위한 rank배열
	static int[] rank;

	static class Edge implements Comparable<Edge> {
		int from;
		int to;
		int weight;
		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(weight, o.weight);
		}
	}

	//union-find 알고리즘에서 최상위 부모를 찾는 메서드
	static int find_root (int x) {
		if (parent[x] == x) return x;
		return parent[x] = find_root(parent[x]);
	}

	//서로 다른 union을 하나의 집합으로 합치는 메서드
	static void union_root (int x, int y) {
		x = find_root(x);
		y = find_root(y);

		if (x == y) return;

		//rank가 낮은 트리를 아래에 붙인다.
		if (rank[x] < rank[y]) {
			parent[x] = y;
		} else if (rank[x] > rank[y]) {
			parent[y] = x;
		} else {
			parent[y] = x;
			rank[x]++;
		}
	}

	// 가중치의 합을 반환하는 크루스칼 알고리즘
	static long kruskal () {
		long result = 0L;
		//결과에 더한 횟수
		int sumCount = 0;

		for (Edge e : edges) {
			//간선으로 이어져있는 두 정점
			int f = e.from;
			int t = e.to;

			//사이클이 발생하는지 확인
			if (find_root(f) == find_root(t)) continue;
			//부모관계 갱신
			union_root(f, t);
			//사이클이 발생하지 않으니 result에 가중치 더하기
			result += e.weight;

			// 만약 v-1번 더했으면 종료
			if (sumCount == V-1) break;
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		// 최소 비용 신장 트리(MST)를 구하기 위해 크루스칼(Kruskal) 알고리즘을 사용한다.
		// 1) 모든 간선을 가중치 기준으로 오름차순 정렬한다.
		// 2) 아직 선택되지 않은 간선 중 가장 가중치가 작은 간선을 하나씩 선택한다.
		// 3) 해당 간선을 추가했을 때 사이클이 발생하지 않는 경우에만 MST에 포함시킨다.
		//    - 사이클 여부는 Union-Find(Disjoint Set) 자료구조로 판단한다.
		//    - 두 정점의 최상위 부모가 같으면 사이클이 발생하므로 선택하지 않는다.
		//    - 부모가 다르면 사이클이 발생하지 않으므로 선택한다.
		// 4) 총 V - 1개의 간선이 선택되면 최소 비용 신장 트리가 완성된다.
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(bf.readLine());
		//정점의 개수 V
		V = Integer.parseInt(st.nextToken());
		//간선의 개수 E
		int E = Integer.parseInt(st.nextToken());
		edges = new ArrayList<>(E);

		//E개의 줄에 A번 정점, B번 정점, 가중치 C
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(bf.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int C = Integer.parseInt(st.nextToken());
			edges.add(new Edge(A, B, C));
		}

		//오름차순으로 정렬
		Collections.sort(edges);

		//부모 기록용 배열 초기화
		parent = new int[V+1];
		rank = new int[V+1];
		for (int i = 1; i <= V; i++) {
			parent[i] = i;
		}

		long result = kruskal();
		System.out.println(result);
	}
}