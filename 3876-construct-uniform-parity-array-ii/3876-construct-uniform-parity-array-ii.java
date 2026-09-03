class Solution {
    public boolean uniformArray(int[] nums1) {
        boolean hasOdd = false;
        boolean hasEven = false;
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int x : nums1) {
            if (x % 2 == 0) {
                hasEven = true;
                minEven = Math.min(minEven, x);
            } else {
                hasOdd = true;
                minOdd = Math.min(minOdd, x);
            }
        }

        if (!hasOdd) {
            return true;
        }

        if (!hasEven) {
            return true;
        }

        return minOdd < minEven;
    }
}