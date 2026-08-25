class Solution {
    public int missingMultiple(int[] nums, int k) {
        boolean[] present = new boolean[101];

        for (int num : nums) {
            if (num % k == 0) {
                present[num / k] = true;
            }
        }

        for (int i = 1; i <= 100; i++) {
            if (!present[i]) {
                return i * k;
            }
        }

        return 101 * k;
    }
}