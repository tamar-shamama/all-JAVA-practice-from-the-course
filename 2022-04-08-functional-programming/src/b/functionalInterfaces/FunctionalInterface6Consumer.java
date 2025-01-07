package b.functionalInterfaces;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.function.Consumer;

public class FunctionalInterface6Consumer {
	
	public static void main(String[] args) {

		// example 1 - print an integer
		Consumer<Integer> printInt = x -> System.out.println(x);

		printInt.accept(8);


		// example 2 - save to file
		Consumer<Serializable> saveToFile = (obj) -> {
			File file = new File("file.obj");
			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));) {
				out.writeObject(obj);
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		String string = "aaa";
		saveToFile.accept(string);
		System.out.println("saved");
	}

}
