package question;

import java.util.*;

class Node3 {
	int n;
	List<Node3> links = new LinkedList<>();
	boolean visit = false;
	int win = 0;
	int lose = 0;
	Node3 (int n) { this.n = n; }
}

public class Ranking {
	public int solution(int n, int[][] results) {

		List<Node3> nodes = new ArrayList<>(n);

		for (int i=0; i<n; i++) nodes.add(new Node3(i+1));

		for (int[] result : results) {
			Node3 winner = nodes.get(result[0]-1);
			Node3 loser = nodes.get(result[1]-1);
			winner.links.add(loser);
		}

		for (Node3 winner : nodes) {
			for (Node3 node : nodes) node.visit = false;

			Queue<Node3> q = new LinkedList<>();
			winner.visit = true;
			q.offer(winner);

			while(!q.isEmpty()) {
				Node3 now = q.poll();

				for (Node3 loser : now.links) {
					if(loser.visit) continue;
					loser.visit = true;
					q.offer(loser);
					winner.win += 1;
					loser.lose += 1;
				}
			}
		}

		int answer = 0;

		for (Node3 node : nodes) {
			if (node.win + node.lose == n - 1) answer++;
		}

		return answer;
	}
}
