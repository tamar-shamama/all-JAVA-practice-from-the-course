package a.runnables;

public class Runnable1 {
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread( new MyRunnable());
		t1.start();
	}


	static class MyRunnable implements Runnable {

		@Override
		public void run() {
			System.out.println("hello");
		}
	}
	
}
