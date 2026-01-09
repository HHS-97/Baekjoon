/*
문제
KOI 부설 과학연구소에서는 많은 종류의 산성 용액과 알칼리성 용액을 보유하고 있다. 각 용액에는 그 용액의 특성을 나타내는 하나의 정수가 주어져있다.  산성 용액의 특성값은 1부터 1,000,000,000까지의 양의 정수로 나타내고, 알칼리성 용액의 특성값은 -1부터 -1,000,000,000까지의 음의 정수로 나타낸다.

같은 양의 세 가지 용액을 혼합한 용액의 특성값은 혼합에 사용된 각 용액의 특성값의 합으로 정의한다. 이 연구소에서는 같은 양의 세 가지 용액을 혼합하여 특성값이 0에 가장 가까운 용액을 만들려고 한다.

예를 들어, 주어진 용액들의 특성값이 [-2, 6, -97, -6, 98]인 경우에는 특성값이 -97와 -2인 용액과 특성값이 98인 용액을 혼합하면 특성값이 -1인 용액을 만들 수 있고, 이 용액이 특성값이 0에 가장 가까운 용액이다. 참고로, 세 종류의 알칼리성 용액만으로나 혹은 세 종류의 산성 용액만으로 특성값이 0에 가장 가까운 혼합 용액을 만드는 경우도 존재할 수 있다.

산성 용액과 알칼리성 용액이 주어졌을 때, 이 중 같은 양의 세 개의 서로 다른 용액을 혼합하여 특성값이 0에 가장 가까운 용액을 만들어내는 세 용액을 찾는 프로그램을 작성하시오.

입력
첫째 줄에는 전체 용액의 수 N이 입력된다. N은 3 이상 5,000 이하의 정수이다. 둘째 줄에는 용액의 특성값을 나타내는 N개의 정수가 빈칸을 사이에 두고 주어진다. 이 수들은 모두 -1,000,000,000 이상 1,000,000,000 이하이다. N개의 용액들의 특성값은 모두 다르고, 산성 용액만으로나 알칼리성 용액만으로 입력이 주어지는 경우도 있을 수 있다.

출력
첫째 줄에 특성값이 0에 가장 가까운 용액을 만들어내는 세 용액의 특성값을 출력한다. 출력해야하는 세 용액은 특성값의 오름차순으로 출력한다. 특성값이 0에 가장 가까운 용액을 만들어내는 경우가 두 개 이상일 경우에는 그 중 아무것이나 하나를 출력한다.
 */

import java.util.*;
import java.io.*;

public class Main {
	// 세개의 수를 합하면 나올 수 있는 최댓값
	static final long INF = 3_000_000_001L;
	public static void main(String[] args) throws IOException {
		/*
		투포인터를 이용해서 문제풀이
		세 개의 용액을 더해야하니까
		배열의 한 인덱스를 잡고 투포인터를 돌리며 세 용액의 합을 구한다.
		두 포인터는 l(left) r(right)로 잡고 l은 i+1번 인덱스 r은 N-1번 인덱스로 시작한다.
		l이 i+1인 이유는 결국 i가 0부터 1씩 커지니까 모든 경우의 수를 볼 수 있기 때문
		세 용액을 섞은 값을 할당하는 변수 sum이 0보다 작으면 l++ (수가 커지려면 l이 올라가야함)
		sum이 0보다 크면 r--을 해줘서 최적값을 구한다.
		현재 sum의 절대값이 minSum에 저장된 값의 절대값보다 작다면 minSum에 sum의 값을 할당해주고
		result 배열의 0, 1, 2번 인덱스를 현재 선택된 값들로 변경해준다.
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//전체 용액의 수 N
		int N = Integer.parseInt(st.nextToken());
		//용액의 특성값을 나타내는 N개의 정수가 빈칸을 사이에 두고 주어진다.
		List<Long> arr = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr.add(Long.parseLong(st.nextToken()));
		}

		Collections.sort(arr);

		//배열의 한 인덱스를 잡고 투포인터를 돌리며 세 용액의 합을 구한다.
		long minSum = INF;
		long[] result = new long[3];

		for (int i = 0; i < N-2; i++) {
			//두 포인터는 l(left) r(right)로 잡고 l은 i+1번 인덱스 r은 N-1번 인덱스로 시작한다.
			int l = i + 1;
			int r = N - 1;
			long sum = arr.get(i) + arr.get(l) + arr.get(r);

			//sum이 0이면 result에 값을 저장하고 루프 나가기
			if (sum == 0) {
				result[0] = arr.get(i);
				result[1] = arr.get(l);
				result[2] = arr.get(r);
				break;
			}

			while (l < r) {

				//현재 sum의 절대값이 minSum에 저장된 값의 절대값보다 작다면 minSum에 sum의 값을 할당해주고
				//result 배열의 0, 1, 2번 인덱스를 현재 선택된 값들로 변경해준다.
				if (Math.abs(sum) < Math.abs(minSum)) {
					minSum = sum;
					result[0] = arr.get(i);
					result[1] = arr.get(l);
					result[2] = arr.get(r);
				}

				//세 용액을 섞은 값을 할당하는 변수 sum이 0보다 작으면 l++ (수가 커지려면 l이 올라가야함)
				//sum이 0보다 크면 r--을 해줘서 최적값을 구한다.
				if (sum < 0) l++;
				else r--;

				sum = arr.get(i) + arr.get(l) + arr.get(r);
			}
		}

		System.out.println(result[0] + " " + result[1] + " " + result[2]);
	}
}