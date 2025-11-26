//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.*;
import java.io.*;

/*
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
	* N개의 자연수 중에서 M개를 고른 수열
	* 같은 수를 여러 번 골라도 된다.
	* 고른 수열은 비내림차순이어야 한다.
		* 길이가 K인 수열 A가 A1 ≤ A2 ≤ ... ≤ AK-1 ≤ AK를 만족하면, 비내림차순이라고 한다.


입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

둘째 줄에 N개의 수가 주어진다. 입력으로 주어지는 수는 10,000보다 작거나 같은 자연수이다.

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.
 */

public class Main {
	static List<String> perm;
	static List<String> result;
	static List<Integer> arr;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		arr = new ArrayList<>(n);
		perm = new ArrayList<>(m);
		result = new LinkedList<>();
		st = new StringTokenizer(br.readLine());

		for(int i = 0; i < n; i++) {
			arr.add(Integer.parseInt(st.nextToken()));
		}

		for (int i=0; i<m; i++) perm.add("0");

		Collections.sort(arr);

		permute(0, 0, n, m);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (String s : result) {
			bw.write(s + "\n");
		}

		bw.flush();
		bw.close();
	}

	private static void permute(int start, int depth, int n, int m) {
		if (depth == m) {
			String s = String.join(" ", perm);
			result.add(s);
			return;
		}

		int last = -1;
		for (int i=start; i<n; i++) {
			if (arr.get(i) == last) continue;

			last = arr.get(i);
			perm.set(depth, String.valueOf(arr.get(i)));
			permute(i, depth+1, n, m);
		}
	}
}