package b.functionalInterfaces;

import java.util.function.Function;

public class FunctionalInterface1Function {
	
	public static void main(String[] args) {


		Function<String, Integer> parser = str -> Integer.parseInt(str);

		String a = "250";
		String b = "32";
		int x = parser.apply(a);
		int y = parser.apply(b);

		System.out.println(x + " + " + y + " = " + (x + y));

	}

}
