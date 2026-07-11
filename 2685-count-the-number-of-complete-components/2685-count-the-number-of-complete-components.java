class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        boolean[] visited = new boolean[n];
        int completeCount = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(i, adj, visited, component);

                int numNodes = component.size();
                int numEdges = 0;
                for (int node : component) {
                    numEdges += adj.get(node).size();
                }
                numEdges /= 2;

                if (numEdges == (numNodes * (numNodes - 1)) / 2) {
                    completeCount++;
                }
            }
        }

        return completeCount;
    }

    private void dfs(int node, List<List<Integer>> adj, boolean[] visited, List<Integer> component) {
        visited[node] = true;
        component.add(node);
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, adj, visited, component);
            }
        }
    }
}