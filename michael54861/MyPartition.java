import edu.gwu.algtest.*;
import edu.gwu.debug.*;
import edu.gwu.util.*;

public class MyPartition implements PartitionAlgorithm {

	public String getName() {
		return "michael5486's implementation of PartitionAlgorithm";
	}

	public void setPropertyExtractor (int algID, PropertyExtractor props) {

    }

    public int leftIncreasingPartition(int[] data, int left, int right) {
        /*System.out.println("Original Array: ");
        for (int k= 0; k < data.length; k++) {
            System.out.print(data[k]);
        }
        System.out.println("");*/

        int partitionValue = data[left];
        int partitionIndex = left;
        int i;
        for (i = left + 1; i <= right; i++) {
            if (data[i] > partitionValue) {
                continue;
            }
            int j = i;
            while (j > partitionIndex) {
                //swap values
                int temp = data[j - 1];
                data[j - 1] = data[j];
                data[j] = temp;
                j--;
            }
            partitionIndex++;

            /*System.out.println("Partitioned: " + i);
            for (int k= 0; k < data.length; k++) {
                System.out.print(data[k]);
            }
            System.out.println("");*/

        }
        return partitionIndex;
    }


    public int rightIncreasingPartition(int[] data, int left, int right) {
    	//leave this empty
    	return 0;
    }

    public static void main(String[] args) {
    	
    	MyPartition partition = new MyPartition();
    	int[] testArray = new int[8];
    	testArray[0] = 4;
    	testArray[1] = 3;
    	testArray[2] = 9;
    	testArray[3] = 2;
    	testArray[4] = 7;
    	testArray[5] = 6;
    	testArray[6] = 5;
    	testArray[7] = 1;
    	partition.leftIncreasingPartition(testArray, 0, 7);
    }
}