import java.util.Arrays;

class Solution {

    private static final int[] E2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
    private static final int[] E3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
    private static final int[] E5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    private static final int[] E7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

    private static int cost(int a, int b, int c, int d) {
        int lim = Math.min(5, Math.min(a, b));
        int best = Integer.MAX_VALUE;
        for (int w = 0; w <= lim; w++) {
            int v = w + (a - w + 2) / 3 + (b - w + 1) / 2;
            if (v < best) best = v;
        }
        return best + c + d;
    }

    private static void fill(char[] out, int from, int m, int a, int b, int c, int d) {
        int k = cost(a, b, c, d);
        int pad = m - k;
        Arrays.fill(out, from, from + pad, '1');
        for (int i = pad; i < m; i++) {
            int left = m - i - 1;
            for (int g = 1; g <= 9; g++) {
                int na = a - E2[g]; if (na < 0) na = 0;
                int nb = b - E3[g]; if (nb < 0) nb = 0;
                int nc = c - E5[g]; if (nc < 0) nc = 0;
                int nd = d - E7[g]; if (nd < 0) nd = 0;
                if (cost(na, nb, nc, nd) <= left) {
                    out[from + i] = (char) ('0' + g);
                    a = na; b = nb; c = nc; d = nd;
                    break;
                }
            }
        }
    }

    public String smallestNumber(String num, long t) {
        int r2 = 0, r3 = 0, r5 = 0, r7 = 0;
        while (t % 2 == 0) { t /= 2; r2++; }
        while (t % 3 == 0) { t /= 3; r3++; }
        while (t % 5 == 0) { t /= 5; r5++; }
        while (t % 7 == 0) { t /= 7; r7++; }
        if (t != 1) return "-1";

        int n = num.length();
        char[] s = num.toCharArray();

        int limit = n;
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') { limit = i; break; }
        }

        int[] p2 = new int[limit + 1], p3 = new int[limit + 1];
        int[] p5 = new int[limit + 1], p7 = new int[limit + 1];
        for (int i = 0; i < limit; i++) {
            int g = s[i] - '0';
            p2[i + 1] = p2[i] + E2[g];
            p3[i + 1] = p3[i] + E3[g];
            p5[i + 1] = p5[i] + E5[g];
            p7[i + 1] = p7[i] + E7[g];
        }

        if (limit == n && p2[n] >= r2 && p3[n] >= r3 && p5[n] >= r5 && p7[n] >= r7) {
            return num;
        }

        if (cost(r2, r3, r5, r7) <= n) {
            char[] out = new char[n];
            for (int i = Math.min(limit, n - 1); i >= 0; i--) {
                int rest = n - i - 1;
                for (int g = s[i] - '0' + 1; g <= 9; g++) {
                    int a = Math.max(0, r2 - p2[i] - E2[g]);
                    int b = Math.max(0, r3 - p3[i] - E3[g]);
                    int c = Math.max(0, r5 - p5[i] - E5[g]);
                    int d = Math.max(0, r7 - p7[i] - E7[g]);
                    if (cost(a, b, c, d) <= rest) {
                        System.arraycopy(s, 0, out, 0, i);
                        out[i] = (char) ('0' + g);
                        fill(out, i + 1, rest, a, b, c, d);
                        return new String(out);
                    }
                }
            }
        }

        int L = Math.max(n + 1, cost(r2, r3, r5, r7));
        char[] res = new char[L];
        fill(res, 0, L, r2, r3, r5, r7);
        return new String(res);
    }
}