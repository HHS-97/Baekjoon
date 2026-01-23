/*
문제
45656이란 수를 보자.

이 수는 인접한 모든 자리의 차이가 1이다. 이런 수를 계단 수라고 한다.

N이 주어질 때, 길이가 N이면서 0부터 9까지 숫자가 모두 등장하는 계단 수가 총 몇 개 있는지 구하는 프로그램을 작성하시오. 0으로 시작하는 수는 계단수가 아니다.

입력
첫째 줄에 N이 주어진다. N은 1보다 크거나 같고, 100보다 작거나 같은 자연수이다.

출력
첫째 줄에 정답을 1,000,000,000으로 나눈 나머지를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final int MOD = 1_000_000_000;
	static final int FULL = (1 << 10) - 1;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//비트 마스크를 이용한 dp로 문제풀이
		int N = Integer.parseInt(st.nextToken());

		//dpCur[last][mask], dpNext[last][mask]
		int[][] dpCur = new int[10][1 << 10];
		int[][] dpNext = new int[10][1 << 10];

		//초기값으로 길이1, 시작 숫자 1~9로 초기화해준다
		for (int i = 1; i <= 9; i++) {
			dpCur[i][1 << i] = 1;
		}

		// 길이를 1에서 N까지 한 자리씩 늘려가며 dp를 진행
		// 이 반복문 하나가 길이를 1 증가시키는 단계를 의미
		//	dpCur는 길이가 len인 경우의 수를 담는 배열이고,
		// dpNext는 길이가 len+1인 계단 수들을 새로 만들어 담을 배열
		for (int len = 1; len < N; len++) {
			// dpNext 초기화
			// 길이가 변할 때마다 이전 계산 결과가 섞이면 안 되므로
			// 항상 초기화
			for (int i = 0; i < 10; i++) {
				Arrays.fill(dpNext[i], 0);
			}

			// last 반복문
			// 길이가 len인 모든 계단 수 중에서
			// last(마지막 자리수)가 0~9인 경우를 전부 순회
			for (int last = 0; last < 10; last++) {

				// mask 반복문
				// 같은 last라도 mask는 서로 다르므로
				// FULL까지 모든 비트마스크를 순회
				for (int mask = 0; mask <= FULL; mask++) {
					// dpCur[last][mask]는
					// 길이가 len이고, 마지막 숫자가 last이며,
					// 사용 숫자 집합이 mask인 계단 수의 개수를 의미
					int val = dpCur[last][mask];
					if (val == 0) continue;

					// 이 상태에서 계단 수 규칙에 따라
					// 다음 자리로 이동 가능한 경우는 last-1, last+1
					// next = last-1 or last+1
					// 새로 사용한 숫자를 mask에 반영(nmask)
					// val를 dpNext[next][nmask]에 누적
					if (last - 1 >= 0) {
						int next = last - 1;
						int nmask = mask | (1 << next);
						dpNext[next][nmask] = (dpNext[next][nmask] + val) % MOD;
					}

					if (last + 1 <= 9) {
						int next = last + 1;
						int nmask = mask | (1 << next);
						dpNext[next][nmask] = (dpNext[next][nmask] + val) % MOD;
					}
				}
			}

			// dpNext를 dpCur로 교체 그리고 dpCur는 len+1이 됨
			int[][] temp = dpCur;
			dpCur = dpNext;
			dpNext = temp;
		}

		long result = 0;
		for (int i = 0; i <= 9; i++) {
			result += dpCur[i][FULL];
		}

		System.out.println(result % MOD);
	}
}