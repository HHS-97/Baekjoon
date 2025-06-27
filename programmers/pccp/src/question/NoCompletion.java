package question;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NoCompletion {

	public String solution(String[] participant, String[] completion) {
		String answer = "";
		Map<String, Integer> map = new ConcurrentHashMap<>();

		for (String com : completion) {
			if (map.getOrDefault(com, -1) == -1) {
				map.put(com, 1);
			} else {
				map.replace(com, map.get(com)+1);
			}
		}

		for (String part : participant) {
			if (map.getOrDefault(part, 0)-1 == -1) {
				answer = part;
				break;
			} else {
				map.replace(part, map.get(part)-1);
			}
		}

		return answer;
	}
}
