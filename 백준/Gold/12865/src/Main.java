/*
문제
이 문제는 아주 평범한 배낭에 관한 문제이다.

한 달 후면 국가의 부름을 받게 되는 준서는 여행을 가려고 한다. 세상과의 단절을 슬퍼하며 최대한 즐기기 위한 여행이기 때문에, 가지고 다닐 배낭 또한 최대한 가치 있게 싸려고 한다.

준서가 여행에 필요하다고 생각하는 N개의 물건이 있다. 각 물건은 무게 W와 가치 V를 가지는데, 해당 물건을 배낭에 넣어서 가면 준서가 V만큼 즐길 수 있다. 아직 행군을 해본 적이 없는 준서는 최대 K만큼의 무게만을 넣을 수 있는 배낭만 들고 다닐 수 있다. 준서가 최대한 즐거운 여행을 하기 위해 배낭에 넣을 수 있는 물건들의 가치의 최댓값을 알려주자.

입력
첫 줄에 물품의 수 N(1 ≤ N ≤ 100)과 준서가 버틸 수 있는 무게 K(1 ≤ K ≤ 100,000)가 주어진다. 두 번째 줄부터 N개의 줄에 거쳐 각 물건의 무게 W(1 ≤ W ≤ 100,000)와 해당 물건의 가치 V(0 ≤ V ≤ 1,000)가 주어진다.

입력으로 주어지는 모든 수는 정수이다.

출력
한 줄에 배낭에 넣을 수 있는 물건들의 가치합의 최댓값을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 물품 수 n, 최대 무게 k
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		// n개의 물건들의 무게 w와 가치 v 배열
		int[] w = new int[n+1];
		int[] v = new int[n+1];

		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			w[i] = Integer.parseInt(st.nextToken());
			v[i] = Integer.parseInt(st.nextToken());
		}

//		int[][] dp = new int[n+1][k+1];

		// 배낭 문제를 dp(동적 계획법)로 해결한다.
		// dp[i][j]는 1번부터 i번 물건까지 고려했을 때,
		// 배낭의 허용 무게가 j일 경우 얻을 수 있는 최대 가치를 의미한다.
		//
		// dp[0][j]와 dp[i][0]은 물건이 없거나 용량이 0인 상태이므로 모두 0으로 초기화한다.
		//
		// i번째 물건의 무게와 가치를 각각 weight[i], value[i]라 할 때,
		// 현재 무게 j에서 i번째 물건을 선택하지 않으면 dp[i][j]는 dp[i-1][j]가 되고,
		// 선택할 수 있다면 dp[i-1][j - weight[i]] + value[i]와 비교하여 더 큰 값을 저장한다.
		//
		// 이 과정을 모든 물건과 모든 무게에 대해 반복하면,
		// dp[n][w]에 배낭의 무게 제한을 넘지 않으면서 얻을 수 있는 최대 가치가 저장된다.

//		for (int i = 1; i <= n; i++) {
//			for (int j = 1; j <= k; j++) {
//				// i번째 물건을 안 담는 경우(기본)
//				dp[i][j] = dp[i-1][j];
//
//				if (w[i] <= j) {
//					dp[i][j] = Math.max(
//							dp[i][j], // = dp[i-1][w] (안 담은 경우)
//							dp[i-1][j-w[i]] + v[i] // 담은 경우
//					);
//				}
//			}
//		}

		// 여기서 더 최적화해서 1차원 dp로 만들어보면
		int[] dp = new int[k+1];

		// 점화식을 보면 어차피 i-1에서만 값을 비교해서 1차원 dp가 가능하다.
		for (int i = 1; i <= n; i++) {
			// 앞에서부터 가면, dp[w - weight[i]]가 이미 i번째 물건을 반영한 값으로 덮어써질 수 있으니 뒤에서 간다
			for (int j = k; j >= w[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j-w[i]] + v[i]);
			}
		}

		System.out.println(dp[k]);
	}
}