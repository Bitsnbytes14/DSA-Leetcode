class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            int r = num % k;
            long[] next = new long[k];

            next[r]++;

            for (int j = 0; j < k; j++) {
                next[(j * r) % k] += dp[j];
            }

            dp = next;

            for (int j = 0; j < k; j++) {
                ans[j] += dp[j];
            }
        }

        return ans;
    }
}