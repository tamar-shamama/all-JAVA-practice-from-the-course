package a.recursion;

public class Demo1 {
	
	public static void main(String[] args) {
		m(); // stack overflow!!
	}

	public static void m() {
		m();
	}
}
