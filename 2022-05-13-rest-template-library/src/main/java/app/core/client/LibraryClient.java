package app.core.client;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import app.core.entities.Book;
import app.core.entities.Library;

public class LibraryClient {
	
	public static void main(String[] args) {
		
		System.out.println("=== client app ===");
		
		RestTemplate restTemplate = new RestTemplate();
		
		String baseUri = "http://localhost:8080/lib/";
		
		// get library
//		Library library1 = restTemplate.getForObject(baseUri + "/1", Library.class);
//		System.out.println(library1);
		
		//add library
//		Library library2 = new Library(0, "hifa", "hifa citi", null);
//		int id = restTemplate.postForObject(baseUri + "/add", library2, Integer.class);
//		System.out.println("library added: " + id);
		
		// add book
//		Book book = new Book(0,"444","rest template", "mishehu", LocalDate.of(1997, 01, 15), null);
//		int id = restTemplate.postForObject(baseUri + "/add/2", book, Integer.class);
//		System.out.println("book added: " + id);
		
		
		// get all books
		Book[] books = restTemplate.getForObject(baseUri + "/get/1", Book[].class);
		List<Book> bookList = Arrays.asList(books);
		System.out.println(bookList);
		
		
		
	}

}
