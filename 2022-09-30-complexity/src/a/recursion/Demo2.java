package a.recursion;

public class Demo2 {
	
	public static void printOneToVal(int val) {
		if (val == 1) {
			System.out.println(val);
			return;
		} else {
			printOneToVal(val-1);
			System.out.println(val);
			return;
		}
	}
	
	public static void main(String[] args) {
		printOneToVal(5);
	}

}
