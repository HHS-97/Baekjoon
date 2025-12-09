/*
문제
수빈이는 동생과 숨바꼭질을 하고 있다. 수빈이는 현재 점 N(0 ≤ N ≤ 100,000)에 있고, 동생은 점 K(0 ≤ K ≤ 100,000)에 있다. 수빈이는 걷거나 순간이동을 할 수 있다. 만약, 수빈이의 위치가 X일 때 걷는다면 1초 후에 X-1 또는 X+1로 이동하게 된다. 순간이동을 하는 경우에는 0초 후에 2*X의 위치로 이동하게 된다.

수빈이와 동생의 위치가 주어졌을 때, 수빈이가 동생을 찾을 수 있는 가장 빠른 시간이 몇 초 후인지 구하는 프로그램을 작성하시오.

입력
첫 번째 줄에 수빈이가 있는 위치 N과 동생이 있는 위치 K가 주어진다. N과 K는 정수이다.

출력
수빈이가 동생을 찾는 가장 빠른 시간을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int MAX = 100000;
	static final int INF = Integer.MAX_VALUE;

	static class Node implements Comparable<Node> {
		int data;
		int time;

		Node (int data, int time) {
			this.data = data;
			this.time = time;
		}

		@Override
		public int compareTo(Node o) {
			return this.time - o.time;
		}
	}

	public static void main(String[] args) throws IOException {
		// 다익스트라 알고리즘을 사용하여 문제를 푼다.
		// 0(순간이동)과 1(걸어서 이동)이 가중치가 되고 0~100000이 노드가 된다.
		// 0~100000이 한번에 모든 노드가 생성 되는건 아니고 x-1, x+1, 2*x의 경우마다 노드를 생성해준다.
		// 노드는 숫자 data와 시간(가중치) time을 필드로 가진다.
		// 그리고 time을 비교하기 위해서 compareTo를 구현해준다.
		// 다익스트라를 위한 dist배열은 최댓값 100000으로 크기를 설정해준다 dist[100000+1]
		// 그 후 모든 값을 int의 최대값으로 설정해준다.
		// 우선순위 큐를 이용하여 항상 시간이 작은 노드가 먼저 나오게 해서 bfs를 돌려준다.
		// bfs를 돌리며 현재 노드의 시간(t)가 현재 노드의 값(data)의 기록된 dist[data]보다 크면 이미 더 좋은 경로로 방문한 적이 있는 것이므로 넘어간다.
		// data가 k와 같으면 도착한 것이므로 루프를 끝낸다.
		// data에 -1, +1, *2를 해주는 경우를 dist 배열에 가중치를 더하고 노드로 생성해 우선순위 큐에 넣어준다.
		// 결과는 dist[k]를 출력하면 된다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 시작점 n, 도착점 k
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		int[] dist = new int[MAX + 1];
		Arrays.fill(dist, INF);
		PriorityQueue<Node> pq = new PriorityQueue<>();

		dist[n] = 0;
		pq.offer(new Node(n, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int x = cur.data;
			int time = cur.time;

			if (time > dist[x]) continue;
			if (k == x) break;

			// 1. 순간이동 x*2 비용 0
			int nx = 2 * x;
			if (nx <= MAX && dist[nx] > time) {
				dist[nx] = time;
				pq.offer(new Node(nx, dist[nx]));
			}

			// 2. 걸어서 x-1 비용 1
			nx = x - 1;
			if (nx >= 0 && dist[nx] > time + 1) {
				dist[nx] = time + 1;
				pq.offer(new Node(nx, dist[nx]));
			}


			// 3. 걸어서 x+1 비용 1
			nx = x + 1;
			if (nx <= MAX && dist[nx] > time + 1) {
				dist[nx] = time + 1;
				pq.offer(new Node(nx, dist[nx]));
			}
		}

		System.out.println(dist[k]);
	}
}