/*
문제
상근이는 1층 빌딩에 침입해 매우 중요한 문서를 훔쳐오려고 한다. 상근이가 가지고 있는 평면도에는 문서의 위치가 모두 나타나 있다. 빌딩의 문은 모두 잠겨있기 때문에, 문을 열려면 열쇠가 필요하다. 상근이는 일부 열쇠를 이미 가지고 있고, 일부 열쇠는 빌딩의 바닥에 놓여져 있다. 상근이는 상하좌우로만 이동할 수 있다.

상근이가 훔칠 수 있는 문서의 최대 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 테스트 케이스의 개수가 주어진다. 테스트 케이스의 수는 100개를 넘지 않는다.

각 테스트 케이스의 첫째 줄에는 지도의 높이와 너비 h와 w (2 ≤ h, w ≤ 100)가 주어진다. 다음 h개 줄에는 빌딩을 나타내는 w개의 문자가 주어지며, 각 문자는 다음 중 하나이다.

'.'는 빈 공간을 나타낸다.
'*'는 벽을 나타내며, 상근이는 벽을 통과할 수 없다.
'$'는 상근이가 훔쳐야하는 문서이다.
알파벳 대문자는 문을 나타낸다.
알파벳 소문자는 열쇠를 나타내며, 그 문자의 대문자인 모든 문을 열 수 있다.
마지막 줄에는 상근이가 이미 가지고 있는 열쇠가 공백없이 주어진다. 만약, 열쇠를 하나도 가지고 있지 않는 경우에는 "0"이 주어진다.

상근이는 처음에는 빌딩의 밖에 있으며, 빌딩 가장자리의 벽이 아닌 곳을 통해 빌딩 안팎을 드나들 수 있다. 각각의 문에 대해서, 그 문을 열 수 있는 열쇠의 개수는 0개, 1개, 또는 그 이상이고, 각각의 열쇠에 대해서, 그 열쇠로 열 수 있는 문의 개수도 0개, 1개, 또는 그 이상이다. 열쇠는 여러 번 사용할 수 있다.

출력
각 테스트 케이스 마다, 상근이가 훔칠 수 있는 문서의 최대 개수를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		//BFS를 이용해 그래프를 탐색한다.
		//visited를 h+2 w+2 크기로 만들어서 밖에서 부터 안으로 들어가는 것을 구현
		//waiting[door] 배열을 만들어서 열쇠가 없어서 문앞에서 막혔을때 나중에 열쇠를 얻었을 경우 큐에 넣을 수 있게 함
		//waiting에 넣을 경우 visited에 기록하지 않고 큐에 넣지 않는다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//테스트 케이스의 개수
		int T = Integer.parseInt(st.nextToken());

		for (int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine());
			//지도의 높이와 너비 h와 w (2 ≤ h, w ≤ 100)
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());

			//존재하는 문서의 갯수
			int maxDoc = 0;

			//현재 모은 문서의 갯수
			int docCount = 0;

			//h개 줄에는 빌딩을 나타내는 w개의 문자
			char[][] graph = new char[h+2][w+2];

			for (int i = 0; i < h+2; i++) {
				Arrays.fill(graph[i], '.');
			}

			for (int i = 1; i < h+1; i++) {
				st = new StringTokenizer(br.readLine());
				String str = st.nextToken();
				for (int j = 0; j < w; j++) {
					char chr = str.charAt(j);
					graph[i][j+1] = chr;
					if (chr == '$') maxDoc++;
				}
			}

			//상근이가 이미 가지고 있는 열쇠
			st = new StringTokenizer(br.readLine());
			String keys = st.nextToken();
			boolean[] hasKey = new boolean[26];

			for (int i = 0; i < keys.length(); i++) {
				//만약 0이면 이미 가지고 있는 열쇠가 없는 것이므로 넘기기
				if (keys.equals("0")) break;
				int key = keys.charAt(i) - 'a';
				hasKey[key] = true;
			}

			//BFS를 위한 q와 문 앞 대기열을 저장할 waiting
			ArrayDeque<int[]> q = new ArrayDeque<>();
			ArrayDeque<int[]>[] waiting = new ArrayDeque[26];
			boolean[][] visited = new boolean[h+2][w+2];

			for (int i = 0; i < 26; i++) {
				waiting[i] = new ArrayDeque<>();
			}

			//시작점
			q.add(new int[] {0, 0});
			visited[0][0] = true;

			//4방향 상하좌우
			int[] dr = {-1, 1, 0, 0};
			int[] dc = {0, 0, -1, 1};

			while (!q.isEmpty()) {
				//만약 존재하는 문서를 모두 모았으면 바로 끝내기
				if (maxDoc == docCount) break;
				int[] cur = q.poll();
				int curR = cur[0];
				int curC = cur[1];

				for (int i = 0; i < 4; i++) {
					int nr = curR + dr[i];
					int nc = curC + dc[i];

					if (nr < 0 || nr >= h+2 || nc < 0 || nc >= w+2) continue;

					char curChr = graph[nr][nc];

					//방문했거나 벽이면 넘어가기
					if (visited[nr][nc] || curChr == '*') continue;
					//문서면 수집하기
					if (curChr == '$') docCount++;
					else if (curChr != '.') {
						int curChrInt = curChr - 'a';
						//만약 열쇠면 hasKey에 true로 등록하고 waiting 대기열에 있는 queue를 q에 넣어준다.
						if ((curChrInt) >= 0 && (curChrInt) <= 25 ) {
							hasKey[curChrInt] = true;
							if (!waiting[curChrInt].isEmpty()) q.addAll(waiting[curChrInt]);
						} else {
							//남은 경우가 문일 경우 밖에 없으니 문일 경우 열쇠의 유무를 확인하고 없으면 waiting에 넣어준다.
							char doorKey = Character.toLowerCase(curChr);
							int doorKeyInt = doorKey - 'a';
							if (!hasKey[doorKeyInt]) {
								waiting[doorKeyInt].add(new int[] {nr, nc});
								continue;
							}
						}
					}

					//q에 넣어주고 방문 처리
					q.add(new int[] {nr, nc});
					visited[nr][nc] = true;

				}
			}

			System.out.println(docCount);
		}

	}
}