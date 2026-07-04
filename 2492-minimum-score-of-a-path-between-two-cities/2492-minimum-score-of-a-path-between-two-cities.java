class Solution {
    public int minScore(int n, int[][] roads) {

        List<int[]>[] graph = new ArrayList[n + 1];

        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] road : roads) {
            graph[road[0]].add(new int[]{road[1], road[2]});
            graph[road[1]].add(new int[]{road[0], road[2]});
        }

        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(1);
        visited[1] = true;

        int ans = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {

            int node = queue.poll();

            for (int[] edge : graph[node]) {

                ans = Math.min(ans, edge[1]);

                if (!visited[edge[0]]) {
                    visited[edge[0]] = true;
                    queue.offer(edge[0]);
                }
            }
        }

        return ans;
    }
}