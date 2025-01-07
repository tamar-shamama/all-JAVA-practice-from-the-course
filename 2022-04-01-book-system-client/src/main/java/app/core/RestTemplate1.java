package app.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplate1 {

	public static void main(String[] args) {
		
		
		/* instead of swagger, it is possible to test the web app directly from
		 * Eclipse, using the RestTemplate class. this class creates a http request and
		 * returns the respond, just like swagger
		 */
		
		

		RestTemplate restTemplate = new RestTemplate();
		String baseUri = "http://localhost:8080/api/book";  // uri is a partial url
		

		
		{ // CREAT - POST - add book
			try {
				Book book1 = new Book(0, "bbb", "bbb", LocalDate.of(2020, 01, 01));
				ResponseEntity<Integer> response = restTemplate.postForEntity(baseUri, book1, Integer.class);
				System.out.println("status: " + response.getStatusCode());
				System.out.println("body: " + response.getBody());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

//		
		{ // READ - GET - get a book
			try {
				Book book = restTemplate.getForObject(baseUri + "/1", Book.class);
				System.out.println(book);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		{ // READ - GET - get all book
			try {
				Book[] books = restTemplate.getForObject(baseUri, Book[].class);
//				System.out.println(Arrays.toString(books));
				// we can convert an array to list
				List<Book> list = Arrays.asList(books);
				for (Book book : list) {
					System.out.println(book);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		{ // update
			try {
				Book book = new Book(3, "bbb", "bbb", LocalDate.of(1985, 01, 01));
				restTemplate.put(baseUri, book);
				System.out.println(book + " updated");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		{ // delete
			try {
				restTemplate.delete(baseUri + "/3");
				System.out.println("deleted");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		{ // delete all
			try {
				restTemplate.delete(baseUri);
				System.out.println("deleted all");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		// get from google
		String google = restTemplate.getForObject("https://www.google.com", String.class);
		System.out.println(google);

		// get from web DICTIONARY
		String dictionaryUrl = "https://api.dictionaryapi.dev/api/v2/entries/en_US";
		
		ArrayList<?> resp = restTemplate.getForObject(dictionaryUrl + "/dog", ArrayList.class);
		Map<?, ?> map = (Map<?, ?>) resp.get(0);

		for (Object key : map.keySet()) {
			System.out.println(key + ": " + map.get(key));
		}


	}

}
