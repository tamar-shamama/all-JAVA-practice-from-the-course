package app.core.controllers;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import app.core.entities.Book;
import app.core.entities.Library;
import app.core.services.LibrarySystemService;

@RestController
@RequestMapping("/lib")
public class LibrarySystemController {
	
	@Autowired
	private LibrarySystemService librarySystemService;
	
	
	@PostMapping("/add")
	public int addLibrary(@RequestBody Library library) {
		
		try {
			return this.librarySystemService.addLibrary(library);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	@PostMapping("/add/{libraryId}")
	public int addBook (@RequestBody Book book, @PathVariable int libraryId) {
		
		try {
			return this.librarySystemService.addBookToLibrary(book, libraryId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/{libraryId}")
	public List<Book> getLibraryBooks(@PathVariable int libraryId) {
		return this.librarySystemService.getAllBooksFromLibrary(libraryId);
	}
	
	@GetMapping("{id}")
	public Library getLibrary (@PathVariable int id) {
		return this.librarySystemService.getLibrary(id);
	}

}
