package generalmath;

import java.util.*;

public class Problem11005 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		long N = sc.nextInt();
		long B = sc.nextLong();
		long quotient = N;
		int i = 0;
		ArrayList<Integer> Narr = new ArrayList<Integer>();
		ArrayList<Character> resultarr = new ArrayList<Character>();

		
		while(true) {
			if(quotient < B) {
				Narr.add(i, (int)quotient);
				System.out.println(quotient);
				break;
			} else {
				Narr.add(i, (int)(quotient%B));
				i++;
				quotient = quotient/B;
				System.out.println(quotient);
			}
		}
		
		Collections.reverse(Narr);
		
		for(int j = 0; j < Narr.size(); j++) {
			int a = Narr.get(j);
			if(Narr.get(j) < 10) {
				resultarr.add(j, Character.forDigit(a , 10)); //Character.forDigit(변환할 int,진수) int to char
			} else {
				resultarr.add(j, (char)(a+55));
			}
		}
		
		StringBuilder sb = new StringBuilder(); //새로알게된 StringBuilder
        for (Character ch : resultarr) {
            sb.append(ch);
        }

        String result = sb.toString();
        System.out.println(result);  // Output: Hello
		
		sc.close();
	}

}
