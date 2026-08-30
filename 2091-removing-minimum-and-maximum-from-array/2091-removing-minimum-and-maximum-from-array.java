
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }

        int left = Math.max(minIdx, maxIdx) + 1;
        int right = n - Math.min(minIdx, maxIdx);
        int both = Math.min(minIdx, maxIdx) + 1 + n - Math.max(minIdx, maxIdx);

        return Math.min(left, Math.min(right, both));
    }
}
