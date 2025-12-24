/*
문제
2차원 평면상에 N(3 ≤ N ≤ 10,000)개의 점으로 이루어진 다각형이 있다. 이 다각형의 면적을 구하는 프로그램을 작성하시오.

입력
첫째 줄에 N이 주어진다. 다음 N개의 줄에는 다각형을 이루는 순서대로 N개의 점의 x, y좌표가 주어진다. 좌표값은 절댓값이 100,000을 넘지 않는 정수이다.

출력
첫째 줄에 면적을 출력한다. 면적을 출력할 때에는 소수점 아래 둘째 자리에서 반올림하여 첫째 자리까지 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		// 좌표가 다각형을 이루는 순서대로 주어지면 좌하 대각선 방향의 좌표 x, y 곱의 합과 우하 대각선 방향의 좌표 x, y 곱의 합을 뺀 후 반으로 나누면
		// 다각형의 넓이를 구할 수 있는 신발끈 이론을 사용
		// 마지막 점에서 처음 점으로 돌아가는 항도 반드시 더해야함
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());

		long postX = Integer.parseInt(st.nextToken());
		long postY = Integer.parseInt(st.nextToken());

		long firstX = postX;
		long firstY = postY;

		long leftdiag = 0;
		long rightdiag = 0;

		long x = postX;
		long y = postY;

		for (int i = 1; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());

			leftdiag += postX * y;
			rightdiag += postY * x;

			postX = x;
			postY = y;
		}

		leftdiag += postX * firstY;
		rightdiag += postY * firstX;

		double result = Math.abs(leftdiag - rightdiag) / 2.0;

		System.out.printf("%.1f\n", result);
	}
}