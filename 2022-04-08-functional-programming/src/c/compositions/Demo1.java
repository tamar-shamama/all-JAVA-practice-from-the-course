package c.compositions;

import java.util.function.Predicate;

public class Demo1 {

	public static void main(String[] args) {

		// functional composition
		String string = "the subject is functional programming.";
		System.out.println(string);

		Predicate<String> startsWithT = s -> s.startsWith("t");
		Predicate<String> endsWithG = s -> s.endsWith("g");

		// compose the 2 predicates above
		Predicate<String> startsWithTAndEndsWithG = s -> startsWithT.test(s) && endsWithG.test(s);

		System.out.println(startsWithT.test(string));
		System.out.println(endsWithG.test(string));
		System.out.println(startsWithTAndEndsWithG.test(string));

	}

}
