/*
문제
수열 A가 주어졌을 때, 가장 긴 증가하는 부분 수열을 구하는 프로그램을 작성하시오.

예를 들어, 수열 A = {10, 20, 10, 30, 20, 50} 인 경우에 가장 긴 증가하는 부분 수열은 A = {10, 20, 10, 30, 20, 50} 이고, 길이는 4이다.

입력
첫째 줄에 수열 A의 크기 N (1 ≤ N ≤ 1,000,000)이 주어진다.

둘째 줄에는 수열 A를 이루고 있는 Ai가 주어진다. (1 ≤ Ai ≤ 1,000,000)

출력
첫째 줄에 수열 A의 가장 긴 증가하는 부분 수열의 길이를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {

	//x보다 크거나 같은 것이 처음으로 성립하는 인덱스를 찾는 메서드
	private static int lowerBound (int[] tails, int size, int x) {
		//탐색 구간의 왼쪽(left)
		int l = 0;
		//탐색 구간의 오른쪽(right)
		int r = size;

		while (l < r) {
			//탐색 구간의 가운데(middle)
			int m = (l + r) >>> 1;

			//tails[m]이 x보다 크거나 같으면 오른쪽 구간은 이제 탐색을 하지 않아도 되니까 r을 m으로 옮겨준다.
			if (tails[m] >= x) r = m;
			//그 외의 경우에는 오른쪽을 탐색해야하니까 l을 m+1로 옮겨준다.
			else l = m + 1;
		}
		//왼쪽에는 전부 x보다 작은 값만 있고
		//그 위치부터 처음으로 x 이상이 시작되는 경계이기 때문에 l을 리턴
		return l;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		//이분탐색을 이용해서 최장 증가 부분수열 문제풀이
		//수열을 루프를 돌림
		//현재 수열의 값을 x라고 하면
		//따로 정답을 구하기위해 만든 tails배열에 x보다 크거나 같은 것이 처음으로 성립하는 인덱스를 찾는다.
		//이때 이분탐색을 이용해서 인덱스를 찾는다.
		//이때 찾은 인덱스가 따로 지정해둔 size와 같으면 size에 1을 더해준다.
		//인덱스가 size와 같다는 것은 결국 x보다 크거나 같은것이 성립하는 인덱스가 없다는 것과 같으니 x를
		//tails의 마지막에 넣어주는 것이다. 그러니 부분수열의 길이가 길어지는 것이다.
		//이렇게 하면 수열의 순서를 무시하지만 최장 증가 부분수열의 길이는 정확하게 구할 수 있다.
		//결국 값을 순서 없이 덮어 씌워도 마지막에 추가되는 값은 결국 저장된 모든 값보다는 크기 때문이다.

		//수열 A의 크기 N (1 ≤ N ≤ 1,000,000)
		int A = Integer.parseInt(st.nextToken());
		//수열 A를 이루고 있는 Ai (1 ≤ Ai ≤ 1,000,000)
		int[] arr = new int[A];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < A; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int[] tails = new int[A];
		int size = 0;

		for (int x : arr) {
			//x보다 크거나 같은 것이 처음으로 성립하는 인덱스
			int idx = lowerBound(tails, size, x);
			tails[idx] = x;
			//이때 찾은 인덱스가 따로 지정해둔 size와 같으면 size에 1을 더해준다.
			if (idx == size) size++;
		}

		System.out.println(size);
	}
}