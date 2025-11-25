//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.io.*;

/*
문제
N개의 자연수와 자연수 M이 주어졌을 때, 아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
N개의 자연수 중에서 M개를 고른 수열

입력
첫째 줄에 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

둘째 줄에 N개의 수가 주어진다. 입력으로 주어지는 수는 10,000보다 작거나 같은 자연수이다.

출력
한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다. 중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.

수열은 사전 순으로 증가하는 순서로 출력해야 한다.
 */

class NumData implements Comparable<NumData> {
	int data;
	boolean visit;

	NumData(int data) { this.data = data; }

	@Override
	public int compareTo(NumData o) { return this.data - o.data; }
}

public class Main {
	static List<NumData> list;
	static List<String> perm;
	static List<String> result = new LinkedList<>();

	static void permutation(int depth, int n, int m) throws IOException {
		if (depth == m) {
			String s = String.join(" ", perm);
			result.add(s);
			return;
		}

		int last = 0;

		for (int i=0; i<n; i++) {
			NumData now = list.get(i);
			if (now.visit) continue;
			if (last == now.data) continue;

			now.visit = true;
			last = now.data;
			perm.set(depth, String.valueOf(now.data));
			permutation(depth+1, n, m);
			now.visit = false;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// N개의 자연수 중에서 M개를 고른 수열
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		list = new ArrayList<>(n);
		perm = new ArrayList<>(m);

		st = new StringTokenizer(br.readLine());
		for (int i=0; i<n; i++) { list.add(new NumData(Integer.parseInt(st.nextToken()))); }
		for (int i=0; i<m; i++) { perm.add("0"); }
		Collections.sort(list);
		permutation(0, n, m);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (String s : result) { bw.write(s + "\n"); }

		bw.flush();
		bw.close();
	}
}