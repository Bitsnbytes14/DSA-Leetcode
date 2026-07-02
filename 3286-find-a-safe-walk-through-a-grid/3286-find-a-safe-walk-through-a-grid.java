import java.util.*;

class Solution {
    public boolean findSafeWalk(List<List<Integer>> grid, int health) {

        int m = grid.size();
        int n = grid.get(0).size();

        int[][] best = new int[m][n];
        for (int[] row : best) {
            Arrays.fill(row, -1);
        }

        Queue<int[]> queue = new LinkedList<>();

        int startHealth = health - grid.get(0).get(0);

        if (startHealth <= 0) {
            return false;
        }

        queue.offer(new int[]{0, 0, startHealth});
        best[0][0] = startHealth;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int[] cur = queue.poll();

            int x = cur[0];
            int y = cur[1];
            int hp = cur[2];

            if (x == m - 1 && y == n - 1) {
                return true;
            }

            for (int k = 0; k < 4; k++) {

                int nx = x + dx[k];
                int ny = y + dy[k];

                if (nx < 0 || ny < 0 || nx >= m || ny >= n) {
                    continue;
                }

                int newHealth = hp - grid.get(nx).get(ny);

                if (newHealth <= 0) {
                    continue;
                }

                if (newHealth > best[nx][ny]) {
                    best[nx][ny] = newHealth;
                    queue.offer(new int[]{nx, ny, newHealth});
                }
            }
        }

        return false;
    }
}