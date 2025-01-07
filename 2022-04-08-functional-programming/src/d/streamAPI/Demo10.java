package d.streamAPI;

import java.util.Arrays;
import java.util.List;

public class Demo10 {
	
	public static void main(String[] args) {

		List<Integer> ages = Arrays.asList(25, 32, 53, 80, 7, 19, 22);
		System.out.println(ages);

		// find maximum
		int max = ages.stream().max((a, b) -> a - b).orElseThrow();
		System.out.println("max is: " + max);

		// find minimum
		int min = ages.stream().min((a, b) -> a - b).orElseThrow();
		System.out.println("min is: " + min);
		
		// find summary
		int sum = ages.stream().reduce((e, acumulator) -> acumulator + e).orElse(-1);
		System.out.println("sum is: " + sum);

		
		// ===========================
		
		
		List<String> list = Arrays.asList("welcome", "to", "functional", "programming");
		String msg = list.stream().reduce((e, message) -> e.concat(" " + message)).orElse("ERROR");
		System.out.println(msg);
		
		
		
	}

}
