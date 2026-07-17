class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxVal = 0;
        for (int x : nums) maxVal = Math.max(maxVal, x);

        int[] freq = new int[maxVal + 1];
        for (int x : nums) freq[x]++;

        int[] cnt = new int[maxVal + 1];
        for (int g = 1; g <= maxVal; g++) {
            long c = 0;
            for (int m = g; m <= maxVal; m += g) c += freq[m];
            cnt[g] = (int) c;
        }

        long[] pairsAtLeast = new long[maxVal + 1];
        for (int g = 1; g <= maxVal; g++) {
            long c = cnt[g];
            pairsAtLeast[g] = c * (c - 1) / 2;
        }

        long[] exact = new long[maxVal + 1];
        for (int g = maxVal; g >= 1; g--) {
            long sum = 0;
            for (int m = 2 * g; m <= maxVal; m += g) sum += exact[m];
            exact[g] = pairsAtLeast[g] - sum;
        }

        long[] cumulative = new long[maxVal + 1];
        for (int g = 1; g <= maxVal; g++) cumulative[g] = cumulative[g - 1] + exact[g];

        int[] answer = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long q = queries[i];
            int lo = 1, hi = maxVal, ans = maxVal;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (cumulative[mid] > q) {
                    ans = mid;
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            answer[i] = ans;
        }
        return answer;
    }
}