/*
문제
정수 A를 B로 바꾸려고 한다. 가능한 연산은 다음과 같은 두 가지이다.
	*2를 곱한다.
	*1을 수의 가장 오른쪽에 추가한다.
A를 B로 바꾸는데 필요한 연산의 최솟값을 구해보자.

입력
첫째 줄에 A, B (1 ≤ A < B ≤ 109)가 주어진다.

출력
A를 B로 바꾸는데 필요한 연산의 최솟값에 1을 더한 값을 출력한다. 만들 수 없는 경우에는 -1을 출력한다.
 */
import java.util.*;
import java.io.*;

public class Main {

	public static long twoMultiple(long n) {
		return n * 2;
	}

	public static long attachOne(long n) {
		return (n*10) + 1;
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		long a = Long.parseLong(st.nextToken());
		long b = Long.parseLong(st.nextToken());

		// BFS를 사용
		Queue<List<Long>> q = new LinkedList<>();
		q.offer(new ArrayList<>(Arrays.asList(a, 0L)));

		while(!q.isEmpty()) {
			List<Long> now = q.poll();
			long num = now.get(0);
			long cnt = now.get(1);

			if (num == b) {
				System.out.println(cnt+1);
				return;
			}

			if (num > b) continue;

			q.offer(new ArrayList<>(Arrays.asList(twoMultiple(num), cnt + 1)));
			q.offer(new ArrayList<>(Arrays.asList(attachOne(num), cnt + 1)));
		}

		System.out.println(-1);
	}


}