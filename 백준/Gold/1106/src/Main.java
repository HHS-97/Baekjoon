/*
문제
세계적인 호텔인 형택 호텔의 사장인 김형택은 이번에 수입을 조금 늘리기 위해서 홍보를 하려고 한다.

형택이가 홍보를 할 수 있는 도시가 주어지고, 각 도시별로 홍보하는데 드는 비용과, 그 때 몇 명의 호텔 고객이 늘어나는지에 대한 정보가 있다.

예를 들어, “어떤 도시에서 9원을 들여서 홍보하면 3명의 고객이 늘어난다.”와 같은 정보이다. 이때, 이러한 정보에 나타난 돈에 정수배 만큼을 투자할 수 있다. 즉, 9원을 들여서 3명의 고객, 18원을 들여서 6명의 고객, 27원을 들여서 9명의 고객을 늘어나게 할 수 있지만, 3원을 들여서 홍보해서 1명의 고객, 12원을 들여서 4명의 고객을 늘어나게 할 수는 없다.

각 도시에는 무한 명의 잠재적인 고객이 있다. 이때, 호텔의 고객을 적어도 C명 늘이기 위해 형택이가 투자해야 하는 돈의 최솟값을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 C와 형택이가 홍보할 수 있는 도시의 개수 N이 주어진다. C는 1,000보다 작거나 같은 자연수이고, N은 20보다 작거나 같은 자연수이다. 둘째 줄부터 N개의 줄에는 각 도시에서 홍보할 때 대는 비용과 그 비용으로 얻을 수 있는 고객의 수가 주어진다. 이 값은 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 문제의 정답을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int INF = 1_000_000_000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int c = Integer.parseInt(st.nextToken());
		int n = Integer.parseInt(st.nextToken());

		int[] cost = new int[n];
		int[] customers = new int[n];

		int maxCustomer = 0;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			cost[i] = Integer.parseInt(st.nextToken());
			customers[i] = Integer.parseInt(st.nextToken());
			maxCustomer = Math.max(customers[i], maxCustomer);
		}

		// 적어도 C명이니까, 최대로 늘어날 수 있는 고객의 수는 c + maxCustomer(한 번의 홍보로 늘어날 수 있는 최대 고객의 수)
		int limit = c + maxCustomer;

		// 고객을 j명 만들기까지의 최소 비용
		int[] dp = new int[limit+1];
		Arrays.fill(dp, INF);
		dp[0] = 0;

		for (int i = 0; i < n; i++) {
			// 고객을 j명 만들기 위한 최소 비용을 갱신
			// j - customers[i]명을 만드는 최소 비용에
			// 현재 도시를 한 번 더 홍보했을 때의 비용(cost[i])을 더한 값과
			// 기존 dp[j] 값을 비교하여 더 작은 값으로 갱신
			// 이때 j를 customers[i]부터 limit까지 오름차순으로 순회하여
			// 같은 도시를 여러 번 사용할 수 있는 무한 배낭 효과를 반영하고,
			// 적어도 c명을 만족하기 위해 c를 초과하는 경우(limit)까지 함께 고려
			for (int j = customers[i]; j <= limit; j++) {
				dp[j] = Math.min(dp[j], dp[j-customers[i]] + cost[i]);
			}
		}

		int result = INF;
		for (int i = c; i <= limit; i++) {
			result = Math.min(result, dp[i]);
		}

		System.out.println(result);
	}
}