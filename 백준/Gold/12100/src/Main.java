/*
문제
2048 게임은 4×4 크기의 보드에서 혼자 즐기는 재미있는 게임이다. 이 링크를 누르면 게임을 해볼 수 있다.

이 게임에서 한 번의 이동은 보드 위에 있는 전체 블록을 상하좌우 네 방향 중 하나로 이동시키는 것이다. 이때, 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지게 된다. 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 다시 합쳐질 수 없다. (실제 게임에서는 이동을 한 번 할 때마다 블록이 추가되지만, 이 문제에서 블록이 추가되는 경우는 없다)


<그림 1>	<그림 2>	<그림 3>
<그림 1>의 경우에서 위로 블록을 이동시키면 <그림 2>의 상태가 된다. 여기서, 왼쪽으로 블록을 이동시키면 <그림 3>의 상태가 된다.


<그림 4>	<그림 5>	<그림 6>	<그림 7>
<그림 4>의 상태에서 블록을 오른쪽으로 이동시키면 <그림 5>가 되고, 여기서 다시 위로 블록을 이동시키면 <그림 6>이 된다. 여기서 오른쪽으로 블록을 이동시켜 <그림 7>을 만들 수 있다.


<그림 8>	<그림 9>
<그림 8>의 상태에서 왼쪽으로 블록을 옮기면 어떻게 될까? 2가 충돌하기 때문에, 4로 합쳐지게 되고 <그림 9>의 상태가 된다.


<그림 10>	<그림 11>	<그림 12>	<그림 13>
<그림 10>에서 위로 블록을 이동시키면 <그림 11>의 상태가 된다.

<그림 12>의 경우에 위로 블록을 이동시키면 <그림 13>의 상태가 되는데, 그 이유는 한 번의 이동에서 이미 합쳐진 블록은 또 합쳐질 수 없기 때문이다.


<그림 14>	<그림 15>
마지막으로, 똑같은 수가 세 개가 있는 경우에는 이동하려고 하는 쪽의 칸이 먼저 합쳐진다. 예를 들어, 위로 이동시키는 경우에는 위쪽에 있는 블록이 먼저 합쳐지게 된다. <그림 14>의 경우에 위로 이동하면 <그림 15>를 만든다.

이 문제에서 다루는 2048 게임은 보드의 크기가 N×N 이다. 보드의 크기와 보드판의 블록 상태가 주어졌을 때, 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 보드의 크기 N (1 ≤ N ≤ 20)이 주어진다. 둘째 줄부터 N개의 줄에는 게임판의 초기 상태가 주어진다. 0은 빈 칸을 나타내며, 이외의 값은 모두 블록을 나타낸다. 블록에 쓰여 있는 수는 2보다 크거나 같고, 1024보다 작거나 같은 2의 제곱꼴이다. 블록은 적어도 하나 주어진다.

출력
최대 5번 이동시켜서 얻을 수 있는 가장 큰 블록을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {

	static class GameQueue {
		//게임판 상태
		int[][] map;
		//이동횟수
		int count;
		GameQueue(int[][] map, int count) {
			this.map = map;
			this.count = count;
		}
	}

	//병합용 메서드
	static int[] merge (int[] line, int N) {
		//0제거해서 숫자만 남기기
		int[] temp = new int[N];
		int t = 0;
		for (int j : line) {
			if(j != 0) temp[t++] = j;
		}

		//같은 숫자 병합하기
		int[] result = new int[N];
		int r = 0;
		int i = 0;
		while (i < t) {
			if (i+1 < t && temp[i] == temp[i+1]) {
				result[r++] = temp[i] * 2;
				//합쳤으니까 2칸 점프하기
				i += 2;
			} else {
				result[r++] = temp[i];
				i++;
			}
		}

		return result;
	}

	//위로 병합하는 메서드
	static int[][] mergeUp (int[][] map, int N) {
		//열을 위 -> 아래로 뽑아서 merge에 보낸 후 열에 다시넣기
		int[][] result = new int[N][N];
		for (int i = 0; i < N; i++) {
			int[] temp = new int[N];
			for (int j = 0; j < N; j++) {
				temp[j] = map[j][i];
			}

			temp = merge(temp, N);

			for (int j = 0; j < N; j++) {
				result[j][i] = temp[j];
			}
		}

		return result;
	}

	//아래로 병합하는 메서드
	static int[][] mergeDown (int[][] map, int N) {
		//열을 아래 -> 위로 뽑아서 merge에 보낸 후 결과를 뒤집어서 열에 다시넣기
		int[][] result = new int[N][N];
		for (int i = 0; i < N; i++) {
			int[] temp = new int[N];
			int t = 0;
			for (int j = N-1; j >= 0; j--) {
				temp[t++] = map[j][i];
			}

			temp = merge(temp, N);
			int idx = 0;
			for (int j = N-1; j >= 0; j--) {
				result[j][i] = temp[idx++];
			}
		}

		return result;
	}

	//오른쪽으로 병합하는 메서드
	static int[][] mergeRight (int[][] map, int N) {
		//행을 뒤집어서 merge에 보낸 후 결과를 뒤집어서 행에 다시넣기
		int[][] result = new int[N][N];
		for (int i = 0; i < N; i++) {
			int[] temp = new int[N];
			int t = 0;
			for (int j = N-1; j >= 0; j--) {
				temp[t++] = map[i][j];
			}

			temp = merge(temp, N);
			int idx = 0;
			for (int j = N-1; j >= 0; j--) {
				result[i][j] = temp[idx++];
			}
		}

		return result;
	}

	//왼쪽으로 병합하는 메서드
	static int[][] mergeLeft (int[][] map, int N) {
		//행을 그대로 뽑아서 merge에 보낸 후 행에 다시넣기
		int[][] result = new int[N][N];
		for (int i = 0; i < N; i++) {
			int[] temp = new int[N];
			for (int j = 0; j < N; j++) {
				temp[j] = map[i][j];
			}

			temp = merge(temp, N);

			for (int j = 0; j < N; j++) {
				result[i][j] = temp[j];
			}
		}

		return result;
	}

	public static void main(String[] args) throws IOException {
		//최대 5번 이동 시킬 수 있으니 브루트포스에 queue를 사용해서 최대 값을 구한다.
		//한 번 queue에서 꺼낼때 4방향으로 돌린 다음 queue에 넣어준다.
		//queue에는 현재 이동시킨 횟수와 현재 상태를 넣어준다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		//보드의 크기 N (1 ≤ N ≤ 20)
		int N = Integer.parseInt(st.nextToken());

		//N개의 줄에는 게임판의 초기 상태
		int[][] game = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				game[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		ArrayDeque<GameQueue> queue = new ArrayDeque<>();
		queue.add(new GameQueue(game, 0));

		//최대값
		int maxNum = 0;

		while (!queue.isEmpty()) {
			GameQueue g = queue.poll();

			//5번 이동했으면 최대값 비교하고 넘기기
			if (g.count == 5) {
				for (int i = 0; i < N; i++) {
					for (int j = 0; j < N; j++) {
						if (g.map[i][j] > maxNum) {
							maxNum = g.map[i][j];
						}
					}
				}

				continue;
			}

			//4방향 병합 메서드로 병합후에 큐에 넣기
			int[][] upQueue = mergeUp(g.map, N);
			queue.add(new GameQueue(upQueue, g.count + 1));
			int[][] downQueue = mergeDown(g.map, N);
			queue.add(new GameQueue(downQueue, g.count + 1));
			int[][] leftQueue = mergeLeft(g.map, N);
			queue.add(new GameQueue(leftQueue, g.count + 1));
			int[][] rightQueue = mergeRight(g.map, N);
			queue.add(new GameQueue(rightQueue, g.count + 1));
		}

		System.out.println(maxNum);
	}
}