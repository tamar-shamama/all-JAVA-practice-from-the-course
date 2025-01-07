package d.streamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Demo07 {

	public static void main(String[] args) {
		

	
		List<String> names = Arrays.asList("Dan", "Ran", "Yoav", "ronen", "Lea");
		System.out.println(names);

		
		// get a letter from user
		// print a message stating if there is a name starting with that letter or not
		
		System.out.print("enter 1st character: ");
		
		Scanner scanner = new Scanner(System.in);
		char c = scanner.next().toLowerCase().charAt(0);  // in case user enter more than one letter...
		scanner.close();
		
		System.out.println("first character is " + c + ".");

		
		
		boolean found = names.stream()                               // turn the list into stream
				.map(e -> e.toLowerCase())                           // lower case
				.anyMatch(e -> e.startsWith(String.valueOf(c)));     // start with (doesn't accept char, only string) 
		
		if (found) {
			System.out.println("a name starting with " + c + " found.");
		} else {
			System.out.println("a name starting with " + c + " NOT found.");
		}

	}

}
