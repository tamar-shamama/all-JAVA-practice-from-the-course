package a.runnables;

public class Runnable3 {
	
	public static void main(String[] args) {
		
		Thread t3 = new Thread(() -> {System.out.println("hello");});
		t3.start();
	}
}
