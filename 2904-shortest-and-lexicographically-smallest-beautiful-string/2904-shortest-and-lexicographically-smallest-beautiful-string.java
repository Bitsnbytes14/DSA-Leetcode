class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int left = 0;
        int ones = 0;
        int minLen = Integer.MAX_VALUE;
        String ans = "";

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                ones++;
            }

            while (ones == k) {
                int len = right - left + 1;

                if (len < minLen) {
                    minLen = len;
                    ans = s.substring(left, right + 1);
                } else if (len == minLen) {
                    String current = s.substring(left, right + 1);
                    if (ans.isEmpty() || current.compareTo(ans) < 0) {
                        ans = current;
                    }
                }

                if (s.charAt(left) == '1') {
                    ones--;
                }

                left++;
            }
        }

        return ans;
    }
}