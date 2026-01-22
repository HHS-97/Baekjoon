/*
문제
2차원 좌표 평면 위의 두 선분 L1, L2가 주어졌을 때, 두 선분이 교차하는지 아닌지 구해보자. 한 선분의 끝 점이 다른 선분이나 끝 점 위에 있는 것도 교차하는 것이다.

L1의 양 끝 점은 (x1, y1), (x2, y2), L2의 양 끝 점은 (x3, y3), (x4, y4)이다.

입력
첫째 줄에 L1의 양 끝 점 x1, y1, x2, y2가, 둘째 줄에 L2의 양 끝 점 x3, y3, x4, y4가 주어진다.

출력
L1과 L2가 교차하면 1, 아니면 0을 출력한다.

제한
-1,000,000 ≤ x1, y1, x2, y2, x3, y3, x4, y4 ≤ 1,000,000
x1, y1, x2, y2, x3, y3, x4, y4는 정수
선분의 길이는 0보다 크다.
 */

import java.util.*;
import java.io.*;

public class Main {
	static class Point {
		long x, y;
		Point(long x, long y) { this.x = x; this.y = y; }
	}

	// ccw(a,b,c)는 a->b, a->c의 외적 부호
	// >0 반시계, <0 시계, =0 일직선
	//점 A → 점 B 로 가는 벡터 AB
	//AB=(b.x−a.x,b.y−a.y)
	static long ccw(Point a, Point b, Point c) {
		long abx = b.x - a.x;
		long aby = b.y - a.y;
		long acx = c.x - a.x;
		long acy = c.y - a.y;
		long cross = abx * acy - aby * acx;
		return Long.compare(cross, 0); // -1, 0, 1 로만 반환
	}

	// 일직선일 때 포함해서 겹치는지 체크
	static boolean overlap1D(long a, long b, long c, long d) {
		long min1 = Math.min(a, b), max1 = Math.max(a, b);
		long min2 = Math.min(c, d), max2 = Math.max(c, d);
		return Math.max(min1, min2) <= Math.min(max1, max2);
	}

	static boolean intersect(Point A, Point B, Point C, Point D) {
		long ab_c = ccw(A, B, C);
		long ab_d = ccw(A, B, D);
		long cd_a = ccw(C, D, A);
		long cd_b = ccw(C, D, B);

		//서로 다른 방향(또는 한 점이 선분 위 포함)
		if (ab_c * ab_d == 0 && cd_a * cd_b == 0) {
			// 완전 일직선인 경우 투영 범위가 겹치면 교차
			return overlap1D(A.x, B.x, C.x, D.x) && overlap1D(A.y, B.y, C.y, D.y);
		}

		return (ab_c * ab_d <= 0) && (cd_a * cd_b <= 0);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		/*
		두 선분 AB, CD가 교차하는지 CCW(외적)를 이용해 판정한다.

		세 점 A, B, C에 대해 ccw(A, B, C)는 벡터 AB와 AC의 외적 부호로 방향을 판단한다.

		cross = (Bx - Ax)*(Cy - Ay) - (By - Ay)*(Cx - Ax)

		cross > 0  : A → B → C 가 반시계 방향
		cross < 0  : A → B → C 가 시계 방향
		cross = 0  : A, B, C 는 일직선(collinear)

		즉, 점 C가 직선 AB의 어느 쪽에 있는지를 나타낸다.

		선분 교차의 핵심 원리
		두 선분 AB와 CD가 교차하려면 점 C와 D가 직선 AB의 서로 다른 편에 있거나, 적어도 하나가 AB 위에 있어야 한다.
		동시에 점 A와 B도 직선 CD의 서로 다른 편에 있거나, 적어도 하나가 CD 위에 있어야 한다.

		이를 CCW로 표현하면
		ccw(A, B, C) * ccw(A, B, D) <= 0
		ccw(C, D, A) * ccw(C, D, B) <= 0

		이 두 조건을 모두 만족하면 교차 가능성이 있다.

		일직선인 경우의 추가 처리
		만약 네 점이 모두 일직선이라면(ccw 값이 전부 0)

		방향 판정만으로는 교차 여부를 알 수 없으므로, 두 선분의 좌표 범위가 실제로 겹치는지를 확인해야 한다.
		x 좌표의 투영 범위가 겹치는지 y 좌표의 투영 범위가 겹치는지

		이 둘이 모두 겹치면 선분은 교차(겹침 또는 끝점 접촉)한다.
		*/

		long x1 = Long.parseLong(st.nextToken());
		long y1 = Long.parseLong(st.nextToken());
		long x2 = Long.parseLong(st.nextToken());
		long y2 = Long.parseLong(st.nextToken());

		st = new StringTokenizer(br.readLine());
		long x3 = Long.parseLong(st.nextToken());
		long y3 = Long.parseLong(st.nextToken());
		long x4 = Long.parseLong(st.nextToken());
		long y4 = Long.parseLong(st.nextToken());

		Point A = new Point(x1, y1);
		Point B = new Point(x2, y2);
		Point C = new Point(x3, y3);
		Point D = new Point(x4, y4);

		System.out.println(intersect(A, B, C, D) ? 1 : 0);
	}
}