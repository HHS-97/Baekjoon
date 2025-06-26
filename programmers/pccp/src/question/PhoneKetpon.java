package question;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneKetpon {
	public int solution(int[] nums) {
		int answer = 0;
		Map<Integer, Integer> map = new ConcurrentHashMap<>();
		List<Integer> keys = new ArrayList<>();

		for (int n : nums) {
			if (map.getOrDefault(n, -1) == -1) {
				map.put(n, 1);
				keys.add(n);
			} else {
				map.replace(n, map.get(n) + 1);
			}
		}

		int maxSelect = nums.length/2;

		if (maxSelect <= keys.size()) {
			answer = maxSelect;
		} else {
			answer = keys.size();
		}

		return answer;
	}
}
