//import java.util.Arrays;
//
//class Solution {
//    public int reductionOperations(int[] nums) {
//        Arrays.sort(nums); // Step 1: Sort the array
//        int operations = 0;
//        int count = 0; // Number of steps we need for each new larger number
//
//        // Step 2: Iterate through the sorted array
//        for (int i = 1; i < nums.length; i++) {
//            if (nums[i] != nums[i - 1]) { // A new distinct number appears
//                count = i; // How many smaller numbers exist before it
//            }
//            operations += count; // Accumulate total operations
//        }
//        return operations;
//    }
//
//    public static void main(String[] args) {
//        Solution sol = new Solution();
//
//        // Test cases
//        int[] nums1 = {5, 1, 3};
//        System.out.println("Output: " + sol.reductionOperations(nums1)); // Expected: 3
//
//        int[] nums2 = {1, 1, 1};
//        System.out.println("Output: " + sol.reductionOperations(nums2)); // Expected: 0
//
//        int[] nums3 = {1, 1, 2, 2, 3};
//        System.out.println("Output: " + sol.reductionOperations(nums3)); // Expected: 4
//
//        int[] nums4 = {10, 10, 10, 10, 1, 10, 10, 10, 10, 10};
//        System.out.println("Output: " + sol.reductionOperations(nums4)); // Expected: 9
//    }
//}
