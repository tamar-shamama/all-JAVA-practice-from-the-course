package a.recursion;

public class Demo4 {
	
	// my solution
	public static void printAToB(int a, int b) {
		
		if (a < b) {
			System.out.println(a);
			a++;
			printAToB(a, b);
			
		} else if (a>b) {
			System.out.println(a);
			a--;
			printAToB(a, b);
			
		} else {
			System.out.println(b);
		}
	}
	
	
	
	// eldar's solution - assume a smaller than b
	public static void printAToB2(int a, int b) {
		
		if (a > b) {
			int t = a;
			a = b;
			b = t;
		}
		
		if (a == b) {
			System.out.println(a);
			
		} else {
			System.out.println(a);
			printAToB(a+1, b);
		}
	}
	
		
	public static void main(String[] args) {
		printAToB(5, -2);
		System.out.println("==========");
		printAToB2(5, -2);
	}

}
