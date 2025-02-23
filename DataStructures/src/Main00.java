package  AVL;
import java.util.*;
import static java.lang.Math.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
class BSTNode {
    int data;
    int h;
    BSTNode left, right;
    public BSTNode(int x){
        this.data = x;
        this.h = 1;
        this.left = this.right = null;
    }
}
class Solution {

    // 1. where to insert   2. what to insert
    // returning new root
    BSTNode insertNode(BSTNode root, int x) {
        if (root == null) {
            return new BSTNode(x);
        }

        if (x < root.data) {
            root.left = insertNode(root.left, x);
        } else if (x > root.data) {
            root.right = insertNode(root.right, x);
        } else {
            // If x == root.data, it's a duplicate; ignore it
            System.out.println("Duplicate value " + x + " ignored.");
            return root; // No changes to the tree
        }

        updateHeight(root);

        // Check and rebalance the tree if necessary
        int balanceFactor = getBF(root);

        if (balanceFactor == 2) { // Left-heavy
            if (getBF(root.left) >= 0) { // LL
                return rightRotate(root);
            } else { // LR
                return LeftRight(root);
            }
        } else if (balanceFactor == -2) { // Right-heavy
            if (getBF(root.right) <= 0) { // RR
                return leftRotate(root);
            } else { // RL
                return RightLeft(root);
            }
        }

        return root; // Return the balanced root
    }

    BSTNode LeftRight(BSTNode root){
        root.left=leftRotate(root.left);
        return rightRotate(root);

    }
    BSTNode RightLeft(BSTNode root){
        root.right=rightRotate(root.right);
        return leftRotate(root);

    }
    BSTNode rightRotate(BSTNode root){
        BSTNode newRoot = root.left;
        BSTNode t1 = root.right,t2 = root.left.right;
        // root.left = t1
        root.left = t2;
        updateHeight(root);
        newRoot.right = root;
        updateHeight(newRoot);
        return newRoot;
    }
    BSTNode leftRotate(BSTNode root){
        BSTNode newRoot = root.right;
        BSTNode t1 = root.left, t2 = root.right.left;
        // root.left = t1
        root.right = t2;
        updateHeight(root);
        newRoot.left = root;
        updateHeight(newRoot);
        return newRoot;
    }
    //Method for deleting a node in AVL tree
    // This Method ensure the Balance of the AVL tree
    BSTNode deleteNode(BSTNode root, int x) {
        if (root == null) {
            return null; // Node not found
        }

        // Step 1: Perform standard BST deletion
        if (x < root.data) {
            root.left = deleteNode(root.left, x);
        } else if (x > root.data) {
            root.right = deleteNode(root.right, x);
        } else {
            // Node to be deleted found
            if (root.left == null || root.right == null) {
                // One child or no child
                root = (root.left != null) ? root.left : root.right;
            } else {
                // Two children: Find in-order successor (smallest in right subtree)
                BSTNode successor = getMinNode(root.right);
                root.data = successor.data; // Replace with successor
                root.right = deleteNode(root.right, successor.data); // Delete successor
            }
        }

        if (root == null) {
            return null; // Tree had one node
        }

        // Step 2: Update height
        updateHeight(root);

        // Step 3: Rebalance the tree
        int balance = getBF(root);

        // Left heavy
        if (balance == 2) {
            if (getBF(root.left) >= 0) { // LL
                return rightRotate(root);
            } else { // LR
                return LeftRight(root);
            }
        }

        // Right heavy
        if (balance == -2) {
            if (getBF(root.right) <= 0) { // RR
                return leftRotate(root);
            } else { // RL
                return RightLeft(root);
            }
        }

        return root; // Return the new root
    }

    BSTNode getMinNode(BSTNode root) {
        while (root.left != null) {
            root = root.left;
        }
        return root; // Smallest value in subtree
    }


    int getBF(BSTNode root){
        return (root.left==null ? 0 : root.left.h) -
                (root.right==null ? 0: root.right.h);
    }
    void updateHeight(BSTNode root){
        root.h = 1 + max(root.left==null ? 0 : root.left.h,
                root.right==null ? 0: root.right.h);
    }

