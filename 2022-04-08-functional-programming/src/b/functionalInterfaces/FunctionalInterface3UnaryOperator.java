package b.functionalInterfaces;

import java.util.function.UnaryOperator;

public class FunctionalInterface3UnaryOperator {
	
	public static void main(String[] args) {

		UnaryOperator<Integer> makeNegative = x -> -x;

		int x = 5;

		x = makeNegative.apply(x);
		System.out.println(x);
	}

}
