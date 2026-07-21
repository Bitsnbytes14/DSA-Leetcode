class Solution {
    public List<String> restoreIpAddresses(String s) {

        List<String> ans = new ArrayList<>();

        backtrack(s, 0, 0, new StringBuilder(), ans);

        return ans;
    }

    private void backtrack(String s, int index, int parts,
                           StringBuilder current,
                           List<String> ans) {

        if (parts == 4 && index == s.length()) {
            ans.add(current.substring(0, current.length() - 1));
            return;
        }

        if (parts == 4 || index == s.length()) {
            return;
        }

        for (int len = 1; len <= 3 && index + len <= s.length(); len++) {

            String segment = s.substring(index, index + len);

            if (segment.length() > 1 && segment.charAt(0) == '0') {
                break;
            }

            int value = Integer.parseInt(segment);

            if (value > 255) {
                continue;
            }

            int oldLength = current.length();

            current.append(segment).append('.');

            backtrack(s, index + len, parts + 1, current, ans);

            current.setLength(oldLength);
        }
    }
}