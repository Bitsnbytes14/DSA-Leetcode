class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        String t = "1" + s + "1";
        int n = t.length();
        List<int[]> blocks = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int j = i;
            char c = t.charAt(i);
            while (j < n && t.charAt(j) == c) j++;
            blocks.add(new int[]{c - '0', j - i});
            i = j;
        }
        int originalOnes = 0;
        for (char c : s.toCharArray()) if (c == '1') originalOnes++;
        int maxGain = 0;
        for (int k = 1; k < blocks.size() - 1; k++) {
            if (blocks.get(k)[0] == 1) {
                int gain = blocks.get(k - 1)[1] + blocks.get(k + 1)[1];
                if (gain > maxGain) maxGain = gain;
            }
        }
        return originalOnes + maxGain;
    }
}