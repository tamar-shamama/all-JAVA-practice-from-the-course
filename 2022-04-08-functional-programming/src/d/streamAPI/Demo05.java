package d.streamAPI;

import java.util.Arrays;
import java.util.List;

public class Demo05 {

	public static void main(String[] args) {
		

		List<Integer> list = Arrays.asList(2, 4, 5, 7, 7, 9, 2, 3, 5, 7);
		System.out.println(list);

		// is there any 5 in the list
		boolean any5 = list.stream().anyMatch((e) -> e == 5);

		System.out.println("is there a 5? " + any5);

		
		

	}

}
