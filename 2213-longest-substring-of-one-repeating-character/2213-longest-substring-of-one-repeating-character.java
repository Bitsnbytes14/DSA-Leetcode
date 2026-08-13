class Solution {
    class Node {
        char leftChar;
        char rightChar;

        int leftLen;
        int rightLen;
        int maxLen;
        int len;

        Node(char c) {
            leftChar = c;
            rightChar = c;
            leftLen = 1;
            rightLen = 1;
            maxLen = 1;
            len = 1;
        }
    }

    Node[] tree;

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {
        int n = s.length();

        tree = new Node[4 * n];

        build(1, 0, n - 1, s);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {
            update(
                1,
                0,
                n - 1,
                queryIndices[i],
                queryCharacters.charAt(i)
            );

            ans[i] = tree[1].maxLen;
        }

        return ans;
    }

    private void build(int node, int l, int r, String s) {
        if (l == r) {
            tree[node] = new Node(s.charAt(l));
            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid, s);
        build(node * 2 + 1, mid + 1, r, s);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private void update(
        int node,
        int l,
        int r,
        int index,
        char c
    ) {
        if (l == r) {
            tree[node] = new Node(c);
            return;
        }

        int mid = l + (r - l) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, c);
        } else {
            update(node * 2 + 1, mid + 1, r, index, c);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node(a.leftChar);

        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;
        res.len = a.len + b.len;

        // Prefix
        res.leftLen = a.leftLen;

        if (a.leftLen == a.len && a.rightChar == b.leftChar) {
            res.leftLen = a.len + b.leftLen;
        }

        // Suffix
        res.rightLen = b.rightLen;

        if (b.rightLen == b.len && a.rightChar == b.leftChar) {
            res.rightLen = b.len + a.rightLen;
        }

        // Best answer inside either half
        res.maxLen = Math.max(a.maxLen, b.maxLen);

        // Join suffix of left + prefix of right
        if (a.rightChar == b.leftChar) {
            res.maxLen = Math.max(
                res.maxLen,
                a.rightLen + b.leftLen
            );
        }

        return res;
    }
}