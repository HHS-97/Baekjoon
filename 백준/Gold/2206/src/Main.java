/*
문제
N×M의 행렬로 표현되는 맵이 있다. 맵에서 0은 이동할 수 있는 곳을 나타내고, 1은 이동할 수 없는 벽이 있는 곳을 나타낸다. 당신은 (1, 1)에서 (N, M)의 위치까지 이동하려 하는데, 이때 최단 경로로 이동하려 한다. 최단경로는 맵에서 가장 적은 개수의 칸을 지나는 경로를 말하는데, 이때 시작하는 칸과 끝나는 칸도 포함해서 센다.

만약에 이동하는 도중에 한 개의 벽을 부수고 이동하는 것이 좀 더 경로가 짧아진다면, 벽을 한 개 까지 부수고 이동하여도 된다.

한 칸에서 이동할 수 있는 칸은 상하좌우로 인접한 칸이다.

맵이 주어졌을 때, 최단 경로를 구해 내는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 1,000), M(1 ≤ M ≤ 1,000)이 주어진다. 다음 N개의 줄에 M개의 숫자로 맵이 주어진다. (1, 1)과 (N, M)은 항상 0이라고 가정하자.

출력
첫째 줄에 최단 거리를 출력한다. 불가능할 때는 -1을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static class Road {
		// 현재 위치 좌표
		int x, y;
		// 벽을 부순 횟수
		int broken = 0;
		// 현재까지 경로의 길이
		int dist;

		public Road(int x, int y, int broken, int dist) {
			this.x = x;
			this.y = y;
			this.broken = broken;
			this.dist = dist;
		}
	}

	public static void main(String[] args) throws IOException {
		// 격자 그래프에서 시작점부터 도착지점까지의 최단경로를 BFS로 찾을 것이다.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int result = -1;

		int[][] graph = new int[n][m];
		for (int i = 0; i < n; i++) {
			String line = br.readLine().trim();
			for (int j = 0; j < m; j++) {
				graph[i][j] = line.charAt(j) - '0';
			}
		}

		/*
		 visited[0][0][0] -> 아직 벽을 부순적이 없는 경우
		 visited[0][0][1] -> 이미 다른 벽을 부쉈을 경우
		*/
		boolean[][][] visited = new boolean[n][m][2];

		ArrayDeque<Road> pq = new ArrayDeque<>();
		// (1, 1) 부터 (n, m)까지 가는경로라고 하니까 시작점의 x = 0, y = 0, 도착지점의 x = n-1, y = m-1 로 지정
		// 시작점 추가 및 visited 설정
		pq.offer(new Road(0, 0, 0, 1));
		visited[0][0][0] = true;

		//          상, 하, 좌, 우
		int[] dr = {-1, 1, 0, 0};
		int[] dc = {0, 0, -1, 1};

		while (!pq.isEmpty()) {
			Road cur = pq.poll();
			int curX = cur.x;
			int curY = cur.y;
			int curBroken = cur.broken;
			int curDist = cur.dist;

			// 만약 루프가 도착점에 도착하면 기록 후 탈출
			if (curX == n - 1 && curY == m - 1) {
				result = curDist;
				break;
			}

			for (int i = 0; i < 4; i++) {
				// 4방향으로 돌리기
				int nextX = curX + dr[i];
				int nextY = curY + dc[i];

				// 길이 갈 수 없는 곳이면 넘기기
				if (nextX < 0 || nextY < 0 || nextX >= n || nextY >= m) continue;

				// 다음 칸이 0인 경우
				if (graph[nextX][nextY] == 0) {
					if (!visited[nextX][nextY][curBroken]) {
						visited[nextX][nextY][curBroken] = true;
						pq.offer(new Road(nextX, nextY, curBroken, curDist + 1));
					}
				}

				// 다음 칸이 1인 경우
				else {
					if (curBroken == 0 && !visited[nextX][nextY][1]) {
						visited[nextX][nextY][1] = true;
						pq.offer(new Road(nextX, nextY, 1, curDist + 1));
					}
				}
			}
		}

		System.out.println(result);
	}
}