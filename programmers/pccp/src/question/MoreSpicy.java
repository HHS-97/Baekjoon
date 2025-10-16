package question;
import java.util.*;

public class MoreSpicy {
	public int solution(int[] scoville, int K) {
		Queue<Integer> scovHeap = new PriorityQueue<Integer> ();
		int answer = 0;

		for(int i=0; i < scoville.length; i++) {
			scovHeap.offer(scoville[i]);
		}

		// 가장 맵지 않은 음식의 지수가 K보다 크면 바로 끝
		// 그러니 처음 값과 두번째 값을 미리 꺼내놓자
		int minScov = scovHeap.poll();
		int secMinScov = scovHeap.poll();

		// 조건은 가장 낮은 지수가 k보다 큰지확인
		while (minScov < K) {
			scovHeap.offer(mixScov(minScov, secMinScov));
			answer++;

			minScov = scovHeap.poll();

			// 만약 더이상 빼올 지수가 없으면 루프 중단
			if (scovHeap.isEmpty()) break;

			// 그 다음 낮은 지수 꺼내오기
			secMinScov = scovHeap.poll();
		}

		// 만약 루프가 끝났는데 K보다 작으면 -1 반환
		if (minScov < K) answer = -1;

		return answer;
	}

	private int mixScov (int first, int second) {
		return first + (second * 2);
	}
}
