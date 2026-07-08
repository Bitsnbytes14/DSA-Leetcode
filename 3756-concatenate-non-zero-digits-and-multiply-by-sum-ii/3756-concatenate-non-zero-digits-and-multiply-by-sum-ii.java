class Solution {
    public int[] sumAndMultiply(String s, int[][] queries) {
        final int MOD = 1_000_000_007;
        int m = s.length();
        
        List<Integer> idxList = new ArrayList<>();
        List<Integer> digList = new ArrayList<>();
        
        for (int i = 0; i < m; i++) {
            char c = s.charAt(i);
            if (c != '0') {
                idxList.add(i);
                digList.add(c - '0');
            }
        }
        
        int k = idxList.size();
        int[] idxs = new int[k];
        long[] V = new long[k + 1];
        long[] S = new long[k + 1];
        long[] pow10 = new long[k + 1];
        pow10[0] = 1;
        
        for (int i = 0; i < k; i++) {
            idxs[i] = idxList.get(i);
        }
        for (int i = 1; i <= k; i++) {
            int d = digList.get(i - 1);
            V[i] = (V[i - 1] * 10 + d) % MOD;
            S[i] = S[i - 1] + d;
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }
        
        int q = queries.length;
        int[] ans = new int[q];
        
        for (int i = 0; i < q; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            
            int posA = lowerBound(idxs, l);
            int posB = upperBound(idxs, r) - 1;
            
            if (posA > posB) {
                ans[i] = 0;
                continue;
            }
            
            int a = posA + 1;
            int b = posB + 1;
            int cnt = b - a + 1;
            
            long x = ((V[b] - V[a - 1] * pow10[cnt]) % MOD + MOD) % MOD;
            long totalSum = S[b] - S[a - 1];
            
            ans[i] = (int) ((x * totalSum) % MOD);
        }
        
        return ans;
    }
    
    private int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
    
    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}