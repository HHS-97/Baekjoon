/*
문제
상근이의 여동생 상냥이는 문방구에서 스티커 2n개를 구매했다. 스티커는 그림 (a)와 같이 2행 n열로 배치되어 있다. 상냥이는 스티커를 이용해 책상을 꾸미려고 한다.

상냥이가 구매한 스티커의 품질은 매우 좋지 않다. 스티커 한 장을 떼면, 그 스티커와 변을 공유하는 스티커는 모두 찢어져서 사용할 수 없게 된다. 즉, 뗀 스티커의 왼쪽, 오른쪽, 위, 아래에 있는 스티커는 사용할 수 없게 된다.



모든 스티커를 붙일 수 없게된 상냥이는 각 스티커에 점수를 매기고, 점수의 합이 최대가 되게 스티커를 떼어내려고 한다. 먼저, 그림 (b)와 같이 각 스티커에 점수를 매겼다. 상냥이가 뗄 수 있는 스티커의 점수의 최댓값을 구하는 프로그램을 작성하시오. 즉, 2n개의 스티커 중에서 점수의 합이 최대가 되면서 서로 변을 공유 하지 않는 스티커 집합을 구해야 한다.

위의 그림의 경우에 점수가 50, 50, 100, 60인 스티커를 고르면, 점수는 260이 되고 이 것이 최대 점수이다. 가장 높은 점수를 가지는 두 스티커 (100과 70)은 변을 공유하기 때문에, 동시에 뗄 수 없다.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스의 첫째 줄에는 n (1 ≤ n ≤ 100,000)이 주어진다. 다음 두 줄에는 n개의 정수가 주어지며, 각 정수는 그 위치에 해당하는 스티커의 점수이다. 연속하는 두 정수 사이에는 빈 칸이 하나 있다. 점수는 0보다 크거나 같고, 100보다 작거나 같은 정수이다.

출력
각 테스트 케이스 마다, 2n개의 스티커 중에서 두 변을 공유하지 않는 스티커 점수의 최댓값을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int t =  Integer.parseInt(st.nextToken());

		for (int i = 0; i < t; i++) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int[][] stickers = new int[2][n];

			for (int j = 0; j < 2; j++) {
				st = new StringTokenizer(br.readLine());
				for (int k = 0; k < n; k++) {
					stickers[j][k] = Integer.parseInt(st.nextToken());
				}
			}

			// dp를 이용해서 위를 선택했을 때는 dp[0][j], 아래를 선택했을 때는 dp[1][j], 아무것도 선택하지 않았을 때는 dp[2][j]에 저장한다. 그 후에 j+1 열에서는 j에서 선택할 수 있는 값 중에 제일 큰 값을 더한다. dp[2]의 경우는 아무것도 선택하지 않는 경우니까 j에서 제일 큰 값을 저장한다.

			int[][] dp = new int[3][n];
			dp[0][0] = stickers[0][0]; // 위 선택의 경우니까 이전의 1, 2의 경우만 가져옴
			dp[1][0] = stickers[1][0]; // 아래 선택의 경우니까 이전의 0, 2의 경우만 가져옴
			dp[2][0] = 0; // 아무것도 선택하지 않는 경우니까 모든 경우를 가져옴

			for (int j = 1; j < n; j++) {
				dp[0][j] = Math.max(dp[1][j-1], dp[2][j-1]) + stickers[0][j];
				dp[1][j] = Math.max(dp[0][j-1], dp[2][j-1]) + stickers[1][j];
				dp[2][j] = Math.max(Math.max(dp[0][j-1], dp[1][j-1]), dp[2][j-1]);
			}


			bw.write(Math.max(Math.max(dp[0][n-1], dp[1][n-1]), dp[2][n-1]) + "\n");
		}

		bw.flush();
		bw.close();
	}
}