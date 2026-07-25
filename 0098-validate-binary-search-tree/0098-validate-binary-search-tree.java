/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, null, null);
    }
    
    private boolean validate(TreeNode node, Long lower, Long upper) {
        if (node == null) return true;
        
        if (lower != null && node.val <= lower) return false;
        if (upper != null && node.val >= upper) return false;
        
        return validate(node.left, lower, (long) node.val) &&
               validate(node.right, (long) node.val, upper);
    }
}