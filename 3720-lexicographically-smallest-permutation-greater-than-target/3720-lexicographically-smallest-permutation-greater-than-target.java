class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        int[] cur = cnt.clone();
        String ans = "";

        for (int i = 0; i < target.length(); i++) {
            int x = target.charAt(i) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (cur[c] > 0) {
                    cur[c]--;

                    StringBuilder sb = new StringBuilder();

                    for (int j = 0; j < i; j++) {
                        sb.append(target.charAt(j));
                    }

                    sb.append((char)('a' + c));

                    for (int d = 0; d < 26; d++) {
                        for (int j = 0; j < cur[d]; j++) {
                            sb.append((char)('a' + d));
                        }
                    }

                    ans = sb.toString();
                    cur[c]++;
                    break;
                }
            }

            if (cur[x] == 0) {
                break;
            }

            cur[x]--;
        }

        return ans;
    }
}