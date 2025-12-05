/*
문제
N줄에 0 이상 9 이하의 숫자가 세 개씩 적혀 있다. 내려가기 게임을 하고 있는데, 이 게임은 첫 줄에서 시작해서 마지막 줄에서 끝나게 되는 놀이이다.

먼저 처음에 적혀 있는 세 개의 숫자 중에서 하나를 골라서 시작하게 된다. 그리고 다음 줄로 내려가는데, 다음 줄로 내려갈 때에는 다음과 같은 제약 조건이 있다. 바로 아래의 수로 넘어가거나, 아니면 바로 아래의 수와 붙어 있는 수로만 이동할 수 있다는 것이다. 이 제약 조건을 그림으로 나타내어 보면 다음과 같다.



별표는 현재 위치이고, 그 아랫 줄의 파란 동그라미는 원룡이가 다음 줄로 내려갈 수 있는 위치이며, 빨간 가위표는 원룡이가 내려갈 수 없는 위치가 된다. 숫자표가 주어져 있을 때, 얻을 수 있는 최대 점수, 최소 점수를 구하는 프로그램을 작성하시오. 점수는 원룡이가 위치한 곳의 수의 합이다.

입력
첫째 줄에 N(1 ≤ N ≤ 100,000)이 주어진다. 다음 N개의 줄에는 숫자가 세 개씩 주어진다. 숫자는 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 중의 하나가 된다.

출력
첫째 줄에 얻을 수 있는 최대 점수와 최소 점수를 띄어서 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int n = Integer.parseInt(st.nextToken());
		// 배열의 0은 왼쪽값, 1은 중간값, 2는 오른쪽값을 선택한 것
		// 이전 값에서 선택할 수 있는 값 중 제일 큰 값 또는 제일 작은 값을 선택해서 현재 값과 더한다.
		int[] maxDp = new int[3];
		int[] minDp = new int[3];

		st = new StringTokenizer(br.readLine());
		// 첫 줄 입력
		int left = Integer.parseInt(st.nextToken());
		int mid = Integer.parseInt(st.nextToken());
		int right = Integer.parseInt(st.nextToken());
		maxDp[0] = left;
		maxDp[1] = mid;
		maxDp[2] = right;
		minDp[0] = left;
		minDp[1] = mid;
		minDp[2] = right;

		for (int i = 1; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			left = Integer.parseInt(st.nextToken());
			mid = Integer.parseInt(st.nextToken());
			right = Integer.parseInt(st.nextToken());

			int[] newMax = new int[3];
			int[] newMin = new int[3];

			newMax[0] = Math.max(maxDp[0], maxDp[1]) + left;
			newMax[1] = Math.max(Math.max(maxDp[1], maxDp[0]), maxDp[2]) + mid;
			newMax[2] = Math.max(maxDp[2], maxDp[1]) + right;

			newMin[0] = Math.min(minDp[0], minDp[1]) + left;
			newMin[1] = Math.min(Math.min(minDp[0], minDp[1]), minDp[2]) + mid;
			newMin[2] = Math.min(minDp[1], minDp[2]) + right;

			maxDp = newMax;
			minDp = newMin;
		}

		int resultMax = Math.max(Math.max(maxDp[0], maxDp[1]), maxDp[2]);
		int resultMin = Math.min(Math.min(minDp[0], minDp[1]), minDp[2]);

		System.out.println(resultMax + " " + resultMin);
	}
}