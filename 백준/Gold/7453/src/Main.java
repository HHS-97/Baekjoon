/*
문제
정수로 이루어진 크기가 같은 배열 A, B, C, D가 있다.

A[a], B[b], C[c], D[d]의 합이 0인 (a, b, c, d) 쌍의 개수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 배열의 크기 n (1 ≤ n ≤ 4000)이 주어진다. 다음 n개 줄에는 A, B, C, D에 포함되는 정수가 공백으로 구분되어져서 주어진다. 배열에 들어있는 정수의 절댓값은 최대 2^28이다.

출력
합이 0이 되는 쌍의 개수를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		//밋 인더 미들과 정렬 후 투포인터를 이용해서 문제풀이
		//밋 인더 미들
		//모든 A[a]와 B[b]의 합을 AB 배열에 담는다.
		//모든 C[c]와 D[d]의 합을 CD 배열에 담는다.
		//그 후에 AB와 CD 배열을 정렬한다.
		//투 포인터를 이용해서 AB와 CD의 합이 0이되는 경우의 수를 구한다.
		//AB는 인덱스를 0부터 시작하고 CD는 인덱스를 CD의 길이 - 1 부터 시작한다.
		//AB[i] + CD[j]가 0보다 작으면 i++ 0보다 크면 j--를 한다.
		//만약 0이라면 중복되는 개수를 센다. 그 후 중복되는 AB와 CD의 갯수를 곱한 후 결과에 더함

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		long result = 0;

		//배열의 크기 n (1 ≤ n ≤ 4000)
		int n = Integer.parseInt(st.nextToken());
		//n개 줄에는 A, B, C, D에 포함되는 정수
		int[] A = new int[n];
		int[] B = new int[n];
		int[] C = new int[n];
		int[] D = new int[n];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			A[i] = Integer.parseInt(st.nextToken());
			B[i] = Integer.parseInt(st.nextToken());
			C[i] = Integer.parseInt(st.nextToken());
			D[i] = Integer.parseInt(st.nextToken());
		}

		int sumArrSize = n*n;

		int[] AB = new int[sumArrSize];
		int[] CD = new int[sumArrSize];
		int nowSumArridx = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				//모든 A[a]와 B[b]의 합을 AB 배열에 담는다.
				AB[nowSumArridx] = A[i] + B[j];
				//모든 C[c]와 D[d]의 합을 CD 배열에 담는다.
				CD[nowSumArridx] = C[i] + D[j];

				nowSumArridx++;
			}
		}

		//그 후에 AB와 CD 배열을 정렬한다.
		Arrays.sort(AB);
		Arrays.sort(CD);

		//투 포인터를 이용해서 AB와 CD의 합이 0이되는 경우의 수를 구한다.
		int sum = 0;
		//AB는 인덱스를 0부터 시작하고 CD는 인덱스를 CD의 길이 - 1 부터 시작한다.
		int i = 0;
		int j = CD.length - 1;

		while (i < sumArrSize && j >= 0) {
			//AB[i] + CD[j]가 0보다 작으면 i++ 0보다 크면 j--를 한다.
			sum = AB[i] + CD[j];

			if (sum < 0) i++;
			else if (sum > 0) j--;
			else {
				//만약 0이라면 중복되는 개수를 센다. 그 후 중복되는 AB와 CD의 갯수를 곱한 후 결과에 더함
				int nowAB = AB[i];
				int nowCD = CD[j];
				long countAB = 0;
				long countCD = 0;

				while (i < sumArrSize && AB[i] == nowAB) {
					countAB++;
					i++;
				}

				while (j >= 0 && CD[j] == nowCD) {
					countCD++;
					j--;
				}

				result += countAB * countCD;
			}
		}

		System.out.println(result);
	}
}