import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(nums[a], nums[b]));
        
        int[] sortedNums = new int[n];
        int[] mp = new int[n]; 
        for (int i = 0; i < n; i++) {
            sortedNums[i] = nums[order[i]];
            mp[order[i]] = i;
        }
        
        int maxLevel = 1;
        while ((1 << maxLevel) < n) maxLevel++;
        maxLevel += 1;
        
        int[][] jump = new int[n][maxLevel];
        
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (sortedNums[i] - sortedNums[j] > maxDiff) j++;
            jump[i][0] = j;
        }
        
        for (int lvl = 1; lvl < maxLevel; lvl++) {
            for (int i = 0; i < n; i++) {
                jump[i][lvl] = jump[jump[i][lvl - 1]][lvl - 1];
            }
        }
        
        int q = queries.length;
        int[] ans = new int[q];
        
        for (int i = 0; i < q; i++) {
            int a = mp[queries[i][0]];
            int b = mp[queries[i][1]];
            if (a < b) {
                int tmp = a; a = b; b = tmp;
            }
            
            if (a == b) {
                ans[i] = 0;
                continue;
            }
            
            int s = 0;
            for (int lvl = maxLevel - 1; lvl >= 0; lvl--) {
                if (jump[a][lvl] > b) {
                    a = jump[a][lvl];
                    s |= (1 << lvl);
                }
            }
            
            if (jump[a][0] > b) {
                ans[i] = -1;
            } else if (a != b) {
                ans[i] = s + 1;
            } else {
                ans[i] = s;
            }
        }
        
        return ans;
    }
}