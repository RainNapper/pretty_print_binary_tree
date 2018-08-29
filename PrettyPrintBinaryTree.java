import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PrettyPrintBinaryTree {

  private static class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;

    public TreeNode(int val) {
      this.val = val;
    }
  }

  public static void printTree(TreeNode root) {
    // Build a complete binary tree, where missing nodes are null.
    List<List<Integer>> rows = new ArrayList<>();
    List<TreeNode> nextLevel = new ArrayList<>();
    nextLevel.add(root);
    int size = 1;
    while (size > 0) {
      size = 0;
      List<Integer> row = new ArrayList<>();
      rows.add(row);

      List<TreeNode> nextLevelTmp = new ArrayList<>();
      for (TreeNode next : nextLevel) {
        if (next == null) {
          row.add(null);
          nextLevelTmp.add(null);
          nextLevelTmp.add(null);
          continue;
        }

        row.add(next.val);
        if (next.left != null) size++;
        if (next.right != null) size++;
        nextLevelTmp.add(next.left);
        nextLevelTmp.add(next.right);
      }

      nextLevel = nextLevelTmp;
    }

    // Ensure we have enough space for the leaf nodes.
    int minPartitionSize = 2;
    int maxSize = minPartitionSize * (int)Math.pow(2, rows.size());

    Integer[][] rowArrs = new Integer[rows.size()][maxSize];
    int rowNum = 0;
    for (List<Integer> row : rows) {
      Integer[] rowArr = rowArrs[rowNum];

      StringBuilder barText = new StringBuilder(maxSize);
      StringBuilder dataText = new StringBuilder(maxSize);

      // Each row is divided into parts based on how many nodes are at that depth.
      int divisions = (int)Math.pow(2, rowNum);
      int divisionSize = maxSize / divisions;
      int idx = 0;
      for (Integer val : row) {
        int offset = divisionSize * idx;

        // Build empty partition slot for the row
        char[] data = new char[divisionSize];
        Arrays.fill(data, ' ');
        char[] bar = new char[divisionSize];
        Arrays.fill(bar, ' ');

        if (val != null) {
          if (rowNum != 0) {
            // Add connecting pipe based on if it is a left or right child.
            if (idx % 2 == 0) {
              bar[divisionSize * 3 / 4] = '/';
            } else {
              bar[divisionSize / 4] = '\\';
            }
          }

          // Center the values text around the partitions center
          char[] valText = String.valueOf(val).toCharArray();
          int midpoint = divisionSize / 2;
          if (valText.length > divisionSize - 2) {
            throw new RuntimeException("Input "+String.valueOf(val)+" has too many digits to fit in partition");
          }
          System.arraycopy(valText, 0, data, midpoint - (valText.length / 2), valText.length);
        }
        barText.append(bar);
        dataText.append(data);
        idx++;
      }
      System.out.println(barText);
      System.out.println(dataText);
      rowNum++;
    }
  }
}
