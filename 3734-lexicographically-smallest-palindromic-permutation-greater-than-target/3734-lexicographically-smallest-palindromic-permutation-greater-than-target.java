class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        int odd = 0;
        int mid = -1;

        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 == 1) {
                odd++;
                mid = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        int h = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = cnt[i] / 2;
        }

        // 1) Try the exact match: H = target's first h characters.
        //    This is the smallest possible candidate, so try it first.
        //    Must be STRICTLY greater than target — if target itself is
        //    achievable, that's not a valid answer, so we fall through.
        {
            int[] rem = half.clone();
            StringBuilder left = new StringBuilder();
            boolean ok = true;

            for (int i = 0; i < h; i++) {
                int x = target.charAt(i) - 'a';

                if (rem[x] == 0) {
                    ok = false;
                    break;
                }

                rem[x]--;
                left.append((char) ('a' + x));
            }

            if (ok) {
                StringBuilder exact = new StringBuilder();
                exact.append(left);

                if (n % 2 == 1) {
                    exact.append((char) ('a' + mid));
                }

                for (int i = left.length() - 1; i >= 0; i--) {
                    exact.append(left.charAt(i));
                }

                String result = exact.toString();

                if (result.compareTo(target) > 0) {
                    return result;
                }
            }
        }

        // 2) Otherwise, find the smallest half H strictly greater than
        //    target's first h characters, preferring the longest matching
        //    prefix (rightmost pivot) first.
        for (int pivot = h - 1; pivot >= 0; pivot--) {
            int[] rem = half.clone();
            boolean ok = true;

            for (int i = 0; i < pivot; i++) {
                int x = target.charAt(i) - 'a';

                if (rem[x] == 0) {
                    ok = false;
                    break;
                }

                rem[x]--;
            }

            if (!ok) {
                continue;
            }

            int x = target.charAt(pivot) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (rem[c] == 0) {
                    continue;
                }

                rem[c]--;

                StringBuilder left = new StringBuilder();

                for (int i = 0; i < pivot; i++) {
                    left.append(target.charAt(i));
                }

                left.append((char) ('a' + c));

                for (int d = 0; d < 26; d++) {
                    while (rem[d] > 0) {
                        left.append((char) ('a' + d));
                        rem[d]--;
                    }
                }

                StringBuilder ans = new StringBuilder();
                ans.append(left);

                if (n % 2 == 1) {
                    ans.append((char) ('a' + mid));
                }

                for (int i = left.length() - 1; i >= 0; i--) {
                    ans.append(left.charAt(i));
                }

                return ans.toString();
            }
        }

        return "";
    }
}