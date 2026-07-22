class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        List<Integer> startsList = new ArrayList<>();
        List<Integer> endsList = new ArrayList<>();
        int i = 0;
        int totalOnes = 0;
        while (i < n) {
            if (s.charAt(i) == '1') {
                int j = i;
                while (j < n && s.charAt(j) == '1') j++;
                startsList.add(i);
                endsList.add(j - 1);
                totalOnes += (j - i);
                i = j;
            } else {
                i++;
            }
        }
        int m = startsList.size();
        int[] runStart = new int[m];
        int[] runEnd = new int[m];
        for (int k = 0; k < m; k++) { runStart[k] = startsList.get(k); runEnd[k] = endsList.get(k); }

        int[] Gleft = new int[m];
        int[] Gright = new int[m];
        for (int k = 0; k < m; k++) {
            int prevEnd = (k == 0) ? -1 : runEnd[k - 1];
            Gleft[k] = runStart[k] - (prevEnd + 1);
            int nextStart = (k == m - 1) ? n : runStart[k + 1];
            Gright[k] = nextStart - runEnd[k] - 1;
        }
        int[] fullGain = new int[m];
        for (int k = 0; k < m; k++) fullGain[k] = Gleft[k] + Gright[k];

        int[][] sparse = null;
        int[] log2 = new int[m + 1];
        if (m > 0) {
            for (int v = 2; v <= m; v++) log2[v] = log2[v / 2] + 1;
            int K = log2[m] + 1;
            sparse = new int[K][m];
            sparse[0] = fullGain.clone();
            for (int kk = 1; kk < K; kk++) {
                for (int idx = 0; idx + (1 << kk) <= m; idx++) {
                    sparse[kk][idx] = Math.max(sparse[kk - 1][idx], sparse[kk - 1][idx + (1 << (kk - 1))]);
                }
            }
        }

        List<Integer> answer = new ArrayList<>(queries.length);
        for (int[] query : queries) {
            int l = query[0], r = query[1];
            int gain = 0;
            if (m > 0) {
                int L_idx = upperBound(runStart, l);
                int R_idx = lowerBound(runEnd, r) - 1;
                if (L_idx <= R_idx && L_idx < m && R_idx >= 0) {
                    if (L_idx == R_idx) {
                        int k = L_idx;
                        gain = Math.min(runStart[k] - l, Gleft[k]) + Math.min(r - runEnd[k], Gright[k]);
                    } else {
                        int boundaryLeft = Math.min(runStart[L_idx] - l, Gleft[L_idx]) + Gright[L_idx];
                        int boundaryRight = Gleft[R_idx] + Math.min(r - runEnd[R_idx], Gright[R_idx]);
                        int mid = Integer.MIN_VALUE;
                        if (R_idx - L_idx >= 2) {
                            mid = rangeMax(sparse, log2, L_idx + 1, R_idx - 1);
                        }
                        gain = Math.max(boundaryLeft, Math.max(boundaryRight, mid));
                    }
                }
            }
            answer.add(totalOnes + Math.max(0, gain));
        }
        return answer;
    }

    private int upperBound(int[] arr, int val) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] <= val) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    private int lowerBound(int[] arr, int val) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) / 2;
            if (arr[mid] < val) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    private int rangeMax(int[][] sparse, int[] log2, int l, int r) {
        int k = log2[r - l + 1];
        return Math.max(sparse[k][l], sparse[k][r - (1 << k) + 1]);
    }
}