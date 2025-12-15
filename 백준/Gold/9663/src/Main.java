/*
문제
N-Queen 문제는 크기가 N × N인 체스판 위에 퀸 N개를 서로 공격할 수 없게 놓는 문제이다.

N이 주어졌을 때, 퀸을 놓는 방법의 수를 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. (1 ≤ N < 15)

출력
첫째 줄에 퀸 N개를 서로 공격할 수 없게 놓는 경우의 수를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static boolean[] usedCol;
	static boolean[] usedDiag1; // row + col 좌상우하 대각선 (좌표적으로 왼쪽수가 올라가고 오른쪽 수가 줄어들어서)
	static boolean[] usedDiag2; // row - col + (n-1) 우상좌하 대각선 (좌표적으로 오른쪽수가 올라가고 왼쪽 수가 줄어들어서)
	static int n;
	static int result = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		usedCol = new boolean[n];
		// row+col, row-col+(n-1)은 최대 2n-2
		usedDiag1 = new boolean[2*n-1];
		usedDiag2 = new boolean[2*n-1];

		nQueen(0);
		System.out.println(result);
	}

	static void nQueen(int row) {
		if (row == n) {
			result++;
			return;
		}

		for (int i = 0; i < n; i++) {
			int d1 = row + i;
			int d2 = row - i + (n -1);
			if (usedCol[i] || usedDiag1[d1] || usedDiag2[d2]) continue;
			usedCol[i] = usedDiag1[d1] = usedDiag2[d2] = true;
			nQueen(row + 1);
			usedCol[i] = usedDiag1[d1] = usedDiag2[d2] = false;
		}
	}
}