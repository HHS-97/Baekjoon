/*
문제
N명의 학생들을 키 순서대로 줄을 세우려고 한다. 각 학생의 키를 직접 재서 정렬하면 간단하겠지만, 마땅한 방법이 없어서 두 학생의 키를 비교하는 방법을 사용하기로 하였다. 그나마도 모든 학생들을 다 비교해 본 것이 아니고, 일부 학생들의 키만을 비교해 보았다.

일부 학생들의 키를 비교한 결과가 주어졌을 때, 줄을 세우는 프로그램을 작성하시오.

입력
첫째 줄에 N(1 ≤ N ≤ 32,000), M(1 ≤ M ≤ 100,000)이 주어진다. M은 키를 비교한 횟수이다. 다음 M개의 줄에는 키를 비교한 두 학생의 번호 A, B가 주어진다. 이는 학생 A가 학생 B의 앞에 서야 한다는 의미이다.

학생들의 번호는 1번부터 N번이다.

출력
첫째 줄에 학생들을 앞에서부터 줄을 세운 결과를 출력한다. 답이 여러 가지인 경우에는 아무거나 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		//위상정렬을 이용해서 문제풀이
		//진입차수가 0인 것을 큐에 넣어서 순서를 만들어주면 앞에 서는 학생이 다 나와야 뒤에 서는 학생이 큐에 들어가니 답을 구할 수 있다.

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//N명의 학생들 (1 ≤ N ≤ 32,000)
		int N = Integer.parseInt(st.nextToken());
		//M은 키를 비교한 횟수 (1 ≤ M ≤ 100,000)
		int M = Integer.parseInt(st.nextToken());
		//진입차수 배열
		int[] entry = new int[N+1];
		//from -> to 관계 배열
		List<Integer>[] arr = new ArrayList[N+1];

		for (int i = 0; i <= N; i++) {
			arr[i] = new ArrayList<>();
		}

		//M개의 줄에는 키를 비교한 두 학생의 번호 A, B가 주어진다.
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());

			entry[B]++;

			arr[A].add(B);
		}

		ArrayDeque<Integer> q = new ArrayDeque<>();
		// 진입차수가 0인 노드들을 큐에 넣어준다.
		for (int i = 1; i <= N; i++) {
			if (entry[i] == 0) q.offer(i);
		}

		StringBuilder sb = new StringBuilder();

		while (!q.isEmpty()) {
			int cur = q.poll();
			//진입차수가 0이니 먼저 sb에 넣어준다.
			sb.append(cur).append(" ");

			for (int i : arr[cur]) {
				//진입차수를 1 줄여준다.
				entry[i]--;
				//진입차수가 0이 되면 큐에 넣어준다.
				if (entry[i] == 0) q.offer(i);
			}
		}

		String result = sb.toString().trim();
		System.out.println(result);
	}
}