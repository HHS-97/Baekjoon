/*
문제
세계적인 도둑 상덕이는 보석점을 털기로 결심했다.

상덕이가 털 보석점에는 보석이 총 N개 있다. 각 보석은 무게 Mi와 가격 Vi를 가지고 있다. 상덕이는 가방을 K개 가지고 있고, 각 가방에 담을 수 있는 최대 무게는 Ci이다. 가방에는 최대 한 개의 보석만 넣을 수 있다.

상덕이가 훔칠 수 있는 보석의 최대 가격을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N과 K가 주어진다. (1 ≤ N, K ≤ 300,000)

다음 N개 줄에는 각 보석의 정보 Mi와 Vi가 주어진다. (0 ≤ Mi, Vi ≤ 1,000,000)

다음 K개 줄에는 가방에 담을 수 있는 최대 무게 Ci가 주어진다. (1 ≤ Ci ≤ 100,000,000)

모든 숫자는 양의 정수이다.

출력
첫째 줄에 상덕이가 훔칠 수 있는 보석 가격의 합의 최댓값을 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		//그리디 알고리즘과 우선순위 큐를 사용해서 문제풀이
		//가방을 최대 무게 오름차순으로 정렬
		//보석도 무게 오름차순으로 정렬
		//루프를 돌리며 현재 가방에 들어갈 수 있는 아직 남아있는 보석들을 우선순위 큐에 넣는다.
		//우선순위 큐를 이용해서 가장 가격이 비싼 보석을 꺼내서 가방에 넣는다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		//N과 K (1 ≤ N, K ≤ 300,000)
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		//N개 줄에는 각 보석의 정보 Mi와 Vi (0 ≤ Mi, Vi ≤ 1,000,000)
		//jewels[i][0] = 무게
		//jewels[i][1] = 가격
		int[][] jewels = new int[N][2];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			jewels[i][0] = Integer.parseInt(st.nextToken());
			jewels[i][1] = Integer.parseInt(st.nextToken());
		}

		//K개 줄에는 가방에 담을 수 있는 최대 무게 Ci (1 ≤ Ci ≤ 100,000,000)
		//bags[i] = 최대 무게
		int[] bags = new int[K];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			bags[i] = Integer.parseInt(st.nextToken());
		}

		//가방을 최대 무게 오름차순으로 정렬
		Arrays.sort(bags);
		//보석도 무게 오름차순으로 정렬
		Arrays.sort(jewels, (a, b) -> Integer.compare(a[0], b[0]));

		//보석을 저장할 우선순위 큐 (보석의 가격만 알고 있으면 되니까 무게는 비교만하고 우선순위 큐에는 가격을 넣는다.)
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		
		//더이상 가방에 들어갈 수 없는 무게면 해당 변수에 현재 idx를 저장
		int idx = 0;

		//구해야할 값이 보석가격 합의 최댓값이니 합만 변수로 해서 계속 더해준다.
		long result = 0;

		//루프를 돌리며 현재 가방에 들어갈 수 있는 아직 남아있는 보석들을 우선순위 큐에 넣는다.
		for (int i = 0; i < K; i++) {
			//현재 가방의 최대 무게
			int maxW = bags[i];

			//보석을 pq에 넣기
			//idx 부터 루프를 돌림 보석이니까 N-1까지
			while (idx < N && jewels[idx][0] <= maxW) {
				pq.add(jewels[idx][1]);
				idx++;
			}

			//가장 가격이 비싼 보석을 꺼내서 가방에 넣는다.
			if (!pq.isEmpty()) result += (long) pq.poll();
		}

		System.out.println(result);
	}
}