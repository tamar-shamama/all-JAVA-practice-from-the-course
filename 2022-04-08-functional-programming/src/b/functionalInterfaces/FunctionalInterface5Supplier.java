package b.functionalInterfaces;

import java.util.function.Supplier;

public class FunctionalInterface5Supplier {
	
	public static void main(String[] args) {

		Supplier<Integer> rand1 = () -> (int) (Math.random() * 101);
		Supplier<Integer> rand2 = () -> (int) (Math.random() * 101) + 100;

		int x;

		x = rand1.get();
		System.out.println(x);

		x = rand2.get();
		System.out.println(x);
	}

}
