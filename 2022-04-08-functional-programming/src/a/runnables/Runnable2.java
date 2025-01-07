package a.runnables;

public class Runnable2 {
	
	public static void main(String[] args) {
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				System.out.println("hello");
			}
		};
		
		
		Thread t2 = new Thread(runnable);
		t2.start();
	}
}
