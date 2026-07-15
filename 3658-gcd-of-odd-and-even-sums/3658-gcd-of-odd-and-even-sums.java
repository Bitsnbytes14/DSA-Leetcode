class Solution {
    public int gcdOfOddEvenSums(int n) {
        long sumOdd = (long) n * n;
        long sumEven = (long) n * (n + 1);
        return (int) gcd(sumOdd, sumEven);
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}