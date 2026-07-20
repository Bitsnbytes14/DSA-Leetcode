class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        k %= total;

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int index = (i * n + j - k + total) % total;
                row.add(grid[index / n][index % n]);
            }

            result.add(row);
        }

        return result;
    }
}