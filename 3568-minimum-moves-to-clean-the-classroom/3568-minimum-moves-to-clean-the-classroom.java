class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int sr = 0, sc = 0, litter = 0;

        int[][] id = new int[m][n];

        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(id[i], -1);
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);
                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    id[i][j] = litter++;
                }
            }
        }

        if (litter == 0) return 0;

        int masks = 1 << litter;
        int energyStates = energy + 1;
        int totalStates = m * n * masks * energyStates;

        boolean[] visited = new boolean[totalStates];
        int[] queue = new int[totalStates];

        int start = (((0 * m + sr) * n + sc) * energyStates + energy);

        queue[0] = start;
        visited[start] = true;

        int head = 0;
        int tail = 1;
        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {
            int size = tail - head;

            while (size-- > 0) {
                int state = queue[head++];

                int e = state % energyStates;
                state /= energyStates;

                int c = state % n;
                state /= n;

                int r = state % m;
                int mask = state / m;

                if (mask == masks - 1) {
                    return moves;
                }

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    char cell = classroom[nr].charAt(nc);

                    if (cell == 'X') {
                        continue;
                    }

                    int ne = e - 1;
                    int nm = mask;

                    if (cell == 'L') {
                        nm |= 1 << id[nr][nc];
                    }

                    if (cell == 'R') {
                        ne = energy;
                    }

                    if (nm == masks - 1) {
                        return moves + 1;
                    }

                    if (ne == 0) {
                        continue;
                    }

                    int next = (((nm * m + nr) * n + nc) * energyStates + ne);

                    if (!visited[next]) {
                        visited[next] = true;
                        queue[tail++] = next;
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}