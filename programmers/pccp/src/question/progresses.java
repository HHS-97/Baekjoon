package question;

import java.util.*;
import java.util.stream.Stream;

/*
문제 설명
프로그래머스 팀에서는 기능 개선 작업을 수행 중입니다. 각 기능은 진도가 100%일 때 서비스에 반영할 수 있습니다.

또, 각 기능의 개발속도는 모두 다르기 때문에 뒤에 있는 기능이 앞에 있는 기능보다 먼저 개발될 수 있고, 이때 뒤에 있는 기능은 앞에 있는 기능이 배포될 때 함께 배포됩니다.

먼저 배포되어야 하는 순서대로 작업의 진도가 적힌 정수 배열 progresses와 각 작업의 개발 속도가 적힌 정수 배열 speeds가 주어질 때 각 배포마다 몇 개의 기능이 배포되는지를 return 하도록 solution 함수를 완성하세요.

제한 사항
작업의 개수(progresses, speeds배열의 길이)는 100개 이하입니다.
작업 진도는 100 미만의 자연수입니다.
작업 속도는 100 이하의 자연수입니다.
배포는 하루에 한 번만 할 수 있으며, 하루의 끝에 이루어진다고 가정합니다. 예를 들어 진도율이 95%인 작업의 개발 속도가 하루에 4%라면 배포는 2일 뒤에 이루어집니다.
 */

public class progresses {
	public int[] solution(int[] progresses, int[] speeds) {
		Queue<Integer> proQueue = new LinkedList<>();
		Queue<Integer> speedsQueue = new LinkedList<>();
		List<Integer> answerList = new LinkedList<>();

		for (int i = 0; i < progresses.length; i++) {
			proQueue.add(progresses[i]);
			speedsQueue.add(speeds[i]);
		}

		// 큐를 이용해서 한바퀴씩 progresses를 돌리는데 앞의 기능이 100이고 뒤의 기능도 100이면 계속 remove하고
		// 앞의 기능이 100이 아니면 stopCount를 1로 해서 remove를 멈춘다.
		// remove한 횟수를 카운트해서 한번의 배포에서 remove한 작업의 개수를 answerList에 저장
		// progresses가 remove되면 speeds도 함께 remove되어야함

		while (!proQueue.isEmpty()) {
			int removeCount = 0;
			int stopCount = 0;

			for (int i = 0; i < proQueue.size(); i++) {
				int removeSpeedValue = speedsQueue.remove();
				int removeProValue = proQueue.remove()+removeSpeedValue;

				if (removeProValue >= 100 && stopCount == 0) {
					removeCount += 1;
				} else {
					stopCount = 1;
				}

				proQueue.add(removeProValue);
				speedsQueue.add(removeSpeedValue);
			}

			for (int j = 0; j < removeCount; j++) {
				proQueue.remove();
				speedsQueue.remove();
			}

			if (removeCount != 0) answerList.add(removeCount);
		}

		int[] answer = new int[answerList.size()];

		for (int k = 0; k < answerList.size(); k++) {
			answer[k] = answerList.get(k);
		}

		return answer;
	}
}
