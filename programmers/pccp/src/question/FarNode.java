package question;
import java.util.*;

class Node2 {
	int n;
	int dist = 0;
	List<Node2> links = new LinkedList<>();
	boolean visited = false;
	Node2(int n) { this.n = n; }
}

public class FarNode {
	public int solution(int n, int[][] edge) {
		List<Node2> nodes = new ArrayList<>(n);
		for(int i=0; i<n; i++) nodes.add(new Node2(i+1));

		for(int[] e : edge) {
			Node2 n1 = nodes.get(e[0] - 1);
			Node2 n2 = nodes.get(e[1] - 1);

			n1.links.add(n2);
			n2.links.add(n1);
		}

		Queue<Node2> q = new LinkedList<>();
		int maxDist = 0;
		Node2 first = nodes.get(0);
		first.visited = true;
		q.offer(first);

		while(!q.isEmpty()) {
			Node2 now = q.poll();

			for (Node2 node : now.links) {
				if (node.visited) continue;

				node.visited = true;
				node.dist = now.dist + 1;
				q.offer(node);

				maxDist = Math.max(maxDist, node.dist);
			}
		}

		int answer = 0;
		for (Node2 node : nodes) {
			if (node.dist == maxDist) answer ++;
		}

		return answer;
	}
}
