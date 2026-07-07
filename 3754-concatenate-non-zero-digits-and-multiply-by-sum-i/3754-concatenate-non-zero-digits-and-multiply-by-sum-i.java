class Solution {
    public long sumAndMultiply(int n) {

        String s = String.valueOf(n);

        StringBuilder sb = new StringBuilder();
        long sum = 0;

        for (char ch : s.toCharArray()) {
            if (ch != '0') {
                sb.append(ch);
                sum += ch - '0';
            }
        }

        if (sb.length() == 0) {
            return 0;
        }

        long x = Long.parseLong(sb.toString());

        return x * sum;
    }
}