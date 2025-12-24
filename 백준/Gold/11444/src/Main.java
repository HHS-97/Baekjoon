/*
문제
피보나치 수는 0과 1로 시작한다. 0번째 피보나치 수는 0이고, 1번째 피보나치 수는 1이다. 그 다음 2번째 부터는 바로 앞 두 피보나치 수의 합이 된다.

이를 식으로 써보면 Fn = Fn-1 + Fn-2 (n ≥ 2)가 된다.

n=17일때 까지 피보나치 수를 써보면 다음과 같다.

0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597

n이 주어졌을 때, n번째 피보나치 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 n이 주어진다. n은 1,000,000,000,000,000,000보다 작거나 같은 자연수이다.

출력
첫째 줄에 n번째 피보나치 수를 1,000,000,007으로 나눈 나머지를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static final long MOD = 1_000_000_007L;

	// 반환: long[2] = {F(n), F(n+1)} (mod MOD)
	static long[] fib (long n) {
		if (n == 0) return new long[] {0L, 1L};

		// n을 반으로 나눈 값에 대한 피보나치 결과
		long[] half = fib(n / 2);
		long a = half[0]; // F(k)
		long b = half[1]; // F(k+1)

		// c = F(2k), d = F(2k+1)
		long twoB = (2L * b) % MOD;
		long inner = (twoB - a + MOD) % MOD;
		// c = F(k) * (2*F(k+1) - F(k))
		long c = (a * inner) % MOD;

		// d = F(k)^2 + F(k+1)^2
		long d = ((a * a) % MOD + (b * b) % MOD) % MOD;

		if (n % 2 == 0) {
			return new long[]{c, d};
		} else {
			// F(2k+1) = d, F(2k+2) = c + d
			long e = (c + d) % MOD;
			return new long[]{d, e};
		}
	}
	public static void main(String[] args) throws IOException {
		// 피보나치 수열을 분할정복을 이용하여 풀이
		// 피보나치를 (F(n), F(n+1)) 쌍으로 같이 구하면, n을 절반으로 줄이면서 한 번에 계산
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long n = Long.parseLong(br.readLine().trim());

		long ans = fib(n)[0]; // F(n)
		System.out.println(ans);
	}
}