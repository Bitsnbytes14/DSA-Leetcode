class Solution {
    public String smallestPalindrome(String s) {

        int[] count = new int[26];

        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        StringBuilder firstHalf = new StringBuilder();
        char middle = 0;

        for (int i = 0; i < 26; i++) {

            for (int j = 0; j < count[i] / 2; j++) {
                firstHalf.append((char) ('a' + i));
            }

            if (count[i] % 2 == 1) {
                middle = (char) ('a' + i);
            }
        }

        StringBuilder ans = new StringBuilder(firstHalf);

        if (middle != 0) {
            ans.append(middle);
        }

        ans.append(new StringBuilder(firstHalf).reverse());

        return ans.toString();
    }
}