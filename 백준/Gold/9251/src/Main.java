/*
문제
LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때, 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.

예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.

입력
첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 최대 1000글자로 이루어져 있다.

출력
첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		// 최장 공통 부분 수열 문제
		// dp 알고리즘을 사용하여 문제를 풀이
		// LCS는 최적 부분 구조를 가지는 문제이기 때문에 DP로 해결할 수 있다.
		// dp[i][j]는 문자열 X의 앞 i글자와 문자열 Y의 앞 j글자의 LCS 길이를 의미한다.
		// 만약 X[i]와 Y[j]가 같다면 해당 문자는 공통 부분수열의 마지막 문자로 사용할 수 있으므로
		// dp[i][j]는 dp[i-1][j-1]에 1을 더한 값이 된다.
		// 반면 두 문자가 다를 경우, 두 문자를 동시에 사용할 수 없기 때문에
		// X의 문자를 제외한 경우(dp[i-1][j])와 Y의 문자를 제외한 경우(dp[i][j-1]) 중
		// 더 큰 값이 전체 LCS의 최적해가 되어 dp[i][j]는 그 최대값으로 결정된다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String x = " " + st.nextToken();
		st = new StringTokenizer(br.readLine());
		String y = " " + st.nextToken();

		int[][] dp = new int[x.length()][y.length()];
		for(int i = 1; i < x.length(); i++) {
			for(int j = 1; j < y.length(); j++) {
				if (x.charAt(i) == y.charAt(j)) dp[i][j] = dp[i-1][j-1] + 1;
				else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}

		// dp에서 제일 끝의 값(x.length()-1, y.length()-1)이 최장 공통 부분수열이다.
		System.out.println(dp[x.length()-1][y.length()-1]);
	}
}