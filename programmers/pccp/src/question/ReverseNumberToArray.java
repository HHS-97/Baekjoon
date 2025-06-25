package question;

import java.util.*;

/*
	자연수 뒤집어 배열로 만들기
	문제 설명
	자연수 n을 뒤집어 각 자리 숫자를 원소로 가지는 배열 형태로 리턴해주세요. 예를들어 n이 12345이면 [5,4,3,2,1]을 리턴합니다.

	제한 조건
	n은 10,000,000,000이하인 자연수입니다.
	입출력 예
	n	return
	12345	[5,4,3,2,1]
 */


public class ReverseNumberToArray {
	public List<Integer> solution(long n) {
		List<Integer> answer = new ArrayList<>();

		String number = String.valueOf(n);

		for (int i = number.length() - 1; i >= 0; i--) {
			int num = Integer.parseInt(String.valueOf(number.charAt(i) - '0'));
			answer.add(num);
		}

		return answer;
	}
}
