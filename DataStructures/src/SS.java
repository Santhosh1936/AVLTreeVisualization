package kk;
import java.util.Arrays;
class SS {
    public int reductionOperations(int[] nums) {
        Arrays.sort(nums);
        int count=0;
        int x=0;
        int k=1;
        int ck=0;
        for(int i=0;i<nums.length-1;i++){
            if(nums[i+1]!=nums[i]){
                count=(nums.length-(i+1));
                ck=1;
                x+=count;
                //  k++;
            }
            //System.out.println(count);

            if(ck!=0){
                k++;
            }
            // k++;
        }
        return x;
    }

    public static void main(String[] args) {
        SS sol = new SS();

        // Test cases
        int[] nums1 = {5, 1, 3};
        System.out.println("Output: " + sol.reductionOperations(nums1)); // Expected: 3

        int[] nums2 = {1, 1, 1};
        System.out.println("Output: " + sol.reductionOperations(nums2)); // Expected: 0

        int[] nums3 = {1, 1, 2, 2, 3};
        System.out.println("Output: " + sol.reductionOperations(nums3)); // Expected: 4

        int[] nums4 = {10, 10, 10, 10, 1, 10, 10, 10, 10, 10};
        System.out.println("Output: " + sol.reductionOperations(nums4)); // Expected: 9
    }
}
