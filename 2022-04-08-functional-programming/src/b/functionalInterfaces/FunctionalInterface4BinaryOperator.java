package b.functionalInterfaces;

import java.util.function.BinaryOperator;

public class FunctionalInterface4BinaryOperator {
	
	public static void main(String[] args) {

		BinaryOperator<Integer> plus = (a, b) -> a + b;

		int a = 4;
		int b = 7;

		int sum = plus.apply(a, b);
		System.out.println(sum);

	
	}

}
