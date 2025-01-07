package d.streamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Demo09 {
	
	public static void main(String[] args) {

		List<Integer> ages = Arrays.asList(25, 32, 53, 80, 7, 19, 22);
		

		// fins any element
		
		{
			Optional<Integer> opt = ages.stream().findAny();
		
			if (opt.isPresent()) {
				int x = opt.get();
				System.out.println(x);
			} else {
				System.out.println("not found");
			}
		}
		
		
		{
			int x = ages.stream().findAny().orElseThrow();
			System.out.println(x);
		}
		
		
		// find first element
		
		{
			int x = ages.stream().findFirst().orElseThrow();
			System.out.println(x);
		}

		
		{  // (to clear the stream so exception will be thrown)
			int x = ages.stream().filter(e -> e > 100).findFirst().orElseThrow();
			System.out.println(x);
		}
		

		{
			int x = ages.stream().filter(e -> e > 100).findFirst().orElse(-1);
			System.out.println(x);
		}

		{
			int x = ages.stream().filter(e -> e > 100).findFirst().orElseThrow(() -> new MyException("stream empty"));
			System.out.println(x);
		}

	}

	static class MyException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public MyException(String message) {
			super(message);
		}
		
		
		
	}

}
