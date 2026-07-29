class Solution {
    static final long LIMIT = 1000001L;
    List<Integer> primes = new ArrayList<>();

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        String mid = "";
        int len = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            len += half[i];
            if ((freq[i] & 1) == 1) mid = String.valueOf((char) ('a' + i));
        }

        sieve(len);

        if (countWays(half, len) < k) return "";

        StringBuilder left = new StringBuilder();

        while (len > 0) {
            for (int c = 0; c < 26; c++) {
                if (half[c] == 0) continue;

                half[c]--;
                long ways = countWays(half, len - 1);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    len--;
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder right = new StringBuilder(left).reverse();
        return left.toString() + mid + right.toString();
    }

    private void sieve(int n) {
        boolean[] comp = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            if (!comp[i]) {
                primes.add(i);
                if ((long) i * i <= n) {
                    for (int j = i * i; j <= n; j += i) comp[j] = true;
                }
            }
        }
    }

    private long countWays(int[] cnt, int total) {
        int[] exp = new int[primes.size()];

        addFact(exp, total, 1);

        for (int x : cnt) {
            addFact(exp, x, -1);
        }

        long res = 1;

        for (int i = 0; i < primes.size(); i++) {
            int p = primes.get(i);
            int e = exp[i];
            while (e-- > 0) {
                if (res > LIMIT / p) return LIMIT;
                res *= p;
            }
        }

        return Math.min(res, LIMIT);
    }

    private void addFact(int[] exp, int n, int sign) {
        for (int i = 0; i < primes.size(); i++) {
            int p = primes.get(i);
            if (p > n) break;
            int t = n;
            while (t > 0) {
                t /= p;
                exp[i] += sign * t;
            }
        }
    }
}