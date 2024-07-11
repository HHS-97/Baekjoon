package Baekjoon;
import java.nio.charset.Charset;
import java.util.*;

public class Jinbup {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> Narr = new ArrayList<Integer>();		
		String N = sc.next();
		int B = sc.nextInt();
		int result = 0;
		
		for(int i = 0; i < N.length(); i++) {
			int temp = Character.getNumericValue(N.charAt(i));
			int ascN = temp - 55;
			if(temp != -1) {
				Narr.add(i, Character.getNumericValue(N.charAt(i)));
			} else {
				Narr.add(i, ascN);
			}
			if(B - ascN < 0) {
				System.out.println("36진법 이상은 안됨");
				System.exit(ascN);
			}
		}

		Collections.reverse(Narr);
		
		for(int k = 0; k < N.length(); k++) {
			result += (int) (Narr.get(k) * Math.pow(B, k));
		}
		
		System.out.println(result);
	}

}
