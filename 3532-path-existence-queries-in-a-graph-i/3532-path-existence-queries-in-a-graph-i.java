class Solution {
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        int[] groupId = new int[n];
        groupId[0] = 0;
        
        for (int i = 1; i < n; i++) {
            if (nums[i] - nums[i - 1] <= maxDiff) {
                groupId[i] = groupId[i - 1];
            } else {
                groupId[i] = groupId[i - 1] + 1;
            }
        }
        
        int q = queries.length;
        boolean[] ans = new boolean[q];
        
        for (int i = 0; i < q; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            ans[i] = groupId[u] == groupId[v];
        }
        
        return ans;
    }
}