package a.recursion;

public class Demo6 {
	
	public static int fibonnaci(int n) {
		
		if (n==0 || n==1) {
			return n;
		} else {
			return fibonnaci(n-1) + fibonnaci(n-2);
		}
	}
	
	
	
	public static int fibonnaciNonRecursive (int n) {
		
		
		int f2 = 1;
		int f3 = 2;
		
		for (int i=3; i<n; i++) {
			
			int temp = f3;
			f3 = f2 + f3;
			f2 = temp;
		}
		return f3;
	}
	
	
	public static void main(String[] args) {
		
		long ts1 = System.currentTimeMillis();
		int a = fibonnaci(30);
		long ts2 = System.currentTimeMillis();
		
		System.out.println(a + ": " + (ts2-ts1) + "ms");
		
		
		long ts3 = System.currentTimeMillis();
		int b = fibonnaciNonRecursive(30);
		long ts4 = System.currentTimeMillis();
		System.out.println(b + ": " + (ts4-ts3) + "ms");
		
	}

}
