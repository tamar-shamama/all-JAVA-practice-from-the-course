package a.recursion;

public class Demo3 {
	
	static int x = 1;
	
	static void printX() {
		System.out.println(x);
		if(x != 5) {
			x++;
			printX();
		}
	}
	
	
	public static void main(String[] args) {
		printX();
	}

}
