package b.complexity;

public class Demo1_binarySearch {
	
	
	public static int linearSearch(int[] arr, int val) {
		
		int c = 0;
		
		for (int i = 0; i<arr.length; i++) {
			c++;
			if (arr[i] == val) {
				System.out.println(c + " iterations");
				return i;
			}
		}
		System.out.println(c + " iterations");
		return -1;
	}
	
	
	public static int binarySearch(int[] arr, int val) {
		
		int low = 0;
		int high = arr.length - 1;
		
		int c = 0;
		
		while (low < high) {
			
			c++;
			int mid = (low + high)/2;
			int midVal = arr[mid];
			
			if (midVal < val) {
				low = mid + 1;
				
			} else if (midVal > val) {
				high = mid - 1;
				
			} else {
				System.out.println(c + " iterations");
				return mid;
			}
			
		}
		System.out.println(c + " iterations");
		
		if (arr[high] == val) {
			return high;
			
		} else {
			return -1;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		
		int[] arr = {1, 5, 8, 12, 17, 55, 64, 109, 502};
		
		System.out.println("linear ======");
		int index1 = linearSearch(arr, 1);
		System.out.println(index1);
		System.out.println("=============");
		
		System.out.println("linear ======");
		int index2 = linearSearch(arr, 502);
		System.out.println(index2);
		System.out.println("=============");
		
		System.out.println("linear ======");
		int index3 = linearSearch(arr, 64);
		System.out.println(index3);
		System.out.println("=============");
		
		
		
		System.out.println("binary ======");
		int index4 = binarySearch(arr, 1);
		System.out.println(index4);
		System.out.println("=============");
		
		System.out.println("binary ======");
		int index5 = binarySearch(arr, 502);
		System.out.println(index5);
		System.out.println("=============");
		
		System.out.println("binary ======");
		int index6 = binarySearch(arr, 64);
		System.out.println(index6);
		System.out.println("=============");
		
		
	}

}
