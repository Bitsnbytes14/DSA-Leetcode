import java.util.*;

class Solution {
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;

        List<List<long[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        int[] indeg = new int[n];
        TreeSet<Long> filteredCosts = new TreeSet<>();

        for (int[] e : edges) {
            int u = e[0], v = e[1];
            long c = e[2];
            if (online[u] && online[v]) {
                adj.get(u).add(new long[]{v, c});
                indeg[v]++;
                filteredCosts.add(c);
            }
        }

        if (filteredCosts.isEmpty()) return -1;

        int[] indegCopy = indeg.clone();
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indegCopy[i] == 0) queue.add(i);
        int[] topo = new int[n];
        int idx = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topo[idx++] = u;
            for (long[] pair : adj.get(u)) {
                int v = (int) pair[0];
                indegCopy[v]--;
                if (indegCopy[v] == 0) queue.add(v);
            }
        }

        Long[] costsSorted = filteredCosts.toArray(new Long[0]);

        long lo = 0, hi = costsSorted.length - 1;
        long ans = -1;
        while (lo <= hi) {
            long mid = (lo + hi) / 2;
            long T = costsSorted[(int) mid];
            if (check(n, adj, topo, idx, T, k)) {
                ans = T;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return (int) ans;
    }

    private boolean check(int n, List<List<long[]>> adj, int[] topo, int topoLen, long T, long k) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        for (int i = 0; i < topoLen; i++) {
            int u = topo[i];
            if (dist[u] == Long.MAX_VALUE) continue;
            long du = dist[u];
            for (long[] pair : adj.get(u)) {
                int v = (int) pair[0];
                long c = pair[1];
                if (c >= T) {
                    long nd = du + c;
                    if (nd < dist[v]) dist[v] = nd;
                }
            }
        }

        return dist[n - 1] != Long.MAX_VALUE && dist[n - 1] <= k;
    }
}