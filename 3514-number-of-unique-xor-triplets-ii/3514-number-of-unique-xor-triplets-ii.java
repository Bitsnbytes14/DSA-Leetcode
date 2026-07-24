class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int LIMIT = 2048; 
        boolean[] present = new boolean[LIMIT];
        for (int num : nums) {
            present[num] = true;
        }
        
        
        int[] vals;
        {
            int count = 0;
            for (int i = 0; i < LIMIT; i++) if (present[i]) count++;
            vals = new int[count];
            int idx = 0;
            for (int i = 0; i < LIMIT; i++) if (present[i]) vals[idx++] = i;
        }
        
        
        boolean[] s2 = new boolean[LIMIT];
        for (int a : vals) {
            for (int b : vals) {
                s2[a ^ b] = true;
            }
        }
        
        
        boolean[] e = new boolean[LIMIT];
        for (int s = 0; s < LIMIT; s++) {
            if (!s2[s]) continue;
            for (int v : vals) {
                e[s ^ v] = true;
            }
        }
        
        int result = 0;
        for (int i = 0; i < LIMIT; i++) {
            if (e[i]) result++;
        }
        return result;
    }
}