package question;
import java.util.*;

/*
입력되는 단어가 순서대로 배치될 때 끝말잇기로 끝까지 이어지는지 확인하세요.
끝말잇기는 사용했던 단어가 다시 사용되면 안됩니다.
단어의 첫 글자는 앞 단어의 마지막 글자로 시작되어야 합니다.
(단, 첫 단어의 첫 글자는 확인하지 않습니다.)

입력1: ["tank", "kick", "know", "wheel", "land", "dream"]
출력1: true

단어의 연결이 모두 이어지고, 중복되는 단어가 없었습니다.

입력2: ["tank", "kick", "know", "wheel", "land", "dream", "mother", "robot", "tank"]
출력2: false

사용되었던 tank 단어가 다시 사용되었습니다.
 */

public class LastWord {
	public boolean solution(String[] words) {
		boolean answer = true;
		Set<String> setWords = new HashSet<>();

		for (int i = 0; i < words.length; i++) {
			if (i > 0) {
				String postWord = words[i-1];
				int postWordLength = postWord.length();
				String word = words[i];
				int wordLength = word.length();

				if (postWord.charAt(postWordLength-1) != word.charAt(0)) {
					answer = false;

					return answer;
				}
			}

			setWords.add(words[i]);
		}

		if (words.length != setWords.size()) answer = false;

		return answer;
	}
}
