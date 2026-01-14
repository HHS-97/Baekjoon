/*
문제
이번 가을학기에 '문제 해결' 강의를 신청한 학생들은 텀 프로젝트를 수행해야 한다. 프로젝트 팀원 수에는 제한이 없다. 심지어 모든 학생들이 동일한 팀의 팀원인 경우와 같이 한 팀만 있을 수도 있다. 프로젝트 팀을 구성하기 위해, 모든 학생들은 프로젝트를 함께하고 싶은 학생을 선택해야 한다. (단, 단 한 명만 선택할 수 있다.) 혼자 하고 싶어하는 학생은 자기 자신을 선택하는 것도 가능하다.

학생들이(s1, s2, ..., sr)이라 할 때, r=1이고 s1이 s1을 선택하는 경우나, s1이 s2를 선택하고, s2가 s3를 선택하고,..., sr-1이 sr을 선택하고, sr이 s1을 선택하는 경우에만 한 팀이 될 수 있다.

예를 들어, 한 반에 7명의 학생이 있다고 하자. 학생들을 1번부터 7번으로 표현할 때, 선택의 결과는 다음과 같다.

1	2	3	4	5	6	7
3	1	3	7	3	4	6
위의 결과를 통해 (3)과 (4, 7, 6)이 팀을 이룰 수 있다. 1, 2, 5는 어느 팀에도 속하지 않는다.

주어진 선택의 결과를 보고 어느 프로젝트 팀에도 속하지 않는 학생들의 수를 계산하는 프로그램을 작성하라.

입력
첫째 줄에 테스트 케이스의 개수 T가 주어진다. 각 테스트 케이스의 첫 줄에는 학생의 수가 정수 n (2 ≤ n ≤ 100,000)으로 주어진다. 각 테스트 케이스의 둘째 줄에는 선택된 학생들의 번호가 주어진다. (모든 학생들은 1부터 n까지 번호가 부여된다.)

출력
각 테스트 케이스마다 한 줄에 출력하고, 각 줄에는 프로젝트 팀에 속하지 못한 학생들의 수를 나타내면 된다.
 */

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //테스트케이스 개수
        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            //학생의 수 정수 n (2 ≤ n ≤ 100,000)
            int n = Integer.parseInt(st.nextToken());
            int[] students = new int[n+1];

            //선택된 학생들의 번호
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j < n+1; j++) {
                int a = Integer.parseInt(st.nextToken());
                students[j] = a;
            }

            //한 방향 그래프 특성을 이용해 DFS로 찾아보자
            //students를 for문으로 1부터 n까지 돌리면서
            //아직 visited하지 않은 시작점에서만 탐색을 시작
            //현재 cur에서 다음 students[cur]을 계속 따라가며
            //처음 보는 노드면 visited=true로 찍고, path에 순서대로 저장
            //동시에 order[cur]에 이번 path에서의 방문 인덱스를 기록
            //탐색 중 이미 visited된 노드를 만나면 멈추고,
            //그 노드가 아직 done=false라면 이번 path 내부에서 생긴 사이클이므로
            //order[cur]부터 path 끝까지의 개수를 teamCount에 더함
            //마지막으로 이번 탐색에서 지나간 path의 모든 노드를 done=true로 바꿔
            //다른 시작점에서 중복 탐색 및 중복 카운트를 하지 않도록 함

            //처리완료했는지
            boolean[] done = new boolean[n+1];
            //전체에서 1번이라도 방문했는지
            boolean[] visited = new boolean[n+1];
            //이번 탐색에서 해당 노드가 path에서 몇 번째 인덱스였는지
            int[] order = new int[n + 1];
            //이번 탐색에서 지나간 노드들 저장
            int[] path = new int[n];
            // 팀에 속한 학생 수
            int teamCount = 0;

            for (int j = 1; j < n+1; j++) {
                //이미 방문했으면 넘기기
                if(visited[j]) continue;

                int cur = j;
                int size = 0;

                // cur를 따라가며 아직 방문 안 한 노드면 계속 진행
                while (!visited[cur]) {
                    visited[cur] = true;
                    order[cur] = size;
                    path[size++] = cur;
                    cur = students[cur];
                }

                // 이미 방문한 cur를 만났는데, 아직 done=false면
                // 이번 경로 내부에서 생긴 사이클 (
                if (!done[cur]) {
                    //order[cur]는 이번 탐색 경로에서 cur가 처음 등장한 위치이고,
                    //탐색 중 다시 cur를 만났다는 것은
                    //그 지점부터 현재까지가 정확히 하나의 사이클이기 때문에
                    //cycleStartIdx = order[cur]
                    int cycleStartIdx = order[cur];
                    teamCount += (size - cycleStartIdx);
                }

                // 이번 탐색에서 밟은 노드는 모두 처리 완료로 마감
                for (int k = 0; k < size; k++) {
                    done[path[k]] = true;
                }
            }

            System.out.println(n - teamCount);
        }
    }
}