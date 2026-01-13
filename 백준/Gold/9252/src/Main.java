/*
문제
LCS(Longest Common Subsequence, 최장 공통 부분 수열)문제는 두 수열이 주어졌을 때, 모두의 부분 수열이 되는 수열 중 가장 긴 것을 찾는 문제이다.

예를 들어, ACAYKP와 CAPCAK의 LCS는 ACAK가 된다.

입력
첫째 줄과 둘째 줄에 두 문자열이 주어진다. 문자열은 알파벳 대문자로만 이루어져 있으며, 최대 1000글자로 이루어져 있다.

출력
첫째 줄에 입력으로 주어진 두 문자열의 LCS의 길이를, 둘째 줄에 LCS를 출력한다.

LCS가 여러 가지인 경우에는 아무거나 출력하고, LCS의 길이가 0인 경우에는 둘째 줄을 출력하지 않는다.
 */

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String A = " " + br.readLine();
		String B = " " + br.readLine();

		//최장 공통 부분 수열
		//점화식
		/*
		문자가 같으면 C(i,j) = C(i-1,j-1)+1
		문자가 다르면 C(i,j) = max(C(i-1,j),C(i,j-1)
		 */
		//행렬을 이용해서 문자를 비교하고 기록을 저장한다. dp
		int[][] arr = new int[A.length()][B.length()];

		for (int i = 1; i < A.length(); i++) {
			for (int j = 1; j < B.length(); j++) {
				// 만약 X[i] == Y[j] 라면 이 문자는 LCS의 마지막 글자로 쓸 수 있다
				if (A.charAt(i) == B.charAt(j)) {
					arr[i][j] = arr[i-1][j-1] + 1;
				} else {
					//만약 X[i] != Y[j] 라면
					//두 문자를 동시에 LCS에 넣을 수 없다
					//둘 중 하나는 포기하고 나머지로 LCS를 구해야 한다.
					//둘 중 더 긴 것이 최적
					arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
				}
			}
		}

		//역추적 (LCS 문자열 복원)
		int i = A.length() - 1, j = B.length() - 1;
		StringBuilder sb = new StringBuilder();

		while (i > 0 && j > 0) {
			if (A.charAt(i) == B.charAt(j)) {
				//같은 문자면 채택 + 대각선 이동
				sb.append(A.charAt(i));
				i--;
				j--;
			} else {
				//다른 문자면 dp 값 큰 쪽으로 이동
				if (arr[i - 1][j] >= arr[i][j - 1]) {
					i--;
				} else {
					j--;
				}
			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int lcsLen = arr[A.length()-1][B.length()-1];
		bw.write(lcsLen+"\n");
		if(lcsLen != 0) {
			bw.write(sb.reverse().toString());
		}
		bw.close();
	}
}