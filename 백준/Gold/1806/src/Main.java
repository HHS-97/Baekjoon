/*
문제
10,000 이하의 자연수로 이루어진 길이 N짜리 수열이 주어진다. 이 수열에서 연속된 수들의 부분합 중에 그 합이 S 이상이 되는 것 중, 가장 짧은 것의 길이를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N (10 ≤ N < 100,000)과 S (0 < S ≤ 100,000,000)가 주어진다. 둘째 줄에는 수열이 주어진다. 수열의 각 원소는 공백으로 구분되어져 있으며, 10,000이하의 자연수이다.

출력
첫째 줄에 구하고자 하는 최소의 길이를 출력한다. 만일 그러한 합을 만드는 것이 불가능하다면 0을 출력하면 된다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 수열의 길이 N
		int N = Integer.parseInt(st.nextToken());
		// 부분합 최저치 S
		int S = Integer.parseInt(st.nextToken());
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		// 투 포인터로 부분합 구하기
		int start = 0, end = 0;

		// 최소길이
		int minLen = N + 1;
		// 현재 부분합
		int nowSum = 0;

		int result = 0;

		while (true) {
			if (nowSum >= S) {
				minLen = Math.min(minLen, end - start);
				nowSum -= arr[start++];
			} else {
				if (end == N) break;
				nowSum += arr[end++];
			}
		}

		if (minLen != N+1) result = minLen;

		System.out.println(result);
	}
}