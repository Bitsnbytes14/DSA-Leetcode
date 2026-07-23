class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        int N = 1;
        while (N <= n) N <<= 1;

        long[] f = new long[N];
        for (int x : nums) f[x] = 1;

        transform(f);
        for (int i = 0; i < N; i++) f[i] = f[i] * f[i] * f[i];
        transform(f);

        int count = 0;
        for (int i = 0; i < N; i++) {
            if (f[i] / N != 0) count++;
        }
        return count;
    }

    private void transform(long[] a) {
        int len = a.length;
        for (int size = 1; size < len; size <<= 1) {
            for (int i = 0; i < len; i += size << 1) {
                for (int j = i; j < i + size; j++) {
                    long u = a[j];
                    long v = a[j + size];
                    a[j] = u + v;
                    a[j + size] = u - v;
                }
            }
        }
    }
}