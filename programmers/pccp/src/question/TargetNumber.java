package question;
import java.util.*;

/*
문제 설명
n개의 음이 아닌 정수들이 있습니다. 이 정수들을 순서를 바꾸지 않고 적절히 더하거나 빼서 타겟 넘버를 만들려고 합니다. 예를 들어 [1, 1, 1, 1, 1]로 숫자 3을 만들려면 다음 다섯 방법을 쓸 수 있습니다.

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3
사용할 수 있는 숫자가 담긴 배열 numbers, 타겟 넘버 target이 매개변수로 주어질 때 숫자를 적절히 더하고 빼서 타겟 넘버를 만드는 방법의 수를 return 하도록 solution 함수를 작성해주세요.

제한사항
주어지는 숫자의 개수는 2개 이상 20개 이하입니다.
각 숫자는 1 이상 50 이하인 자연수입니다.
타겟 넘버는 1 이상 1000 이하인 자연수입니다.
 */

public class TargetNumber {
	public int solution(int[] numbers, int target) {
		int answer = 0;
		Queue<List<Integer>> q = new LinkedList<>();
		List<Integer> numListPlus = new LinkedList<>();
		List<Integer> numListMinus = new LinkedList<>();

		numListPlus.add(numbers[0]);
		numListPlus.add(0);
		numListMinus.add(numbers[0] * -1);
		numListMinus.add(0);

		q.offer(numListPlus);
		q.offer(numListMinus);

		while(!q.isEmpty()) {
			List<Integer> n = q.poll();
			List<Integer> plus = new LinkedList<>();
			List<Integer> minus = new LinkedList<>();

			if (n.get(1) == (numbers.length-1)) {
				if (n.get(0) == target) answer++;
				continue;
			}

			plus.add(n.get(0) + numbers[n.get(1) + 1]);
			plus.add(n.get(1) + 1);
			minus.add(n.get(0) - numbers[n.get(1) + 1]);
			minus.add(n.get(1) + 1);

			q.offer(plus);
			q.offer(minus);
		}

		return answer;
	}
}
