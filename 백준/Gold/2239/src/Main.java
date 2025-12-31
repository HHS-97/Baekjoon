/*
문제
스도쿠는 매우 간단한 숫자 퍼즐이다. 9×9 크기의 보드가 있을 때, 각 행과 각 열, 그리고 9개의 3×3 크기의 보드에 1부터 9까지의 숫자가 중복 없이 나타나도록 보드를 채우면 된다. 예를 들어 다음을 보자.

위 그림은 참 잘도 스도쿠 퍼즐을 푼 경우이다. 각 행에 1부터 9까지의 숫자가 중복 없이 나오고, 각 열에 1부터 9까지의 숫자가 중복 없이 나오고, 각 3×3짜리 사각형(9개이며, 위에서 색깔로 표시되었다)에 1부터 9까지의 숫자가 중복 없이 나오기 때문이다.

하다 만 스도쿠 퍼즐이 주어졌을 때, 마저 끝내는 프로그램을 작성하시오.

입력
9개의 줄에 9개의 숫자로 보드가 입력된다. 아직 숫자가 채워지지 않은 칸에는 0이 주어진다.

출력
9개의 줄에 9개의 숫자로 답을 출력한다. 답이 여러 개 있다면 그 중 사전식으로 앞서는 것을 출력한다. 즉, 81자리의 수가 제일 작은 경우를 출력한다.
 */

import java.io.*;
import java.util.*;

public class Main {
	// 입력받은 스도쿠 테이블
	static int[][] sdoku = new int[9][9];
	// 3x3에 1~9중에서 어떤 숫자를 사용했는지
	static int[][] txt = new int[10][10];
	// 현재 행에서 사용한 숫자
	static int[][] rows = new int[9][10];
	// 현재 열에서 사용한 숫자
	static int[][] cols = new int[9][10];

	// 몇 번째 3x3인지 반환해주는 메서드
	private static int getSubgridIndex(int i, int j) {
		return (i / 3) * 3 + (j / 3) + 1;
	}

	// 3x3에 1~9중에서 어떤 숫자를 사용했는지 확인 메서드
	// 이미 사용한 숫자면 true, 사용하지 않은 숫자면 false 반환
	private static boolean inTxt (int n, int box) {
		if (txt[box][n] != 0) return true;
		else return false;
	}

	private static boolean inRow (int i, int n) {
		if (rows[i][n] != 0) return true;
		else return false;
	}

	private static boolean inCol (int j, int n) {
		if (cols[j][n] != 0) return true;
		else return false;
	}

	// 검증 메서드
	private static boolean isValid(int n, int box, int i, int j) {
		return !inTxt(n, box) &&
				!inRow(i, n) &&
				!inCol(j, n);
	}

	// 백트래킹
	private static boolean sdokuBt () {
		// 테이블 순회
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (sdoku[i][j] == 0) {
					int box = getSubgridIndex(i, j);
					// 숫자 1~9까지 넣어보기
					for (int k = 1; k < 10; k++) {
						if (isValid(k, box, i, j)) {
							sdoku[i][j] = k;
							rows[i][k] = 1;
							cols[j][k] = 1;
							txt[box][k] = 1;
							// 앞에 빈칸을 성공적으로 채웟으면 true
							// 성공적으로 채우지 못했으면 0으로 바꿈
							if (sdokuBt()) return true;
							sdoku[i][j] = 0;
							rows[i][k] = 0;
							cols[j][k] = 0;
							txt[box][k] = 0;
						}
					}

					// 전부 isValid를 통과하지 못하면
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		for (int i = 0; i < 9; i++) {
			String str = br.readLine();
			for (int j = 0; j < 9; j++) {
				char ch = str.charAt(j);
				sdoku[i][j] = ch - '0';
				if (sdoku[i][j] != 0) {
					int box = getSubgridIndex(i, j);
					rows[i][sdoku[i][j]] = 1;
					cols[j][sdoku[i][j]] = 1;
					txt[box][sdoku[i][j]] = 1;
				}
			}
		}

		sdokuBt();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				sb.append(sdoku[i][j]);
			}
			sb.append("\n");
		}

		System.out.println(sb.toString());
	}
}