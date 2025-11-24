//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.io.*;

/*
문제
루트 없는 트리가 주어진다. 이때, 트리의 루트를 1이라고 정했을 때, 각 노드의 부모를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 노드의 개수 N (2 ≤ N ≤ 100,000)이 주어진다. 둘째 줄부터 N-1개의 줄에 트리 상에서 연결된 두 정점이 주어진다.

출력
첫째 줄부터 N-1개의 줄에 각 노드의 부모 노드 번호를 2번 노드부터 순서대로 출력한다.
 */

class Node1 {
	int n;
	int parent = 0;
	List<Node1> links = new LinkedList<>();
	boolean visited = false;

	Node1(int n) {
		this.n = n;
	}
}

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 노드의 개수
		int n = Integer.parseInt(st.nextToken());

		// 노드 리스트
		List<Node1> nodes = new ArrayList<>(n);
		for (int i=0; i<n; i++) { nodes.add(new Node1(i+1)); }

		// 트리 상에서 연결된 두 정점
		for (int i=0; i<n-1; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			nodes.get(a-1).links.add(nodes.get(b-1));
			nodes.get(b-1).links.add(nodes.get(a-1));
		}

		// BFS로 부모 찾기
		Queue<Node1> q = new LinkedList<>();
		Node1 root = nodes.get(0);
		q.offer(root);

		while(!q.isEmpty()) {
			Node1 now = q.poll();
			now.visited = true;

			for (Node1 next : now.links) {
				if (next.visited) continue;
				next.parent = now.n;
				q.offer(next);
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		for (int i=1; i<n; i++) { bw.write(String.valueOf(nodes.get(i).parent) + "\n"); }

		bw.flush();
		bw.close();
	}
}