    public List<List<Integer>> LevelOrserTraversal(BSTNode root) {
        List<List<Integer>> outer=new ArrayList<>();
        if(root==null){
            return outer;
        }
        Queue<BSTNode> q=new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            int s=q.size();
            List<Integer> inner=new ArrayList<>();
            for(int i=0;i<s;i++) {
                BSTNode r = q.poll();
                inner.add(r.data);
                if (r.left!=null){
                    q.add(r.left);
                }
                if(r.right!=null){
                    q.add(r.right);
                }

            }
            outer.add(inner);
        }
        return outer;
    }
    static   List<Integer> ans=new ArrayList<>();
    public List<Integer> Preorder(BSTNode root) {
        if(root==null){
            return ans;
        }
        ans.add(root.data);
        Preorder(root.left);
        Preorder(root.right);
        return ans;
    }
    static   List<Integer> post=new ArrayList<>();
    public List<Integer> Postorder(BSTNode root) {
        if(root==null){
            return post;
        }
        Postorder(root.left);
        Postorder(root.right);
        post.add(root.data);
        return post;
    }
    static   List<Integer> in=new ArrayList<>();
    public List<Integer> Inorder(BSTNode root) {
        if(root==null){
            return in;
        }
        Inorder(root.left);
        in.add(root.data);
        Inorder(root.right);
        return in;
    }

    public BSTNode searchNode(BSTNode root, int x) {
        while (root != null) {
            if (x < root.data) {
                root = root.left;  // Go to the left subtree
            } else if (x > root.data) {
                root = root.right;  // Go to the right subtree
            } else {
                return root;  // Found the node with value x
            }
        }
        return null;
    }
    // Method for inserting multiple values, separated by commas
    public BSTNode insertMultipleNodes(BSTNode root, String values) {
        String[] valueArray = values.split(",");  // Split the input string by commas

        for (String value : valueArray) {
            value = value.trim(); // Remove any leading/trailing spaces
            try {
                int num = Integer.parseInt(value); // Parse the value to an integer
                root = insertNode(root, num);  // Insert the value into the AVL tree
                System.out.println("Inserted: " + num);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value: '" + value + "'! Please enter valid integers.");
            }
        }
        return root;  // Return the updated root after all insertions
    }
    public int calculateHeight(BSTNode root) {
        if (root == null) {
            return 0; // The height of an empty tree is 0
        }
        // Recursively calculate the height of the left and right subtrees
        int leftHeight = calculateHeight(root.left);
        int rightHeight = calculateHeight(root.right);
        // Return the maximum height of the two subtrees, plus one for the current node
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
class Main00 {
    static Scanner sc = new Scanner(System.in);
    static Solution sol = new Solution();
    public static void main(String[] args) {
//        BSTNode root = null;
//        TreePrinter<BSTNode> printer = new TreePrinter<>(n -> "" + n.data, n -> n.left, n -> n.right);
//
//        System.out.println("AVL Tree Operations:\n1. Insert <num>\n2. Delete <num>\n3. Search <num>\n4. Traversal (Level/Pre/In/Post)\n5. Exit");
//
//        while (true) {
//            System.out.print("\nEnter command: ");
//            String input = sc.nextLine().trim();
//
//            if (input.startsWith("Insert")) {
//                int num = Integer.parseInt(input.substring(7));
//                root = sol.insertNode(root, num);
//                System.out.println("Inserted: " + num);
//            } else if (input.startsWith("Delete")) {
//                int num = Integer.parseInt(input.substring(7));
//                root = sol.deleteNode(root, num);
//                System.out.println("Deleted: " + num);
//            } else if (input.startsWith("Search")) {
//                int num = Integer.parseInt(input.substring(7));
//                System.out.println(sol.searchNode(root, num) != null ? "Node found: " + num : "Node not found: " + num);
//            } else if (input.equalsIgnoreCase("Traversal Level")) {
//                sol.LevelOrserTraversal(root);
//            } else if (input.equalsIgnoreCase("Traversal Pre")) {
//                System.out.print("Pre-Order Traversal: ");
//                sol.Preorder(root);
//                System.out.println();
//            } else if (input.equalsIgnoreCase("Traversal In")) {
//                System.out.print("In-Order Traversal: ");
//                sol.Inorder(root);
//                System.out.println();
//            } else if (input.equalsIgnoreCase("Traversal Post")) {
//                System.out.print("Post-Order Traversal: ");
//                sol.Postorder(root);
//                System.out.println();
//            } else if (input.equalsIgnoreCase("Exit")) {
//                System.out.println("Exiting AVL Tree Program.");
//                break;
//            } else {
//                System.out.println("Invalid command. Please try again.");
//            }
//            printer.printTree(root);
//        }
        BSTNode root = null; // declare an empty tree
        TreePrinter<BSTNode> printer = new TreePrinter<>(n->""+n.data, n->n.left, n->n.right);
        System.out.println("\n==============================");
        System.out.println("AVL Tree Operations Menu:");
        System.out.println("1. Insert a single value");
        System.out.println("2. Insert multiple values (comma-separated)");
        System.out.println("3. Delete a node");
        System.out.println("4. Search for a node");
        System.out.println("5. Display tree traversals");
        System.out.println("6. Calculate height of the tree");
        System.out.println("7. Get the balance factor of the tree");
        System.out.println("8. Display the current tree visually");
        System.out.println("9. Exit");
        System.out.print("Choose an option (1-9): ");

        // Display the menu options
        while (true) {

            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.print("Enter value to insert: ");
                    try {
                        int num = Integer.parseInt(sc.nextLine().trim());
                        root = sol.insertNode(root, num);
                        System.out.println("Inserted: " + num);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                    break;

                case "2":
                    System.out.print("Enter values to insert (comma-separated): ");
                    String values = sc.nextLine().trim();
                    if (!values.matches("(\\d+,\\s*)*\\d+")) {
                        System.out.println("Invalid input! Please provide a comma-separated list of integers.");
                        break;
                    }
                    root = sol.insertMultipleNodes(root, values);
                    break;

                case "3":
                    System.out.print("Enter value to delete: ");
                    try {
                        int num = Integer.parseInt(sc.nextLine().trim());
                        root = sol.deleteNode(root, num);
                        System.out.println((root != null) ? "Deleted: " + num : "Node not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                    break;
                case "4":
                    System.out.print("Enter value to search: ");
                    try {
                        int num = Integer.parseInt(sc.nextLine().trim());
                        BSTNode result = sol.searchNode(root, num);
                        System.out.println((result != null) ? "Node found: " + num : "Node not found.");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a valid integer.");
                    }
                    break;


                case "5":
                    System.out.println(root == null ? "The tree is empty." : "Level-Order Traversal: " + sol.LevelOrserTraversal(root));
                    break;

                case "6":
                    if (root == null) {
                        System.out.println("The tree is empty.");
                    } else {
                        System.out.println("Pre-Order Traversal: " + sol.Preorder(root));
                        System.out.println("In-Order Traversal: " + sol.Inorder(root));
                        System.out.println("Post-Order Traversal: " + sol.Postorder(root));
                    }
                    break;

                case "7":
                    // In-order traversal
//                    System.out.println("In-Order Traversal: " + sol.Inorder(root));
                    int treeHeight = sol.calculateHeight(root);
                    System.out.println("The height of the tree is: " + treeHeight);
                    break;

                case "8":
                    if (root == null) {
                        System.out.println("The tree is empty. Nothing to display.");
                    } else {
                        System.out.println("Current tree:");
                        printer.printTree(root);
                    }
                    break;


                case "9":
                    // Exit
                    System.out.println("Exiting AVL Tree Program. Goodbye!");
                    printer.printTree(root);  // Print final tree before exiting
                    return;  // Exit the program

                default:
                    System.out.println("Invalid choice! Please choose a valid option.");
                    break;
            }

//
//        while (sc.hasNext()) {
//            String input = sc.next().trim();
//           // Implement In-order, Pre-order, and Post-order traversal methods.
//
//
//            // Read input and trim whitespace
//            if(input.startsWith("le")){
//                System.out.println("Level order Traversal");
//                List<List<Integer>> Level=new ArrayList<>();
//                Level=sol.LevelOrserTraversal(root);
//                System.out.println(Level);
//            }
//            else if(input.startsWith("Search")){
//                try {
////                    int num = Integer.parseInt(input.split(" ")[1]);
//                    int num = Integer.parseInt(input.substring(6).trim()); // Extract number after "Search"
//                    BSTNode result = sol.searchNode(root, num);
//                    if (result != null) {
//                        System.out.println("Node " + num + " found in the tree.");
//                    } else {
//                        System.out.println("Node " + num + " not found in the tree.");
//                    }
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid search command.");
//                }
//
//            }
//
//            else if (input.startsWith("d")) { // Handle deletion
//                if (input.length() > 1) {
//                    try {
//                        // Extract number after 'd' and delete
//                        int num = Integer.parseInt(input.substring(1));
//                        root = sol.deleteNode(root, num);
//                        System.out.println("Deleted " + num + " from the tree.");
//                    } catch (NumberFormatException e) {
//                        System.out.println("Invalid delete command: '" + input + "'.");
//                    }
//                } else {
//                    System.out.println("Invalid command: 'd' should be followed by a number.");
//                }
//            }
//           else if(input.startsWith("Pre")){
//                System.out.println("Pre - Order");
//                List<Integer> preorder=sol.Preorder(root);
//                System.out.println(preorder);
//
//            }
//            else if(input.startsWith("Post")){
//                System.out.println("Post - Order");
//                List<Integer> postorder=sol.Postorder(root);
//                System.out.println(postorder);
//
//            }
//            else if(input.startsWith("In")){
//                System.out.println("In - Order");
//                List<Integer> inorder=sol.Inorder(root);
//                System.out.println(inorder);
//
//            }
////            else if (input.startsWith("InsertMultiple")) {
////                // Handle multiple insertions (comma-separated values)
////                String[] parts = input.split(" ", 2); // Split into command and value part
////                if (parts.length < 2) {
////                    System.out.println("Invalid command! Please provide values after 'InsertMultiple'.");
////                    continue;
////                }
////
////                String values = parts[1].trim(); // Extract comma-separated values
////                root = sol.insertMultipleNodes(root, values);  // Insert multiple values into the tree
////            }  // Insert multiple values
//            else { // Handle insertion
//                try {
//                    // Parse the input as an integer and insert
//                    int num = Integer.parseInt(input);
//                    root = sol.insertNode(root, num);
//                    System.out.println("Inserted " + num + " into the tree.");
//                } catch (NumberFormatException e) {
//                    System.out.println("Invalid input for insertion: '" + input + "'.");
//                }
//            }
//            //String input = sc.next();
////            if (input.startsWith("d")) {
////                // Delete operation: input format "d<num>" (e.g., "d15")
////                int num = Integer.parseInt(input.substring(1));
////                root = sol.deleteNode(root, num);
////            } else {
////                // Insert operation
////                int num = Integer.parseInt(input);
////                root = sol.insertNode(root, num);
////            }

            //root = sol.insertNode(root, sc.nextInt());
            printer.printTree(root);
        }
    }
}
class TreePrinter<T> {

    private Function<T, String> getLabel;
    private Function<T, T> getLeft;
    private Function<T, T> getRight;

    private PrintStream outStream = System.out;

    private boolean squareBranches = false;
    private boolean lrAgnostic = false;
    private int hspace = 2;
    private int tspace = 1;

    public TreePrinter(Function<T, String> getLabel, Function<T, T> getLeft, Function<T, T> getRight) {
        this.getLabel = getLabel;
        this.getLeft = getLeft;
        this.getRight = getRight;
    }

    public void setPrintStream(PrintStream outStream) {
        this.outStream = outStream;
    }

    public void setSquareBranches(boolean squareBranches) { this.squareBranches = squareBranches; }

    public void setLrAgnostic(boolean lrAgnostic) { this.lrAgnostic = lrAgnostic; }

    public void setHspace(int hspace) { this.hspace = hspace; }

    public void setTspace(int tspace) { this.hspace = tspace; }

    /*
        Prints ascii representation of binary tree.
        Parameter hspace is minimum number of spaces between adjacent node labels.
        Parameter squareBranches, when set to true, results in branches being printed with ASCII box
        drawing characters.
     */
    public void printTree(T root) {
        List<TreeLine> treeLines = buildTreeLines(root);
        printTreeLines(treeLines);
    }

    /*
        Prints ascii representations of multiple trees across page.
        Parameter hspace is minimum number of spaces between adjacent node labels in a tree.
        Parameter tspace is horizontal distance between trees, as well as number of blank lines
        between rows of trees.
        Parameter lineWidth is maximum width of output
        Parameter squareBranches, when set to true, results in branches being printed with ASCII box
        drawing characters.
     */
    public void printTrees(List<T> trees, int lineWidth) {
        List<List<TreeLine>> allTreeLines = new ArrayList<>();
        int[] treeWidths = new int[trees.size()];
        int[] minLeftOffsets = new int[trees.size()];
        int[] maxRightOffsets = new int[trees.size()];
        for (int i = 0; i < trees.size(); i++) {
            T treeNode = trees.get(i);
            List<TreeLine> treeLines = buildTreeLines(treeNode);
            allTreeLines.add(treeLines);
            minLeftOffsets[i] = minLeftOffset(treeLines);
            maxRightOffsets[i] = maxRightOffset(treeLines);
            treeWidths[i] = maxRightOffsets[i] - minLeftOffsets[i] + 1;
        }

        int nextTreeIndex = 0;
        while (nextTreeIndex < trees.size()) {
            // print a row of trees starting at nextTreeIndex

            // first figure range of trees we can print for next row
            int sumOfWidths = treeWidths[nextTreeIndex];
            int endTreeIndex = nextTreeIndex + 1;
            while (endTreeIndex < trees.size() && sumOfWidths + tspace + treeWidths[endTreeIndex] < lineWidth) {
                sumOfWidths += (tspace + treeWidths[endTreeIndex]);
                endTreeIndex++;
            }
            endTreeIndex--;

            // find max number of lines for tallest tree
            int maxLines = allTreeLines.stream().mapToInt(list -> list.size()).max().orElse(0);

            // print trees line by line
            for (int i = 0; i < maxLines; i++) {
                for (int j = nextTreeIndex; j <= endTreeIndex; j++) {
                    List<TreeLine> treeLines = allTreeLines.get(j);
                    if (i >= treeLines.size()) {
                        System.out.print(spaces(treeWidths[j]));
                    } else {
                        int leftSpaces = -(minLeftOffsets[j] - treeLines.get(i).leftOffset);
                        int rightSpaces = maxRightOffsets[j] - treeLines.get(i).rightOffset;
                        System.out.print(spaces(leftSpaces) + treeLines.get(i).line + spaces(rightSpaces));
                    }
                    if (j < endTreeIndex) System.out.print(spaces(tspace));
                }
                System.out.println();
            }

            for (int i = 0; i < tspace; i++) {
                System.out.println();
            }

            nextTreeIndex = endTreeIndex + 1;
        }
    }

    private void printTreeLines(List<TreeLine> treeLines) {
        if (treeLines.size() > 0) {
            int minLeftOffset = minLeftOffset(treeLines);
            int maxRightOffset = maxRightOffset(treeLines);
            for (TreeLine treeLine : treeLines) {
                int leftSpaces = -(minLeftOffset - treeLine.leftOffset);
                int rightSpaces = maxRightOffset - treeLine.rightOffset;
                outStream.println(spaces(leftSpaces) + treeLine.line + spaces(rightSpaces));
            }
        }
    }

    private List<TreeLine> buildTreeLines(T root) {
        if (root == null) return Collections.emptyList();
        else {
            String rootLabel = getLabel.apply(root);
            List<TreeLine> leftTreeLines = buildTreeLines(getLeft.apply(root));
            List<TreeLine> rightTreeLines = buildTreeLines(getRight.apply(root));

            int leftCount = leftTreeLines.size();
            int rightCount = rightTreeLines.size();
            int minCount = Math.min(leftCount, rightCount);
            int maxCount = Math.max(leftCount, rightCount);

            // The left and right subtree print representations have jagged edges, and we essentially we have to
            // figure out how close together we can bring the left and right roots so that the edges just meet on
            // some line.  Then we add hspace, and round up to next odd number.
            int maxRootSpacing = 0;
            for (int i = 0; i < minCount; i++) {
                int spacing = leftTreeLines.get(i).rightOffset - rightTreeLines.get(i).leftOffset;
                if (spacing > maxRootSpacing) maxRootSpacing = spacing;
            }
            int rootSpacing = maxRootSpacing + hspace;
            if (rootSpacing % 2 == 0) rootSpacing++;
            // rootSpacing is now the number of spaces between the roots of the two subtrees

            List<TreeLine> allTreeLines = new ArrayList<>();

            // strip ANSI escape codes to get length of rendered string. Fixes wrong padding when labels use ANSI escapes for colored nodes.
            String renderedRootLabel = rootLabel.replaceAll("\\e\\[[\\d;]*[^\\d;]", "");

            // add the root and the two branches leading to the subtrees

            allTreeLines.add(new TreeLine(rootLabel, -(renderedRootLabel.length() - 1) / 2, renderedRootLabel.length() / 2));

            // also calculate offset adjustments for left and right subtrees
            int leftTreeAdjust = 0;
            int rightTreeAdjust = 0;

            if (leftTreeLines.isEmpty()) {
                if (!rightTreeLines.isEmpty()) {
                    // there's a right subtree only
                    if (squareBranches) {
                        if (lrAgnostic) {
                            allTreeLines.add(new TreeLine("\u2502", 0, 0));
                        } else {
                            allTreeLines.add(new TreeLine("\u2514\u2510", 0, 1));
                            rightTreeAdjust = 1;
                        }
                    } else {
                        allTreeLines.add(new TreeLine("\\", 1, 1));
                        rightTreeAdjust = 2;
                    }
                }
            } else if (rightTreeLines.isEmpty()) {
                // there's a left subtree only
                if (squareBranches) {
                    if (lrAgnostic) {
                        allTreeLines.add(new TreeLine("\u2502", 0, 0));
                    } else {
                        allTreeLines.add(new TreeLine("\u250C\u2518", -1, 0));
                        leftTreeAdjust = -1;
                    }
                } else {
                    allTreeLines.add(new TreeLine("/", -1, -1));
                    leftTreeAdjust = -2;
                }
            } else {
                // there's a left and right subtree
                if (squareBranches) {
                    int adjust = (rootSpacing / 2) + 1;
                    String horizontal = String.join("", Collections.nCopies(rootSpacing / 2, "\u2500"));
                    String branch = "\u250C" + horizontal + "\u2534" + horizontal + "\u2510";
                    allTreeLines.add(new TreeLine(branch, -adjust, adjust));
                    rightTreeAdjust = adjust;
                    leftTreeAdjust = -adjust;
                } else {
                    if (rootSpacing == 1) {
                        allTreeLines.add(new TreeLine("/ \\", -1, 1));
                        rightTreeAdjust = 2;
                        leftTreeAdjust = -2;
                    } else {
                        for (int i = 1; i < rootSpacing; i += 2) {
                            String branches = "/" + spaces(i) + "\\";
                            allTreeLines.add(new TreeLine(branches, -((i + 1) / 2), (i + 1) / 2));
                        }
                        rightTreeAdjust = (rootSpacing / 2) + 1;
                        leftTreeAdjust = -((rootSpacing / 2) + 1);
                    }
                }
            }

            // now add joined lines of subtrees, with appropriate number of separating spaces, and adjusting offsets

            for (int i = 0; i < maxCount; i++) {
                TreeLine leftLine, rightLine;
                if (i >= leftTreeLines.size()) {
                    // nothing remaining on left subtree
                    rightLine = rightTreeLines.get(i);
                    rightLine.leftOffset += rightTreeAdjust;
                    rightLine.rightOffset += rightTreeAdjust;
                    allTreeLines.add(rightLine);
                } else if (i >= rightTreeLines.size()) {
                    // nothing remaining on right subtree
                    leftLine = leftTreeLines.get(i);
                    leftLine.leftOffset += leftTreeAdjust;
                    leftLine.rightOffset += leftTreeAdjust;
                    allTreeLines.add(leftLine);
                } else {
                    leftLine = leftTreeLines.get(i);
                    rightLine = rightTreeLines.get(i);
                    int adjustedRootSpacing = (rootSpacing == 1 ? (squareBranches ? 1 : 3) : rootSpacing);
                    TreeLine combined = new TreeLine(leftLine.line + spaces(adjustedRootSpacing - leftLine.rightOffset + rightLine.leftOffset) + rightLine.line,
                            leftLine.leftOffset + leftTreeAdjust, rightLine.rightOffset + rightTreeAdjust);
                    allTreeLines.add(combined);
                }
            }
            return allTreeLines;
        }
    }

    private static int minLeftOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(l -> l.leftOffset).min().orElse(0);
    }

    private static int maxRightOffset(List<TreeLine> treeLines) {
        return treeLines.stream().mapToInt(l -> l.rightOffset).max().orElse(0);
    }

    private static String spaces(int n) {
        return String.join("", Collections.nCopies(n, " "));
    }

    private static class TreeLine {
        String line;
        int leftOffset;
        int rightOffset;

        TreeLine(String line, int leftOffset, int rightOffset) {
            this.line = line;
            this.leftOffset = leftOffset;
            this.rightOffset = rightOffset;
        }
    }
}
//project-directory/
//        │
//        ├── src/
//        │   ├── main/
//        │   │   └── java/
//        │   │       └── your/package/name/
//        │   │           └── Main011.java
//│   │           └── Solution.java
//│   │           └── BSTNode.java
//│   │           └── TreePrinter.java
//│   │
//        │   └── test/
//        │       └── java/
//        │           └── your/package/name/
//        │               └── AVLTreeTest.java   <-- JUnit test class goes here